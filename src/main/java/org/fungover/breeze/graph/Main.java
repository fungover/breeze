package org.fungover.breeze.graph;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Node<String>> nodes = List.of(
                new Node<>("A"), new Node<>("B"), new Node<>("C"),
                new Node<>("D"), new Node<>("E"), new Node<>("F")
        );

        List<Edge<String>> edges = List.of(
                new Edge<>(nodes.get(0), nodes.get(2), 2),  //A to C
                new Edge<>(nodes.get(0), nodes.get(1), 5),  //A to B
                new Edge<>(nodes.get(1), nodes.get(2), 1),  //B to C
                new Edge<>(nodes.get(1), nodes.get(3), 4),  //B to D
                new Edge<>(nodes.get(1), nodes.get(4), 2),  //B to E
                new Edge<>(nodes.get(2), nodes.get(4), 7),  //C to E
                new Edge<>(nodes.get(3), nodes.get(4), 6),  //D to E
                new Edge<>(nodes.get(3), nodes.get(5), 3),  //D to F
                new Edge<>(nodes.get(4), nodes.get(5), 1)   //E to F
        );

        WeightedGraph<String> graph = new WeightedGraph<>(nodes, edges);

//        for (Node<Integer> node : graph.getNodes()) {
//            System.out.println(node +" : " + graph.startNodeWithEdges.get(node)+"\n");
//        }

        Dijkstra<String> dijkstra = new Dijkstra<>(graph);
//        dijkstra.findShortestPath(graph, nodes.get(0), nodes.get(5));
//        System.out.println(nodes.get(1).getDistance());
//        System.out.println(nodes.get(3).getDistance());
        dijkstra.updateDistance(nodes.get(0), graph);
        System.out.println(nodes.get(0).getDistance());
        System.out.println(nodes.get(1).getDistance());
        System.out.println(nodes.get(2).getDistance());
        System.out.println(dijkstra.getUnvisitedNodes());
        System.out.println(dijkstra.getVisitedNodes());
    }
}
