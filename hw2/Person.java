//Name: Xiaodi Tao AndrewID: xiaodit
package hw2;

import hw2.NutriProfiler.AgeGroupEnum;

public abstract class Person {

	float age, weight, height, physicalActivityLevel; //age in years, weight in kg, height in cm
	String ingredientsToWatch;
	float[][] nutriConstantsTable = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT][NutriProfiler.AGE_GROUP_COUNT];

	NutriProfiler.AgeGroupEnum ageGroup;


	abstract void initializeNutriConstantsTable();
	abstract float calculateEnergyRequirement();

	// Non-default constructor initializes the variables in person object
	// Also, get the AgeGroupEnum of the object
	Person(float age, float weight, float height, float physicalActivityLevel, String ingredientsToWatch) {
		this.age=age;
		this.weight=weight;
		this.height=height;
		this.physicalActivityLevel=physicalActivityLevel;
		this.ingredientsToWatch=ingredientsToWatch;
		for(AgeGroupEnum item : AgeGroupEnum.values()){
			if(age <= item.getAge()){
				this.ageGroup = item;
				break;
			}
		}
	}

	//returns an array of nutrient values of size NutriProfiler.RECOMMENDED_NUTRI_COUNT. 
	//Each value is calculated as follows:
	//For Protein, it multiples the constant with the person's weight.
	//For Carb and Fiber, it simply takes the constant from the 
	//nutriConstantsTable based on NutriEnums' nutriIndex and the person's ageGroup
	//For others, it multiples the constant with the person's weight and divides by 1000.
	//Try not to use any literals or hard-coded values for age group, nutrient name, array-index, etc. 

	float[] calculateNutriRequirement() {
		// Decides whether the person is male or female 
		// then initializes the NutriConstantsTable
		float[] res =new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT];
		int ageIndex = 0;
		if (this instanceof Male) {
			((Male)this).initializeNutriConstantsTable();
		}
		if (this instanceof Female) {
			((Female)this).initializeNutriConstantsTable();
		}
		for(AgeGroupEnum itEnum: AgeGroupEnum.values()) {
			if (this.age <= itEnum.getAge()) {
				ageIndex=itEnum.getAgeGroupIndex();
				break;
			}
		}
		// For different nutrient, returns different nutrient values
		int nutrientIndex=0;
		for(NutriProfiler.NutriEnum itEnum: NutriProfiler.NutriEnum.values()) {
			if (itEnum.getNutrientCode().trim().equals("205")||itEnum.getNutrientCode().trim().equals("291")) {
				res[nutrientIndex]=nutriConstantsTable[nutrientIndex][ageIndex];
				nutrientIndex++;			
			}else if (itEnum.getNutrientCode().trim().equals("203")) {
				res[nutrientIndex]=nutriConstantsTable[nutrientIndex][ageIndex]*weight;
				nutrientIndex++;
			}else {
				res[nutrientIndex]=nutriConstantsTable[nutrientIndex][ageIndex]*weight/1000;
				nutrientIndex++;
			}
		}
		return res;
	}
}
