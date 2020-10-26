package Models;

import Controllers._MainController;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

/*
The main model class. This class represents the model component of the MVC design pattern.
 */
public class _MainModel{

    private boolean graphStateSaved = false;
    private boolean graphSaved = false;
    private File currentGraphFile = null;

    private List<GraphEdge> edges;
    private List<GraphNode> nodes;

    private Set<GraphNode> selectedNodes;
    private Set<GraphEdge> selectedEdges;
    private ElementPosition selectedPoint;

    private boolean isShiftHeld = false;
    private boolean isElementBeingDragged = false;

    private boolean isGraphDirected;
    private boolean isGraphWeighted;

    private FixedStack<GraphState> undoStack;
    private FixedStack<GraphState> redoStack;

    /*
    TEMPORARY CONSTRUCTOR FOR TESTING.
     */
    public _MainModel(){
        edges = new ArrayList<>();
        nodes = new ArrayList<>();
        selectedNodes = new LinkedHashSet<>();
        selectedEdges = new LinkedHashSet<>();
        undoStack = new FixedStack<>(20);
        redoStack = new FixedStack<>(20);
        isGraphDirected = false;
        isGraphWeighted = false;

    }

    /*
    This method is called whenever the mouse is clicked on the graph panel.
    It passes in the x and y value of the relative cursor location and uses other methods to calculate what was clicked.
     */
    public void graphPressed(int x, int y){
        //If shift is not held, then we want no other selection other than what will be selected.
        if(!isShiftHeld){
            deselectAll();
        }

        //Attempts to first see if a node was clicked (nodes take precedence over edges).
        GraphNode holdNode = checkIfNodeSelected(x,y);

        //If a node was clicked, add it to the selections.
        if(holdNode != null) {
            holdNode.setSelected(true);
            selectedNodes.add(holdNode);
        }
        //If no node was clicked, see if any edges were clicked.
        else {
            checkIfEdgeSelected(x,y);
        }

        //Moves the selection point to this location.
        selectedPoint = new ElementPosition(x, y);

        //Check if any edge had both its nodes selected. If so, select the edge.
        checkIfEdgesNodesSelected();
    }

    /*
    Deselect all currently selected elements.
     */
    private void deselectAll(){
        for(GraphNode node : selectedNodes){
            node.setSelected(false);
        }
        for(GraphEdge edge : selectedEdges){
            edge.setSelected(false);
        }
        selectedNodes = new HashSet<>();
        selectedEdges = new HashSet<>();
    }

    /*
    Performs the necessary operations to check all nodes and see which, if any, were clicked.
     */
    private GraphNode checkIfNodeSelected(int x, int y){
        GraphNode holdNode = null;
        for(GraphNode node : nodes){
            if(isNodeSelected(x,y,node)){
                holdNode = node;
            }
        }
        return holdNode;
    }

    /*
    Performs the necessary operations to check all edges and see which, if any, were clicked.
     */
    private void checkIfEdgeSelected(int x, int y){
        for (GraphEdge edge : edges) {
            if (isEdgeSelected(x, y, edge)) {
//                edge.getFirstNode().setSelected(true);
//                edge.getSecondNode().setSelected(true);
//                selectedNodes.add(edge.getFirstNode());
//                selectedNodes.add(edge.getSecondNode());
                edge.setSelected(true);
                selectedEdges.add(edge);
            }
        }
    }

    /*
    Checks if both of an edges nodes were selected.
    If so, select the edge.
     */
    private void checkIfEdgesNodesSelected(){
        for(GraphEdge edge : edges){
            if (edge.getFirstNode().isSelected() || edge.getSecondNode().isSelected()){
                edge.setSelected(true);
                selectedEdges.add(edge);
            }
        }
    }

    /*
    Uses pythagoras theorem to see if a node was clicked.
     */
    private boolean isNodeSelected(int x, int y, GraphNode node){
        int a = node.getPosition().x() - x;
        int b = node.getPosition().y() - y;
        double c = Math.sqrt((a*a)+(b*b));
        return c <= _MainController.NODE_SIZE/2;
    }

    /*
    Uses the line equation to see if an edge was clicked.
     */
    private boolean isEdgeSelected(int x, int y, GraphEdge edge){
        int lenience = _MainController.EDGE_THICKNESS;

        if(!((x >= edge.getFirstNode().getPosition().x()-lenience && x <= edge.getSecondNode().getPosition().x()+lenience) ||
                (x <= edge.getFirstNode().getPosition().x()+lenience && x >= edge.getSecondNode().getPosition().x()-lenience))){
            return false;
        }

        double m = (double)(edge.getFirstNode().getPosition().y() - edge.getSecondNode().getPosition().y())/
                (double)(edge.getFirstNode().getPosition().x() - edge.getSecondNode().getPosition().x());

        double c = (double) edge.getFirstNode().getPosition().y() - (m * (double) edge.getFirstNode().getPosition().x());

        double calculatedY = (m * x) + c;

        return y - calculatedY < lenience && y - calculatedY >= -lenience;
    }

    /*
    If the mouse is dragged, drag all selected elements by the mouse offset.
     */
    public void dragSelected(int x, int y){
        int xOffset = selectedPoint.x()-x;
        int yOffset = selectedPoint.y()-y;

        if(!isElementBeingDragged && selectedNodes.size()>0){
            pushGraphState();
        }

        ElementPosition position;
        for(GraphNode node : nodes){
            if(node.isSelected()){
                position = node.getPosition();
                node.setPosition(new ElementPosition(position.x()-xOffset,position.y()-yOffset));
                isElementBeingDragged = true;
            }
        }
        selectedPoint = new ElementPosition(x,y);
    }

    /*
    Getter: Is an element currently being dragged?
     */
    public boolean isElementBeingDragged(){
        return isElementBeingDragged;
    }

    /*
    Signals to the model that the user has released the mouse button after a drag
     */
    public void signalDragEnd(){
        isElementBeingDragged = false;
    }

    /*
    Getter: Returns the number of currently selected nodes.
     */
    public int getSelectedNodeCount(){
        return selectedNodes.size();
    }

    /*
    Getter: Returns the number of currently selected edges.
     */
    public int getSelectedEdgeCount(){return selectedEdges.size();}

    /*
    Getter: Returns all nodes.
     */
    public List<GraphNode> getNodes(){
        return nodes;
    }

    /*
    Getter: Returns all edges.
     */
    public List<GraphEdge> getEdges(){
        return edges;
    }

    /*
    Getter: Returns the selection point.
     */
    public ElementPosition getSelectedPoint(){
        return selectedPoint;
    }

    /*
    Adds a new node to the model.
     */
    public void addNode(String id){
        pushGraphState();
        if(selectedPoint == null){
            nodes.add(new GraphNode(new ElementPosition(), id));
        }
        else {
            nodes.add(new GraphNode(selectedPoint,id));
        }

    }

    /*
    Adds a new edge to the model.
     */
    public void addEdge(Float weight){
        pushGraphState();
        Iterator<GraphNode> iterator = selectedNodes.iterator();
        GraphEdge edge = new GraphEdge(iterator.next(), iterator.next(),weight,isGraphDirected ? 0 : null);
        edges.add(edge);
        edge.getFirstNode().addEdge(edge);
        edge.getSecondNode().addEdge(edge);
    }

    /*
    Flips the direction of an edge if possible
     */
    public boolean flipSelectedEdge(){
        if(selectedEdges.size()==1 && isGraphDirected){
            selectedEdges.iterator().next().flipDirection();
            return true;
        }
        return false;
    }

    public void editSelectedEdgeWeight(Float weight){
        if(selectedEdges.size()==1 && isGraphWeighted){
            selectedEdges.iterator().next().setWeight(weight);
        }
    }

    /*
    Checks if a given ID is available for use.
     */
    public boolean isNodeIdAvailable(String id){
        for(GraphNode node : nodes){
            if(node.getId().equals(id)){
                return false;
            }
        }
        return true;
    }

    /*
    Getter: Returns isGraphDirected.
     */
    public boolean getIsGraphDirected(){
        return isGraphDirected;
    }

    /*
    Getter: Returns isGraphWeighted.
     */
    public boolean getIsGraphWeighted(){
        return isGraphWeighted;
    }

    /*
    Setter: Sets isGraphDirected.
     */
    public void setIsGraphDirected(boolean isGraphDirected){
        this.isGraphDirected = isGraphDirected;
    }

    /*
    Setter: Sets isGraphWeighted.
     */
    public void setIsGraphWeighted(boolean isGraphWeighted){
        this.isGraphWeighted = isGraphWeighted;
    }

    /*
    Deletes all selected nodes and edges.
     */
    public void deleteSelected(){
        pushGraphState();
        for(GraphEdge edge : selectedEdges){
            edge.deleteEdgeReferencesOffNodes();
            edges.remove(edge);
        }
        for(GraphNode node : selectedNodes){
            edges.removeAll(node.getConnectingEdges());
            nodes.remove(node);
        }
        deselectAll();
    }

    /*
    Setter: Sets isShiftHeld.
     */
    public void setShiftHeld(Boolean shiftHeld){
        this.isShiftHeld = shiftHeld;
    }

    private void pushGraphState(){
        graphStateSaved = false;
        undoStack.push(new GraphState(nodes,edges,selectedNodes,selectedEdges));
        redoStack.clearStack();
    }

    /*
    Undo's the graphs last change, reverting it to its previous state on the undo stack.
     */
    public void undo(){
        GraphState topState = undoStack.pop();
        if (topState == null){

            return;
        }
        redoStack.push(new GraphState(nodes,edges,selectedNodes,selectedEdges));
        loadGraphState(topState);

    }

    /*
    If the undo feature was recently used, the redo feature will revert the change of the last undo.
     */
    public void redo(){
        GraphState topState = redoStack.pop();
        if (topState == null){
            return;
        }
        loadGraphState(topState);

    }

    /*
    Handles the saving of a given file.
     */
    public void passFileForSaving(File file){
        if(file != null){
            try {
                saveGraphStateAs(file);
            } catch (IOException ef){
                ef.printStackTrace();
            }
        }
    }

    /*
    Loads a given graph state to the model.
     */
    private void loadGraphState(GraphState state){
        nodes = state.getNodes();
        edges = state.getEdges();
        selectedNodes = state.getSelectedNodes();
        selectedEdges = state.getselectedEdges();
    }

    /*
    Saves the current graph state in the given file.
     */
    public void saveGraphStateAs(File file) throws IOException {
        FileOutputStream fOut = new FileOutputStream(file);
        ObjectOutputStream oOut = new ObjectOutputStream(fOut);
        oOut.writeObject(new GraphState(nodes,edges,selectedNodes,selectedEdges,isGraphDirected,isGraphWeighted));
        oOut.close();
        fOut.close();

        graphSaved = true;
        graphStateSaved = true;
        currentGraphFile = file;
    }

    /*
    Saves the current graph state in the current graph file associated with this graph.
     */
    public void saveGraphState() throws IOException{
        saveGraphStateAs(currentGraphFile);
    }

    /*
    Opens a graph state from a specified file and loads it to the model.
     */
    public void openGraphStateFromFile(File file) throws IOException, ClassNotFoundException{
        FileInputStream fIn = new FileInputStream(file);
        ObjectInputStream oIn = new ObjectInputStream(fIn);
        GraphState state = (GraphState) oIn.readObject();
        oIn.close();
        fIn.close();
        loadGraphState(state);
        this.isGraphDirected = state.getIsDirected();
        this.isGraphWeighted = state.getIsWeighted();

        graphSaved = true;
        graphStateSaved = true;
        currentGraphFile = file;
    }

    /*
    Getter: Is the graph saved to a file.
     */
    public boolean isGraphSaved(){
        return graphSaved;
    }

    /*
    Getter: Is the current state of the graph saved to its pre specified file.
     */
    public boolean isGraphStateSaved(){
        return graphStateSaved;
    }

    /*
    Resets the graph data in the model.
     */
    public void resetGraph(){
        graphSaved = false;
        graphStateSaved = false;
        currentGraphFile = null;
        edges = new ArrayList<>();
        nodes = new ArrayList<>();
        selectedNodes = new LinkedHashSet<>();
        selectedEdges = new LinkedHashSet<>();
        undoStack = new FixedStack<>(20);
        redoStack = new FixedStack<>(20);
    }

    /*
    Getter: Returns the number of nodes.
     */
    public int getNodeCount(){
        return nodes.size();
    }

    /*
    Getter: Returns the number of edges.
     */
    public int getEdgeCount(){
        return edges.size();
    }

    /*
    Calculates the number of nodes with odd degree counts.
     */
    private int getOddDegreeNodeCount(){
        int oddDegreeNodes = 0;
        for(GraphNode node : nodes){
            if(node.getDegree() % 2 == 1){
                oddDegreeNodes++;
            }
        }
        return oddDegreeNodes;
    }

    /*
    Returns true if the graph has a eularian cycle.
     */
    public boolean getHasEulerianCycle(){
        return getOddDegreeNodeCount() == 0;
    }

    /*
    Returns true if the graph has a eularian path.
     */
    public boolean getHasEulerianPath(){
        int count = getOddDegreeNodeCount();
        return count == 0 || count == 2;
    }

    /*
    Returns true if the graph is complete (every node is directly connected to every other node).
     */
    public boolean getIsGraphComplete(){
        if(this.isGraphDirected){
            return false;
        }

        for(GraphNode node : nodes){
            if(!(node.getDegree() == nodes.size()-1)){
                return false;
            }
        }
        return edges.size() == (nodes.size() * (nodes.size()-1))/2;
    }

    /*
    Returns true if the graph is connected (every node can be reached from every other node).
     */
    public boolean getIsGraphConnected(){
        List<GraphNode> encounteredNodes = new ArrayList<>();
        if (nodes.size() >= 1){
            encounteredNodes.add(nodes.get(0));
            encounteredNodes = checkNodesEdges(nodes.get(0), encounteredNodes);
            return nodes.size() == encounteredNodes.size();
        }
        else {
            return false;
        }
    }

    /*
    Recursive method that starts from a node and finds every node that can be reached from said node.
     */
    private List<GraphNode> checkNodesEdges(GraphNode currentNode, List<GraphNode> checkedNodes){
        for (GraphEdge edge : currentNode.getConnectingEdges()){
            if(!checkedNodes.contains(edge.getFirstNode())){
                checkedNodes.add(edge.getFirstNode());
                checkedNodes = checkNodesEdges(edge.getFirstNode(),checkedNodes);
            }
            else if(!checkedNodes.contains(edge.getSecondNode())){
                checkedNodes.add(edge.getSecondNode());
                checkedNodes = checkNodesEdges(edge.getSecondNode(),checkedNodes);
            }
        }
        return checkedNodes;
    }

    /*
    Returns true if the graph is planar (no edges cross).
     */
    public boolean getIsGraphPlanar(){
        //The edges being checked
        GraphEdge edge1;
        GraphEdge edge2;

        //The positions of every node relating to the 2 edges
        ElementPosition edge1Pos1;
        ElementPosition edge1Pos2;
        ElementPosition edge2Pos1;
        ElementPosition edge2Pos2;

        //The gradient and intercept of each edge
        float edge1Gradient;
        float edge1YIntercept;
        float edge2Gradient;
        float edge2YIntercept;

        //The x and y coords of the intercept between the 2 edges.
        float lineInterceptX;
        float lineInterceptY;

        //Iterates so that each edge is compared once. This iterates NC2 times where N is the number of edges.
        for(int index1 = 0; index1 < edges.size(); index1++){
            for(int index2 = index1 + 1; index2 < edges.size(); index2++){
                edge1 = edges.get(index1);
                edge2 = edges.get(index2);

                //If the edges share a node, skips this iteration.
                if(edge1.getFirstNode() == edge2.getFirstNode() || edge1.getFirstNode() == edge2.getSecondNode() || edge1.getSecondNode() == edge2.getFirstNode() || edge1.getSecondNode() == edge2.getSecondNode()){
                    continue;
                }

                //Establishes the intercept point by finding the equation of both edges.
                edge1Pos1 = edge1.getFirstNode().getPosition();
                edge1Pos2 = edge1.getSecondNode().getPosition();
                edge2Pos1 = edge2.getFirstNode().getPosition();
                edge2Pos2 = edge2.getSecondNode().getPosition();
                edge1Gradient = getLineGradient(edge1Pos1.x(), -edge1Pos1.y(), edge1Pos2.x(), -edge1Pos2.y());
                edge2Gradient = getLineGradient(edge2Pos1.x(), -edge2Pos1.y(), edge2Pos2.x(), -edge2Pos2.y());
                edge1YIntercept = getLineIntercept(edge1Gradient,edge1Pos1.x(), -edge1Pos1.y());
                edge2YIntercept = getLineIntercept(edge2Gradient,edge2Pos1.x(), -edge2Pos1.y());
                lineInterceptX = (edge2YIntercept-edge1YIntercept)/(edge1Gradient-edge2Gradient);
                lineInterceptY = (edge1Gradient*lineInterceptX) + edge1YIntercept;

                //If the point of intercept happens between both edges nodes x and y coords, then the intersection is visible and hence the graph is not planar.
                if((lineInterceptX > edge1Pos1.x() && lineInterceptX < edge1Pos2.x())||(lineInterceptX < edge1Pos1.x() && lineInterceptX > edge1Pos2.x())){
                    if((lineInterceptY > -edge1Pos1.y() && lineInterceptY < -edge1Pos2.y())||(lineInterceptY < -edge1Pos1.y() && lineInterceptY > -edge1Pos2.y())){
                        if((lineInterceptX > edge2Pos1.x() && lineInterceptX < edge2Pos2.x())||(lineInterceptX < edge2Pos1.x() && lineInterceptX > edge2Pos2.x())){
                            if((lineInterceptY > -edge2Pos1.y() && lineInterceptY < -edge2Pos2.y())||(lineInterceptY < -edge2Pos1.y() && lineInterceptY > -edge2Pos2.y())){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /*
    Static function that returns the gradient of a line given 2 points.
     */
    private static float getLineGradient(float x1, float y1, float x2, float y2){
        return (y1-y2)/(x1-x2);
    }

    /*
    Static function that returns the Y intercept of a line given its gradient and a point.
     */
    private static float getLineIntercept(float m, float x, float y){
        return y-(m*x);
    }


    public HashMap<GraphNode, GraphEdge> djikstrasAlgorithm(GraphNode startNode){
        HashMap<GraphNode,Integer> currentShortestPaths = new HashMap<>();
        HashMap<GraphNode, GraphEdge> previousEdgeInPathToShortestRoute = new HashMap<>();

        List<GraphNode> visited = new ArrayList<>();
        List<GraphNode> unvisited = new ArrayList<>(getNodes());

        for(GraphNode node : getNodes()){
            currentShortestPaths.put(node,Integer.MAX_VALUE);
            previousEdgeInPathToShortestRoute.put(node,null);
        }
        currentShortestPaths.put(startNode,0);

        GraphNode holdNode;
        while (unvisited.size() > 0){
            unvisited = orderNodes(currentShortestPaths,unvisited);
            holdNode = unvisited.get(0);
            unvisited.remove(holdNode);
            for(GraphEdge edge : holdNode.getConnectingEdges()){
                if(edge.getStartNode() == holdNode || edge.getStartNode() == null){
                    if(currentShortestPaths.get(holdNode)+edge.getWeight() < currentShortestPaths.get(edge.getAlternateNode(holdNode))){
                        currentShortestPaths.put(edge.getAlternateNode(holdNode), currentShortestPaths.get(holdNode)+edge.getWeight().intValue());
                        previousEdgeInPathToShortestRoute.put(edge.getAlternateNode(holdNode), edge);
                    }
                }
            }
            visited.add(holdNode);
        }

        return previousEdgeInPathToShortestRoute;
    }

    private List<GraphNode> orderNodes(Map<GraphNode,Integer> currentShortestPaths, List<GraphNode> nodes){
        int j;
        for(int i = 1; i < nodes.size(); i++){
            j = i - 1;
            while(currentShortestPaths.get(nodes.get(i)) < currentShortestPaths.get(nodes.get(j))){
                j--;
                if(j < 0){
                    break;
                }
            }
            j++;
            if (i != j){
                Collections.swap(nodes,i,j);
                i--;
            }
        }
        return nodes;
    }

    public boolean kruskalsAlgorithmCheckNextEdge(GraphEdge edge, List<GraphEdge> MSTConnectedEdges){
        if(MSTConnectedEdges.size() < 2){
            return false;
        }
        List<GraphNode> checkedNodes = new ArrayList<>();
        return kruskalsAlgorithmCheckForCycle(edge.getFirstNode(), edge.getSecondNode(), MSTConnectedEdges, checkedNodes);
    }

    private boolean kruskalsAlgorithmCheckForCycle(GraphNode currentNode, GraphNode expectedNode, List<GraphEdge> MSTConnectedEdges, List<GraphNode> checkedNodes){
        GraphNode nextNode;
        checkedNodes.add(currentNode);
        boolean holdIfCycle;

        for(GraphEdge edge : MSTConnectedEdges){
            nextNode = edge.getAlternateNode(currentNode);
            if(nextNode == expectedNode){
                return true;
            }
            else if(nextNode != null && !checkedNodes.contains(nextNode)){
                holdIfCycle = kruskalsAlgorithmCheckForCycle(nextNode,expectedNode,MSTConnectedEdges,checkedNodes);
                if(holdIfCycle){
                    return true;
                }

            }
        }

        return false;
    }

    public List<GraphEdge> orderEdges(List<GraphEdge> edges){
        int j;
        for(int i = 1; i < edges.size(); i++){
            j = i - 1;
            while(edges.get(i).getWeight() < edges.get(j).getWeight()){
                j--;
                if(j < 0){
                    break;
                }
            }
            j++;
            if (i != j){
                Collections.swap(edges,i,j);
                i--;
            }
        }
        return edges;
    }


    public void highlightGraphElements(List<GraphElements> elements){
        for(GraphElements element : elements){
            element.setHighlighted(true);
        }
    }

    public void highlightGraphElements(GraphElements element){
        element.setHighlighted(true);
    }

    public void highlightGraphElementAsIncorrect(GraphElements element){
        System.out.println(element);
        element.setIncorrectHighlighted(true);
    }

    public void unhighlightAllElements(){
        for (GraphNode node : nodes){
            node.setHighlighted(false);
            node.setIncorrectHighlighted(false);
        }
        for (GraphEdge edge : edges){
            edge.setHighlighted(false);
            edge.setIncorrectHighlighted(false);
        }
    }

    /*
    Recursive method finds the factorial of a number.
     */
    private static BigInteger factorial(BigInteger n, int i){
        return i == 1 ? n : factorial((n).multiply(BigInteger.valueOf(i-1)), i-1);
    }

    /*
    Calculates NCR.
     */
    private static int ncr(int n, int r){
        return ((factorial(BigInteger.valueOf(n),n)).divide(factorial(BigInteger.valueOf(r),r).multiply(factorial(BigInteger.valueOf(n-r),n-r)))).intValue();
    }
}
