package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WeightedGraph implements Graph {
    public List<Node<Integer>> listOfNodes = new ArrayList<>();
    public List<Edge<Integer>> listOfEdges = new ArrayList<>();

    public WeightedGraph addNode (Node<Integer> node) {
        listOfNodes.add(node);
        return this;
    }

    public WeightedGraph addEdge (Edge<Integer> edge) {
        listOfEdges.add(edge);
        return this;
    }

    @Override
    public Collection<Node<Integer>> getNodes() {
        return listOfNodes;
    }

    @Override
    public Collection<Edge<Integer>> getEdges(Node<Integer> node) {

        return List.of();
    }
}
