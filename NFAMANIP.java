import java.util.*;
import java.util.Collections;

public class NFAMANIP{
    public static Vector<State> ENFAtoNFA(Vector<State> states, Vector<Integer> acceptingStates){
        Vector<State> states_updated = new Vector<State>();
        for(State state: states){
            Vector<Integer> eclosure = Eclosure(state, states, new Vector<Integer>());
            for(int finalstate: acceptingStates){
                if (eclosure.contains(finalstate))
                    state.setAccepting(true);
            }
            if (state.getAccepting() && !acceptingStates.contains(state.getName())){
                acceptingStates.add(state.getName());
            }
            // checks to see if eclosure ends  on current, if so it is up to date
            if(eclosure.isEmpty()){
                states_updated.add(state);
                continue;
            }

            // now that we know all reachable e states, we have to add all of their non e transitions to the current state.
            HashMap<Integer,Vector<Integer>> transitions = state.getTransitions();
            transitions.remove(0);
            Set<Integer> keys = transitions.keySet();
            
            for(int key : eclosure){
                State tempState = states.get(key);
                HashMap<Integer,Vector<Integer>> tempTransitions = tempState.getTransitions();
                for (int val : keys){
                    Vector<Integer> curr_trans = transitions.get(val);
                    Vector<Integer> temp_trans = tempTransitions.get(val);
                    transitions.replace(val, combineMappings(curr_trans,temp_trans));
                }
                }
                
                transitions.remove(0);
                state.setTransitions(transitions);
                states_updated.add(state);
            } 
            return states_updated;
    }
    public static Vector<Integer> Eclosure(State state, Vector<State> states, Vector<Integer> visited){
    
        //get current states transitions
        HashMap<Integer,Vector<Integer>> transitions = state.getTransitions();
        Vector<Integer> eclosure = new Vector<Integer>();
        //see if it has any e moves, if not it is good to go
        if(transitions.get(0) == null)
            return eclosure;
        // recursive step
            eclosure = (transitions.get(0));
            //will store all possible states reached in all paths
            Vector<Integer> temp = new Vector<Integer>();
            //search each initial path
            for(int i=0; i < eclosure.size(); i++){
            //recursively following every tree
            if(visited.contains(eclosure.get(i)))
                continue;
            
            visited.add(eclosure.get(i));
            Vector<Integer> tempList = Eclosure(states.get(eclosure.get(i)), states, visited);
           
            //adding new possiblities to temp if they havent already been reached
            for(int item: tempList){
                if(!temp.contains(item)){
                    temp.add(item);
                }
            }
            }
         // adding states to eclosure
            for(int item: temp){
            if(!eclosure.contains(item))
                eclosure.add(item);
            }
        return eclosure;
    }   
    public static Vector<Integer> combineMappings(Vector<Integer> v1, Vector<Integer> v2){

        for(int val : v2){
            if (!v1.contains(val)){
                v1.add(val);
            }
        }
        Collections.sort(v1);
        
      return v1;
    }
    public static Vector<Integer> updateAccepting(Vector<State> states, Vector<Integer> acceptingStates){
        
        for(State state: states){
            Vector<Integer> eclosure = Eclosure(state, states, new Vector<Integer>());
            for(int finalstate: acceptingStates){
                if (eclosure.contains(finalstate))
                    state.setAccepting(true);
            }
            if (state.getAccepting() && !acceptingStates.contains(state.getName())){
                acceptingStates.add(state.getName());
            }
        }

        Collections.sort(acceptingStates);

        return acceptingStates;
    }
}