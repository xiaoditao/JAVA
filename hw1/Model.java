//Name:Xiaodi Tao
//Andrew id:xiaodit
package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Model {
	//member variables of the class
	Product[] products;
	Nutrient[] nutrients;
	ProductNutrient[] productNutrients;

	//reads NutriByte.PRODUCT_FILE file to load product objects 
	//in the products array 
	void readProducts(String filename) throws FileNotFoundException {
		//reads the file, computes the number of rows in the file
		Scanner input=new Scanner(new File(filename));
		int numOfRow=0;
		while(input.hasNextLine()) {
			numOfRow++;
			input.nextLine();
		}
		//load the products array one by one, using constructor
		products=new Product[numOfRow-1];
		Scanner input2=new Scanner(new File(filename));
		input2.nextLine();
		int i=0;
		while(input2.hasNextLine()) {
			String row=input2.nextLine();
			String[] split1=row.split(((char)34+","+(char)34));
			products[i++]=new Product(split1[0].substring(1, split1[0].length())
					, split1[1], split1[4], split1[7].substring(0, split1[7].length()-1));
		}
		//close the input
		input.close();
		input2.close();
	}

	//reads NutriByte.NUTRIENT_FILE to load objects in the nutrients and 
	//productNutrients arrays. Note that the nutrients array will hold 
	//only unique nutrient objects, stored in the order in which they 
	//are read from the file
	void readNutrients(String filename) throws FileNotFoundException {
		//reads the file, computes the number of unique nutrient objects
		//uses StringBuilder to append the unique Nutrient_Code
		Scanner input=new Scanner(new File(filename));
		int numOfRow=0;
		StringBuilder uniqueNutrient=new StringBuilder();
		while(input.hasNextLine()) {
			String row0=input.nextLine();
			String[] split0=row0.split(((char)34+","+(char)34));
			if(!uniqueNutrient.toString().contains(split0[1]+",")) {
				uniqueNutrient=uniqueNutrient.append(split0[1]+",");
				numOfRow++;
			}
		}
		//load the products array one by one, using constructor
		//keep only the unique nutrient objects
		nutrients=new Nutrient[numOfRow-1];
		Scanner input2=new Scanner(new File(filename));
		input2.nextLine();
		int i=0;
		StringBuilder uniqueNutrien2=new StringBuilder();
		while(input2.hasNextLine()) {
			String row=input2.nextLine();
			String[] split1=row.split(((char)34+","+(char)34));
			if(!uniqueNutrien2.toString().contains(split1[1]+",")) {
				uniqueNutrien2.append(split1[1]+",");
				nutrients[i++]=new Nutrient(split1[1], split1[2], split1[5]);
			}
		}

		//reads the file, computes the number of unique productNutrient objects
		Scanner input3 =new Scanner(new File(filename));
		input3.nextLine();
		int numOfRowProductNutrient=0;
		while(input3.hasNextLine()) {
			numOfRowProductNutrient++;
			input3.nextLine();
		}
		//load the products array one by one, using constructor
		productNutrients=new ProductNutrient[numOfRowProductNutrient];
		Scanner input4=new Scanner(new File(filename));
		input4.nextLine();
		int i2=0;
		while(input4.hasNextLine()) {
			String row=input4.nextLine();
			String[] split1=row.split(((char)34+","+(char)34));
			productNutrients [i2++]=new ProductNutrient(split1[0].substring
					(1, split1[0].length()), split1[1], split1[2], 
					Float.parseFloat(split1[4]),split1[5].substring(0, split1[5].length()-1));
		}
		//close the input
		input.close();
		input2.close();
		input3.close();
		input4.close();
	}

	//reads NutriByte.SERVING_SIZE_FILE to populate four fields – servingSize, 
	//servingUom, householdSize, householdUom - in each product object in the products array.
	void readServingSizes(String filename) throws FileNotFoundException {
		//reads the file and populate four field for each product object
		Scanner input2=new Scanner(new File(filename));
		input2.nextLine();
		int i=0;
		while(input2.hasNextLine()) {
			String row=input2.nextLine();
			String[] split1=row.split("\",\"");
			products[i].servingSize=Float.parseFloat(split1[1]);
			products[i].servingUom=split1[2];
			products[i].householdSize=Float.parseFloat(split1[3]);
			products[i].householdUom=split1[4];
			i++;
		}
		//close the input
		input2.close();
	}


}
