package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra<T> {

    public List<Node<T>> unvisitedNodes = new ArrayList<>();
    public List<Node<T>> visitedNodes = new ArrayList<>();

    public Dijkstra(Graph<T> graph) {
        unvisitedNodes.addAll(graph.getNodes());
    }

    public void findShortestPath(WeightedGraph<T> graph, Node<T> start, Node<T> end) {
        start.setDistance(0);


//        while (!unvisitedNodes.isEmpty())
//           if(unvisited.equals(end) break;

        for (Edge<T> edge : graph.getEdges(start)) {
            edge.getDestination().setDistance(edge.getWeight());
        }


//        }
    }


    public void getPath(Node<T> target) {

    }

    public void getDistance(Node<T> target) {

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