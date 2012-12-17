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
    	//Reader myReader = new Reader("./data_sources/car.data");
        Reader myReader = new Reader("./data_sources/Book1.csv");
//    	System.out.println(myReader.getCell(0, 3));
//    	System.out.println(myReader.getCell(4, 2));
//        System.out.println(myReader.noRows);
//        System.out.println(myReader.noCols);
        ToStringHelper help = new ToStringHelper();
        String classes = help.toString(myReader.getValues(4));
        System.out.println(classes);
    	myReader.printRow(0);
        System.out.println();
        treeSelection myTree = new treeSelection(myReader, 4);
        double info = myTree.infoNeed(4);
        System.out.println(info);
    }
    
    
}
    
    
