package graph;

import java.util.Map;

public class Node<T> {
    T data;
    Map<Node<T>, Double> shortestPaths;
    Node<T> previousNode;
    double distance;
}