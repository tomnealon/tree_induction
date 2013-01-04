package tree_induction;

import java.util.*;

/**
 *
 * @author tom
 */
public class Id3Classifier {
    
    // cutOffRatio is the ratio at which a decision node is considered 
    // "good enough" for making a final decision on a tuple/data-row. It is 
    // necessary where a set of attributes does not provide enough information 
    // to make a decision tree that can make a definitive decision on how to
    // classify that row.
    private double cutOffRatio = 0.8;
    private Reader reader;
    private int classAtt;
    public double classInfoGain;
    private Set classificationValues;
    
    public Id3Classifier(Reader reader, int classAtt)  {
        this.reader = reader;
        this.classAtt = classAtt;
        classInfoGain = infoNeed();
        this.classificationValues = reader.getValues(classAtt);
    }
    
    public DecisionTree buildDecisionTree() {
        // Define new DecisionTree
        DecisionTree newTree = new DecisionTree();
        int splitAtt = getBestAtt();
        // Set top node with the splitting attribute.
        newTree.addTopNode(splitAtt);
        newTree.setValues((HashSet) reader.getValues(splitAtt));
        System.out.println("Added root node with Att: " + splitAtt);
        
        
        
        // Go through each value of the splitting attribute and create a child 
        // node for that value and evaluate it's helpfulness in terms of the 
        // ratio of values in the classifcation attribute.
        HashSet<String> splitAttValues = (HashSet) reader.getValues(splitAtt);
        // Iterate through the values of the splitting attribute.
        for(String eachValue : splitAttValues) {
            // Create a reader for each of those values. Each reader is using a
            // subset where each row has each splitting attribute value.
            Reader subReader = reader.getSubsetReader(splitAtt, eachValue);
            // Calculate the new position of the classification attribute once 
            // the splitting attribute has been removed.
            int childClassAtt;
            if(splitAtt < classAtt) {
                childClassAtt = classAtt--;
            } else {
                childClassAtt = classAtt;
            }
            
            subReader.getValues(childClassAtt);
            // Feed that reader to a new Id3Classifier.
            Id3Classifier subClassifier = new Id3Classifier(subReader, childClassAtt);
            String childClassificationValue = subClassifier.getMostCommonClassificationValue();
            
            subClassifier.classificationRatio(eachValue);
        }
        
        //TreeNode parent = new TreeNode;
        
        return newTree;
    }
    
    /**
     * Checks to see if there is only one kind of classification value in the
     * classification column. If there is it returns that value. Otherwise it
     * returns null.
     * @return 
     */
    public String checkDecisionValue() {
        String commonValue = reader.getCell(0, classAtt);
        for (Iterator it = reader.getColList(classAtt).iterator(); it.hasNext();) {
            String eachValue = (String) it.next();
            if(!eachValue.equals(commonValue)) {
                return null;
            }
        }
        return commonValue;
    }
    
    /**
     * Should be combined with classificationRatio method to return a new object
     * type containing the ratio and the most common value so the ratios are not
     * calculated twice.
     * 
     * @return 
     */
    public String getMostCommonClassificationValue() {
        String mostCommonValue = null;
        double currentBestRatio = 0;
        for (Iterator it = classificationValues.iterator(); it.hasNext();) {
            String eachValue = (String) it.next();
            double currentRatio = classificationRatio(eachValue);
            if(currentRatio > currentBestRatio) {
                currentBestRatio = currentRatio;
                mostCommonValue = eachValue;
            }
        }
        return mostCommonValue;
    }
    
    public double classificationRatio(String value) {
        double ratio = 0;
        ArrayList classificationList = reader.getColList(classAtt);
        for (Iterator it = classificationList.iterator(); it.hasNext();) {
            String eachValue = (String) it.next();
            if(eachValue.equals(value)) {
                ratio++;
            }
        }
        if(ratio == 0) {
            return 0;
        }
        //System.out.println("Classification values occur: " + ratio);
        //System.out.println("Thare are Rows: " + (double) reader.getRowNo());
        ratio = ratio / (double) reader.getRowNo();
        //System.out.println("Classification ratio is: " + ratio);
        return ratio;
    }
    
    
    
    /**
     * Gives the attribute/column for which information gain is maximum or 
     * entropy is minimum.
     * 
     * @param colList
     * @return 
     */
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
    
    /**
     * Gives the attribute/column for which information gain is maximum or 
     * entropy is minimum.
     * 
     * @return 
     */
    public int getBestAtt() {
        int best =  0;
        double currentInfo = 0;
        for(int col = 0; col < reader.getColNo(); col++) {
            if(col != classAtt) {
                if(infoAtt(col) > currentInfo) {
                    currentInfo = infoAtt(col);
                    best = col;
                }
            }
        }        
        return best;
    }
    
    /**
     * Calculated the information needed to classify a row according to the test
     * column.
     * 
     * @return 
     */
    private double infoNeed() {
        double info = 0;
        int noClasses = reader.getValues(classAtt).size();
        double rows = reader.getRowNo();
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
    
    /**
     * Calculates the information/entropy of a given attribute/column.
     * 
     * @param col
     * @return 
     */
    public double infoAtt(int col) {
        double info = 0;
        Iterator valueIterator = reader.getValues(classAtt).iterator();
        while(valueIterator.hasNext()) {
            
        }
        return info;
    }
    

    
    /**
     * 
     *  Calculates the information gain for partitioning on that attribute.
     */
    public double testAtt(int col) {
        double testInfo = 0;
        Iterator testIterator = reader.getValues(col).iterator();
        while(testIterator.hasNext()) {
            Iterator valueIterator = reader.getValues(classAtt).iterator();
            String testValue = (String) testIterator.next();
            double noValues = reader.valueOccurs(testValue, col);
            double testInfoMult = noValues / reader.getRowNo();
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
    
    /**
     * Find the most common classification value for a given attribute value.
     * Used to pre-select which value of a given attribute is most useful for 
     * predicting a classification value.
     * @param att
     * @param attValue
     * @return 
     */
    public String getCommonClassValueForAttValue(int att, String attValue) {
        String commonClassValue = null;
        HashMap valueOccursMap = new HashMap();
        for (Iterator it = classificationValues.iterator(); it.hasNext();) {
            String eachValue = (String) it.next();
            valueOccursMap.put(eachValue, 0);
        }
        for(int i = 0; i < reader.getRowNo(); i++) {
            ArrayList currentRow = reader.getRowList(i);
            if(currentRow.get(att).equals(attValue)) {
                String currentValue = (String) currentRow.get(classAtt);
                valueOccursMap.put(currentValue, (int) valueOccursMap.get(currentValue) + 1);
            }
        }
        
        ToStringHelper help = new ToStringHelper();
        System.out.println(help.toString(valueOccursMap));
        
        int currentScore = 0;
        for (Iterator it = classificationValues.iterator(); it.hasNext();) {
            String eachValue = (String) it.next();
            if((int) valueOccursMap.get(eachValue) > currentScore) {
                currentScore = (int) valueOccursMap.get(eachValue);
                commonClassValue = eachValue;
                //System.out.println(valueOccursMap.get(eachValue) + " > " + currentScore);
            }
            
        }
        return commonClassValue;
    }
    
    public String getBestAttValueForClassValue(int attCol, String classValue) {
        String bestAttValue = null;
        HashMap valueOccursMap = new HashMap();
        for (Iterator it = reader.getValues(attCol).iterator(); it.hasNext();) {
            String eachValue = (String) it.next();
            valueOccursMap.put(eachValue, 0);
        }
        for(int i = 0; i < reader.getRowNo(); i++) {
            ArrayList currentRow = reader.getRowList(i);
            if(currentRow.get(classAtt).equals(classValue)) {
                String currentValue = (String) currentRow.get(attCol);
                valueOccursMap.put(currentValue, (int) valueOccursMap.get(currentValue) + 1);
            }
        }
        ToStringHelper help = new ToStringHelper();
        System.out.println(help.toString(valueOccursMap));
        
        int currentScore = 0;
        for (Iterator it = reader.getValues(attCol).iterator(); it.hasNext();) {
            String eachValue = (String) it.next();
            if((int) valueOccursMap.get(eachValue) > currentScore) {
                currentScore = (int) valueOccursMap.get(eachValue);
                bestAttValue = eachValue;
            }
            
        }
        return bestAttValue;
    }
    
    /**
     * 
    *    Gives the number of times a classification value occurs in the same row
    *    as a certain value in another column.
     */
    private int valueOccursForColValue(String classValue, int col, String colValue) {
        int occurances = 0;
        for(int i = 0; i < reader.getRowNo(); i++) {
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
