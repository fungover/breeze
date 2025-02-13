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
        node.setDistance(0);
        Collection<Edge<T>> edges = new ArrayList<>(graph.getEdges(node));
        for (Edge<T> edge : edges) {
            if (isDestinationNodeUnvisited(edge)) {
                System.out.println(edge.getSource());
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

    public void markNodeAsVisited (Node<T> currentNode) {
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
        return unvisitedNodes;
    }

    public List<Node<T>> getVisitedNodes() {
        return visitedNodes;
    }

    /**
     * Prints the path from the start node to the target node
     * @param target the end node
     */
    public void getPath(Node<T> target) {
        List<Node<T>> path = new ArrayList<>();
        Node<T> currentNode = target;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getPreviousNode();
        }

        Collections.reverse(path);
        System.out.println("Path = " + path);
    }

    /**
     * Prints the distance from the start node to the target node
     * @param target the end node
     */
    public void getDistance(Node<T> target) {
        System.out.println("Distance from start to end is " + target.getDistance());
    }

    public void findShortestPathFacit(WeightedGraph<T> graph, Node<T> start, Node<T> end) {
        // Sätt startnodens avstånd till 0, eftersom avståndet från startnoden till sig själv är 0
        start.setDistance(0);

        // Skapa en prioritetskö som prioriterar noder baserat på deras nuvarande avstånd
        PriorityQueue<Node<T>> priorityQueue = new PriorityQueue<>(Comparator.comparing(Node::getDistance));

        // Lägg till startnoden i prioritetskön
        priorityQueue.add(start);

        // Kör en loop så länge prioritetskön inte är tom
        while (!priorityQueue.isEmpty()) {
            // Ta bort och returnera noden med det minsta avståndet från kön
            Node<T> currentNode = priorityQueue.poll();

            // Om den aktuella noden är slutnoden, avsluta loopen
            if (currentNode.equals(end)) break;

            // Iterera genom alla kanter som är anslutna till den aktuella noden
            for (Edge<T> edge : graph.getEdges(currentNode)) {
                // Hämta målnoden som är ansluten genom kanten
                Node<T> adjacentNode = edge.getDestination();

                // Beräkna det nya möjliga avståndet till den angränsande noden
                double newDist = currentNode.getDistance() + edge.getWeight();

                // Om det nya beräknade avståndet är kortare än det nuvarande lagrade avståndet för den angränsande noden
                if (newDist < adjacentNode.getDistance()) {
                    // Uppdatera den angränsande nodens avstånd med det nya kortare avståndet
                    adjacentNode.setDistance(newDist);

                    // Sätt den aktuella noden som föregående nod för den angränsande noden
                    adjacentNode.setPreviousNode(currentNode);

                    // Lägg till den angränsande noden i prioritetskön så att den kan behandlas senare
                    priorityQueue.add(adjacentNode);

//
//                    public void findAllShortestPaths (WeightedGraph < T > graph, Node < T > start){

                    }

                }
            }
        }
    }