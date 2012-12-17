package tree_induction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Reader {
        public int noRows;
        public int noCols;
    
	private ArrayList<ArrayList> store = new ArrayList<>();
        private ArrayList<Set> attValues = new ArrayList<>();
	private boolean setEmptyToNull = true;
        private boolean firstRowLabels = false;
        
        private int startRow = 0;

	public Reader(String fileName) throws IOException {
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
            return attValues.get(col);
        }
	
	public ArrayList getRow(int row) {
		return store.get(row);
	}
	
	public String getCell(int row, int col) {
		return String.valueOf(store.get(row).get(col));
	}
	
	public void printRow(int row) {
		ArrayList dataArray = getRow(row);
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
        
        private Set<String> getAttValues(int col) {
            Set<String> colClasses = new HashSet<>();
            for(int i = startRow; i < noRows; i++) {
                colClasses.add(getCell(i, col));
            }
            return colClasses;
        }

}
