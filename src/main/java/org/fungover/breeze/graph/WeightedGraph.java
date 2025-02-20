package org.fungover.breeze.graph;

import java.util.*;

/**
 * Represents a weighted graph.
 *
 * @param <T> the type of data stored in the nodes of the graph.
 */
public class WeightedGraph<T> implements Graph<T> {
    private List<Node<T>> listOfNodes = new ArrayList<>();
    private List<Edge<T>> listOfEdges = new ArrayList<>();

    /**
     * Constructs a weighted graph with the specified nodes and edges.
     *
     * @param nodes the list of nodes in the graph.
     * @param edges the list of edges in the graph.
     */
    public WeightedGraph(List<Node<T>> nodes, List<Edge<T>> edges) {
        this.listOfNodes.addAll(nodes);
        this.listOfEdges.addAll(edges);
    }

    /**
     * Returns the list of nodes in the graph.
     *
     * @return the list of nodes in the graph.
     */
    @Override
    public List<Node<T>> getNodes() {
        return listOfNodes;
    }

    /**
     * Returns the list of edges connected to the specified node.
     *
     * @param node the node whose connected edges are to be returned.
     * @return the list of edges connected to the specified node.
     */
    @Override
    public List<Edge<T>> getEdges(Node<T> node) {
        List<Edge<T>> edges = new ArrayList<>();
        for (Edge<T> edge : listOfEdges) {
            if (edge.getSource().equals(node)) {
                edges.add(edge);
            }
        }
        return edges;
    }
}
