package org.fungover.breeze.graph;

/**
 * Represents a node in a graph with associated data, a reference to a previous node,
 * and a distance value (commonly used in graph traversal algorithms).
 *
 * @param <T> the type of data stored in this node
 */
public class Node<T> {
    /**
     * The data stored in this node.
     */
    private final T data;

    /**
     * The previous node in the path. This can be used to reconstruct the path taken
     * in graph traversal or shortest path algorithms.
     */
    private Node<T> previousNode;

    /**
     * The distance value associated with this node. This is typically used in algorithms
     * such as Dijkstra's to represent the current shortest distance from a starting node.
     */
    private double distance;

    /**
     * Constructs a new Node with the specified data. The previous node is initialized
     * to {@code null} and the distance is set to {@link Double#MAX_VALUE} to indicate that
     * the node has not been reached yet.
     *
     * @param data the data to be stored in this node
     */
    public Node(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        this.data = data;
        this.previousNode = null;
        this.distance = Double.MAX_VALUE;
    }

    /**
     * Returns the data stored in this node.
     *
     * @return the data of type {@code T} held by this node
     */
    public T getData() {
        return data;
    }

    /**
     * Returns the previous node in the path.
     *
     * @return the previous {@code Node} in the path, or {@code null} if none is set
     */
    public Node<T> getPreviousNode() {
        return previousNode;
    }

    /**
     * Returns the distance associated with this node.
     *
     * @return the distance value as a {@code double}
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the previous node in the path.
     *
     * @param previousNode the node that precedes this node in the traversal path
     */
    public void setPreviousNode(Node<T> previousNode) {
        this.previousNode = previousNode;
    }

    /**
     * Sets the distance associated with this node.
     *
     * @param distance the new distance value to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
