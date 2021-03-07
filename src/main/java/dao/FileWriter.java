package dao;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileWriter {
	
	public FileWriter(String output) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Bill.txt")));
			bw.write(output);
			bw.close();
		} catch ( IOException e) {
			System.out.println("ERROR (creating Bill.txt)");
			e.printStackTrace();
		}
	}
}
