package Controllers;

import Models._MainModel;
import Views._MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class KeyBindController {

    private static final String EVENT_SHIFT_PRESSED = "1";
    private static final String EVENT_SHIFT_RELEASED = "2";

    private _MainModel model;
    private _MainView view;

    private InputMap inputMap;
    private ActionMap actionMap;

    public KeyBindController(InputMap inputMap, ActionMap actionMap, _MainModel model, _MainView view){
        this.inputMap = inputMap;
        this.actionMap = actionMap;
        this.model = model;
        this.view = view;
        setKeyBinds();
    }

    private void setKeyBinds(){
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK), EVENT_SHIFT_PRESSED);
        actionMap.put(EVENT_SHIFT_PRESSED,shiftPressed);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT,0,true), EVENT_SHIFT_RELEASED);
        actionMap.put(EVENT_SHIFT_RELEASED,shiftReleased);
    }

    private Action shiftPressed = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setShiftHeld(true);
        }
    };

    private Action shiftReleased = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setShiftHeld(false);
        }
    };
}
