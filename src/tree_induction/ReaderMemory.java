package tree_induction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class ReaderMemory implements Reader {
    private int noRows;
    private int noCols;

    private ArrayList<ArrayList> store = new ArrayList<>();
    private ArrayList<Set> attValues = new ArrayList<>();
    private boolean setEmptyToNull = true;
    private boolean firstRowLabels = false;

    private int startRow = 0;
    
    /**
     * 
     * @param fileName
     * @throws IOException 
     */
    public ReaderMemory(String fileName) throws IOException {
        System.out.println("Reader created using: " + fileName);
        BufferedReader CSVFile = new BufferedReader(new FileReader(fileName));
        String dataRow = CSVFile.readLine();
        while (dataRow != null) {
            String[] dataArray = dataRow.split(",");
            store.add(setTypeList(dataArray));
            dataRow = CSVFile.readLine();
        }
        CSVFile.close();
        // Metadata calculations for this dataset.
        calcNoCols();
        calcNoRows();
        buildAttValues();
    }
    
    /**
     * Constructor for creating a subset of an existing reader that is generated
     * by taking all the rows that have a certain value in a certain column and 
     * then removing that column.
     * 
     * @param parentReader
     * @param col
     * @param value 
     */
    public ReaderMemory(Reader parentReader, int col, String value) {
        
        for (int i = 0; i < parentReader.getRowNo(); i++) {
            ArrayList currentRow = parentReader.getRowList(i);
            if(currentRow.get(col) == value) {
                currentRow.remove(col);
                store.add(currentRow);
            }
        }
        // Metadata calculations for this dataset.
        calcNoCols();
        calcNoRows();
        buildAttValues();
        System.out.println("SubReader Created using col: " + col + " value: " + value);
    }
    

    
    @Override
    public ArrayList getColList(int col) {
        ArrayList<String> colList = new ArrayList<>();
        for (Iterator<ArrayList> it = store.iterator(); it.hasNext();) {
            ArrayList eachRow = it.next();
            colList.add((String) eachRow.get(col));
        }
        return colList;
    }
    
    @Override
    public Reader getSubsetReader(int col, String value) {
        return new ReaderMemory(this, col, value);
    }

    @Override
    public int getRowNo() {
        return noRows;
    }

    @Override
    public int getColNo() {
        return noCols;
    }

    @Override
    public int valueOccurs(String value, int col) {
        int occurances = 0;
        for(int i = 0; i < noRows; i++) {
            if(getCell(i, col).equals(value)) {
                occurances++;
            }
        }
        return occurances;
    }

    public Set getValues(int col) {
        return getAttValues(col);
    }

    @Override
    public ArrayList getRowList(int row) {
            return store.get(row);
    }

    @Override
    public String getCell(int row, int col) {
            return String.valueOf(store.get(row).get(col));
    }

    public void printRow(int row) {
            ArrayList dataArray = getRowList(row);
            for (Object item:dataArray) {
                    System.out.print(item + "\t");
            }
    }

    private boolean detectInt(String cell) {
            return cell.matches("-?\\d+(\\.\\d+)?");
    }

    private ArrayList setTypeList(String[] rowArray) {
            ArrayList typedRow = new ArrayList();
            int currentCol = 0;
            for (String item:rowArray) {
                    if (detectInt(item)) {
                            typedRow.add(Integer.parseInt(item));
                    } else {
                            typedRow.add(item);
                    }
                    currentCol++;
            }
            return typedRow;
    }

    private ArrayList<?> procColumn(int colNo, int valuesBoundary) {
        ArrayList discreteValues = null;
        ArrayList rawColumn = store.get(colNo);

        return discreteValues;
    }

    private int calcNoRows() {
        noRows = store.size();
        return noRows;
    }

    private int calcNoCols() {
        noCols = store.get(0).size();
        return noCols;
    }

    private void buildAttValues() {
        for(int i = 0; i < noCols; i++) {
            attValues.add(getAttValues(i));
        }
    }

    public Set<String> getAttValues(int col) {
        Set<String> colClasses = new HashSet<>();
        for(int i = startRow; i < noRows; i++) {
            colClasses.add(getCell(i, col));
        }
        return colClasses;
    }

}
