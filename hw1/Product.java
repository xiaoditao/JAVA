//Name:Xiaodi Tao
//Andrew id:xiaodit
package hw1;

public class Product {
	//member variables
	String ndbNumber;
	String productName;
	String manufacturer;
	String ingredients;
	float servingSize;
	String servingUom;
	float householdSize;
	String householdUom;
	//constructor
	public Product(String ndbNumber,String productName, String manufacturer,String ingredients) {
		this.ndbNumber=ndbNumber;
		this.productName=productName;
		this.manufacturer=manufacturer;
		this.ingredients=ingredients;
	}
}
