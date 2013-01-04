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
    Id3Classifier classifier;
    Reader dataSet;
    int classificationAtt;
    
    public DecisionTree(Reader dataSet, int classificationAtt) {  
        this.classificationAtt = classificationAtt;
        this.dataSet = dataSet;
        classifier = new Id3Classifier(dataSet, classificationAtt);
    }
    
    public void growTree() {
        boolean treeFinished = false;
        int splitAtt = classifier.getBestAtt();
        // Set top node with the splitting attribute.
        addTopNode(splitAtt, dataSet);
        setValues((HashSet) classifier.reader.getValues(splitAtt));
        System.out.println("Added root node with Att: " + splitAtt);
        while(treeFinished = false) {
            
            treeFinished = addLevel(classifier.reader.getValues(splitAtt));
            //subReader = reader.getSubsetReader(splitAtt, eachValue);
            //HashSet<String> splitAttValues = (HashSet) reader.getValues(splitAtt);
            //Id3Classifier subClassifier = new Id3Classifier(subReader, childClassAtt);
            // Calculate the new position of the classification attribute once 
            // the splitting attribute has been removed.
//            if(splitAtt < childClassAtt) {
//                childClassAtt = childClassAtt--;
//            }
        }  
    }
  
    public boolean addTopNode(int att, Reader reader) {
        if(topNode != null) {
            topNode = new TreeNode(att, null, reader);
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
    
    public boolean addLevel(Set values) {
        ArrayList<TreeNode> newLevel = new ArrayList<>(); 
        for (Iterator parentIt = scaffold.get(currentLevel).iterator(); parentIt.hasNext();) {
            TreeNode eachParent = (TreeNode) parentIt.next();
            for (Iterator valuesIt = values.iterator(); valuesIt.hasNext();) {
                String eachValue  = (String) valuesIt.next();
                int parentSplitAtt = eachParent.splitAtt;
                //eachParent.getChildDataSet(eachValue);
                Reader childDataSet = eachParent.getChildDataSet(eachValue);
                Id3Classifier subClassifier = new Id3Classifier(childDataSet, classificationAtt);
                
                int childSplitAtt = subClassifier.getBestAtt();
                
                TreeNode newNode = new TreeNode(childSplitAtt, eachValue, childDataSet);
                
                System.out.println("New Node SplitAtt: " + childSplitAtt + " Value: " + eachValue + " Level: " + currentLevel);
                
                eachParent.linkChild(newNode, eachValue);
                String currentResultForNode = subClassifier.checkDecisionValue();
                if(currentResultForNode != null) {
                    newNode.classificationValue = currentResultForNode;
                } else {
                    newLevel.add(newNode);
                }
            }
        }
        scaffold.add(newLevel);
        currentLevel++;
        return true;
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
