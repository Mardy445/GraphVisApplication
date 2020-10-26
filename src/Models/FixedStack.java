package Models;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FixedStack<T> {
    private List<T> stackArray;
    private int maxSize;

    public FixedStack(int maxSize){
        this.maxSize = maxSize;
        this.stackArray = new LinkedList<>();
    }

    public void push(T element){
        stackArray.add(element);
        if(stackArray.size() == maxSize+1){
            stackArray.remove(0);
        }
    }

    public T pop(){
        if(!isEmpty()) {
            T element = stackArray.get(stackArray.size()-1);
            stackArray.remove(stackArray.size()-1);
            return element;
        }
        else {
            return null;
        }
    }

    public T peak(){
        if(!isEmpty()) {
            return stackArray.get(stackArray.size() - 1);
        }
        return null;
    }

    public boolean isEmpty(){
        return stackArray.size() == 0;
    }

    public void clearStack(){
        stackArray.clear();
    }
}
