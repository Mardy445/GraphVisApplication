package Controllers;

import Models._MainModel;
import Views._MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpButtonListener implements ActionListener {
    private _MainModel model;
    private _MainView view;

    public HelpButtonListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(view,"This application can be used to test and understand graphing properties and algorithms.\n\n" +
                "To select multiple nodes or edges, hold down shift while selecting.\n\n" +
                "The graph editor tool bar can be dragged out to move around as needed.", "Help",JOptionPane.INFORMATION_MESSAGE);
    }
}
