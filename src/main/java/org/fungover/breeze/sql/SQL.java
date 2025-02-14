package org.fungover.breeze.sql;

import java.util.ArrayList;
import java.util.List;

public class SQL {

  public static SelectStep select() {
    return new SQLBuilder();
  }

  public interface SelectStep {
    SelectAfterStep allColumns();

    SelectAfterStep columns(String... columns);
  }

  public interface SelectAfterStep {
    FromStep from(String table);
  }

  public interface FromStep {
    String build();

    WhereStep where();

    GroupByStep groupBy(String column);
  }

  public interface WhereStep {
    ColumnStep column(String column);
  }

  public interface ColumnStep {
    EqualStep equalTo(String query);

    EqualStep equalTo(double query);

    EqualStep equalTo(boolean query);

    EqualStep equalTo(int query);

    EqualStep equalTo(float query);
  }

  public interface EqualStep {
    String build();

    GroupByStep groupBy(String column);
  }

  public interface GroupByStep {
    String build();
  }

  private static class SQLBuilder implements SelectStep, SelectAfterStep, FromStep, WhereStep, ColumnStep, EqualStep, GroupByStep {
    private String table;
    private final ArrayList<String> columns = new ArrayList<>();
    private String where;
    private String equal;
    private String groupBy;

    @Override
    public SelectAfterStep allColumns() {
      this.columns.add("*");
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
    public ColumnStep column(String column) {
      this.where = column;
      return this;
    }

    @Override
    public EqualStep equalTo(String query) {
      this.equal = query;
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
    public EqualStep equalTo(float query) {
      this.equal = String.valueOf(query);
      return this;
    }

    @Override
    public EqualStep equalTo(double query) {
      this.equal = String.valueOf(query);
      return this;
    }

    @Override
    public String build() {
      StringBuilder output = new StringBuilder("SELECT " + String.join(", ", columns) + " FROM " + table);

      if (where != null && equal != null) {
        output.append(!where.isEmpty() && !equal.isEmpty() ? " WHERE " + where + " = " + equal : "");
      }

      if (groupBy != null) {
        output.append(" GROUP BY ").append(groupBy);
      }

      return output.toString();
    }
  }
}
