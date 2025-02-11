package graph;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Node<String>> nodes = List.of(
                new Node<>("A"), new Node<>("B"), new Node<>("C"),
                new Node<>("D"), new Node<>("E"), new Node<>("F")
        );

        List<Edge<Integer>> edges = List.of(
                new Edge<>(nodes.get(0), nodes.get(1), 4),  //0 to 1
                new Edge<>(nodes.get(0), nodes.get(7), 8),  //0 to 7
                new Edge<>(nodes.get(1), nodes.get(2), 8),  //1 to 2
                new Edge<>(nodes.get(1), nodes.get(7), 11), //1 to 7
                new Edge<>(nodes.get(7), nodes.get(1), 11), //7 to 1
                new Edge<>(nodes.get(7), nodes.get(8), 7),  //7 to 8
                new Edge<>(nodes.get(7), nodes.get(6), 1),  //7 to 6
                new Edge<>(nodes.get(2), nodes.get(8), 2),  //2 to 8
                new Edge<>(nodes.get(2), nodes.get(5), 4),  //2 to 5
                new Edge<>(nodes.get(2), nodes.get(3), 7),  //2 to 3
                new Edge<>(nodes.get(8), nodes.get(6), 6),  //8 to 6
                new Edge<>(nodes.get(8), nodes.get(2), 2),  //8 to 2
                new Edge<>(nodes.get(6), nodes.get(5), 2),  //6 to 5
                new Edge<>(nodes.get(5), nodes.get(3), 14), //5 to 3
                new Edge<>(nodes.get(5), nodes.get(4), 10), //5 to 4
                new Edge<>(nodes.get(3), nodes.get(5), 14), //3 to 5
                new Edge<>(nodes.get(3), nodes.get(4), 9)   //3 to 4
        );

        WeightedGraph<String> graph = new WeightedGraph<>(nodes, edges);

        System.out.println(graph.startNodeWithEdges.get(node0));
    }
}
