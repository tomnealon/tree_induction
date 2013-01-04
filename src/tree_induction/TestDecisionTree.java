/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree_induction;

import java.io.IOException;

/**
 *
 * @author tom
 */
public class TestDecisionTree {
    
    public static void main(String[] args) throws IOException {
        TestDecisionTree.launch();
     }
    
    public static void launch() throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));      
        ReaderMemory myReader = new ReaderMemory("./data_sources/Book1.csv");
        DecisionTree myDecisionTree = new DecisionTree(myReader, 4);
        myDecisionTree.growTree();
    }
    
}
