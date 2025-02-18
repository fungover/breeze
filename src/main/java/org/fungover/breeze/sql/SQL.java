package org.fungover.breeze.sql;

import java.util.ArrayList;
import java.util.List;

public class SQL {

  public static SelectStep select() {
    return new SQLBuilder();
  }

  public interface SelectStep {
    SelectAfterStep allColumns();

    SelectStep distinct();

    SelectAfterStep columns(String... columns);
  }

  public interface SelectAfterStep {
    FromStep from(String table);
  }

  public interface FromStep {
    String build();

    WhereStep where();

    GroupByStep groupBy(String column);

    JoinStep join(String table, String condition);

    OrderByStep orderBy(String column, boolean ascending);

    LimitStep limit(int limit);
  }

  public interface JoinStep {
    FromStep inner();

    FromStep left();

    FromStep right();

    FromStep full();
  }

  public interface WhereStep {
    ConditionStep column(String column);
  }

  public interface ConditionStep {
    ConditionStep and(String column);

    ConditionStep or(String column);

    EqualStep equalTo(String query);

    EqualStep equalTo(boolean query);

    EqualStep equalTo(int query);

    EqualStep equalTo(double query);

    EqualStep equalTo(float query);

    ConditionStep between(int start, int end);

    ConditionStep like(String pattern);

    ConditionStep in(String... values);

    String build();
  }

  public interface EqualStep {
    String build();

    GroupByStep groupBy(String column);

    OrderByStep orderBy(String column, boolean ascending);

    LimitStep limit(int limit);

    HavingStep having(String condition);
  }

  public interface GroupByStep {
    String build();

    OrderByStep orderBy(String column, boolean ascending);

    HavingStep having(String condition);
  }

  public interface OrderByStep {
    String build();

    LimitStep limit(int limit);

    OrderByStep thenOrderBy(String column, boolean ascending);
  }

  public interface LimitStep {
    String build();

    OffsetStep offset(int offset);
  }

  public interface OffsetStep {
    String build();
  }

  public interface HavingStep {
    String build();
  }

  private static class SQLBuilder implements SelectStep, SelectAfterStep, FromStep, WhereStep, ConditionStep, EqualStep, GroupByStep, OrderByStep, LimitStep, OffsetStep, JoinStep, HavingStep {
    private String table;
    private final List<String> columns = new ArrayList<>();
    private boolean distinct = false;
    private String where;
    private String equal;
    private String groupBy;
    private final List<String> orderBy = new ArrayList<>();
    private String join;
    private Integer limit;
    private Integer offset;
    private String having;
    private boolean hasCondition = false;

    @Override
    public SelectAfterStep allColumns() {
      this.columns.add("*");
      return this;
    }

    @Override
    public SelectStep distinct() {
      this.distinct = true;
      return this;
    }

    @Override
    public SelectAfterStep columns(String... columns) {
      this.columns.clear();
      this.columns.addAll(List.of(columns));
      return this;
    }

    @Override
    public FromStep from(String table) {
      this.table = table;
      return this;
    }

    @Override
    public WhereStep where() {
      return this;
    }

    public GroupByStep groupBy(String column) {
      this.groupBy = column;
      return this;
    }

    @Override
    public ConditionStep column(String column) {
      this.where = column;
      return this;
    }

    @Override
    public ConditionStep and(String column) {
      this.where += " AND " + column;
      return this;
    }

    @Override
    public ConditionStep or(String column) {
      this.where += " OR " + column;
      return this;
    }

    @Override
    public ConditionStep between(int start, int end) {
      this.where += " BETWEEN " + start + " AND " + end;
      this.hasCondition = true;
      return this;
    }

    @Override
    public ConditionStep like(String pattern) {
      this.where += " LIKE '" + pattern + "'";
      this.hasCondition = true;
      return this;
    }

    @Override
    public ConditionStep in(String... values) {
      this.where += " IN ('" + String.join("', '", values) + "')";
      this.hasCondition = true;
      return this;
    }

    @Override
    public EqualStep equalTo(String query) {
      this.equal = "'" + query + "'";
      return this;
    }

    @Override
    public EqualStep equalTo(boolean query) {
      this.equal = String.valueOf(query);
      return this;
    }

    @Override
    public EqualStep equalTo(int query) {
      this.equal = String.valueOf(query);
      return this;
    }

    @Override
    public EqualStep equalTo(double query) {
      this.equal = String.valueOf(query);
      return this;
    }

    @Override
    public EqualStep equalTo(float query) {
      this.equal = String.valueOf(query);
      return this;
    }

    @Override
    public JoinStep join(String table, String condition) {
      this.join = " JOIN " + table + " ON " + condition;
      return this;
    }

    @Override
    public FromStep inner() {
      this.join = " INNER" + join;
      return this;
    }

    @Override
    public FromStep left() {
      this.join = " LEFT" + join;
      return this;
    }

    @Override
    public FromStep right() {
      this.join = " RIGHT" + join;
      return this;
    }

    @Override
    public FromStep full() {
      this.join = " FULL" + join;
      return this;
    }

    @Override
    public OrderByStep orderBy(String column, boolean ascending) {
      this.orderBy.add(column + (ascending ? " ASC" : " DESC"));
      return this;
    }

    @Override
    public OrderByStep thenOrderBy(String column, boolean ascending) {
      this.orderBy(column, ascending);
      return this;
    }

    @Override
    public LimitStep limit(int limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public OffsetStep offset(int offset) {
      this.offset = offset;
      return this;
    }

    @Override
    public HavingStep having(String condition) {
      this.having = condition;
      return this;
    }


    @Override
    public String build() {
      StringBuilder output = new StringBuilder("SELECT ");

      if (distinct) {
        output.append("DISTINCT ");
      }
      output.append(String.join(", ", columns)).append(" FROM ").append(table);

      if (join != null) {
        output.append(join);
      }

      if (where != null && !where.isEmpty()) {
        output.append(" WHERE ").append(where);

        if (equal != null && !equal.isEmpty() && !equal.equals("''")) {
          output.append(" = ").append(equal);
        } else if (!hasCondition) {
          output.delete(output.length() - where.length() - 7, output.length());
        }
      }

      if (groupBy != null) {
        output.append(" GROUP BY ").append(groupBy);
      }

      if (having != null) {
        output.append(" HAVING ").append(having);
      }

      if (!orderBy.isEmpty()) {
        output.append(" ORDER BY ").append(String.join(", ", orderBy));
      }

      if (limit != null) {
        output.append(" LIMIT ").append(limit);
      }

      if (offset != null) {
        output.append(" OFFSET ").append(offset);
      }

      return output.toString();
    }
  }
}
