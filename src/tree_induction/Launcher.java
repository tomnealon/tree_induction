package tree_induction;

import java.io.IOException;
/**
 *
 * @author tom
 */
public class Launcher {
    
    public static void main(String[] args) throws IOException {
        Launcher.launch();
     }
    
    public static void launch() throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));      
    	//Reader myReader = new ReaderMemory("./data_sources/car.data");
        ReaderMemory myReader = new ReaderMemory("./data_sources/Book1.csv");
//    	System.out.println(myReader.getCell(0, 3));
//    	System.out.println(myReader.getCell(4, 2));
//        System.out.println(myReader.noRows);
//        System.out.println(myReader.noCols);
        ToStringHelper help = new ToStringHelper();
        String classes = help.toString(myReader.getValues(4));
        System.out.println(classes);
    	myReader.printRow(0);
        System.out.println();
        Id3Classifier myClassifier = new Id3Classifier(myReader, 4);
        double info;
        info = myClassifier.testAtt(0);
        System.out.println(myClassifier.classInfoGain);
        System.out.println(info);
        info = myClassifier.testAtt(1);
        System.out.println(info);
        info = myClassifier.testAtt(2);
        System.out.println(info);
        info = myClassifier.testAtt(3);
        System.out.println(info);
        
        
        String colValues = help.toString(myReader.getColList(4));
        System.out.println(colValues);
        
        System.out.println(myClassifier.classificationRatio("no"));
    }
    
    
}
    
    
