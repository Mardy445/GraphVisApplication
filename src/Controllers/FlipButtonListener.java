package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlipButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public FlipButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!model.getIsGraphDirected()){
            view.displayErrorMessage("Cannot flip the edge of an undirected graph.");
            return;
        }
        if(!model.flipSelectedEdge()){
            view.displayErrorMessage("Please select one and only one edge to flip.");
            return;
        }
        view.completeRefreshOperation(model.getNodes(), model.getEdges(), model.getSelectedPoint());

    }
}
