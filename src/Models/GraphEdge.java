package Models;

import java.util.List;

/*
This class represents an edge on the graph.
 */
public class GraphEdge extends GraphElements{

    //The weight of the edge. Null if graph unweighted.
    private Float weight;

    //The index of the node that point towards the other node. Null if undirected graph.
    private Integer indexOfStartNode;

    //The 2 nodes that this edge connects to.
    private GraphNode[] nodes;

    /*
    Constructor
     */
    public GraphEdge(GraphNode node1, GraphNode node2, Float weight, Integer direction){
        nodes = new GraphNode[] {node1,node2};
        this.weight = weight;
        this.indexOfStartNode = direction;
    }

    /*
    Getter: Returns the node of index 0
     */
    public GraphNode getFirstNode(){
        return nodes[0];
    }

    /*
    Getter: Returns the node of index 1
     */
    public GraphNode getSecondNode(){
        return nodes[1];
    }

    /*
    Getter: Returns the weight
     */
    public Float getWeight(){
        return weight;
    }

    /*
    Getter: Returns the start node if applicable
     */
    public GraphNode getStartNode(){
        if(indexOfStartNode != null) {
            return nodes[indexOfStartNode];
        }
        return null;
    }

    /*
    Getter: Returns the alternate node if applicable.
     */
    public GraphNode getAlternateNode(GraphNode node){
        if(node == nodes[0]) {
            return nodes[1];
        }
        else if (node == nodes[1]){
            return nodes[0];
        }
        return null;
    }

    public Integer getIndexOfStartNode(){
        return indexOfStartNode;
    }

    /*
    Flips the direction of the arrow if possible.
     */
    public void flipDirection(){
        if(indexOfStartNode != null) {
            indexOfStartNode = (indexOfStartNode + 1) % 2;
        }
    }

    public void setWeight(Float weight){
        this.weight = weight;
    }

    public void deleteEdgeReferencesOffNodes(){
        nodes[0].removeEdge(this);
        nodes[1].removeEdge(this);
    }

}
