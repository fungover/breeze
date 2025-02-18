package org.fungover.breeze.graph;

import java.util.*;

public class WeightedGraph<T> implements Graph<T> {
    public List<Node<T>> listOfNodes = new ArrayList<>();
    public List<Edge<T>> listOfEdges = new ArrayList<>();
    public Map<Node<T>, List<Edge<T>>> nodesWithEdges = new HashMap<>();

    public WeightedGraph(List<Node<T>> nodes, List<Edge<T>> edges) {
        this.listOfNodes.addAll(nodes);
        this.listOfEdges.addAll(edges);
        mapNodesWithEdges(getNodes());
    }

    public void addNode(Node<T> node) {
        listOfNodes.add(node);
    }

    public WeightedGraph<T> addEdge(Edge<T> edge) {
        listOfEdges.add(edge);
        return this;
    }

    @Override
    public List<Node<T>> getNodes() {
        return listOfNodes;
    }

    @Override
    public List<Edge<T>> getEdges(Node<T> node) {
        List<Edge<T>> edges = new ArrayList<>();
        for (Edge<T> edge : listOfEdges)
            if (edge.getSource().equals(node)) {
                edges.add(edge);
            }
        return edges;
    }
}
