package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SaveAsButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public SaveAsButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = view.displayFileInputBox(true);
        model.passFileForSaving(file);
    }
}
