package Controllers;

import Models._MainModel;
import Views._MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public SettingsButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
