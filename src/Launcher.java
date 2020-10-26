import Controllers._MainController;
import Models._MainModel;
import Views._MainView;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        _MainModel model = new _MainModel();

        _MainView view = new _MainView();

        _MainController controller = new _MainController(model,view);

        view.setVisible(true);
    }
}
