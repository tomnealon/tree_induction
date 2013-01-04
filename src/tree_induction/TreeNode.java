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
    
    public HashMap classificationRatioMap = new HashMap();
    
    public TreeNode(int splitAtt, String value) {
        this.splitAtt = splitAtt; 
        this.decidingValue = value;
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
