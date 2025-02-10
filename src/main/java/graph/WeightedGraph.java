package graph;

import java.util.*;

public class WeightedGraph implements Graph {
    public List<Node<Integer>> listOfNodes = new ArrayList<>();
    public List<Edge<Integer>> listOfEdges = new ArrayList<>();

    public WeightedGraph<T> addNode(Node<T> node) {
        listOfNodes.add(node);
        return this;
    }

    public WeightedGraph<T> addEdge(Edge<T> edge) {
        listOfEdges.add(edge);
        return this;
    }

    @Override
    public Collection<Node<Integer>> getNodes() {
        return listOfNodes;
    }

    @Override
    public Collection<Edge<T>> getEdges(Node<T> node) {
        List<Edge<T>> edges = new ArrayList<>();
        for (Edge<T> edge : listOfEdges)
            if (edge.getSource().equals(node)) {
                edges.add(edge);
            }
        return edges;
    }

    public void mapSourceNodeWithEdges(Node<T> node) {
        List<Edge<T>> edges = new ArrayList<>();
        for (Edge<T> edge : listOfEdges) {
            if (edge.getSource().equals(node)) {
                Node<T> start = edge.getSource();
                edges.add(edge);
                startNodeWithEdges.put(start, edges);
            }
        }
    }

    public void mapSourceNodesWithEdges(Collection<Node<T>> listOfNodes) {
        for (Node<T> node : listOfNodes) {
            sourceNodeWithEdges(node);
        }
    }
}
