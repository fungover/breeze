package org.fungover.breeze.graph;

import java.util.*;

public class Dijkstra<T> {

    private final List<Node<T>> unvisitedNodes = new ArrayList<>();
    private final List<Node<T>> visitedNodes = new ArrayList<>();

    public Dijkstra() {
    }

    /**
     * Updates the distance for each unvisited destination node connected to the current node
     * when sum of source node and weight is less than the destinations distance
     * @param node the current node being processed
     * @param graph the weighted graph containing the nodes and edges
     */
    void updateDistance(Node<T> node, WeightedGraph<T> graph) {
        Collection<Edge<T>> edges = graph.getEdges(node);
        for (Edge<T> edge : edges) {
            if (edge.getSource().equals(edge.getDestination())) {
                continue;
            }
            if (isDestinationNodeUnvisited(edge)) {
                double sum = edge.getSource().getDistance() + edge.getWeight();

                if (sum < edge.getDestination().getDistance()) {
                    edge.getDestination().setDistance(sum);
                    setPreviousNode(node, edge);
                }
            }
        }
        markNodeAsVisited(node);
    }


    boolean isDestinationNodeUnvisited(Edge<T> edge) {
        return unvisitedNodes.contains(edge.getDestination());
    }


    void setPreviousNode(Node<T> node, Edge<T> edge) {
        edge.getDestination().setPreviousNode(node);
    }

    void markNodeAsVisited(Node<T> currentNode) {
        visitedNodes.add(currentNode);
        unvisitedNodes.remove(currentNode);
    }

    /**
     * Finds the unvisited node with the shortest distance
     * @return an Optional containing the unvisited node with the shortest distance,
     * or an empty Optional if no unvisited nodes remain
     */
    Optional<Node<T>> findShortestUnvisitedDistance() {
        return unvisitedNodes.stream()
                .min(Comparator.comparingDouble(Node::getDistance));
    }

    /**
     * Finds the shortest path from the start node to the end node
     * @param graph the weighted graph containing the nodes and edges
     * @param start the starting node
     * @param end the end node
     */
    public void findShortestPath(WeightedGraph<T> graph, Node<T> start, Node<T> end) {
        if (graph == null || start == null || end == null) {
            throw new IllegalArgumentException("Graph, start and end node cannot be null");
        }
        resetGraphState(graph);
        start.setDistance(0);
        Node<T> currentNode = start;

        while (!unvisitedNodes.isEmpty() && !currentNode.equals(end)) {
            updateDistance(currentNode, graph);

            Optional<Node<T>> optionalNode = findShortestUnvisitedDistance();
            if (optionalNode.isEmpty() || optionalNode.get().getDistance() == Double.MAX_VALUE) {
                break;
            }

            currentNode = optionalNode.get();
        }
    }

    /**
     * Handles cyclic graphs by resetting the distance and previous node information for all nodes
     * in the graph. This method ensures that Dijkstra's algorithm can be run multiple times without
     * interference from previous calculations. It clears the list of unvisited nodes and repopulates
     * it with all nodes in the graph.
     *
     * @param graph the weighted graph containing the nodes and edges
     */
    void resetGraphState(WeightedGraph<T> graph) {
        for (Node<T> node : graph.getNodes()) {
            node.setDistance(Double.MAX_VALUE);
            node.setPreviousNode(null);
        }

        visitedNodes.clear();
        unvisitedNodes.clear();
        unvisitedNodes.addAll(graph.getNodes());
    }

    /**
     * Finds the shortest paths from the start node to all other nodes
     * @param graph the weighted graph containing the nodes and edges
     * @param start the starting node
     */
    public void findAllShortestPaths(WeightedGraph<T> graph, Node<T> start) {
        if (graph == null || start == null) {
            throw new IllegalArgumentException("Graph and start node cannot be null");
        }
        resetGraphState(graph);
        start.setDistance(0);
        Node<T> currentNode = start;

        while (!unvisitedNodes.isEmpty()) {
            updateDistance(currentNode, graph);

            Optional<Node<T>> optionalNode = findShortestUnvisitedDistance();
            if (optionalNode.isEmpty()) {
                break;
            }
            currentNode = optionalNode.get();
        }
    }

    List<Node<T>> getUnvisitedNodes() {
        return unvisitedNodes;
    }

    List<Node<T>> getVisitedNodes() {
        return visitedNodes;
    }

    /**
     * Returns the path from the start node to the target node.
     * The path is represented as a list, starting from the start node to the target node
     *
     * @param target the end node for which the path is needed
     * @return the list containing the nodes from the start node to the target node
     */
    public List<Node<T>> getPath(Node<T> target) {
        if (target == null) {
            throw new IllegalArgumentException("Target node cannot be null");
        }
        List<Node<T>> path = new ArrayList<>();
        Node<T> currentNode = target;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getPreviousNode();
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Return distance from the target node
     *
     * @param target the end node
     * @return the target nodes distance
     */
    public double getDistance(Node<T> target) {
        if (target == null) {
            throw new IllegalArgumentException("Target node cannot be null");
        }
        return target.getDistance();
    }
}
