package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class NewButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public NewButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int choice;

        if(!model.isGraphSaved()){
            choice = view.displayYesNoInputBox("Would you like to save this graph before creating a new one?");
            if (choice == 0){
                model.passFileForSaving(view.displayFileInputBox(true));
            }
            else if (choice == 2){
                return;
            }
        }

        else if (!model.isGraphStateSaved()){
            choice = view.displayYesNoInputBox("Would you like to save the current state of this graph before creating a new one?");
            if(choice == 0){
                try {
                    model.saveGraphState();
                }
                catch (IOException eI){
                    view.displayErrorMessage("Unable to save graph");
                    return;
                }
            }
            if (choice == 2){
                return;
            }
        }

        HashMap<String,Boolean> graphInfo = view.displayNewGraphInputBox();
        if(graphInfo == null){
            return;
        }

        model.resetGraph();
        model.setIsGraphDirected(graphInfo.get("Directed"));
        model.setIsGraphWeighted(graphInfo.get("Weighted"));
        view.completeRefreshOperation(model.getNodes(), model.getEdges(), model.getSelectedPoint());
    }
}
