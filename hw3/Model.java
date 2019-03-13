//Name: Xiaodi Tao AndrewID: xiaodit
package hw3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Model {
	// Sets member variables
	static ObservableMap<String, Product> productsMap= FXCollections.observableHashMap();
	static ObservableMap<String, Nutrient> nutrientsMap= FXCollections.observableHashMap();
	ObservableList<Product> searchResultList = FXCollections.observableArrayList();
	// Reads NutriByte.PRODUCT_FILE file to load product objects in the productsMap
	void readProducts(String filename) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(filename), csvFormat);
			for (CSVRecord csvRecord : csvParser) {
				Product product = new Product(csvRecord.get(0),csvRecord.get(1),
						csvRecord.get(4),csvRecord.get(7));
				productsMap.put(csvRecord.get(0), product);
			}
		} catch (FileNotFoundException e1) {e1.printStackTrace();}
		catch (IOException e2) {e2.printStackTrace();}
	}
	// Reads NutriByte.NUTRIENT_FILE to load objects in the nutrients and products maps
	// NutrientsMap holds only unique nutrient objects 
	void readNutrients(String filename) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(filename), csvFormat);
			for (CSVRecord csvRecord : csvParser) {
				Nutrient nutrient = new Nutrient(csvRecord.get(1),csvRecord.get(2),
						csvRecord.get(5));
				if (!nutrientsMap.containsKey(csvRecord.get(1))) {
					nutrientsMap.put(csvRecord.get(1), nutrient);
				}
				if (!csvRecord.get(4).equals("0.0")) {
					Product product=productsMap.get(csvRecord.get(0));
					Product.ProductNutrient productNutrient=product.new ProductNutrient(nutrient.getNutrientCode(),Float.parseFloat(csvRecord.get(4)));
					product.getProductNutrients().put(nutrient.getNutrientCode(), productNutrient);
				}

			}
		} catch (FileNotFoundException e1) {e1.printStackTrace();}
		catch (IOException e2) {e2.printStackTrace();}
	}
	// Reads NutriByte.SERVING_SIZE_FILE to populate four fields 
	// in each product object in the productsMaps
	void readServingSizes(String filename) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(filename), csvFormat);
			for (CSVRecord csvRecord : csvParser) {
				if (!csvRecord.get(1).equals("")&&!csvRecord.get(3).equals("")) {
					Product product=productsMap.get(csvRecord.get(0));
					product.setServingSize(Float.parseFloat(csvRecord.get(1)));
					product.setServingUom(csvRecord.get(2));
					product.setHouseholdSize(Float.parseFloat(csvRecord.get(3)));
					product.setHouseholdUom(csvRecord.get(4));
				}
				if (csvRecord.get(1).equals("")&&!csvRecord.get(3).equals("")) {
					Product product=productsMap.get(csvRecord.get(0));
					product.setServingSize(0f);
					product.setServingUom(csvRecord.get(2));
					product.setHouseholdSize(Float.parseFloat(csvRecord.get(3)));
					product.setHouseholdUom(csvRecord.get(4));
				}
				if (!csvRecord.get(1).equals("")&&csvRecord.get(3).equals("")) {
					Product product=productsMap.get(csvRecord.get(0));
					product.setServingSize(Float.parseFloat(csvRecord.get(1)));
					product.setServingUom(csvRecord.get(2));
					product.setHouseholdSize(0f);
					product.setHouseholdUom(csvRecord.get(4));
				}
				if (csvRecord.get(1).equals("")&&csvRecord.get(3).equals("")) {
					Product product=productsMap.get(csvRecord.get(0));
					product.setServingSize(0f);
					product.setServingUom(csvRecord.get(2));
					product.setHouseholdSize(0f);
					product.setHouseholdUom(csvRecord.get(4));
				}
			}
		} catch (FileNotFoundException e1) {e1.printStackTrace();}
		catch (IOException e2) {e2.printStackTrace();}
	}
	// Reads the profile file chosen by the user 
	// Uses DataFiler’s child classes to read CSV or XML files depending 
	// the file extension as csv or xml respectively
	// Returns true or false depending on the value returned by the readFile() method 
	// of CSVFilder or XMLFiler
	boolean readProfile(String filename){
		if(filename==null||filename==""||filename.length()<4) {
			return false;
		}else {
			int length = filename.trim().length();
			String fileType = filename.substring(length-3,length);
			if(fileType.toLowerCase().equals("csv".toLowerCase())){
				CSVFiler csvFiler = new CSVFiler();
				return csvFiler.readFile(filename);
			}else if (fileType.toLowerCase().equals("xml".toLowerCase())) {
				XMLFiler xmlFiler = new XMLFiler();
				return xmlFiler.readFile(filename);
			}
			else {
				return false;
			}
		}
	}
	void writeProfile(String filename){
		CSVFiler csvFiler = new CSVFiler();
		csvFiler.writeFile(filename);
	}
}
