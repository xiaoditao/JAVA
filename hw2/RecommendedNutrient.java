//Name: Xiaodi Tao AndrewID: xiaodit
package hw2;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecommendedNutrient{
	// Member variables
	private StringProperty nutrientCode = new SimpleStringProperty();
	private FloatProperty nutrientQuantity = new SimpleFloatProperty();
	// Default constructor that initializes nutrientCode to empty string
	public RecommendedNutrient() {
		this.nutrientCode.set(" ");
		this.nutrientQuantity.set(0);
	}
	// Non-default constructor initializes nutrientCode, and nutrientQuantity using the values passed to it
	public RecommendedNutrient(String nutrientCode,float nutrientQuantity) {
		this.nutrientCode.set(nutrientCode);
		this.nutrientQuantity.set(nutrientQuantity);
	}
	// All setters and getters
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
