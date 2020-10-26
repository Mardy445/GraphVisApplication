package Models;

import java.io.Serializable;

public class ElementPosition implements Serializable {
    private int x;
    private int y;

    public ElementPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public ElementPosition(){
        this.x = 0;
        this.y = 0;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }
}
