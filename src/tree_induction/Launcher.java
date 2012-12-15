/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    	Reader myReader = new Reader("C:/Users/tom/git/dmtest/Example.csv");
    	System.out.println(myReader.getCell(0, 3));
    	System.out.println(myReader.getCell(4, 2));
    	myReader.printRow(0);
    }
    
    
}
    
    
