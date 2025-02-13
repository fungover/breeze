package org.fungover.breeze.graph;

import java.util.Map;

public class Node<T> {
    T data;
    Node<T> previousNode;
    double distance;

    public Node(T data) {
        this.data = data;
        this.previousNode = null;
        this.distance = Double.MAX_VALUE;
    }

    public T getData() {
        return data;
    }

    public Node<T> getPreviousNode() {
        return previousNode;
    }

    public double getDistance() {
        return distance;
    }

    public void setPreviousNode(Node<T> previousNode) {
        this.previousNode = previousNode;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Node{" + data +
                '}';
    }
}