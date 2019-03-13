//Name:Xiaodi Tao
//Andrew id:xiaodit
package hw1;

public class ProductNutrient {
	//member variables
	String ndbNumber;
	String nutrientCode;
	String nutrientName;
	float quantity;
	String nutrientUom;
	//constructor
	public ProductNutrient(String ndbNumber, String nutrientCode, String nutrientName, float quantity, String nutrientUom) {
		this.ndbNumber=ndbNumber;
		this.nutrientCode=nutrientCode;
		this.nutrientName=nutrientName;
		this.quantity=quantity;
		this.nutrientUom=nutrientUom;
	}
}
