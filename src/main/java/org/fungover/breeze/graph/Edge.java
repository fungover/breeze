package org.fungover.breeze.graph;

public class Edge<T> {
    Node<T> source;
    double weight;
    Node<T> destination;

    public Edge (Node<T> source, Node<T> destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Node<T> getSource() {
        return source;
    }

    public Node<T> getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", weight=" + weight +
                ", destination=" + destination +
                '}';
    }
}
