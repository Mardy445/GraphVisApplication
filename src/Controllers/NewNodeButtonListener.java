package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewNodeButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public NewNodeButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = view.displayNodeIDInputBox();
        if(!model.isNodeIdAvailable(id)){
            view.displayErrorMessage("ID " + id + " is already in use by a node.");
            return;
        }
        if(id != null && !id.equals("")){
            model.addNode(id);
            view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
        }
    }
}
