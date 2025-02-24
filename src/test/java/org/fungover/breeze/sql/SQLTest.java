package org.fungover.breeze.sql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.fungover.breeze.sql.SQL.SortOrder.*;
import static org.junit.jupiter.api.Assertions.*;

class SQLTest {

  @Test
  @DisplayName("Can select all columns")
  void canSelectAllColumns() {
    String selectAll = SQL.select().allColumns().from("my_table").build();

    assertThat(selectAll).isEqualTo("SELECT * FROM my_table");
  }

  @Test
  @DisplayName("Can select distinct columns")
  void canSelectDistinctColumns() {
    String selectDistinct = SQL.select().distinct().columns("name", "age").from("users").build();

    assertThat(selectDistinct).isEqualTo("SELECT DISTINCT name, age FROM users");
  }

  @Test
  @DisplayName("Can add where clause to SELECT statement")
  void canSelectWhereClause() {
    String selectColumnsEqualTo = SQL.select().columns("id", "car").from("orders").where().column("id").isEqualTo(1).build();

    assertThat(selectColumnsEqualTo).isEqualTo("SELECT id, car FROM orders WHERE id = 1");
  }

  @Test
  @DisplayName("WHERE clause supports multiple conditions")
  void whereClauseSupportsMultipleConditions() {
    String whereWithAnd = SQL.select().allColumns().from("users").where().column("age").between(18, 30).and("status").isEqualTo("active").build();

    assertThat(whereWithAnd).isEqualTo("SELECT * FROM users WHERE age BETWEEN 18 AND 30 AND status = 'active'");
  }

  @Test
  @DisplayName("WHERE clause supports LIKE and IN operators")
  void whereClauseSupportsLikeAndIn() {
    String whereLike = SQL.select().allColumns().from("users").where().column("name").like("%John%").build();
    String whereIn = SQL.select().allColumns().from("products").where().column("category").in("Electronics", "Books").build();

    assertAll(
            () -> assertThat(whereLike).isEqualTo("SELECT * FROM users WHERE name LIKE '%John%'"),
            () -> assertThat(whereIn).isEqualTo("SELECT * FROM products WHERE category IN ('Electronics', 'Books')")
    );
  }

  @Test
  @DisplayName("Can add GROUP BY and HAVING clauses")
  void canSelectGroupByAndHaving() {
    String groupByHaving = SQL.select().columns("category", "COUNT(*)").from("products").groupBy("category").having("COUNT(*) > 10").build();

    assertThat(groupByHaving).isEqualTo("SELECT category, COUNT(*) FROM products GROUP BY category HAVING COUNT(*) > 10");
  }

  @Test
  @DisplayName("Can add ORDER BY with multiple columns")
  void canOrderByMultipleColumns() {
    String orderByMultiple = SQL.select().columns("name", "age").from("users").orderBy("age", DESC).thenOrderBy("name", ASC).build();

    assertThat(orderByMultiple).isEqualTo("SELECT name, age FROM users ORDER BY age DESC, name ASC");
  }

  @Test
  @DisplayName("Can add LIMIT and OFFSET")
  void canLimitAndOffsetResults() {
    String limitOffset = SQL.select().allColumns().from("users").limit(10).offset(20).build();

    assertThat(limitOffset).isEqualTo("SELECT * FROM users LIMIT 10 OFFSET 20");
  }

  @Test
  @DisplayName("Handles empty column names gracefully")
  void handlesEmptyColumns() {
    String emptyColumns = SQL.select().columns().from("users").build();

    assertThat(emptyColumns).isEqualTo("SELECT * FROM users");
  }

  @Test
  @DisplayName("Handles empty table name gracefully")
  void handlesEmptyTableName() {
    var emptyTable = SQL.select().columns("id").from("");

    assertThatThrownBy(emptyTable::build).isInstanceOf(IllegalArgumentException.class).hasMessage("Table name is required");
  }

  @Test
  @DisplayName("Handles WHERE clause with no conditions")
  void handlesWhereWithoutConditions() {
    String whereWithoutCondition = SQL.select().allColumns().from("users").where().column("").build();

    assertThat(whereWithoutCondition).isEqualTo("SELECT * FROM users");
  }

  @Test
  @DisplayName("Supports INNER JOIN")
  void supportsInnerJoin() {
    String innerJoin = SQL.select().allColumns().from("orders").join("customers", "orders.customer_id = customers.id").inner().build();

    assertThat(innerJoin).isEqualTo("SELECT * FROM orders INNER JOIN customers ON orders.customer_id = customers.id");
  }

  @Test
  @DisplayName("Supports LEFT JOIN")
  void supportsLeftJoin() {
    String leftJoin = SQL.select().allColumns().from("orders").join("customers", "orders.customer_id = customers.id").left().build();

    assertThat(leftJoin).isEqualTo("SELECT * FROM orders LEFT JOIN customers ON orders.customer_id = customers.id");
  }

  @Test
  @DisplayName("Supports RIGHT JOIN")
  void supportsRightJoin() {
    String rightJoin = SQL.select().allColumns().from("orders").join("customers", "orders.customer_id = customers.id").right().build();

    assertThat(rightJoin).isEqualTo("SELECT * FROM orders RIGHT JOIN customers ON orders.customer_id = customers.id");
  }

  @Test
  @DisplayName("Supports FULL JOIN")
  void supportsFullJoin() {
    String fullJoin = SQL.select().allColumns().from("orders").join("customers", "orders.customer_id = customers.id").full().build();

    assertThat(fullJoin).isEqualTo("SELECT * FROM orders FULL JOIN customers ON orders.customer_id = customers.id");
  }

  @Test
  @DisplayName("Handles LIMIT without OFFSET")
  void handlesLimitWithoutOffset() {
    String limitOnly = SQL.select().allColumns().from("products").limit(5).build();

    assertThat(limitOnly).isEqualTo("SELECT * FROM products LIMIT 5");
  }

  @Test
  @DisplayName("Handles HAVING without GROUP BY")
  void handlesHavingWithoutGroupBy() {
    String havingWithoutGroupBy = SQL.select()
            .columns("category")
            .from("products")
            .groupBy("category")  // Ensuring GROUP BY is used before HAVING
            .having("COUNT(*) > 10")
            .build();

    assertThat(havingWithoutGroupBy).isEqualTo("SELECT category FROM products GROUP BY category HAVING COUNT(*) > 10");
  }


  @Test
  @DisplayName("Handles ORDER BY without WHERE")
  void handlesOrderByWithoutWhere() {
    String orderByOnly = SQL.select().columns("name").from("users").orderBy("name", ASC).build();
    assertThat(orderByOnly).isEqualTo("SELECT name FROM users ORDER BY name ASC");
  }

  @Test
  @DisplayName("Handles LIKE with different patterns")
  void handlesLikeWithDifferentPatterns() {
    String likeStart = SQL.select().allColumns().from("users").where().column("name").like("John%").build();
    String likeEnd = SQL.select().allColumns().from("users").where().column("name").like("%Doe").build();
    String likeMiddle = SQL.select().allColumns().from("users").where().column("name").like("%mid%").build();
    String likeInjection = SQL.select().allColumns().from("users").where().column("name").like("'; DROP TABLE users; --").build();

    assertAll(
            () -> assertThat(likeStart).isEqualTo("SELECT * FROM users WHERE name LIKE 'John%'"),
            () -> assertThat(likeEnd).isEqualTo("SELECT * FROM users WHERE name LIKE '%Doe'"),
            () -> assertThat(likeMiddle).isEqualTo("SELECT * FROM users WHERE name LIKE '%mid%'"),
            () -> assertThat(likeInjection).isEqualTo("SELECT * FROM users WHERE name LIKE '''; DROP TABLE users; --'")
    );
  }

  @Test
  @DisplayName("WHERE clause supports OR conditions")
  void whereClauseSupportsOrConditions() {
    String whereWithOr = SQL.select()
            .allColumns()
            .from("users")
            .where()
            .column("age")
            .between(18, 30)
            .or("status")
            .isEqualTo("inactive")
            .build();

    assertThat(whereWithOr).isEqualTo("SELECT * FROM users WHERE age BETWEEN 18 AND 30 OR status = 'inactive'");
  }

  @Test
  @DisplayName("WHERE clause supports boolean conditions")
  void whereClauseSupportsBooleanConditions() {
    String whereBoolean = SQL.select()
            .allColumns()
            .from("employees")
            .where()
            .column("is_active")
            .isEqualTo(true)
            .build();

    assertThat(whereBoolean).isEqualTo("SELECT * FROM employees WHERE is_active = true");
  }

  @Test
  @DisplayName("WHERE clause supports floating point conditions")
  void whereClauseSupportsFloatingPointConditions() {
    String whereFloat = SQL.select()
            .allColumns()
            .from("transactions")
            .where()
            .column("amount")
            .isEqualTo(99.99f)
            .build();

    String whereDouble = SQL.select()
            .allColumns()
            .from("transactions")
            .where()
            .column("amount")
            .isEqualTo(1000.50)
            .build();

    assertAll(
            () -> assertThat(whereFloat).isEqualTo("SELECT * FROM transactions WHERE amount = 99.99"),
            () -> assertThat(whereDouble).isEqualTo("SELECT * FROM transactions WHERE amount = 1000.5")
    );
  }

  @Test
  @DisplayName("GROUP BY clause is supported independently")
  void groupByClauseIsSupportedIndependently() {
    String groupBy = SQL.select()
            .columns("department", "COUNT(*)")
            .from("employees")
            .groupBy("department")
            .build();

    assertThat(groupBy).isEqualTo("SELECT department, COUNT(*) FROM employees GROUP BY department");
  }

  @Test
  @DisplayName("ORDER BY can be applied after GROUP BY")
  void orderByAfterGroupBy() {
    String groupByOrderBy = SQL.select()
            .columns("department", "COUNT(*)")
            .from("employees")
            .groupBy("department")
            .orderBy("COUNT(*)", DESC)
            .build();

    assertThat(groupByOrderBy).isEqualTo("SELECT department, COUNT(*) FROM employees GROUP BY department ORDER BY COUNT(*) DESC");
  }

  @Test
  @DisplayName("LIMIT can be applied after WHERE clause")
  void limitAfterWhereClause() {
    String whereLimit = SQL.select()
            .allColumns()
            .from("customers")
            .where()
            .column("city")
            .isEqualTo("New York")
            .limit(5)
            .build();

    assertThat(whereLimit).isEqualTo("SELECT * FROM customers WHERE city = 'New York' LIMIT 5");
  }

  @Test
  @DisplayName("LIMIT can be applied after ORDER BY")
  void limitAfterOrderBy() {
    String orderByLimit = SQL.select()
            .columns("name", "age")
            .from("users")
            .orderBy("age", ASC)
            .limit(3)
            .build();

    assertThat(orderByLimit).isEqualTo("SELECT name, age FROM users ORDER BY age ASC LIMIT 3");
  }

  @Test
  @DisplayName("WHERE clause is not added if equal value is empty")
  void whereClauseIsNotAddedIfEqualValueIsEmpty() {
    String query = SQL.select()
            .allColumns()
            .from("users")
            .where()
            .column("name")
            .isEqualTo("")
            .build();

    assertThat(query).isEqualTo("SELECT * FROM users");
  }

  @Test
  @DisplayName("EqualStep supports groupBy statement")
  void equalStepSupportsGroupByStatement() {
    String query = SQL.select()
            .allColumns()
            .from("my_table")
            .where()
            .column("first_name")
            .isEqualTo("Steve")
            .groupBy("last_name")
            .build();

    assertThat(query).isEqualTo("SELECT * FROM my_table WHERE first_name = 'Steve' GROUP BY last_name");
  }

  @Test
  @DisplayName("EqualStep supports having statement")
  void equalStepSupportsHavingStatement() {
    String query = SQL.select()
            .allColumns()
            .from("my_table")
            .where()
            .column("first_name")
            .isEqualTo("Steve")
            .having("COUNT(first_name) > 2")
            .build();

    assertThat(query).isEqualTo("SELECT * FROM my_table WHERE first_name = 'Steve' HAVING COUNT(first_name) > 2");
  }

  @Test
  @DisplayName("EqualStep supports orderBy statement")
  void equalStepSupportsOrderByStatement() {
    String query = SQL.select()
            .allColumns()
            .from("my_table")
            .where()
            .column("first_name")
            .isEqualTo("Steve")
            .orderBy("last_name", ASC)
            .build();

    assertThat(query).isEqualTo("SELECT * FROM my_table WHERE first_name = 'Steve' ORDER BY last_name ASC");
  }
}