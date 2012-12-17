package tree_induction;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author tom
 */
public class treeSelection {
    private Reader reader;
    private int classAtt;
    
    public treeSelection(Reader reader, int classAtt)  {
        this.reader = reader;
        this.classAtt = classAtt;
    }
    
    public double infoNeed() {
        double info = 0;
        int noClasses = reader.getValues(classAtt).size();
        double rows = reader.noRows;
        Iterator valueIterator = reader.getValues(classAtt).iterator();
        while(valueIterator.hasNext()) {
            String currentValue = String.valueOf(valueIterator.next());
            double instances = reader.valueOccurs(currentValue, classAtt);
            //System.out.println(currentValue + " " + rows + " " + instances + " " + (instances / rows));
            //System.out.println((logTwo(instances / rows))*(instances / rows));
            info = info - (logTwo(instances / rows))*(instances / rows);
        }
        return info;
    } 
    
    public double infoAtt(int col) {
        double info = 0;
        Iterator valueIterator = reader.getValues(classAtt).iterator();
        while(valueIterator.hasNext()) {
            
        }
        return info;
    }
    
    public int attributeSelect() {
        
        return 1;
    }
    
    /*      
    *    Gives the number of times a classification value occurs in the same row
    *    as a certain value in another column.
     */
    public int valueOccursForColValue(String value, int col, String colValue) {
        int i = 0;
        
        return i;
    }
    
    private double logTwo(double i) {
        return (Math.log(i) / Math.log(2));
    }
    
    
    
}
