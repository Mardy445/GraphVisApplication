package Controllers;

import Models.GraphEdge;
import Models.GraphElements;
import Models.GraphNode;
import Models._MainModel;
import Views._MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class RunnableDjikstra implements ActionListener {
    private _MainModel model;
    private _MainView view;
    private String id1;
    private String id2;

    private HashMap<GraphNode, GraphEdge> currentResult;
    private GraphNode endNode;

    public RunnableDjikstra(_MainModel model, _MainView view, String id1, String id2){
        this.model = model;
        this.view = view;
        this.id1 = id1;
        this.id2 = id2;
        this.currentResult = model.djikstrasAlgorithm(model.getNodes().get(0));
        this.endNode = model.getNodes().get(0);
        changeHighlightedElements(endNode);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(id1)){
            startNodeDropDownBoxChanged(e);
        }
        else if (e.getActionCommand().equals(id2)){
            endNodeDropDownBoxChanged(e);
        }
    }

    private void startNodeDropDownBoxChanged(ActionEvent e){
        currentResult = model.djikstrasAlgorithm((GraphNode) ((JComboBox)e.getSource()).getSelectedItem());
        changeHighlightedElements(endNode);
    }

    private void endNodeDropDownBoxChanged(ActionEvent e){
        this.endNode = (GraphNode) ((JComboBox)e.getSource()).getSelectedItem();
        changeHighlightedElements(endNode);
    }

    private void changeHighlightedElements(GraphNode endNode){
        List<GraphElements> elements = new ArrayList<>();
        GraphNode currentHoldNode = endNode;

        model.unhighlightAllElements();
        while(currentResult.get(currentHoldNode) != null){
            elements.add(currentHoldNode);
            elements.add(currentResult.get(currentHoldNode));
            currentHoldNode = currentResult.get(currentHoldNode).getAlternateNode(currentHoldNode);
        }
        elements.add(currentHoldNode);
        model.highlightGraphElements(elements);
        view.completeRefreshOperation(model.getNodes(), model.getEdges(), model.getSelectedPoint());
    }
}
