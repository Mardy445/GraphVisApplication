package Models;

import java.io.Serializable;
import java.util.*;

public class GraphState implements Serializable {

    private List<GraphNode> nodes;
    private List<GraphEdge> edges;
    private Set<GraphNode> selectedNodes;
    private Set<GraphEdge> selectedEdges;
    private boolean isDirected;
    private boolean isWeighted;

    public GraphState(List<GraphNode> nodes, List<GraphEdge> edges, Set<GraphNode> selectedNodes, Set<GraphEdge> selectedEdges){
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>(edges);
        this.selectedNodes = new HashSet<>(selectedNodes);
        this.selectedEdges = new HashSet<>(selectedEdges);
    }

    public GraphState(List<GraphNode> nodes, List<GraphEdge> edges, Set<GraphNode> selectedNodes, Set<GraphEdge> selectedEdges, boolean isDirected, boolean isWeighted){
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>(edges);
        this.selectedNodes = new HashSet<>(selectedNodes);
        this.selectedEdges = new HashSet<>(selectedEdges);
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
    }

    public List<GraphNode> getNodes(){
        return nodes;
    }

    public List<GraphEdge> getEdges(){
        return edges;
    }

    public Set<GraphNode> getSelectedNodes(){
        return selectedNodes;
    }

    public Set<GraphEdge> getselectedEdges(){
        return selectedEdges;
    }

    public boolean getIsDirected(){
        return isDirected;
    }

    public boolean getIsWeighted(){
        return isWeighted;
    }
}
