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
        treeSelector myTree = new treeSelector(myReader, 4);
        double info;
        info = myTree.testAtt(0);
        System.out.println(myTree.classInfoGain);
        System.out.println(info);
        info = myTree.testAtt(1);
        System.out.println(info);
        info = myTree.testAtt(2);
        System.out.println(info);
        info = myTree.testAtt(3);
        System.out.println(info);
    }
    
    
}
    
    
