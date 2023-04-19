import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class FileReader{

private int numberOfStates;
private int alphabetSize;
private Vector<Integer> acceptingStates;
private Vector<State> states;

public FileReader(String filename){

try{
//load file
File input = new File(filename);
Scanner reader = new Scanner(input);

// get number of states
String line = reader.nextLine();
String[] parseLine = line.split(" ");
this.numberOfStates = Integer.parseInt(parseLine[parseLine.length - 1]);

// get number of characters in alphabet
line = reader.nextLine();
parseLine = line.split(" ");
this.alphabetSize = Integer.parseInt(parseLine[parseLine.length - 1]);

//get accepting states
line = reader.nextLine();
parseLine = line.split(" ");
boolean atValues = false;
this.acceptingStates = new Vector<Integer>();

for(int i = 0; i < parseLine.length; i++){
    if(parseLine[i].equals("states:")){
        atValues = true;
        continue;
    }
    if(atValues == false)
        continue;
    
    acceptingStates.add(Integer.parseInt(parseLine[i]));
}

//get transitions
this.states = new Vector<State>();
int statenum = 0;
while(reader.hasNextLine()){
    HashMap<Integer, Vector<Integer>> transitions = new HashMap<Integer, Vector<Integer>>();
    line = reader.nextLine();
    Scanner lineScanner = new Scanner(line);
    int action =0;
    while(lineScanner.hasNext()){
        Vector<Integer> translist = new Vector<Integer>();
        String chunk = lineScanner.next();
        if(chunk.equals("{}")){
            transitions.put(action, translist);
            action++;
            continue;
        }
        chunk = chunk.substring(1,chunk.length()-1);
        String[] chunkParsed = chunk.split(",");
        for(int i =0; i <chunkParsed.length; i ++){
           translist.add(Integer.parseInt(chunkParsed[i]));
        }
        transitions.put(action, translist);
        action++;
    }
    boolean isFinal = false;
    if(this.acceptingStates.contains(statenum)){
            isFinal = true;
    }
    State state = new State(statenum,isFinal, transitions);
    states.add(state);
    lineScanner.close();
    statenum++;
}
}
catch(FileNotFoundException e){
    System.out.println("error");
}
}
public Vector<State> getStates(){
    return this.states;
}
public Vector<Integer> getAcceptingStates(){
    return this.acceptingStates;
}
public void PrintResults(){
    System.out.println("Number of states: "+this.numberOfStates);
    System.out.println("Alphabet size: "+this.alphabetSize);
    System.out.print("Accepting states: ");
    for(int i=0 ; i < acceptingStates.size(); i++){
        System.out.print(acceptingStates.get(i));
        if(i < acceptingStates.size()-1){
            System.out.print(" ");
        }
    }
    System.out.print("\n");

    for ( State state: this.states ){
        HashMap<Integer, Vector<Integer>> transitions = state.getTransitions();
        System.out.print("{} ");
        Set<Integer> keys =  transitions.keySet();
        keys.remove(0);
        for(int key: keys){
            Vector<Integer> vals = transitions.get(key);
            System.out.print("{");
            for(int i=0 ; i < vals.size(); i++){
                System.out.print(vals.get(i));
                if(i < vals.size()-1){
                    System.out.print(",");
                }
            }
            System.out.print("} ");
        }
        System.out.println();
    }
}
public static void main(String[] args){

    FileReader reader = new FileReader(args[0]);

    reader.states = NFAMANIP.ENFAtoNFA(reader.states, reader.acceptingStates);
    reader.acceptingStates = NFAMANIP.updateAccepting(reader.states,reader.acceptingStates);
    reader.PrintResults();
}
}
