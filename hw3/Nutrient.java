//Name: Xiaodi Tao AndrewID: xiaodit
package hw3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Nutrient {
	// Sets member variables
	private StringProperty nutrientCode =new SimpleStringProperty();
	private StringProperty nutrientName =new SimpleStringProperty();
	private StringProperty nutrientUom =new SimpleStringProperty();
	// Default constructor that initializes all string properties to empty strings 
	public Nutrient() {
		this.nutrientCode.set(" ");
		this.nutrientName.set(" ");
		this.nutrientUom.set(" ");
	}
	// Non-default constructor initializes nutrientCode, nutrientName, and nutrientUom properties
	public Nutrient(String nutrientCode,String nutrientName,String nutrientUom) {
		this.nutrientCode.set(nutrientCode);
		this.nutrientName.set(nutrientName);
		this.nutrientUom.set(nutrientUom);
	}
	// All setters and getters
	public String getNutrientCode() {
		return nutrientCode.get();
	}
	public void setNutrientCode(String nutrientCode) {
		this.nutrientCode.set(nutrientCode);
	}
	public String getNutrientName() {
		return nutrientName.get();
	}
	public void setNutrientName(String nutrientName) {
		this.nutrientName.set(nutrientName);
	}
	public String getNutrientUom() {
		return nutrientUom.get();
	}
	public void setNutrientUom(String nutrientUom) {
		this.nutrientUom.set(nutrientUom);
	}
	public void setNutrientCode(StringProperty nutrientCode) {
		this.nutrientCode = nutrientCode;
	}
	public void setNutrientName(StringProperty nutrientName) {
		this.nutrientName = nutrientName;
	}
	public void setNutrientUom(StringProperty nutrientUom) {
		this.nutrientUom = nutrientUom;
	}
	public StringProperty nutrientNameProperty() {
		return this.nutrientName;
	}
	public StringProperty nutrientCodeProperty() {
		return this.nutrientCode;
	}
	public StringProperty nutrientUomProperty() {
		return this.nutrientUom;
	}
}
