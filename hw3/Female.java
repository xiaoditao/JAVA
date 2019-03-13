//Name: Xiaodi Tao AndrewID: xiaodit
package hw3;

import hw3.NutriProfiler.AgeGroupEnum;

public class Female extends Person {

	float[][] nutriConstantsTableFemale = new float[][]{
		//AgeGroups: 3M, 6M, 1Y, 3Y, 8Y, 13Y, 18Y, 30Y, 50Y, ABOVE 
		{1.52f, 1.52f, 1.2f, 1.05f, 0.95f, 0.95f, 0.71f, 0.8f, 0.8f, 0.8f}, //0: Protein constants
		{60, 60, 95, 130, 130, 130, 130, 130, 130, 130}, //1: Carbohydrate
		{19, 19, 19, 19, 25, 26, 26, 25, 25, 21},  //2: Fiber constants
		{36, 36, 32, 21, 16, 15, 14, 14, 14, 14}, 	//3: Histidine
		{88, 88, 43, 28, 22, 21, 19, 19, 19, 19}, 	//4: isoleucine
		{156, 156, 93, 63, 49, 47, 44 , 42, 42, 42},//5: leucine
		{107, 107, 89, 58, 46, 43, 40, 38, 38, 38}, //6: lysine
		{59, 59, 43, 28, 22, 21, 19, 19, 19, 19}, 	//7: methionine
		{59, 59, 43, 28, 22, 21, 19, 19, 19, 19}, 	//8: cysteine
		{135, 135, 84, 54, 41, 38, 35, 33, 33, 33}, //9: phenylalanine
		{135, 135, 84, 54, 41, 38, 35, 33, 33, 33}, //10: phenylalanine
		{73, 73, 49, 32, 24, 22, 21, 20, 20, 20}, 	//11: threonine
		{28, 28, 13, 8, 6, 6, 5, 5, 5, 5}, 			//12: tryptophan
		{87, 87, 58, 37, 28, 27, 24, 24, 24, 24	}  	//13: valine
	};

	Female(float age, float weight, float height, float physicalActivityLevel, String ingredientsToWatch) {
		// Non default constructor that invokes super's constructor
		super(age, weight, height, physicalActivityLevel, ingredientsToWatch);
		initializeNutriConstantsTable();
	}

	@Override
	float calculateEnergyRequirement() {
		// Returns Energy requirement calculated as per Table 2
		// Uses AgeGroupEnum to identify which age group does the object set in
		float res = 0;
		int[] X = {-75,44,78,80};
		int ageIndex=0;
		for (AgeGroupEnum item : AgeGroupEnum.values()) {
			if (this.age <= item.getAge()) {
				ageIndex=item.getAgeGroupIndex();
				break;
			}
		}
		// With the ageIndex it has, computes the energy required
		if (ageIndex < 4) {
			res = (float)(89 * this.weight - X[ageIndex]);
		}else if (ageIndex >= 4 && ageIndex <= 6){
			res=(float)(135.3-(30.8*this.age)+this.physicalActivityLevel*(10*this.weight
					+934*this.height/100)+20);		
		}else {
			res = (float)(354-(6.91*this.age)+this.physicalActivityLevel*(9.36*this.weight
					+726*this.height/100));
		}
		return res;
	}

	@Override
	void initializeNutriConstantsTable() {
		// Copies values from nutriConstantsTableMale /nutriConstantsTableFemale to the 
		// Person’s nutriConstantsTable
		for(int i=0;i<nutriConstantsTable.length;i++) {
			for(int j=0;j<nutriConstantsTable[i].length;j++) {
				super.nutriConstantsTable[i][j]=this.nutriConstantsTableFemale[i][j];
			}
		}
	}
}
