package graph;

public class Main {
    public static void main(String[] args) {

        Node<Integer> node0 = new Node<>(0);
        Node<Integer> node7 = new Node<>(7);
        Node<Integer> node1 = new Node<>(1);

        Edge<Integer> edge0to7 = new Edge<>(node0, node7, 8);
        Edge<Integer> edge0to1 = new Edge<>(node0, node1, 4);
        Edge<Integer> edge1to2 = new Edge<>(node1, node2, 8);
        Edge<Integer> edge7to8 = new Edge<>(node7, node8, 7);
        Edge<Integer> edge7to6 = new Edge<>(node7, node6, 1);

        WeightedGraph<Integer> graph = new WeightedGraph<>();

        graph.addNode(node0).addNode(node1).addNode(node7);
        graph.addEdge(edge).addEdge(edge1);

        //graph.getNodes().forEach(System.out::println);
        graph.mapSourceNodeWithEdges(node0);
        graph.mapSourceNodeWithEdges(node1);
        graph.mapSourceNodeWithEdges(node7);

        System.out.println(graph.startNodeWithEdges.get(node7));
    }
}
