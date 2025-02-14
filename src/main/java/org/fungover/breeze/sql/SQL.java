package org.fungover.breeze.sql;

public class SQL {

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
}
