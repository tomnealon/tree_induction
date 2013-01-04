package tree_induction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Provides an interface for a collection of decision nodes allowing a tree to 
 * be used to analyse a data set.
 * @author tom
 */
public class DecisionTree {
    private TreeNode topNode = null;
    private ArrayList<ArrayList> scaffold = new ArrayList<>();
    private int currentLevel = 0;
    private int currentNodePos = 0;
    
    public DecisionTree() {  
    }
    
  
    public boolean addTopNode(int att) {
        if(topNode != null) {
            topNode = new TreeNode(att, null);
            ArrayList<TreeNode> level = new ArrayList();
            level.add(topNode);
            scaffold.add(level);
            return true;
        } else {
            return false;
        }
    }
    
    public void incrementLevel() {
        currentLevel++;
        currentNodePos = 0;
    }
    
    public void addLevel(int splitAtt, Set values, Id3Classifier classifier) {
        ArrayList<TreeNode> newLevel = new ArrayList<>(); 
        for (Iterator parentIt = scaffold.get(currentLevel).iterator(); parentIt.hasNext();) {
            TreeNode eachParent = (TreeNode) parentIt.next();
            for (Iterator valuesIt = values.iterator(); valuesIt.hasNext();) {
                String eachValue  = (String) valuesIt.next();
                TreeNode newNode = new TreeNode(splitAtt, eachValue);
                
                System.out.println("New Node SplitAtt: " + splitAtt + " Value: " + eachValue + " Level: " + currentLevel);
                
                eachParent.linkChild(newNode, eachValue);
                String currentResultForNode = classifier.checkDecisionValue();
                if(currentResultForNode != null) {
                    newNode.classificationValue = currentResultForNode;
                } else {
                    newLevel.add(newNode);
                }
            }
        }
        scaffold.add(newLevel);
        currentLevel++;
    }
    
    public void addNode() {
        scaffold.add(scaffold);
    }
    
    
    
    public boolean setValues(HashSet values) {
        if(topNode != null) {
            topNode.addChildValues(values);
            return true;
        } else {
            return false;
        }
    }
    
    
}
