package graph;

public class Edge<T> {
    Node<T> source;
    Node<T> destination;
    double weight;

    public Edge (Node<T> source,Node<T> destination, double weight) {
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
}
