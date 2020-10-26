package Views;

import Controllers._MainController;
import Models.ElementPosition;
import Models.GraphEdge;
import Models.GraphNode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
An extension of JPanel that presents a visual interactive version of the graph.
 */
public class GraphInteractionPanel extends JPanel {

    //The currently visible nodes and edges and the selected point.
    private List<GraphNode> nodes = new ArrayList<>();
    private List<GraphEdge> edges = new ArrayList<>();
    private ElementPosition selectedPoint;

    /*
    Setter: Sets the visible nodes.
     */
    public void setNodes(List<GraphNode> nodes){
        this.nodes = nodes;
    }

    /*
    Setter: Sets the visible edges.
     */
    public void setEdges(List<GraphEdge> edges){
        this.edges = edges;
    }

    /*
    Setter: Sets the visible selected point.
     */
    public void setSelectedPoint(ElementPosition selectedPoint){
        this.selectedPoint = selectedPoint;
    }

    /*
    Called by the alternate graphics thread.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        displayEdges(g2d);
        displayNodes(g2d);
        displayPoint(g2d);
    }

    /*
    Displays the nodes and their ID. The colour is dependent on whether or not they are selected.
     */
    private void displayNodes(Graphics2D g2d){
        g2d.setFont(new Font(_MainView.FONT_TYPE, _MainView.FONT_STYLE,_MainView.FONT_SIZE));
        for(GraphNode node : nodes){
            int diameter = _MainController.NODE_SIZE;
            g2d.setColor(node.isHighlighted() ? _MainView.HIGHLIGHTED_ELEMENT_COLOUR : (node.isIncorrectHighlighted() ? _MainView.INCORRECTLY_HIGHLIGHTED_ELEMENT_COLOUR : (node.isSelected() ? _MainView.SELECTED_NODE_COLOUR : _MainView.DEFAULT_NODE_COLOUR)));
            g2d.fillOval(node.getPosition().x()-(diameter/2),node.getPosition().y()-(diameter/2),diameter,diameter);

            g2d.setColor(Color.BLACK);
            g2d.drawString(node.getId(),node.getPosition().x()-((diameter/2)-2),node.getPosition().y()+_MainView.FONT_SIZE/2);
        }
    }

    /*
    Displays the nodes and their potential weight. The colour is dependent on whether or not they are selected.
     */
    private void displayEdges(Graphics2D g2d){
        ElementPosition position1;
        ElementPosition position2;
        ElementPosition arrowEndPosition;
        ElementPosition nonArrowEndPosition;

        double m;

        Font font = new Font(_MainView.FONT_TYPE, _MainView.FONT_STYLE,_MainView.FONT_SIZE);

        for(GraphEdge edge : edges){
            g2d.setColor(edge.isHighlighted() ? _MainView.HIGHLIGHTED_ELEMENT_COLOUR : (edge.isIncorrectHighlighted() ? _MainView.INCORRECTLY_HIGHLIGHTED_ELEMENT_COLOUR : (edge.isSelected() ? _MainView.SELECTED_EDGE_COLOUR : _MainView.DEFAULT_EDGE_COLOUR)));
            position1 = edge.getFirstNode().getPosition();
            position2 = edge.getSecondNode().getPosition();
            g2d.setStroke(new BasicStroke(_MainController.EDGE_THICKNESS));
            g2d.drawLine(position1.x(),position1.y(),position2.x(),position2.y());

            m = (double) (edge.getFirstNode().getPosition().y() - edge.getSecondNode().getPosition().y()) /
                    (double) (edge.getFirstNode().getPosition().x() - edge.getSecondNode().getPosition().x());
            if(edge.getWeight() != null) {
                displayEdgesWeight(g2d,edge,m,font);
            }

            if(edge.getIndexOfStartNode() != null){
                arrowEndPosition = edge.getIndexOfStartNode() == 0 ? position2 : position1;
                nonArrowEndPosition = edge.getIndexOfStartNode() == 0 ? position1 : position2;
                displayEdgesArrow(g2d, arrowEndPosition, nonArrowEndPosition, m);

            }
        }
    }

    private void displayEdgesWeight(Graphics2D g2d, GraphEdge edge, double m, Font font){
        AffineTransform affineTransform = new AffineTransform();

        affineTransform.rotate(Math.atan(m), 0, 0);
        g2d.setFont(font.deriveFont(affineTransform));
        g2d.drawString(edge.getWeight().toString(), Math.abs(edge.getFirstNode().getPosition().x() + edge.getSecondNode().getPosition().x())/2, Math.abs(edge.getFirstNode().getPosition().y() + edge.getSecondNode().getPosition().y())/2);
    }

    private void displayEdgesArrow(Graphics2D g2d, ElementPosition arrowNodePosition, ElementPosition nonArrowNodePosition, double m){
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        int arrowSize = _MainView.ARROW_SIZE;
        double a = Math.atan(m);
        double aNormal = Math.atan(-1.0/m);

        int flipMultiplier = arrowNodePosition.x() < nonArrowNodePosition.x() ? 1 : -1;
        xPoints[0] = (int) (arrowNodePosition.x() + (flipMultiplier * ((_MainController.NODE_SIZE/2) * Math.cos(a))));
        yPoints[0] = (int) (arrowNodePosition.y() + (flipMultiplier * ((_MainController.NODE_SIZE/2) * Math.sin(a))));
        xPoints[2] = (int) (arrowNodePosition.x() + (flipMultiplier * ((_MainController.NODE_SIZE/2 + arrowSize) * Math.cos(a))));
        yPoints[2] = (int) (arrowNodePosition.y() + (flipMultiplier * ((_MainController.NODE_SIZE/2 + arrowSize) * Math.sin(a))));
        xPoints[1] = (int) (xPoints[2] - (arrowSize/2 * Math.cos(aNormal)));
        yPoints[1] = (int) (yPoints[2] - (arrowSize/2 * Math.sin(aNormal)));
        xPoints[3] = (int) (xPoints[2] + (arrowSize/2 * Math.cos(aNormal)));
        yPoints[3] = (int) (yPoints[2] + (arrowSize/2 * Math.sin(aNormal)));

        g2d.fillPolygon(xPoints,yPoints,4);
    }

    /*
    Displays the selected point if any.
     */
    private void displayPoint(Graphics2D g2d){
        if(selectedPoint != null){
            int diameter = _MainController.SELECTED_POINT_SIZE;
            g2d.setColor(_MainView.SELECTED_POINT_COLOUR);
            g2d.fillOval(selectedPoint.x()-(diameter/2),selectedPoint.y()-(diameter/2), diameter,diameter);
        }
    }
}
