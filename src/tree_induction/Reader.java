/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree_induction;

import java.util.ArrayList;
import java.util.Set;

/**
 * A Reader reads a CSV file and makes the dataset available to data analysis
 * classes.
 * 
 * 
 * @author tom
 */
public interface Reader {
    
    Reader getSubsetReader(int col, String value);
    
    int getRowNo();
    
    int getColNo();
    
    /**
     * Counts the number of times a particular value occurs in a given column.
     * @param value the string to be checked for.
     * @param col the column to be checked.
     * @return the number of times the value occurs in the column.
     * 
     */
    int valueOccurs(String value, int col);
    
    Set getValues(int col);
    
    ArrayList getRow(int row);
    
    String getCell(int row, int col);
    
    
    
}
