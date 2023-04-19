import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class StringReader{
    private Vector<String> strings = new Vector<String>();
    
    public StringReader(String filename){
    
      try{
        File input = new File(filename);
        Scanner reader = new Scanner(input);
        while(reader.hasNextLine()){
            String str = reader.nextLine();
            strings.add(str);
        }
      }
      catch(FileNotFoundException e){
        System.out.println("error");
      }
}
public Vector<String> getStrings(){
    return strings;
}
public static void main(String[] args){
    
    FileReader reader = new FileReader(args[0]);
    StringReader stringReader = new StringReader(args[1]);
    
    NFASIM.simulator(reader.getStates().get(0), reader.getStates(), reader.getAcceptingStates(), stringReader.getStrings());
}
}