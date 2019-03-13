package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner input;
		try {
			input = new Scanner(new File("SampleProducts.csv"));
			for(int i=0;i<5;i++) {
				String string=input.nextLine();
				String[] row=string.split((char)34+","+(char)34);
				for(int j=0;j<row.length;j++) {
					System.out.println(row[j]);
				}
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}

}
