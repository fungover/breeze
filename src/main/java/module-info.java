module org.fungover.breeze {
    requires jdk.incubator.vector;

    exports org.fungover.breeze.util;
    exports org.fungover.breeze.matrix;
    exports org.fungover.breeze.graph;

    opens org.fungover.breeze.util to junit;
}
