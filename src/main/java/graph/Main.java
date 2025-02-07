package graph;

public class Main {
    public static void main(String[] args) {

        Node<Integer> node0 = new Node<>(0);
        Node<Integer> node7 = new Node<>(7);
        Node<Integer> node1 = new Node<>(1);

        Edge<Integer> edge = new Edge<>(node0, node7, 8);
        Edge<Integer> edge1 = new Edge<>(node0, node1, 4);

        WeightedGraph<Integer> graph = new WeightedGraph<>();

        graph.addNode(node0).addNode(node1).addNode(node7);
        graph.addEdge(edge).addEdge(edge1);

        graph.getNodes().forEach(System.out::println);
    }
}
