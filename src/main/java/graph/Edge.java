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
}
