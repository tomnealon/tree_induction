package tree_induction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author tom
 */
public class TreeNode {
    public TreeNode parent = null;
    public String classificationValue = null;
    public int splitAtt;
    public String decidingValue;
    public ArrayList<TreeNode> children = new ArrayList<>();
    public HashSet<String> childValues = new HashSet<>();
    public HashMap valuesChildrenMap = new HashMap();
    public HashMap classificationRatioMap = new HashMap();
    private String finalDecision = null;
    public Reader dataSet; 
    
    public void setFinalDecision(String decision) {
        this.finalDecision = decision;
    }
    
    public TreeNode(int splitAtt, String value, Reader dataSet) {
        this.splitAtt = splitAtt; 
        this.decidingValue = value;
        this.dataSet = dataSet;
        
    }
    
    public Reader getChildDataSet(String decidingValue) {
        return dataSet.getSubsetReader(splitAtt, decidingValue);
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
