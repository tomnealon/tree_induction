package tree_induction;

import java.io.IOException;
/**
 *
 * @author tom
 */
public class Launcher {
    
    public static void main(String[] args) throws IOException {
        Launcher.launch();
     }
    
    public static void launch() throws IOException {
        System.out.println("START");
        ReaderMemory myReader = new ReaderMemory("./data_sources/Book1.csv");
        DecisionTree myDecisionTree = new DecisionTree(myReader, 4);
        myDecisionTree.growTree();
    }
    
    
}
    
    
