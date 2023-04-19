import java.util.HashMap;
import java.util.*;
public class State {
    private int name;
    private boolean isFinal;
    private HashMap<Integer, Vector<Integer>> transitions;

    public State(int name, boolean isFinal, HashMap<Integer,Vector<Integer>> transitions){
        this.name = name;
        this.isFinal = isFinal;
        this.transitions = transitions;
    }
    public HashMap<Integer,Vector<Integer>> getTransitions(){
        return this.transitions;
    }
    public int getName(){
        return this.name;
    }
    public void setTransitions(HashMap<Integer, Vector<Integer>> transitions){
        this.transitions = transitions;
    }
    public void setAccepting(boolean tf){
        this.isFinal = tf;
    }
    public boolean getAccepting(){
        return this.isFinal;
    }

}
