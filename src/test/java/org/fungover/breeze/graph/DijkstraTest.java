package org.fungover.breeze.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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

        graph = new  WeightedGraph<>(nodes, edges);
        dijkstra = new Dijkstra<>(graph);

        //Update start node to 0
        nodes.get(0).setDistance(0);
    }

    @Test
    @DisplayName("UpdateDistance should update distance of destinations if sum of source and weight is less")
    void updateDistanceShouldUpdateDistanceOfDestinationsIfSumOfSourceAndWeightIsLess() {
        double expectedSourceDistance = 0;
        double expectedDistanceB = 5;
        double expectedDistanceC = 2;

        dijkstra.updateDistance(nodes.get(0), graph);

        assertAll(
                "Distances A, B and C",
                () -> assertThat(nodes.get(0).getDistance()).isEqualTo(expectedSourceDistance),
                () -> assertThat(nodes.get(1).getDistance()).isEqualTo(expectedDistanceB),
                () -> assertThat(nodes.get(2).getDistance()).isEqualTo(expectedDistanceC)
        );
    }

    @Test
    @DisplayName("MarkNodeAsVisited should add current node to visited")
    void markNodeAsVisitedShouldAddCurrentNodeToVisited() {
        Node<String> nodeA = nodes.get(0);

        dijkstra.markNodeAsVisited(nodeA);
        boolean visitedNodeContainsA = dijkstra.visitedNodes.contains(nodeA);

        assertThat(visitedNodeContainsA).isEqualTo(true);
    }

    @Test
    @DisplayName("MarkNodeAsVisited should remove current node from unvisited")
    void markNodeAsVisitedShouldRemoveCurrentNodeFromUnvisited() {
        Node<String> nodeA = nodes.get(0);

        dijkstra.markNodeAsVisited(nodeA);
        boolean unvisitedNodeDoesNotContainA = dijkstra.unvisitedNodes.contains(nodeA);

        assertThat(unvisitedNodeDoesNotContainA).isEqualTo(false);
    }

    @Test
    @DisplayName("FindShortestUnvisitedDistance returns node with lowest distance")
    void findShortestUnvisitedDistanceReturnsNodeWithLowestDistance() {
        double nodeC = 2;
        dijkstra.updateDistance(nodes.get(0), graph);

        Optional<Node<String>> lowestNode = dijkstra.findShortestUnvisitedDistance();
        assertThat(lowestNode.get().getDistance()).isEqualTo(nodeC);
    }

    @Test
    @DisplayName("SetPreviousNode updates destination with it´s former node")
    void setPreviousNodeUpdatesDestinationWithItSFormerNode() {
        Node<String> expectedFormerNode = nodes.get(0);

        dijkstra.setPreviousNode(nodes.get(0), edges.get(0));

        assertThat(nodes.get(2).getPreviousNode()).isEqualTo(expectedFormerNode);
    }

    @Test
    @DisplayName("SourceNodeExistInUnvisitedNodes return true if node exist")
    void sourceNodeExistInUnvisitedNodesReturnTrueIfNodeExist() {

        boolean nodeExists = dijkstra.isSourceNodeUnvisited(edges.get(0));

        assertThat(nodeExists).isEqualTo(true);
    }

    @Test
    @DisplayName("findShortestPath should stop when reaching end node")
    void findShortestPathShouldStopWhenReachingEndNode() {
        Node<String> end = nodes.getLast();
        Node<String> start = nodes.getFirst();

        double expectedEndDistance = 8;

        dijkstra.findShortestPath(graph, start, end);

        assertThat(end.getDistance()).isEqualTo(expectedEndDistance);
    }


    @Test
    @DisplayName("UnvisitedNodes should be empty after running findShortestPath")
    void unvisitedNodesShouldBeEmptyAfterRunningFindShortestPath() {
        Node<String> end = nodes.getLast();
        Node<String> start = nodes.getFirst();
        boolean emptyList = true;

        dijkstra.findShortestPath(graph, start, end);

        assertThat(dijkstra.getUnvisitedNodes().isEmpty()).isEqualTo(emptyList);
    }

}