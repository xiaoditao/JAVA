//Name: Xiaodi Tao AndrewID: xiaodit
package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;


public class Product {
	// Sets member variables
	private StringProperty ndbNumber =new SimpleStringProperty();
	private StringProperty productName=new SimpleStringProperty();
	private StringProperty manufacturer=new SimpleStringProperty();
	private StringProperty ingredients=new SimpleStringProperty();
	private StringProperty servingUom=new SimpleStringProperty();
	private StringProperty householdUom=new SimpleStringProperty();
	private FloatProperty servingSize =new SimpleFloatProperty();
	private FloatProperty householdSize=new SimpleFloatProperty();
	private ObservableMap<String, ProductNutrient> productNutrients= FXCollections.observableHashMap();	// input into the map
	// Inner class ProductNutrient
	// It has nutrientCode and nutrientQuantity as its two properties
	// Its default constructor initializes nutrientCode to empty string 
	// Its non-default constructor initializes nutrientCode and nutrientQuantity
	// Sets all the setters and getters
	class ProductNutrient{
		private StringProperty nutrientCode = new SimpleStringProperty();
		private FloatProperty nutrientQuantity = new SimpleFloatProperty();
		public ProductNutrient(){
			this.nutrientCode.set(" ");
			this.nutrientQuantity.set(0);
		}
		public ProductNutrient(String nutrientCode,float nutrientQuantity) {
			this.nutrientCode.set(nutrientCode);
			this.nutrientQuantity.set(nutrientQuantity);
		}
		public String getNutrientCode() {
			return nutrientCode.get();
		}
		public void setNutrientCode(String nutrientCode) {
			this.nutrientCode.set(nutrientCode);
		}
		public float getNutrientQuantity() {
			return nutrientQuantity.get();
		}
		public void setNutrientCode(float nutrientQuantity) {
			this.nutrientQuantity.set(nutrientQuantity);
		}
		public void setNutrientCode(StringProperty nutrientCode) {
			this.nutrientCode = nutrientCode;
		}
		public void setNutrientQuantity(FloatProperty nutrientQuantity) {
			this.nutrientQuantity = nutrientQuantity;
		}
		public FloatProperty nutrientQuantityProperty() {
			return this.nutrientQuantity;
		}
		public StringProperty nutrientCodeProperty() {
			return this.nutrientCode;
		}
	}
	// Product has a default constructor that initializes all string properties to empty strings
	// Its non-default constructor initializes ndbNumber, productName, manufacturer, and ingredients
	// Its member variable productNutrients is a hash map to store nutrients in the product
	public Product() {
		this.ndbNumber.set(" ");
		this.productName.set(" ");
		this.manufacturer.set(" ");
		this.ingredients.set(" ");
	}
	public Product(String ndbNumber,String productName, String manufacturer,String ingredients) {
		this.ndbNumber.set(ndbNumber);
		this.productName.set(productName);
		this.manufacturer.set(manufacturer);
		this.ingredients.set(ingredients);
	}
	// All the setters and getters
	public String getNdbNumber() {
		return ndbNumber.get();
	}
	public void setNdbNumber(String ndbNumber) {
		this.ndbNumber.set(ndbNumber);
	}
	public String getProductName() {
		return productName.get();
	}
	public void setProductName(String productName) {
		this.productName.set(productName);
	}
	public String getManufacturer() {
		return manufacturer.get();
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer.set(manufacturer);
	}
	public String getIngredients() {
		return ingredients.get();
	}
	public void setIngredients(String ingredients) {
		this.ingredients.set(ingredients);
	}
	public String getServingUom() {
		return servingUom.get();
	}
	public void setServingUom(String servingUom) {
		this.servingUom.set(servingUom);
	}
	public String getHouseholdUom() {
		return householdUom.get();
	}
	public void setHouseholdUom(String householdUom) {
		this.householdUom.set(householdUom);
	}
	public Float getServingSize() {
		return servingSize.get();
	}
	public void setServingSize(Float servingSize) {
		this.servingSize.set(servingSize);;
	}
	public Float getHouseholdSize() {
		return householdSize.get();
	}
	public void setHouseholdSize(Float householdSize) {
		this.householdSize.set(householdSize);
	}
	public ObservableMap<String, ProductNutrient> getProductNutrients() {
		return productNutrients;
	}
	public void setProductNutrients(String nutrientCode,float nutrientQuantity) {
		productNutrients.put(nutrientCode, new ProductNutrient(nutrientCode,nutrientQuantity));
	}
	public void setNdbNumber(StringProperty ndbNumber) {
		this.ndbNumber = ndbNumber;
	}
	public void setProductName(StringProperty productName) {
		this.productName = productName;
	}
	public void setManufacturer(StringProperty manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setIngredients(StringProperty ingredients) {
		this.ingredients = ingredients;
	}
	public void setServingUom(StringProperty servingUom) {
		this.servingUom = servingUom;
	}
	public void setHouseholdUom(StringProperty householdUom) {
		this.householdUom = householdUom;
	}
	public void setServingSize(FloatProperty servingSize) {
		this.servingSize = servingSize;
	}
	public void setHouseholdSize(FloatProperty householdSize) {
		this.householdSize = householdSize;
	}
	public void setProductNutrients(ObservableMap<String, ProductNutrient> productNutrients) {
		this.productNutrients = productNutrients;
	}
	public StringProperty ndbNumberProperty() {
		return this.ndbNumber;
	}
	public StringProperty productNameProperty() {
		return this.productName;
	}
	public StringProperty manufacturerProperty() {
		return this.manufacturer;
	}
	public StringProperty ingredientsProperty() {
		return this.ingredients;
	}
	public FloatProperty servingSizeProperty() {
		return this.servingSize;
	}
	public StringProperty servingUomProperty() {
		return this.servingUom;
	}
	public FloatProperty householdSizeProperty() {
		return this.householdSize;
	}
	public StringProperty householdUomProperty() {
		return this.householdUom;
	}
	// toString method
	public String toString() {
		return productName.get().toUpperCase()+" by "+ manufacturer.get();  
	}
}
