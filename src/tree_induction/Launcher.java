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
        ReaderMemory myReader = new ReaderMemory("./data_sources/Book1.csv");
//        myReader.printRow(3);
//        myReader.printAll();
        DecisionTree myDecisionTree = new DecisionTree(myReader, 4);
        myDecisionTree.growTree();
    }
    
    
}
    
    
