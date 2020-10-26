package Models;

import java.io.Serializable;
import java.security.PublicKey;

/*
Super class of both GraphNode and GraphEdge
 */
public class GraphElements implements Serializable {

    //Whether or not the element is selected
    private boolean isSelected;

    //Whether or not the element is highlighted during algorithms
    private boolean isHighlighted;

    //Whether or not the element is highlighted for being incorrect during algorithms
    private boolean isIncorrectHighlighted;

    /*
    Constructor. All elements are unselected upon creation.
     */
    public GraphElements(){
        isSelected = false;
        isHighlighted = false;
        isIncorrectHighlighted = false;
    }


    /*
    Getter: Returns a boolean value to represent whether or not the element is selected.
     */
    public boolean isSelected(){
        return isSelected;
    }

    /*
    Setter: Sets isSelected.
     */
    public void setSelected(boolean selected){
        isSelected = selected;
    }

    /*
    Getter: Returns a boolean value to represent whether or not the element is highlighted.
     */
    public boolean isHighlighted(){
        return isHighlighted;
    }

    /*
    Setter: Sets isHighlighted.
     */
    public void setHighlighted(boolean highlighted){
        isHighlighted = highlighted;
    }

    /*
    Getter: Returns a boolean value to represent whether or not the element is incorrectly highlighted.
     */
    public boolean isIncorrectHighlighted(){
        return isIncorrectHighlighted;
    }

    /*
    Setter: Sets isIncorrectlyHighlighted.
     */
    public void setIncorrectHighlighted(boolean isIncorrectHighlighted){
        this.isIncorrectHighlighted = isIncorrectHighlighted;
    }
}
