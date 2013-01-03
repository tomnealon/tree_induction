package tree_induction;

import java.util.HashSet;

/**
 *
 * @author tom
 */
public class DecisionTree {
    TreeNode topNode = null;

    public DecisionTree(TreeNode topNode) {
        this.topNode = topNode;
    }
    
    public DecisionTree() {
        
    }
    
    public boolean addTopNode(int att) {
        if(topNode != null) {
            topNode = new TreeNode(att, null);
            return true;
        } else {
            return false;
        }
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
