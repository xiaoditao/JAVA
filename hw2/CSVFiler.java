//Name: Xiaodi Tao AndrewID: xiaodit
package hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class CSVFiler extends DataFiler{
	@Override
	public boolean readFile(String filename) {
		// Takes the csv filename, reads the first line that has gender, age, weight, height, 
		//physical activity level, and a series of comma separated ingredients to watch. 
		//Uses this data to create a Male or Female object and assigns it to NutriByte.person. 
		//Returns true if file read successfully. Returns false otherwise. 
		try {
			Scanner input = new Scanner(new File(filename));
			String row = input.nextLine();
			String[] sarr = row.split(",");
			String ingredientsWatch = "";
			// Gets the String type of ingredients to watch
			// Identify the gender and create the Female or Male object
			if (sarr[0].equals("Female")) {
				StringBuilder sBuilder =new StringBuilder();
				for(int j =5;j<sarr.length-1;j++) {
					sBuilder=sBuilder.append(sarr[j].trim()+",");	
				}
				sBuilder=sBuilder.append(sarr[sarr.length-1]);
				ingredientsWatch = sBuilder.toString();
				Female female=new Female(Float.parseFloat(sarr[1]), Float.parseFloat(sarr[2]), 
						Float.parseFloat(sarr[3]), Float.parseFloat(sarr[4]), ingredientsWatch);
				NutriByte.person=female;
			}else {
				StringBuilder sBuilder =new StringBuilder();
				for(int j =5;j<sarr.length-1;j++) {	
					sBuilder=sBuilder.append(sarr[j].trim()+",");
				}
				sBuilder=sBuilder.append(sarr[sarr.length-1]);
				ingredientsWatch = sBuilder.toString();
				// Creates the object using its constructor
				Male male=new Male(Float.parseFloat(sarr[1]), Float.parseFloat(sarr[2]), 
						Float.parseFloat(sarr[3]), Float.parseFloat(sarr[4]), ingredientsWatch);
				NutriByte.person=male;
				input.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	@Override
	public void writeFile(String filename) {}
}
