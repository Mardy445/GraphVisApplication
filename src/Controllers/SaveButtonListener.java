package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SaveButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public SaveButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(model.isGraphSaved()){
            try {
                model.saveGraphState();
            }
            catch (IOException eI){
                view.displayErrorMessage("Your graph could not be saved. Try using 'Save as' to save to a new file");
            }
        }
        else {
            model.passFileForSaving(view.displayFileInputBox(true));
        }
    }
}
