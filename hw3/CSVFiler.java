//Name: Xiaodi Tao AndrewID: xiaodit
package hw3;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import hw3.NutriProfiler.PhysicalActivityEnum;


public class CSVFiler extends DataFiler{
	@Override
	public boolean readFile(String filename) {
		// Takes the csv filename, reads the first line that has gender, age, weight, height, 
		// physical activity level, and a series of comma separated ingredients to watch. 
		// Uses this data to create a Male or Female object and assigns it to NutriByte.person. 
		// Returns true if file read successfully. Returns false otherwise. 
		try {
			Scanner input = new Scanner(new File(filename));
			String row = input.nextLine();
			try{
				Person person = validatePersonData(row);
				NutriByte.person=person;
			}catch (InvalidProfileException e) {
				input.close();
				return false;
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	@Override
	public void writeFile(String filename) {
		// Use Stringbuilder and String arrays to help write file 
		// when clicking saving button
		try {
			FileWriter fWriter = new FileWriter("profiles/"+filename);
			StringBuilder sBuilder =new StringBuilder();
			if(NutriByte.person instanceof Female){
				sBuilder.append("Female"+"TAO");
			}
			if(NutriByte.person instanceof Male){
				sBuilder.append("Male"+"TAO");
			}
			String[] ingredient = NutriByte.person.ingredientsToWatch.split(",");
			sBuilder.append(NutriByte.person.age+"TAO");
			sBuilder.append(NutriByte.person.weight+"TAO");
			sBuilder.append(NutriByte.person.height+"TAO");
			sBuilder.append(NutriByte.person.physicalActivityLevel+"TAO");
			for (int i=0;i<ingredient.length-1;i++) {
				sBuilder.append(ingredient[i]+"TAO");
			}
			sBuilder.append(ingredient[ingredient.length-1]);
			String[] record = sBuilder.toString().split("TAO");
			fWriter.write(record[0]);
			for(int i=1;i<record.length;i++) {
				fWriter.write(", "+record[i]);
			}
			fWriter.write(System.getProperty("line.separator"));

			for(int n=0;n<Controller.dietProductsListTemp.size();n++){
				String[] record2 = new String[3];

				Product product = Controller.dietProductsListTemp.get(n);
				record2[0] = product.getNdbNumber()+"";
				record2[1] = product.getServingSize()+"";
				record2[2] = product.getHouseholdSize()+"";
				fWriter.write(record2[0]+","+record2[1]+","+record2[2]);
				fWriter.write(System.getProperty("line.separator"));
			}
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Takes the first string with gender, age, weight...etc. and checks each value one by one. 
	// If some data is missing or invalid, throws InvalidProfileException with appropriate message
	public Person validatePersonData(String data) {
		String row[] = data.split(",");
		String gender;
		float agereal;
		float weightreal;
		float heightreal;
		float physicalActivityLevelreal;
		String ingredientsToWatch;
		try {
			gender = row[0].trim();
			if (!gender.equalsIgnoreCase("male")&&!gender.equalsIgnoreCase("female")) {
				throw new InvalidProfileException("The profile must have gender: Female or Male as first word");
			}
		} catch (InvalidProfileException e) {	
			new InvalidProfileException("Could not read profile data");
			return null;
		}
		// Throws InvalidProfileException with appropriate message 
		try {
			String agestring = row[1].trim();
			float age = Float.parseFloat(agestring);
			try {
				agereal=age;
				if (agereal < 0) {
					throw new InvalidProfileException("Age must be positive");
				}
			} catch (InvalidProfileException e) {
				return null;
			}
		} catch (NumberFormatException e) {	
			try {
				throw new InvalidProfileException("Invalid data for Age: "+row[1]+"\n"+
						"Age must be a number");
			} catch (InvalidProfileException e2) {
				new InvalidProfileException("Could not read profile data");
				return null;
			}
		}
		// Throws InvalidProfileException with appropriate message 
		try {
			String weightstring = row[2].trim();
			float weight = Float.parseFloat(weightstring);
			try {
				weightreal=weight;
				if (weightreal < 0) {
					throw new InvalidProfileException("Weight must be positive");
				}
			} catch (InvalidProfileException e) {
				return null;
			}
		} catch (NumberFormatException e) {		
			try {
				throw new InvalidProfileException("Invalid data for Weight: "+row[2]+"\n"+
						"Weight must be a number");
			} catch (InvalidProfileException e2) {
				new InvalidProfileException("Could not read profile data");
				return null;
			}
		}
		// Throws InvalidProfileException with appropriate message 
		try {
			String heightstring = row[3].trim();
			float height = Float.parseFloat(heightstring);
			try {
				heightreal=height;
				if (heightreal < 0) {
					throw new InvalidProfileException("Height must be positive");
				}
			} catch (InvalidProfileException e) {
				return null;
			}
		} catch (NumberFormatException e) {	
			try {
				throw new InvalidProfileException("Invalid data for Height: "+row[3]+"\n"+
						"Height must be a number");
			} catch (InvalidProfileException e2) {
				new InvalidProfileException("Could not read profile data");
				return null;
			}
		}
		// Throws InvalidProfileException with appropriate message 
		try {
			String physicalActivityLevelString = row[4].trim();
			float  physicalActivityLevel = Float.parseFloat(physicalActivityLevelString);
			try {
				physicalActivityLevelreal=physicalActivityLevel;
				int flag=0;
				for (PhysicalActivityEnum pEnum:PhysicalActivityEnum.values()) {
					if (physicalActivityLevelreal==pEnum.getPhysicalActivityLevel()) {
						flag++;
					}
				}
				if (flag==0) {
					throw new InvalidProfileException("Invalid physical activity level: "+row[4]+"\n"+
							"Must be: 1.0, 1.1, 1.25, or 1.48");
				}
			} catch (InvalidProfileException e) {
				new InvalidProfileException("Could not read profile data");
				return null;
			}
		} catch (NumberFormatException e) {	
			try {
				throw new InvalidProfileException("Invalid data for physical Activity Level: "+row[4]+"\n"+
						"Physical Activity Level must be a number");
			} catch (InvalidProfileException e2) {
				new InvalidProfileException("Could not read profile data");
				return null;
			}
		}
		// Throws InvalidProfileException with appropriate message 
		try {
			StringBuilder sBuilder =new StringBuilder();
			for(int j =5;j<row.length-1;j++) {	
				sBuilder=sBuilder.append(row[j].trim()+",");
			}
			sBuilder=sBuilder.append(row[row.length-1]);
			ingredientsToWatch = sBuilder.toString();
			if (ingredientsToWatch.trim().length()==0||ingredientsToWatch==null) {
				throw new InvalidProfileException("Missing data for ingredients to watch");
			}
		} catch (InvalidProfileException e) {			
			return null;
		}
		if (gender.equalsIgnoreCase("male")) {
			Male male =new Male(agereal, weightreal, heightreal, physicalActivityLevelreal, ingredientsToWatch);
			return male;
		}
		if (gender.equalsIgnoreCase("female")) {
			Female female = new Female(agereal, weightreal, heightreal, physicalActivityLevelreal, ingredientsToWatch);
			return female;
		}
		return null;
	}
	// Takes each product listed after the first line of person-data, and checks each value one by one
	// If invalid or missing data, then skips the product, and reads the next product 
	// even if no products are listed, the profile’s person-data should still be displayed. 
	public Product validateProductData(String data) {
		String[] row = data.split(",");
		String ndbNumber;

		try {
			ndbNumber = row[0].trim();
			boolean flag= Model.productsMap.containsKey(ndbNumber);
			if (flag == false) {
				throw new InvalidProfileException("No product found with this code: "+ndbNumber);
			}

		} catch (InvalidProfileException e) {
			return null;
		}

		try {
			Float.parseFloat(row[1].trim());
		} catch (NumberFormatException e) {
			if (row.length==2) {
				new InvalidProfileException("Cannot read:"+ row[0].trim()+" "+row[1].trim()+" "+
						"\n"+ "The data must be - String, number, number - for ndb number, serving size household size");
			}else {
				new InvalidProfileException("Cannot read:"+ row[0].trim()+" "+row[1].trim()+" "+row[2].trim()+
						"\n"+ "The data must be - String, number, number - for ndb number, serving size household size");
			}
			return null;
		}
		if (row.length==3) {
			try {
				Float.parseFloat(row[2].trim());
			} catch (NumberFormatException e) {
				if (row.length==2) {
					new InvalidProfileException("Cannot read:"+ row[0].trim()+" "+row[1].trim()+" "+
							"\n"+ "The data must be - String, number, number - for ndb number, serving size household size");
				}else {
					new InvalidProfileException("Cannot read:"+ row[0].trim()+" "+row[1].trim()+" "+row[2].trim()+
							"\n"+ "The data must be - String, number, number - for ndb number, serving size household size");
				}
				return null;
			}
		}else {
			new InvalidProfileException("Cannot read:"+ row[0].trim()+" "+row[1].trim()+" "+
					"\n"+ "The data must be - String, number, number - for ndb number, serving size household size");
			return null;
		}
		Product res = Model.productsMap.get(ndbNumber);
		Product real = new Product();
		real.setNdbNumber(res.getNdbNumber());
		real.setServingSize(Float.parseFloat(row[1]));
		real.setHouseholdSize(Float.parseFloat(row[2]));
		real.setServingUom(res.getServingUom());
		real.setHouseholdUom(res.getHouseholdUom());
		real.setProductName(res.getProductName());
		real.setProductNutrients(res.getProductNutrients());
		return real;	
	}

}
