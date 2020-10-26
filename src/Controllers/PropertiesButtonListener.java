package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertiesButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public PropertiesButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        view.displayPropertiesBox(model.getNodeCount(),model.getEdgeCount(),model.getHasEulerianCycle(), model.getHasEulerianPath(), model.getIsGraphComplete(), model.getIsGraphConnected(), model.getIsGraphPlanar());
    }
}
