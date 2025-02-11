package graph;

import java.util.*;

public class WeightedGraph<T> implements Graph<T> {
    public List<Node<T>> listOfNodes = new ArrayList<>();
    public List<Edge<T>> listOfEdges = new ArrayList<>();
    public Map<Node<T>, List<Edge<T>>> nodesWithEdges = new HashMap<>();

    public WeightedGraph(List<Node<T>> nodes, List<Edge<T>> edges) {
        this.listOfNodes.addAll(nodes);
        this.listOfEdges.addAll(edges);
    }

    public void addNode(Node<T> node) {
        listOfNodes.add(node);
    }

    public WeightedGraph<T> addEdge(Edge<T> edge) {
        listOfEdges.add(edge);
        return this;
    }

    public Map<Node<T>, List<Edge<T>>> getNodesWithEdges() {
        return nodesWithEdges;
    }

    @Override
    public Collection<Node<T>> getNodes() {
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

    public void linkNodeWithEdges(Node<T> node) {
        List<Edge<T>> edges = new ArrayList<>();
        for (Edge<T> edge : listOfEdges) {
            if (edge.getSource().equals(node)) {
                Node<T> start = edge.getSource();
                edges.add(edge);
                nodesWithEdges.put(start, edges);
            }
        }
    }

    public void mapNodesWithEdges(Collection<Node<T>> listOfNodes) {
        for (Node<T> node : listOfNodes) {
            linkNodeWithEdges(node);
        }
    }

}
