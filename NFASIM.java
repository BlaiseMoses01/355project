import java.util.*;
public class NFASIM{

    public static void simulator(State state, Vector<State> states, Vector<Integer> acceptingStates, Vector<String> testStrs){
        
        for( String str : testStrs){
            Vector<State> curr_states = new Vector<State>();
            curr_states.add(state);
            
            for(int i = 0; i < str.length(); i++){
                int val = str.charAt(i)-96;
                Vector<Integer> temp = new Vector<Integer>();
                for(State temp_state : curr_states){
                    HashMap<Integer, Vector<Integer>> transitions = temp_state.getTransitions();
                    Vector<Integer> templist = transitions.get(val);
                    for(int toAdd : templist){
                        if(!temp.contains(toAdd))
                            temp.add(toAdd);
                    }
                }
                curr_states.clear();
                for(int values : temp){
                    curr_states.add(states.get(values));
                }
            }
            boolean accepting = false;
            for(State state_final: curr_states){
                if (state_final.getAccepting())
                    accepting = true;
            }
            if(accepting){
                System.out.println("accept");
            }
            else{
                System.out.println("reject");
            }
        }

    }
}