package graph;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra<T> {

    public List<Node<T>> unvisitedNodes = new ArrayList<>();
    public List<Node<T>> visitedNodes = new ArrayList<>();

    public Dijkstra (Graph<T> graph) {
        unvisitedNodes.addAll(graph.getNodes());
    }

    public void findShortestPath(Graph<T> graph, Node<T> start, Node<T> end) {

    }

   public void findAllShortestPaths(Graph<T> graph, Node<T> start){

   }

   public void getPath(Node<T> target) {

   }

   public void getDistance(Node<T> target) {

   }

}

