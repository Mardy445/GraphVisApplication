package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public DeleteButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteSelected();
        view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
    }
}
