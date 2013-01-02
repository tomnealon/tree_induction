package tree_induction;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author tom
 */
public class TreeNode {
    int splitAtt;
    String value;
    ArrayList<TreeNode> children = new ArrayList<>();
    HashSet<String> childValues = new HashSet<>();
    
    public TreeNode(int splitAtt, String value) {
        this.splitAtt = splitAtt; 
        this.value = value;
    }
    
    public void addChild() {
        
    }
    
    
    
}
