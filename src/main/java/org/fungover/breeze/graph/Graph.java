package org.fungover.breeze.graph;

import java.util.Collection;

public interface Graph<T> {
    Collection<Node<T>> getNodes();
    Collection<Edge<T>> getEdges(Node<T> node);
}