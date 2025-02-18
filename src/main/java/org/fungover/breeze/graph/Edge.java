package org.fungover.breeze.graph;

/**
 * Represents an edge in a graph.
 *
 * @param <T> the type of data stored in the nodes connected by this edge.
 */
public class Edge<T> {
    private Node<T> source;
    private double weight;
    private Node<T> destination;

    /**
     * Constructs an edge with the specified source node, destination node, and weight.
     *
     * @param source the source node of the edge.
     * @param destination the destination node of the edge.
     * @param weight the weight of the edge.
     * @throws IllegalArgumentException if the weight is negative or if either node is null.
     */
    public Edge(Node<T> source, Node<T> destination, double weight) {
        if (weight < 0.0) {
            throw new IllegalArgumentException("Weight can't be a negative number");
        }
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Value can't be null");
        }

        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Returns the source node of the edge.
     *
     * @return the source node of the edge.
     */
    public Node<T> getSource() {
        return source;
    }

    /**
     * Returns the destination node of the edge.
     *
     * @return the destination node of the edge.
     */
    public Node<T> getDestination() {
        return destination;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return the weight of the edge.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns a string representation of the edge.
     *
     * @return a string representation of the edge.
     */
    @Override
    public String toString() {
        return "Start = " + source +
                " --> " + weight +
                " --> Stop = " + destination + " |";
    }
}
