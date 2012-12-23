package tree_induction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tom
 */
public class treeSelector {
    private ReaderMemory reader;
    private int classAtt;
    public double classInfoGain;
    
    public treeSelector(ReaderMemory reader, int classAtt)  {
        this.reader = reader;
        this.classAtt = classAtt;
        classInfoGain = infoNeed();
    }
    
    public int getBestAtt(List<Integer> colList) {
        int best =  0;
        double currentInfo = 0;
        for(int col : colList) {
            if(infoAtt(col) > currentInfo) {
                currentInfo = infoAtt(col);
                best = col;
            }
        }        
        return best;
    }
    
    private double infoNeed() {
        double info = 0;
        int noClasses = reader.getValues(classAtt).size();
        double rows = reader.getRows();
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
    

    
    /*
     *  Calculates the information gain for partitioning on that attribute.
     */
    public double testAtt(int col) {
        double testInfo = 0;
        Iterator testIterator = reader.getValues(col).iterator();
        while(testIterator.hasNext()) {
            Iterator valueIterator = reader.getValues(classAtt).iterator();
            String testValue = (String) testIterator.next();
            double noValues = reader.valueOccurs(testValue, col);
            double testInfoMult = noValues / reader.getRows();
            double testInfoForValue = 0;
            while(valueIterator.hasNext()) {
                String classValue = (String) valueIterator.next();
                double matches = valueOccursForColValue(classValue, col, testValue);
                
                //System.out.println(testValue + " " + classValue + " " + matches);
                if(matches != 0) {
                    testInfoForValue = testInfoForValue - (logTwo(matches / noValues) * (matches / noValues));
                }
                System.out.println(testValue + " " + classValue + " " + noValues + " " + matches + " " + testInfoForValue);
            }
            testInfo = testInfo + testInfoForValue * testInfoMult;
        }
        return classInfoGain - testInfo;
    }
    
    /*      
    *    Gives the number of times a classification value occurs in the same row
    *    as a certain value in another column.
     */
    private int valueOccursForColValue(String classValue, int col, String colValue) {
        int occurances = 0;
        for(int i = 0; i < reader.getRows(); i++) {
            if(reader.getCell(i, col).equals(colValue) && reader.getCell(i, classAtt).equals(classValue)) {
                occurances++;
            }
        }
        return occurances;
    }
    
    private double logTwo(double i) {
        return (Math.log(i) / Math.log(2));
    }
    
    
    
}
