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
    
    ToStringHelper help = new ToStringHelper();
    
    int count;
    
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
        
        boolean treeFinished = false;
        while(treeFinished == false || count < 13) {
            System.out.println("grow tree while count: " + count + " Level: "+currentLevel);
            treeFinished = addLevel();
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
        
        Set<String> childValues = reader.getAttValues(att);
        topNode = new TreeNode(att, null, reader, childValues);
        ArrayList<TreeNode> level = new ArrayList();
        level.add(topNode);
        scaffold.add(level);
    }
    
    public void incrementLevel() {
        currentLevel++;
        currentNodePos = 0;
    }
    
    public boolean addLevel() {
        
        System.out.println("_____addLevel_____");
        Iterator currentLevelIt = scaffold.get(currentLevel).iterator();
        while(currentLevelIt.hasNext()) {
            TreeNode node = (TreeNode) currentLevelIt.next();
            System.out.print("Node Level "+currentLevel+" Split: "+ node.splitAtt+" Value: "+node.attValue);
            System.out.println();
        }
        //System.out.println("_____addLevel_____");
        
        ArrayList<TreeNode> newLevel = new ArrayList<>(); 
        
        ToStringHelper help = new ToStringHelper();
        help.toString(scaffold.get(currentLevel));
        
        int currentClassAtt = classificationAtt;
        for (Iterator parentIt = scaffold.get(currentLevel).iterator(); parentIt.hasNext();) {
            TreeNode eachParent = (TreeNode) parentIt.next();
            System.out.println("Using Parent Node with splitAtt; "+eachParent.splitAtt+" attValue: "+eachParent.attValue);
            System.out.println("Using Parent Node with values: "+eachParent.childValues);
            currentClassAtt--;
            Set currentValues = eachParent.childValues;
            for (Iterator valuesIt = currentValues.iterator(); valuesIt.hasNext();) {
                String eachValue  = (String) valuesIt.next();
                int parentSplitAtt = eachParent.splitAtt;
                //eachParent.getChildDataSet(eachValue);
                Reader childDataSet = eachParent.getChildDataSet(eachValue);
                if(childDataSet.getRowNo() == 0) {
 
                } else {
                    Id3Classifier subClassifier = new Id3Classifier(childDataSet, currentClassAtt);
                    int childSplitAtt = subClassifier.getBestAtt(); 
                    TreeNode newNode = new TreeNode(childSplitAtt, eachValue, childDataSet, childDataSet.getAttValues(childSplitAtt)); 
                    System.out.println("New Node SplitAtt: " + childSplitAtt + " Value: " + eachValue + " Level: " + currentLevel); 
                    eachParent.linkChild(newNode, eachValue);
                    String finalDecisionValue = subClassifier.checkDecisionValue();
                    System.out.println("Final Decision? "+finalDecisionValue);
                    if(finalDecisionValue != null) {
                        newLevel.add(newNode);
                    }
                    
                }

                
                System.out.println("NOW ADDING NODE TO SCAFFOLD!");
                
//                if(finalDecisionValue != null) {
//                    newNode.classificationValue = finalDecisionValue;
//                } else {
//                    System.out.println("NOW ADDING LEVEL!");
//                    newLevel.add(newNode);
//                }
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
