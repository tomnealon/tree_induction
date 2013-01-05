package tree_induction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author tom
 */
public class TreeNode {
    public String myName = "nodey!";
    public TreeNode parent = null;
    public String classificationValue = null;
    public int splitAtt;
    public String attValue;
    public ArrayList<TreeNode> children = new ArrayList<>();
    public Set<String> childValues;
    public HashMap valuesChildrenMap = new HashMap();
    public HashMap classificationRatioMap = new HashMap();
    private String finalDecision = null;
    public Reader dataSet; 
    
    public void setFinalDecision(String decision) {
        this.finalDecision = decision;
    }
    
    public TreeNode(int splitAtt, String value, Reader dataSet, Set childValues) {
        System.out.println("New Node:: SplitAt: "+splitAtt+" Using Value: "+value);
        this.splitAtt = splitAtt; 
        this.attValue = value;
        this.dataSet = dataSet;
        this.childValues = childValues;
        
    }
    
    public Reader getChildDataSet(String decidingValue) {
        Reader newDataSet = dataSet.getSubsetReader(splitAtt, decidingValue);
        return newDataSet;
    }
    
    public void linkChild(TreeNode child, String value) {
        valuesChildrenMap.put(value, child);
    }
    
    public void addRatio(String value, double ratio) {
        classificationRatioMap.put(value, ratio);
    }
    
    public double getRatio(String value) {
        return (double) classificationRatioMap.get(value);
    }
    
    public void addChildValues(HashSet values) {
        this.childValues = values;
    }
    
    public void addChild() {
        
    }
    
    
    
}
