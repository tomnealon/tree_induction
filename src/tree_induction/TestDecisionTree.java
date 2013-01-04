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
    DecisionTree myTree = new DecisionTree();
    
    public static void main(String[] args) throws IOException {
        TestDecisionTree.launch();
     }
    
    public static void launch() throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));      
        ReaderMemory myReader = new ReaderMemory("./data_sources/Book1.csv");
        Id3Classifier myClassifier = new Id3Classifier(myReader, 4);
        myClassifier.buildDecisionTree();
    }
    
}
