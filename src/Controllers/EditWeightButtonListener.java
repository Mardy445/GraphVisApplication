package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditWeightButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public EditWeightButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!model.getIsGraphWeighted()){
            view.displayErrorMessage("Cannot edit the weight of an unweighted graph.");
            return;
        }
        if(!(model.getSelectedEdgeCount() == 1)){
            view.displayErrorMessage("Please select one and only one edge.");
            return;
        }
        String weight = view.displayEdgeIDInputBox();
        try {
            model.editSelectedEdgeWeight(Float.parseFloat(weight));
        } catch (NumberFormatException | NullPointerException ignored) {
            view.displayErrorMessage("Invalid input. Please input a numeric value.");
        }
        view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
    }
}
