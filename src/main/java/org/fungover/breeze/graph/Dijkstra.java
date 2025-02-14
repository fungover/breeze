package org.fungover.breeze.graph;

import java.util.*;

public class Dijkstra<T> {

    public List<Node<T>> unvisitedNodes = new ArrayList<>();
    public List<Node<T>> visitedNodes = new ArrayList<>();
    public WeightedGraph<T> graph;

    public Dijkstra(WeightedGraph<T> graph) {
        unvisitedNodes.addAll(graph.getNodes());
        this.graph = graph;
    }

    /**
     * Updates the distance for each unvisited destination node connected to the current node
     * when sum of source node and weight is less than the destinations distance
     * @param node the current node being processed
     * @param graph the weighted graph containing the nodes and edges
     */
    public void updateDistance(Node<T> node, WeightedGraph<T> graph) {
        Collection<Edge<T>> edges = graph.getEdges(node);
        for (Edge<T> edge : edges) {
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

    public boolean isDestinationNodeUnvisited(Edge<T> edge) {
        return unvisitedNodes.contains(edge.getDestination());
    }


    public void setPreviousNode(Node<T> node, Edge<T> edge) {
        edge.getDestination().setPreviousNode(node);
    }

    public void markNodeAsVisited(Node<T> currentNode) {
        visitedNodes.add(currentNode);
        unvisitedNodes.remove(currentNode);
    }

    /**
     * Finds the unvisited node with the shortest distance
     * @return an Optional containing the unvisited node with the shortest distance,
     * or an empty Optional if no unvisited nodes remain
     */
    public Optional<Node<T>> findShortestUnvisitedDistance() {
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
        start.setDistance(0);
        Node<T> currentNode = start;

        while (!unvisitedNodes.isEmpty()) {
            updateDistance(currentNode, graph);

            if (currentNode.equals(end)) {
                break;
            }

            Optional<Node<T>> optionalNode = findShortestUnvisitedDistance();
            if (optionalNode.isEmpty()) {
                break;
            }
            currentNode = optionalNode.get();
        }
    }

    /**
     * Finds the shortest paths from the start node to all other nodes
     * @param graph the weighted graph containing the nodes and edges
     * @param start the starting node
     */
    public void findAllShortestPaths(WeightedGraph<T> graph, Node<T> start) {
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

    public List<Node<T>> getUnvisitedNodes() {
        unvisitedNodes.forEach(System.out::println);
        return unvisitedNodes;
    }

    public List<Node<T>> getVisitedNodes() {
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
        return target.getDistance();
    }
}
