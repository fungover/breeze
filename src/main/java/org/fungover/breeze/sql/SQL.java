package org.fungover.breeze.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a fluent DQL SQL API.
 * This class is meant to add an abstraction layer on top of SQL
 *
 * <h2>Usage Example:</h2>
 * <pre>
 *   {@code String query = SQL.select().allColumns().from("my_table").build();
 *
 *   System.out.println(query); // "SELECT * FROM my_table
 *   }
 * </pre>
 */
public final class SQL {
  /**
   * Don't let anyone instantiate this class
   */
  private SQL() {
  }

  public enum SortOrder {
    ASC, DESC
  }

  /**
   * Starting point for the API
   *
   * @return An instance of the query API
   */
  public static SelectStep select() {
    return new SQLBuilder();
  }

  /**
   * Provides functions to specify what to select
   */
  public interface SelectStep {
    /**
     * Adds "*" after SELECT to the SQL statement
     *
     * @return The supported functions following DQL SQL
     */
    SelectAfterStep allColumns();

    /**
     * Adds "DISTINCT" to the SQL statement
     *
     * @return The supported functions following DQL SQL
     */
    SelectStep distinct();

    /**
     * Adds each column to the SQL statement
     *
     * @param columns A comma separated list of columns to be selected
     * @return The supported functions following DQL SQL
     */
    SelectAfterStep columns(String... columns);
  }

  /**
   * Provides a function to select which table to be used for the query
   */
  public interface SelectAfterStep {
    /**
     * Adds FROM [table] to the SQL statement
     *
     * @param table The table from which the columns are selected from
     * @return The supported functions following DQL SQL
     */
    FromStep from(String table);
  }

  /**
   * Provides functions for querying the selected columns
   */
  public interface FromStep {
    /**
     * Builds the SQL query in to a {@code String}
     *
     * @return The entire SQL statement
     */
    String build();

    /**
     * Adds support for condition steps to the SQL statement
     *
     * @return The supported functions following DQL SQL
     */
    WhereStep where();

    /**
     * Adds support for grouping by a specific column
     *
     * @param column The column to group by
     * @return The supported functions following DQL SQL
     */
    GroupByStep groupBy(String column);

    /**
     * Adds support for joining a table on a specific condition
     *
     * @param table     The table to join
     * @param condition The condition to join on
     * @return The supported functions following DQL SQL
     */
    JoinStep join(String table, String condition);

    /**
     * Adds support for ordering the result based on a column and which order
     *
     * @param column The column to order by
     * @param order  The direction to sort by
     * @return The supported functions following DQL SQL
     */
    OrderByStep orderBy(String column, SortOrder order);

    /**
     * Adds support for limiting the amount returned
     *
     * @param limit The amount to be limited
     * @return The supported functions following DQL SQL
     */
    LimitStep limit(int limit);
  }

  /**
   * Provides functions to implement different JOINs in the SQL statement
   */
  public interface JoinStep {
    /**
     * Adds support for INNER JOINS
     *
     * @return The supported functions following DQL SQL
     */
    FromStep inner();

    /**
     * Adds support for LEFT JOINS
     *
     * @return The supported functions following DQL SQL
     */
    FromStep left();

    /**
     * Adds support for RIGHT JOINS
     *
     * @return The supported functions following DQL SQL
     */
    FromStep right();

    /**
     * Adds support for FULL JOINS
     *
     * @return The supported functions following DQL SQL
     */
    FromStep full();
  }

  /**
   * Provides a function to select which column to apply equals to
   */
  public interface WhereStep {
    /**
     * Adds support for selecting which column to apply one or multiple conditions to
     *
     * @param column The column to apply the condition to
     * @return The supported functions following DQL SQL
     */
    ConditionStep column(String column);
  }

  /**
   * Provides functions to add condition statements such as in and like
   */
  public interface ConditionStep {
    /**
     * Adds support for the AND operator
     *
     * @param column The column to apply the and operator to
     * @return The supported functions following DQL SQL
     */
    ConditionStep and(String column);

    /**
     * Adds support for the or operator
     *
     * @param column The column to apply the or operator to
     * @return The supported functions following DQL SQL
     */
    ConditionStep or(String column);

    /**
     * Adds the query to select on
     *
     * @param query The query to equal a column by
     * @return The supported functions following DQL SQL
     */
    <T> EqualStep isEqualTo(T query);

    /**
     * Adds support for the between operator
     *
     * @param start The initial starting value
     * @param end   The ending value
     * @return The supported functions following DQL SQL
     */
    ConditionStep between(int start, int end);

    /**
     * Adds support for the like operator
     *
     * @param pattern The pattern to apply the like operator on
     * @return The supported functions following DQL SQL
     */
    ConditionStep like(String pattern);

    /**
     * Adds support for the in operator
     *
     * @param values The values to apply the in operator on
     * @return The supported functions following DQL SQL
     */
    ConditionStep in(String... values);

    /**
     * Builds the SQL query in to a {@code String}
     *
     * @return The entire SQL statement
     */
    String build();
  }

  /**
   * Provides functions to structure the query using statements such as groupBy and orderBy
   */
  public interface EqualStep {
    /**
     * Builds the SQL query in to a {@code String}
     *
     * @return The entire SQL statement
     */
    String build();

    /**
     * Adds support for grouping by a specific column
     *
     * @param column The column to group by
     * @return The supported functions following DQL SQL
     */
    GroupByStep groupBy(String column);

    /**
     * Adds support for ordering the result based on a column and which order
     *
     * @param column The column to order by
     * @param order  The direction to sort by
     * @return The supported functions following DQL SQL
     */
    OrderByStep orderBy(String column, SortOrder order);

    /**
     * Adds support for limiting the amount returned
     *
     * @param limit The amount to be limited
     * @return The supported functions following DQL SQL
     */
    LimitStep limit(int limit);

    /**
     * Adds support for the having clause
     *
     * @param condition The condition to apply the HAVING clause on
     * @return The supported functions following DQL SQL
     */
    HavingStep having(String condition);
  }

  /**
   * Provides functions to sort or filter the query
   */
  public interface GroupByStep {
    /**
     * Builds the SQL query in to a {@code String}
     *
     * @return The entire SQL statement
     */
    String build();

    /**
     * Adds support for ordering the result based on a column and which order
     *
     * @param column The column to order by
     * @param order  The direction to sort by
     * @return The supported functions following DQL SQL
     */
    OrderByStep orderBy(String column, SortOrder order);

    /**
     * Adds support for the having clause
     *
     * @param condition The condition to apply the HAVING clause on
     * @return The supported functions following DQL SQL
     */
    HavingStep having(String condition);
  }

  /**
   * Provides functions to either build or sort/filter the SQL query
   */
  public interface OrderByStep {
    /**
     * Builds the SQL query in to a {@code String}
     *
     * @return The entire SQL statement
     */
    String build();

    /**
     * Adds support for limiting the amount returned
     *
     * @param limit The amount to be limited
     * @return The supported functions following DQL SQL
     */
    LimitStep limit(int limit);

    /**
     * Adds support for ordering the result based on a column and which order
     *
     * @param column The column to order by
     * @param order  The direction to sort by
     * @return The supported functions following DQL SQL
     */
    OrderByStep thenOrderBy(String column, SortOrder order);
  }

  /**
   * Provides functions to either build or add an offset to the SQL query
   */
  public interface LimitStep {
    /**
     * Builds the SQL query in to a {@code String}
     *
     * @return The entire SQL statement
     */
    String build();

    /**
     * Adds support for the offset operator
     *
     * @param offset The amount to offset by
     * @return The supported functions following DQL SQL
     */
    OffsetStep offset(int offset);
  }

  /**
   * Provides a function to build the SQL statement
   */
  public interface OffsetStep {
    /**
     * Builds the SQL query in to a {@code String}
     *
     * @return The entire SQL statement
     */
    String build();
  }

  /**
   * Provides a function to build the SQL statement
   */
  public interface HavingStep {
    /**
     * Builds the SQL query in to a {@code String}
     *
     * @return The entire SQL statement
     */
    String build();
  }

  private static class SQLBuilder implements SelectStep, SelectAfterStep, FromStep, WhereStep, ConditionStep, EqualStep, GroupByStep, OrderByStep, LimitStep, OffsetStep, JoinStep, HavingStep {
    private void validateRequiredFields() {
      if (table == null || table.isEmpty()) {
        throw new IllegalArgumentException("Table name is required");
      }
    }

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
      if (columns.length > 0) {
        this.columns.addAll(List.of(columns));
      } else {
        this.columns.add("*");
      }
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
    public <T> EqualStep isEqualTo(T query) {
      if (query instanceof String) {
        this.equal = "'" + query + "'";
      } else {
        this.equal = query.toString();
      }
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
    public OrderByStep orderBy(String column, SortOrder order) {
      this.orderBy.add(column + " " + order.toString());
      return this;
    }

    @Override
    public OrderByStep thenOrderBy(String column, SortOrder order) {
      this.orderBy(column, order);
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
      validateRequiredFields();
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
