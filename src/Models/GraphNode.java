package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
This class represents a node on the class.
 */
public class GraphNode extends GraphElements {

    //The id of the node
    private String id;

    //The position of the node
    private ElementPosition position;

    private List<GraphEdge> connectingEdges;
    private List<GraphEdge> accesableEdges;

    /*
    The constructor.
     */
    public GraphNode(ElementPosition position, String id){
        this.position = position;
        this.id = id;
        connectingEdges = new ArrayList<>();
    }

    /*
    Getter: Returns the position of the node.
     */
    public ElementPosition getPosition(){
        return position;
    }

    /*
    Setter: Sets the position of the node.
     */
    public void setPosition(ElementPosition position){
        this.position = position;
    }

    /*
    Getter: Returns the nodes ID.
     */
    public String getId(){
        return id;
    }

    public void addEdge(GraphEdge edge){
        connectingEdges.add(edge);
    }

    public void removeEdge(GraphEdge edge){
        connectingEdges.remove(edge);
    }

    public List<GraphEdge> getConnectingEdges(){
        return connectingEdges;
    }

    public int getDegree(){
        return connectingEdges.size();
    }

    @Override
    public String toString(){
        return id;
    }
}
