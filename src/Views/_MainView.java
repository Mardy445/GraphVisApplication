package Views;

import Controllers.RunnableDjikstra;
import Models.ElementPosition;
import Models.GraphEdge;
import Models.GraphNode;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/*
The main view class. This class represents the view component of the MVC design pattern.
 */
public class _MainView extends JFrame{

    //Constants
    static final Color SELECTED_POINT_COLOUR = new Color(0x091FDE);
    static final Color DEFAULT_NODE_COLOUR = new Color(0x0067DE);
    static final Color SELECTED_NODE_COLOUR = new Color(0xDE009E);
    static final Color DEFAULT_EDGE_COLOUR = DEFAULT_NODE_COLOUR;
    static final Color SELECTED_EDGE_COLOUR = SELECTED_NODE_COLOUR;
    static final Color HIGHLIGHTED_ELEMENT_COLOUR = new Color(0x2BE917);
    static final Color INCORRECTLY_HIGHLIGHTED_ELEMENT_COLOUR = new Color(0xE9000D, true);


    static final String FONT_TYPE = "TimesRoman";
    static final int FONT_STYLE = Font.BOLD;
    static final int FONT_SIZE = 12;

    static final int ARROW_SIZE = 30;

    //File Buttons
    private ToolBarButton newButton, SaveAsButton, SaveButton, OpenButton, PropertiesButton, AlgorithmicsButton, helpButton, settingsButton;

    //Interaction Buttons
    private ToolBarButton newNodeButton, newEdgeButton, undoButton, redoButton, deleteButton, flipButton, editWeightButton;

    //Main Graph Panel
    private GraphInteractionPanel graphPanel;

    /*
    Constructor: Inits the frame.
     */
    public _MainView(){
        initFrame();
    }

    /*
    Initialises the JFrames default GUI look by setting all the buttons, toolbars and the main graph area.
     */
    private void initFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        //Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        add(mainPanel);
        setButtons();
        setToolBars(mainPanel);
        setGraphGrid(mainPanel);
    }

    /*
    Sets all the buttons with there respective information and icons.
     */
    private void setButtons(){
        newButton = new ToolBarButton("New","Icons\\add-page.png");
        SaveAsButton = new ToolBarButton("Save As","Icons\\save-as.png");
        SaveButton = new ToolBarButton("Save","Icons\\save-disk.png");
        OpenButton = new ToolBarButton("Open","Icons\\open-folder-outline.png");
        PropertiesButton = new ToolBarButton("Properties","Icons\\graph-line-screen.png");
        AlgorithmicsButton = new ToolBarButton("Algorithms","Icons\\algorithm.png");
        helpButton = new ToolBarButton("Help","Icons\\information.png");
        settingsButton = new ToolBarButton("Settings", "Icons\\settings.png");
        newNodeButton = new ToolBarButton("Add Node","Icons\\add.png");
        newEdgeButton = new ToolBarButton("Add Edge","Icons\\add.png");
        undoButton = new ToolBarButton("Undo", "Icons\\undo.png");
        redoButton = new ToolBarButton("Redo","Icons\\redo.png");
        deleteButton = new ToolBarButton("Delete Selected", "Icons\\garbage.png");
        flipButton = new ToolBarButton("Flip Edge Direction","Icons\\flip-object.png");
        editWeightButton = new ToolBarButton("Edit weight","Icons\\number-change.png");
    }

    /*
    The following methods set the action listener event for each button.
    They also set the mouse event handlers.
     */
    public void setNewButtonListener(ActionListener a){
        newButton.addActionListener(a);
    }
    public void setSaveAsButtonListener(ActionListener a){
        SaveAsButton.addActionListener(a);
    }
    public void setSaveButtonListener(ActionListener a){
        SaveButton.addActionListener(a);
    }
    public void setOpenButtonListener(ActionListener a){
        OpenButton.addActionListener(a);
    }
    public void setPropertiesButtonListener(ActionListener a){
        PropertiesButton.addActionListener(a);
    }
    public void setAlgorithmicsButtonListener(ActionListener a){
        AlgorithmicsButton.addActionListener(a);
    }
    public void setHelpButtonListener(ActionListener a){
        helpButton.addActionListener(a);
    }
    public void setSettingsButtonListener(ActionListener a){
        settingsButton.addActionListener(a);
    }
    public void setNewNodeButtonListener(ActionListener a){
        newNodeButton.addActionListener(a);
    }
    public void setNewEdgeButtonListener(ActionListener a){
        newEdgeButton.addActionListener(a);
    }
    public void setUndoButtonListener(ActionListener a){
        undoButton.addActionListener(a);
    }
    public void setRedoButtonListener(ActionListener a){
        redoButton.addActionListener(a);
    }
    public void setDeleteButtonListener(ActionListener a){
        deleteButton.addActionListener(a);
    }
    public void setFlipButtonListener(ActionListener a){ flipButton.addActionListener(a);}
    public void setEditWeightButtonListener(ActionListener a){ editWeightButton.addActionListener(a);}
    public void setMouseListener(MouseListener a){ graphPanel.addMouseListener(a);}
    public void setMouseMotionListener(MouseMotionListener a){ graphPanel.addMouseMotionListener(a);}

    /*
    Getter: Returns the main graph panel.
     */
    public JPanel getGraphPanel(){
        return graphPanel;
    }

    /*
    Creates and sets the main toolbars.
     */
    private void setToolBars(JPanel mainPanel){
        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new BorderLayout());

        mainPanel.add(toolbarPanel,BorderLayout.NORTH);

        JToolBar fileToolBar = new JToolBar();
        fileToolBar.add(newButton);
        fileToolBar.add(SaveAsButton);
        fileToolBar.add(SaveButton);
        fileToolBar.add(OpenButton);
        fileToolBar.add(PropertiesButton);
        fileToolBar.add(AlgorithmicsButton);
        fileToolBar.add(settingsButton);
        fileToolBar.add(helpButton);
        fileToolBar.setFloatable(false);
        fileToolBar.setRollover(true);

        JToolBar graphToolBar = new JToolBar("Toolbar");
        graphToolBar.add(newNodeButton);
        graphToolBar.add(newEdgeButton);
        graphToolBar.add(undoButton);
        graphToolBar.add(redoButton);
        graphToolBar.add(deleteButton);
        graphToolBar.add(flipButton);
        graphToolBar.add(editWeightButton);
        graphToolBar.setRollover(true);
        graphToolBar.addSeparator();

        fileToolBar.setBorderPainted(true);
        graphToolBar.setBorderPainted(false);
        fileToolBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.gray,Color.lightGray));

        toolbarPanel.add(fileToolBar, BorderLayout.NORTH);
        toolbarPanel.add(graphToolBar, BorderLayout.SOUTH);
    }

    /*
    Creates and sets the main panel for graph manipulation.
     */
    private void setGraphGrid(JPanel mainPanel){
        graphPanel = new GraphInteractionPanel();
        graphPanel.setBackground(Color.WHITE);

        mainPanel.add(graphPanel);
    }



    /*
    Completely updates the graph with new information retrieved from the model.
     */
    public void completeRefreshOperation(List<GraphNode> nodes, List<GraphEdge> edges, ElementPosition point){
        clearGraph();
        setNodes(nodes);
        setEdges(edges);
        setSelectedPoint(point);
        refreshGraph();
    }

    /*
    Clears the currently visible graph.
     */
    public void clearGraph(){
        graphPanel.setEdges(new ArrayList<>());
        graphPanel.setNodes(new ArrayList<>());
        graphPanel.setSelectedPoint(null);
    }

    /*
    Sets the nodes to be visible on the graph panel.
     */
    public void setNodes(List<GraphNode> nodes){
        graphPanel.setNodes(nodes);
    }

    /*
    Sets the edges to be visible on the graph panel.
     */
    public void setEdges(List<GraphEdge> edges){
        graphPanel.setEdges(edges);
    }

    /*
    Sets the selection point to be visible on the graph panel.
     */
    public void setSelectedPoint(ElementPosition point){
        graphPanel.setSelectedPoint(point);
    }

    /*
    Calls repaint on the graph panel to refresh the view.
     */
    public void refreshGraph(){
        graphPanel.repaint();
    }

    /*
    Displays an error message.
     */
    public void displayErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message,"Error",JOptionPane.ERROR_MESSAGE);
    }

    public int displayYesNoInputBox(String message){
        Object[] options = {"Yes","No","Cancel"};
        int choice = JOptionPane.showOptionDialog(this,message,"In",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        return choice;
    }

    /*
    Displays an input box to ask the user to input a node ID.
     */
    public String displayNodeIDInputBox(){
        return JOptionPane.showInputDialog(this, "Input a node ID");
    }

    /*
    Displays an input box to ask the user to input an edge weight.
     */
    public String displayEdgeIDInputBox(){
        return JOptionPane.showInputDialog(this, "Input an edge weight");
    }

    public HashMap<String,Boolean> displayNewGraphInputBox(){
        HashMap<String,Boolean> inputData = new HashMap<>();

        JCheckBox isDirected = new JCheckBox();
        JCheckBox isWeighted = new JCheckBox();

        JPanel isDirectedPanel = new JPanel();
        JPanel isWeightedPanel = new JPanel();
        isDirectedPanel.add(new JLabel("Directed"));
        isDirectedPanel.add(isDirected);
        isWeightedPanel.add(new JLabel("Weighted"));
        isWeightedPanel.add(isWeighted);

        Object[] graphInfo = {
                isDirectedPanel,
                isWeightedPanel
        };

        int option = JOptionPane.showConfirmDialog(this, graphInfo, "New Graph", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            inputData.put("Directed",isDirected.isSelected());
            inputData.put("Weighted",isWeighted.isSelected());
            return inputData;
        }
        return null; //TBC
    }

    public File displayFileInputBox(Boolean isSaveFileBox){
        JFileChooser chooser = new JFileChooser();

        if(isSaveFileBox) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }

        chooser.setCurrentDirectory(new File("Saves"));
        chooser.setSelectedFile(new File(""));
        int value = isSaveFileBox ? chooser.showSaveDialog(this) : chooser.showOpenDialog(this);
        if(value == JFileChooser.APPROVE_OPTION){
            return chooser.getSelectedFile();
        }
        return null;
    }

    public void displayPropertiesBox(int nodeCount, int edgeCount, boolean hasEularianCycle, boolean hasEularianPath, boolean isGraphComplete, boolean isGraphConnected, boolean isGraphPlanar){
        String message = "Node Count: " + nodeCount + "\n" +
                "Edge Count: " + edgeCount + "\n" +
                "Eularian Cycle: " + hasEularianCycle + "\n" +
                "Eularian Path: " + hasEularianPath + "\n" +
                "Graph Complete:" + isGraphComplete + "\n" +
                "Graph Connected:" + isGraphConnected + "\n" +
                "Graph Planar:" + isGraphPlanar;

        JLabel label = new JLabel(message);
        label.setText("<html>" + message.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        JScrollPane panel = new JScrollPane(label);

        JOptionPane pane = new JOptionPane(panel, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(this, "Properties");
        dialog.setSize(new Dimension(200,300));
        dialog.setVisible(true);

        //JOptionPane.showMessageDialog(this,panel, "Properties", JOptionPane.INFORMATION_MESSAGE);
    }

    public int displayAlgorithmsBox(boolean isDirected, boolean isWeighted){
        JRadioButton djikstra = new JRadioButton("Djikstra's Algorithm");
        JRadioButton MST = new JRadioButton("MST: Kruskals Algorithm");
        JRadioButton maximumMatching = new JRadioButton("Find Maximum Matching");
        djikstra.setEnabled(isWeighted);
        MST.setEnabled(isWeighted);

        ButtonGroup group = new ButtonGroup();
        group.add(djikstra);
        group.add(MST);
        group.add(maximumMatching);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(djikstra);
        panel.add(MST);
        panel.add(maximumMatching);

        int option = JOptionPane.showConfirmDialog(this, panel, "Algorithms", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if(djikstra.isSelected()){
                return 0;
            }
            else if(MST.isSelected()){
                return 1;
            }
            else if(maximumMatching.isSelected()){
                return 2;
            }
            else {
                return 3;
            }
        }
        return -1;
    }

    public void displayDjikstraOptionBox(List<GraphNode> nodes, RunnableDjikstra instance, String id1, String id2){
        JComboBox startNodeSelection = new JComboBox(nodes.toArray());
        JComboBox endNodeSelection = new JComboBox(nodes.toArray());
        startNodeSelection.setActionCommand(id1);
        endNodeSelection.setActionCommand(id2);
        startNodeSelection.addActionListener(instance);
        endNodeSelection.addActionListener(instance);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        panel1.add(new JLabel("Start Node"));
        panel1.add(startNodeSelection);
        panel2.add(new JLabel("End Node"));
        panel2.add(endNodeSelection);
        mainPanel.add(panel1);
        mainPanel.add(panel2);

        JOptionPane.showMessageDialog(this, mainPanel,"Djikstra", JOptionPane.INFORMATION_MESSAGE);

    }

    public void displayKruskalOptionBox(){
        JOptionPane.showMessageDialog(this,"Click ok to end the process.","Kruskal",JOptionPane.INFORMATION_MESSAGE);
    }

}
