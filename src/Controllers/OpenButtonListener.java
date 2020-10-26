package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OpenButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public OpenButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = view.displayFileInputBox(false);
        if(file != null){
            try {
                model.openGraphStateFromFile(file);
                view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
            } catch (IOException ef){
                view.displayErrorMessage("File " + file.getName() + " was an incorrect file for this program.");
                ef.printStackTrace();
            }
            catch (ClassNotFoundException ec){
                view.displayErrorMessage("File " + file.getName() + " could not be loaded.");
            }
        }
    }
}
