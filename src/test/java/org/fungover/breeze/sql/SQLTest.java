package org.fungover.breeze.sql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class SQLTest {

  @Test
  @DisplayName("Can select all columns")
  void canSelectAllColumns() {
    String selectAll = SQL.select().allColumns().from("my_table").build();

    assertThat(selectAll).isEqualTo("SELECT * FROM my_table");
  }

  @Test
  @DisplayName("Can add where clause to SELECT statement")
  void canSelectWhereClause() {
    String selectColumnsEqualTo = SQL.select().columns("id", "car").from("orders").where().column("id").equalTo(1).build();

    assertThat(selectColumnsEqualTo).isEqualTo("SELECT id, car FROM orders WHERE id = 1");
  }

  @Test
  @DisplayName("WHERE clause works with multiple types")
  void whereClauseWorksWithMultipleTypes() {
    String stringWhere = SQL.select().allColumns().from("users").where().column("name").equalTo("SQL").build();
    String floatWhere = SQL.select().allColumns().from("users").where().column("income").equalTo(100.0f).build();
    String doubleWhere = SQL.select().allColumns().from("users").where().column("balance").equalTo(300.0).build();


    assertAll(
            () -> assertThat(stringWhere).isEqualTo("SELECT * FROM users WHERE name = SQL"),
            () -> assertThat(floatWhere).isEqualTo("SELECT * FROM users WHERE income = 100.0"),
            () -> assertThat(doubleWhere).isEqualTo("SELECT * FROM users WHERE balance = 300.0")
    );
  }

  @Test
  @DisplayName("Can add GROUP BY clause to WHERE statement")
  void canSelectGroupByClause() {
    String groupBy = SQL.select().allColumns().from("group_by_table").where().column("electric").equalTo(true).groupBy("car").build();

    assertThat(groupBy).isEqualTo("SELECT * FROM group_by_table WHERE electric = true GROUP BY car");
  }

  @Test
  @DisplayName("Does not add where clause if equals is empty")
  void doesNotAddWhereClauseIfEmpty() {
    String emptyEqual = SQL.select().allColumns().from("my_table").where().column("name").equalTo("").build();

    assertThat(emptyEqual).isEqualTo("SELECT * FROM my_table");
  }

  @Test
  @DisplayName("Does not add equal clause if where is empty")
  void doesNotAddEqualClauseIfEmpty() {
    String emptyWhere = SQL.select().allColumns().from("my_table").where().column("").equalTo("user").build();

    assertThat(emptyWhere).isEqualTo("SELECT * FROM my_table");
  }

  @Test
  @DisplayName("Group by clause can be added after from clause")
  void groupByClauseCanBeAddedAfterFromClause() {
    String groupBy = SQL.select().columns("first_name", "last_name").from("users").groupBy("first_name").build();

    assertThat(groupBy).isEqualTo("SELECT first_name, last_name FROM users GROUP BY first_name");
  }
}
