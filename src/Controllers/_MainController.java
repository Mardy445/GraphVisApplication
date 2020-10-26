package Controllers;

import Models._MainModel;
import Views._MainView;

import javax.swing.*;

/*
The main controller class. This class represents the controller component of the MVC design pattern by linking the view and model.
 */
public class _MainController {

    //Shared Constants
    public static final int SELECTED_POINT_SIZE = 10;
    public static final int NODE_SIZE = 100;
    public static final int EDGE_THICKNESS = 3;

    //Model and view references
    private _MainModel model;
    private _MainView view;

    //Constructor
    public _MainController(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
        addEventListeners();
    }

    /*
    Adds listeners and event handlers for all of the events
     */
    private void addEventListeners(){
        view.setNewButtonListener(new NewButtonListener(model,view));
        view.setSaveAsButtonListener(new SaveAsButtonListener(model,view));
        view.setSaveButtonListener(new SaveButtonListener(model,view));
        view.setOpenButtonListener(new OpenButtonListener(model,view));
        view.setPropertiesButtonListener(new PropertiesButtonListener(model,view));
        view.setAlgorithmicsButtonListener(new AlgorithmsButtonListener(model,view));
        view.setHelpButtonListener(new HelpButtonListener(model,view));
        view.setSettingsButtonListener(new SettingsButtonListener(model,view));
        view.setNewNodeButtonListener(new NewNodeButtonListener(model,view));
        view.setNewEdgeButtonListener(new NewEdgeButtonListener(model,view));
        view.setUndoButtonListener(new UndoButtonListener(model,view));
        view.setRedoButtonListener(new RedoButtonListener(model,view));
        view.setDeleteButtonListener(new DeleteButtonListener(model,view));
        view.setFlipButtonListener(new FlipButtonListener(model, view));
        view.setEditWeightButtonListener(new EditWeightButtonListener(model,view));

        MouseListener listener = new MouseListener(model,view);
        view.setMouseListener(listener);
        view.setMouseMotionListener(listener);

        new KeyBindController(view.getGraphPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW), view.getGraphPanel().getActionMap(), model, view);
    }


}
