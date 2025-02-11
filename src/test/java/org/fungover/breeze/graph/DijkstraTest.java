package org.fungover.breeze.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DijkstraTest {

    Dijkstra<String> dijkstra;
    WeightedGraph<String> graph;
    List<Node<String>> nodes;
    List<Edge<String>> edges;

    @BeforeEach
    void setUp() {
        nodes = List.of(
                new Node<>("A"), new Node<>("B"), new Node<>("C"),
                new Node<>("D"), new Node<>("E"), new Node<>("F")
        );

         edges = List.of(
                new Edge<>(nodes.get(0), nodes.get(2), 6),  //A to C
                new Edge<>(nodes.get(0), nodes.get(1), 8),  //A to B
                new Edge<>(nodes.get(1), nodes.get(2), 4),  //B to C
                new Edge<>(nodes.get(1), nodes.get(3), 2),  //B to D
                new Edge<>(nodes.get(1), nodes.get(4), 5),  //B to E
                new Edge<>(nodes.get(2), nodes.get(4), 2)   //C to E
        );

        graph = new  WeightedGraph<>(nodes, edges);
        dijkstra = new Dijkstra<>(graph);
    }

    @Test
    @DisplayName("UpdateDistance should update distance of destinations if sum of source and weight is less")
    void updateDistanceShouldUpdateDistanceOfDestinationsIfSumOfSourceAndWeightIsLess() {
        double expectedSourceDistance = 0;
        double expectedDistanceB = 8;
        double expectedDistanceC = 6;

        dijkstra.updateDistance(nodes.get(0), graph);

        assertAll(
                "Distances A, B and C",
                () -> assertThat(nodes.get(0).getDistance()).isEqualTo(expectedSourceDistance),
                () -> assertThat(nodes.get(1).getDistance()).isEqualTo(expectedDistanceB),
                () -> assertThat(nodes.get(2).getDistance()).isEqualTo(expectedDistanceC)
        );
    }

}