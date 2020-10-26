package Controllers;

import Models.GraphEdge;
import Models._MainModel;
import Views._MainView;

import java.util.ArrayList;
import java.util.List;

public class RunnableKruskal implements Runnable{
    private _MainModel model;
    private _MainView view;
    private boolean flag;

    public RunnableKruskal(_MainModel model, _MainView view){
        this.model = model;
        this.view = view;
        this.flag = true;
    }

    public void stop(){
        flag = false;
    }

    @Override
    public void run() {
        List<GraphEdge> edges = model.orderEdges(model.getEdges());
        GraphEdge currentEdgeToCheck;
        List<GraphEdge> MSTConnectedEdges = new ArrayList<>();
        boolean isCycleFormed;
        int i = 0;

        try {
            while (flag) {
                if (i == edges.size()) {
                    break;
                }
                currentEdgeToCheck = edges.get(i);
                i++;
                isCycleFormed = model.kruskalsAlgorithmCheckNextEdge(currentEdgeToCheck, MSTConnectedEdges);
                if (!isCycleFormed) {
                    MSTConnectedEdges.add(currentEdgeToCheck);
                    model.highlightGraphElements(currentEdgeToCheck);
                    model.highlightGraphElements(currentEdgeToCheck.getFirstNode());
                    model.highlightGraphElements(currentEdgeToCheck.getSecondNode());
                } else {
                    model.highlightGraphElementAsIncorrect(currentEdgeToCheck);
                }
                view.completeRefreshOperation(model.getNodes(),model.getEdges(),model.getSelectedPoint());
                Thread.sleep(600);
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
