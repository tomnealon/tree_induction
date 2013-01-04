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
        System.out.println("begin grow tree");
        
        int splitAtt = classifier.getBestAtt();
         System.out.println("calculate splitAtt: " + splitAtt);
        // Set top node with the splitting attribute.
        addTopNode(splitAtt, dataSet);
        setValues((HashSet) classifier.reader.getAttValues(splitAtt));
        
        int count = 0;
        boolean treeFinished = false;
        while(treeFinished == false) {
            System.out.println("grow tree while count: " + count + " Level: "+currentLevel);
            treeFinished = addLevel(classifier.reader.getAttValues(splitAtt));
            count++;
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
  
    public void addTopNode(int att, Reader reader) {
        System.out.println("Added root node with Att: " + att);
//        if(topNode != null) {
            topNode = new TreeNode(att, null, reader);
            ArrayList<TreeNode> level = new ArrayList();
            level.add(topNode);
            scaffold.add(level);
            ToStringHelper help = new ToStringHelper();
            Iterator it = scaffold.get(currentLevel).iterator();
//            return true;
//        } else {
//            return false;
//        }
    }
    
    public void incrementLevel() {
        currentLevel++;
        currentNodePos = 0;
    }
    
    public boolean addLevel(Set values) {
        System.out.println("addLevel");
        ArrayList<TreeNode> newLevel = new ArrayList<>(); 
        ToStringHelper help = new ToStringHelper();
        help.toString(scaffold.get(currentLevel));
        int currentClassAtt = classificationAtt;
        for (Iterator parentIt = scaffold.get(currentLevel).iterator(); parentIt.hasNext();) {
            currentClassAtt--;
            TreeNode eachParent = (TreeNode) parentIt.next();
            for (Iterator valuesIt = values.iterator(); valuesIt.hasNext();) {
                String eachValue  = (String) valuesIt.next();
                int parentSplitAtt = eachParent.splitAtt;
                //eachParent.getChildDataSet(eachValue);
                Reader childDataSet = eachParent.getChildDataSet(eachValue);
                Id3Classifier subClassifier = new Id3Classifier(childDataSet, currentClassAtt);
                
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
        return false;
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
