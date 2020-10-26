package Controllers;

import Models.GraphEdge;
import Models._MainModel;
import Views._MainView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AlgorithmsButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public AlgorithmsButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int input = view.displayAlgorithmsBox(model.getIsGraphDirected(), model.getIsGraphWeighted());
        switch (input){
            case 0:
                handleDjikstrasAlgorithm();
                break;
            case 1:
                handleKruskalsAlgorithm();
                break;
            case 2:
                break;
            case 3:
                view.displayErrorMessage("An algorithm must be selected to proceed.");
        }
    }

    private void handleDjikstrasAlgorithm(){
        String id1 = "start";
        String id2 = "end";
        RunnableDjikstra instance = new RunnableDjikstra(model,view, id1, id2);
        view.displayDjikstraOptionBox(model.getNodes(), instance, id1, id2);
        model.unhighlightAllElements();
        view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
    }

    private void handleKruskalsAlgorithm(){
        RunnableKruskal instance = new RunnableKruskal(model, view);
        new Thread(instance).start();
        view.displayKruskalOptionBox();
        instance.stop();
        model.unhighlightAllElements();
        view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
    }
}
