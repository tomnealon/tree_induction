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
    	Reader myReader = new Reader("./data_sources/car.data");
    	System.out.println(myReader.getCell(0, 3));
    	System.out.println(myReader.getCell(4, 2));
    	myReader.printRow(0);
        treeSelection myTree = new treeSelection(myReader, 6);
    }
    
    
}
    
    
