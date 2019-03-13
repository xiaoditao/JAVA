//Name:Xiaodi Tao
//Andrew id:xiaodit
package hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;


public class NutriByte {
	Model model = new Model();  				//will handle all data read from the data files
	Scanner input = new Scanner(System.in);  	//to be used for all console i/o in this class

	//set the variables for the class
	static final String PRODUCT_FILE = "data/SampleProducts.csv";
	static final String NUTRIENT_FILE = "data/SampleNutrients.csv";
	static final String SERVING_SIZE_FILE = "data/SampleServingSize.csv";

	//main method, creates the object of the class and loads the data files
	public static void main(String[] args) throws FileNotFoundException {
		NutriByte nutriByte = new NutriByte();
		nutriByte.model.readProducts(PRODUCT_FILE);
		nutriByte.model.readNutrients(NUTRIENT_FILE);
		nutriByte.model.readServingSizes(SERVING_SIZE_FILE );
		//switch the input from the user
		switch (nutriByte.getMenuChoice()) {
		case 1: {
			nutriByte.printSearchResults(nutriByte.searchProductsWithSelectedIngredients(nutriByte.getIngredientChoice()));
			break;
		}
		case 2: {
			int nutrientChoice = nutriByte.getNutrientChoice();
			nutriByte.printSearchResults(nutriByte.searchProductsWithSelectedNutrient(nutrientChoice), nutrientChoice);
			break;
		}
		case 3:  
		default: System.out.println("Good Bye!"); break;
		}
	}

	//print the welcome page for the user
	int getMenuChoice() {
		System.out.println("*** Welcome to NutriByte ***");
		System.out.println("--------------------------------------------------");
		System.out.println("1. Find ingredient(s)");
		System.out.println("2. Find a nutrient");
		System.out.println("3. Exit");
		input = new Scanner(System.in);
		return input.nextInt();
	}


	//get the keywords for searching the ingredients from the user
	String getIngredientChoice() {
		input.nextLine();
		System.out.println("Type the keywords to search for the ingredients");
		System.out.println("--------------------------------------------------");
		return input.nextLine();
	}


	//displays the list of nutrients in three columns and get the choice from the user
	int getNutrientChoice() {
		System.out.println("Select the nutrient you are looking for");
		System.out.println("--------------------------------------------------");
		int count=0;
		int row=0;
		for(Nutrient n:model.nutrients) {
			//print three columns
			row=row+1;
			if(row<=9) {
				System.out.printf("%-1d%-1s%-36s", 
						++count," . ", n.nutrientName );
			}
			if(row>9) {
				System.out.printf("%-1d%-1s%-35s", 
						++count," . ", n.nutrientName );
			}
			if(row%3==0) {
				System.out.println("");
			}

		}
		System.out.println();
		System.out.println("--------------------------------------------------");
		Scanner input=new Scanner(System.in);
		int t=input.nextInt();
		input.close();
		//return the choice from the user
		return t;
	}


	//gets an array of Products that have the selected nutrient for each of the choice
	Product[] searchProductsWithSelectedIngredients(String searchString) {
		//go through all the product objects from the search array and model.product
		//compares them, if they are the same, put it into the product array and finally return
		String[] searchArray=searchString.split(" ");
		int row=0;
		Product[] res=new Product[0];
		//compute the number of the product object in the array
		for (Product p: model.products) {
			for(String s1:searchArray) {
				if (p.ingredients.toLowerCase().contains(s1.toLowerCase())) {					
					row++;
					break;
				}
			}
		}
		//if no found, return the array with a length of 0
		if (row==0) {
			return res;
		}
		//get the product array and return
		Product[] pp=new Product[row];
		int ii=0;
		String[] productArray=searchString.split(" ");
		for(int i=0;i<model.products.length;i++) {
			for(String s2:productArray) {
				if (model.products[i].ingredients.toLowerCase().contains(s2.toLowerCase())) {
					pp[ii++]=model.products[i];
					break;
				}
			}

		}
		return pp;
	}


	//get an array of Products that have the selected nutrient for each of the menu choice.
	Product[] searchProductsWithSelectedNutrient(int menuChoice) {
		int row=0;
		//get the number of product objects in the array
		for (ProductNutrient pn: model.productNutrients) {
			if (pn.nutrientName.toLowerCase().contains
					(model.nutrients[menuChoice-1].nutrientName.toLowerCase())&&pn.quantity!=0) {
				row++;
			}
		}
		//get the productNutrient array
		ProductNutrient[] p1=new ProductNutrient[row];
		int x=0;
		for (ProductNutrient pn: model.productNutrients) {
			if (pn.nutrientName.toLowerCase().contains
					(model.nutrients[menuChoice-1].nutrientName.toLowerCase())&&pn.quantity!=0) {
				p1[x++]=pn;
			}
		}
		//get the product array and return
		Product[] pd1=new Product[row];
		int num=0;
		for(ProductNutrient pn2: p1) {
			for(Product pd:model.products) {
				if(pd.ndbNumber.equals(pn2.ndbNumber)) {
					pd1[num++]=pd;
				}
			}
		}
		return pd1;
	}

	//print the output when a single parameter of type Product[] array is passed
	void printSearchResults(Product[] searchResults) {
		//if not found, print no found; if found, print the number of the findings
		if(searchResults.length==0) {
			System.out.println("*** 0 products found ***");
			System.out.println("--------------------------------------------------");
		}else {
			int count=1;
			//print product names and manufacturers
			System.out.println("*** "+searchResults.length+" products found ***");
			for(Product p:searchResults) {
				System.out.println(count+". "+p.productName+" from "+p.manufacturer);
				count++;
			}
			System.out.println("--------------------------------------------------");
		}
	}

	//print the output when the type Product[] array and an int x are passed
	void printSearchResults(Product[] searchResults, int nutrientChoice) {
		//get the productNutrient array that is corresponding to the searchResult array
		ProductNutrient[] productNutrientArray=new ProductNutrient[searchResults.length];
		int x=0;
		for(ProductNutrient pn1:model.productNutrients) {
			for(Product p1:searchResults) {
				if (pn1.ndbNumber.equals(p1.ndbNumber)&&pn1.nutrientName.toLowerCase().
						equals(model.nutrients[nutrientChoice-1].nutrientName.toLowerCase())) {
					productNutrientArray[x]=pn1;
					x++;
				}
			}
		}
		//print the number of results
		System.out.println("*** "+searchResults.length+" products found ***");
		int count=1;
		//for each of the results, print the householdSize, householdUom, productName and other information
		for(int i=0;i<searchResults.length;i++) {
			float num=searchResults[i].servingSize*productNutrientArray[i].quantity/100;
			System.out.print(count+". ");
			System.out.printf("%.2f",searchResults[i].householdSize);
			System.out.print(" "+searchResults[i].householdUom+" of "+searchResults[i].ndbNumber
					+": "+searchResults[i].productName+" has ");
			System.out.printf("%.2f",num);
			System.out.print(" "+productNutrientArray[i].nutrientUom+" of "+model.nutrients[nutrientChoice-1].nutrientName);
			System.out.println();
			count++;
		}
		System.out.println("--------------------------------------------------");
	}
}

