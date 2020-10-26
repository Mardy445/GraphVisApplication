package Controllers;

import Models._MainModel;
import Views._MainView;

import java.awt.event.*;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener {
    private _MainModel model;
    private _MainView view;

    public MouseListener(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        model.graphPressed(e.getX(),e.getY());
        view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(model.isElementBeingDragged()){
            model.signalDragEnd();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        model.dragSelected(e.getX(),e.getY());
        view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
