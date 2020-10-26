package Controllers;

import Models._MainModel;
import Views._MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEdgeButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public NewEdgeButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(model.getSelectedNodeCount() == 2){
            if (model.getIsGraphWeighted()) {
                String input = view.displayEdgeIDInputBox();
                try {
                    float weight = Float.parseFloat(input);
                    model.addEdge(weight);
                } catch (NumberFormatException | NullPointerException ignored) {
                    view.displayErrorMessage("Invalid input. Please input a numeric value.");
                }
            }
            else {
                model.addEdge(null);
            }
            view.completeRefreshOperation(model.getNodes(), model.getEdges(), model.getSelectedPoint());
        }
        else {
            view.displayErrorMessage("Select 2 nodes only to add an edge");
        }
    }
}
