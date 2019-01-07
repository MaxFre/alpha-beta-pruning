package pacman;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class testar {

	
	public testar(){
	
		
		
	String 		filenameToBeOverWritten = "myData/dataset.txt";
	
		
	
	
	// tömmer filen först
	System.out.println("emptying this file: "+ filenameToBeOverWritten);
	FileOutputStream writer;
	
	try {
		writer = new FileOutputStream(filenameToBeOverWritten);
		writer.write(("").getBytes());
		writer.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	
	public static void main(String[] args) {
		testar t = new testar();

	}

}
