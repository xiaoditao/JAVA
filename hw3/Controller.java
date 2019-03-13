//Name: Xiaodi Tao AndrewID: xiaodit
package hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import hw3.NutriProfiler.PhysicalActivityEnum;
import hw3.Product.ProductNutrient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class Controller {
	static ObservableList<Product> dietProductsListTemp = FXCollections.observableArrayList();
	// Selecting any product in the combo-box should display the product’s nutrients 
	// in the productNutrientsTableView. Its ingredients are displayed in the productIngredientsTextArea,
	// its serving size with serving Uom and household size with householdUom
	ChangeListener<Product> ProductsCombobox=new ChangeListener<Product>() {
		public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product NewValue) {
			NutriByte.view.dietServingSizeTextField.setStyle("-fx-text-inner-color: black;");
			if(!(NutriByte.view.productsComboBox.getSelectionModel().getSelectedIndex()<0)){
				ObservableList<Product.ProductNutrient> ProductNutrientList1 = FXCollections.observableArrayList();
				for(Product.ProductNutrient pp: NutriByte.view.productsComboBox.getValue().getProductNutrients().values()) {
					ProductNutrientList1.add(pp);
				}
				NutriByte.view.productNutrientsTableView.setItems(ProductNutrientList1);
				String showIngredient = "Product Ingredients:"+NutriByte.view.productsComboBox.getValue().getIngredients();
				NutriByte.view.productIngredientsTextArea.textProperty().set(showIngredient);
				NutriByte.view.householdSizeLabel.setText(NutriByte.view.productsComboBox.getValue().getHouseholdSize()+" "+NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem().getHouseholdUom());
				NutriByte.view.servingSizeLabel.setText(NutriByte.view.productsComboBox.getValue().getServingSize()+" "+NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem().getServingUom());
				NutriByte.view.servingUom.setText(NutriByte.view.productsComboBox.getValue().getServingSize()+" "+NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem().getServingUom());
				NutriByte.view.householdServingUom.setText(NutriByte.view.productsComboBox.getValue().getHouseholdSize()+" "+NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem().getHouseholdUom());
			}
		}
	};

	class RecommendNutrientsButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// Sets variables
			NutriByte.view.dietServingSizeTextField.setStyle("-fx-text-inner-color: black;");
			float physicalActivityLevel = 0;
			String inputAge = NutriByte.view.ageTextField.getText();
			String inputWeight = NutriByte.view.weightTextField.getText();
			String inputHeight = NutriByte.view.heightTextField.getText();
			String gender = NutriByte.view.genderComboBox.getSelectionModel().getSelectedItem();
			int inputGender = NutriByte.view.genderComboBox.getSelectionModel().getSelectedIndex();
			String inputPhysicalActivityLevel = NutriByte.view.physicalActivityComboBox.getSelectionModel().getSelectedItem();
			String ingredientsToWatch = NutriByte.view.ingredientsToWatchTextArea.getText();
			float ageReal;
			float weightReal;
			float heightReal;
			// Cope with exceptions in gender
			try {
				if (inputGender<0) {
					throw new InvalidProfileException("Missing gender information");
				}
			} catch (InvalidProfileException e) {			
				return;
			}
			// Cope with exceptions in age
			try {
				if (inputAge.trim().length()==0||inputAge==null) {
					throw new InvalidProfileException("Missing age information");
				}
			} catch (InvalidProfileException e) {	
				String[] strings=new String[2];

				return;
			}	
			try {
				ageReal= Float.parseFloat(inputAge.trim());
			} catch (NumberFormatException e) {
				try {
					throw new InvalidProfileException("Incorrect age input. Must be a number");
				} catch (InvalidProfileException e2) {
					return;
				}
			}
			try {
				if (ageReal < 0) {
					throw new InvalidProfileException("Age must be a positive number");
				}
			} catch (Exception e) {
				return;
			}
			// Cope with exceptions in weight
			try {
				if (inputWeight.trim().length()==0||inputWeight==null) {
					throw new InvalidProfileException("Missing weight information");
				}
			} catch (InvalidProfileException e) {			
				return;
			}	
			try {
				weightReal= Float.parseFloat(inputWeight.trim());

			} catch (NumberFormatException e) {
				try {
					throw new InvalidProfileException("Incorrect weight input. Must be a number");
				} catch (InvalidProfileException e2) {
					return;
				}
			}		
			try {
				if (weightReal < 0) {
					throw new InvalidProfileException("Weight must be a positive number");
				}
			} catch (Exception e) {
				return;
			}
			// Cope with exceptions in height
			try {
				if (inputHeight.trim().length()==0||inputHeight==null) {
					throw new InvalidProfileException("Missing height information");
				}
			} catch (InvalidProfileException e) {			
				return;
			}		
			try {
				heightReal= Float.parseFloat(inputHeight.trim());

			} catch (NumberFormatException e) {
				try {
					throw new InvalidProfileException("Incorrect height input. Must be a number");
				} catch (InvalidProfileException e2) {
					return;
				}
			}			
			try {
				if (heightReal < 0) {
					throw new InvalidProfileException("Height must be a positive number");
				}
			} catch (Exception e) {
				return;
			}
			// Cope with exceptions in physical activity
			if(inputPhysicalActivityLevel==null||inputPhysicalActivityLevel.trim().length()==0 || inputPhysicalActivityLevel =="") {
				physicalActivityLevel =1f;
			}else {
				for (PhysicalActivityEnum item : PhysicalActivityEnum.values()) {
					if(inputPhysicalActivityLevel.toLowerCase().equals(item.getName().toLowerCase())) {
						physicalActivityLevel = item.getPhysicalActivityLevel();
					}
				}	
			}
			if(gender.toLowerCase().equals("female")) {
				NutriByte.person = new Female(ageReal, weightReal, heightReal, physicalActivityLevel, ingredientsToWatch);
			}
			if (gender.toLowerCase().equals("male")) {
				NutriByte.person = new Male(ageReal, weightReal, heightReal, physicalActivityLevel, ingredientsToWatch);
			}			
			NutriProfiler.createNutriProfile(NutriByte.person);
			NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
		}
	}

	class OpenMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//Opens the FileChooser for the user to choose a profile data file that can be .csv or .xml. 
			//Passes the selected file’s file name to the Model’s readProfile() method. 
			//Displays the profile data in the GUI. 
			//Invokes NutriProfiler’s createNutriProfile() method to populate the recommendedNutrientsList.
			//NutriByte.view.productsComboBox.setItems(NutriByte.model.searchResultList);
			NutriByte.view.dietServingSizeTextField.setStyle("-fx-text-inner-color: black;");
			Stage stage =new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select file");
			fileChooser.setInitialDirectory(new File("profiles/")); //local path 
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("CSV Files", "*.csv"),
					new ExtensionFilter("XML Files", "*.xml"));		
			File file = fileChooser.showOpenDialog(stage);
			NutriByte.view.newNutriProfileMenuItem.fire();
			// If could not open, use newNutriProfileMenuItem button to call the 
			// exception dealing method
			boolean open =true;
			open = NutriByte.model.readProfile("profiles/"+file.getName());
			open=!open;
			if (open=false) {
				NutriByte.view.newNutriProfileMenuItem.fire();
			}
			if (open=true) {
				try {
					BufferedReader filereader = new BufferedReader(new FileReader("profiles/"+file.getName()));
					String item=null;
					try {
						item = filereader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// If person is null, use newNutriProfileMenuItem button to call the 
					// exception dealing method, and reads the records from files to 
					// populate person 
					if (NutriByte.person==null) {
						NutriByte.view.newNutriProfileMenuItem.fire();
						filereader.close();
						return;
					}				
					CSVFiler csvFiler=new CSVFiler();
					if(NutriByte.person instanceof Female) {
						NutriByte.view.genderComboBox.getSelectionModel().select(0);
					}
					else {
						NutriByte.view.genderComboBox.getSelectionModel().select(1);
					}
					NutriByte.view.ageTextField.setText(String.format("%.2f",NutriByte.person.age));
					NutriByte.view.weightTextField.setText(String.format("%.2f",NutriByte.person.weight));
					int Index = 0;
					for(PhysicalActivityEnum each : PhysicalActivityEnum.values()){
						if(NutriByte.person.physicalActivityLevel<=each.getPhysicalActivityLevel()){
							break;
						}
						Index++;
					}
					if(Index>=4) {
						Index=3;
					}
					NutriByte.view.physicalActivityComboBox.getSelectionModel().select(Index);
					NutriByte.view.ingredientsToWatchTextArea.setText(NutriByte.person.ingredientsToWatch);
					NutriByte.view.heightTextField.setText(String.format("%.2f",NutriByte.person.height));
					NutriByte.view.ingredientsToWatchTextArea.setText(NutriByte.person.ingredientsToWatch);
					NutriProfiler.createNutriProfile(NutriByte.person);
					// Read records from files to populate products
					// Use a new product list called tempList to contain products
					ObservableList<Product> tempList = FXCollections.observableArrayList();
					item = filereader.readLine();
					if (item==null) {
						filereader.close();
						return;
					}
					while(item!=null){
						String[] row=item.split(",");
						Product p=csvFiler.validateProductData(item);
						if (p==null||p.equals(null)) {
							item=filereader.readLine();
							continue;
						}
						NutriByte.person.dietProductsList.add(p);
						tempList.add(Model.productsMap.get(row[0].trim()));
						item = filereader.readLine();	
					}					
					NutriByte.person.populateDietNutrientMap();
					NutriByte.view.nutriChart.updateChart();
					NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
					NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
					NutriByte.view.productsComboBox.setItems(NutriByte.model.searchResultList);
					NutriByte.view.productsComboBox.setItems(tempList);
					NutriByte.view.productsComboBox.getSelectionModel().select(0);
					NutriByte.view.searchResultSizeLabel.setText(tempList.size()+" product(s) found");		
					filereader.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		}
	}
	// Clear up all data from the previous user interaction, including nutriChart
	class NewMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.dietServingSizeTextField.setStyle("-fx-text-inner-color: black;");
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			NutriByte.view.initializePrompts();
			NutriByte.view.nutriChart.clearChart();
			NutriByte.person=null;
			NutriByte.view.dietProductsTableView.setItems(null);
			NutriByte.view.productNutrientsTableView.setItems(null);
			NutriByte.view.recommendedNutrientsTableView.setItems(null);
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.dietHouseholdSizeTextField.clear();
			NutriByte.view.dietServingSizeTextField.clear();
		}
	}
	// Set contents in about menuItem
	class AboutMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("NutriByte");
			alert.setContentText("Version 2.0 \nRelease 1.0\nCopyleft Java Nerds\nThis software is designed purely for educational purposes.\nNo commercial use intended");
			Image image = new Image(getClass().getClassLoader().getResource(NutriByte.NUTRIBYTE_IMAGE_FILE).toString());
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(300);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			alert.setGraphic(imageView);
			alert.showAndWait();
		}
	}
	// Cope with all search functionality
	class SearchButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.dietServingSizeTextField.setStyle("-fx-text-inner-color: black;");
			NutriByte.model.searchResultList.clear();
			String productInput = NutriByte.view.productSearchTextField.getText();
			String nutrientInput = NutriByte.view.nutrientSearchTextField.getText();
			String ingredientInput = NutriByte.view.ingredientSearchTextField.getText();
			Collection<Product> productList = new ArrayList<Product>();
			productList=Model.productsMap.values();
			Collection<Nutrient> nutrientList = new ArrayList<>();
			nutrientList = Model.nutrientsMap.values();
			int flag=0;
			String nutricode1=null;
			// Figure out all the conditions in the two textInputField
			if (!productInput.isEmpty()&&nutrientInput.isEmpty()&&ingredientInput.isEmpty()) {
				flag=1;
			}
			if (!productInput.isEmpty()&&!nutrientInput.isEmpty()&&ingredientInput.isEmpty()) {
				flag=2;
			}
			if (!productInput.isEmpty()&&!nutrientInput.isEmpty()&&!ingredientInput.isEmpty()) {
				flag=3;
			}
			if (productInput.isEmpty()&&!nutrientInput.isEmpty()&&ingredientInput.isEmpty()) {
				flag=4;
			}
			if (productInput.isEmpty()&&!nutrientInput.isEmpty()&&!ingredientInput.isEmpty()) {
				flag=5;
			}
			if (productInput.isEmpty()&&nutrientInput.isEmpty()&&ingredientInput.isEmpty()) {
				flag=6;
			}
			if (productInput.isEmpty()&&nutrientInput.isEmpty()&&!ingredientInput.isEmpty()) {
				flag=7;
			}
			if (!productInput.isEmpty()&&nutrientInput.isEmpty()&&!ingredientInput.isEmpty()) {
				flag=8;
			}
			// For different conditions, search the result
			// Use ndbNumber to find the products and its product nutrients
			// to populate products
			switch (flag) {
			// case 1: Use ndbNumber to find the products and its product nutrients
			// to populate products
			case 1:
				for (Product p : productList) {
					if (p.getProductName().toLowerCase().contains(productInput.toLowerCase())) {
						NutriByte.model.searchResultList.add(p);
					}
				}
				break;
				// case 2: Use ndbNumber to find the products and its product nutrients
				// to populate products	
			case 2:
				for (Nutrient n : nutrientList) {
					if (n.getNutrientName().toLowerCase().contains(nutrientInput.toLowerCase())) {
						nutricode1=n.getNutrientCode();
					}
				}
				for (Product p : productList) {
					ObservableMap<String, ProductNutrient> maptest= FXCollections.observableHashMap();
					maptest=p.getProductNutrients();
					Collection<ProductNutrient> pndata=new ArrayList<>();
					pndata = maptest.values();
					if (p.getProductName().toLowerCase().contains(productInput.toLowerCase())) {
						for (ProductNutrient pn : pndata) {
							if (pn.getNutrientCode().equals(nutricode1)) {
								NutriByte.model.searchResultList.add(p);
							}
						}
					}
				}
				break;
				// case 3: Use ndbNumber to find the products and its product nutrients
				// to populate products	
			case 3:
				for (Nutrient n : nutrientList) {
					if (n.getNutrientName().toLowerCase().contains(nutrientInput.toLowerCase())) {
						nutricode1=n.getNutrientCode();
					}
				}
				for (Product p : productList) {
					ObservableMap<String, ProductNutrient> maptest= FXCollections.observableHashMap();
					maptest=p.getProductNutrients();
					Collection<ProductNutrient> pndata=new ArrayList<>();
					pndata = maptest.values();
					if (p.getProductName().toLowerCase().contains(productInput.toLowerCase())&&p.getIngredients().toLowerCase().contains(ingredientInput.toLowerCase())) {
						for (ProductNutrient pn : pndata) {
							if (pn.getNutrientCode().equals(nutricode1)) {
								NutriByte.model.searchResultList.add(p);
							}
						}
					}
				}
				break;
				// case 4: Use ndbNumber to find the products and its product nutrients
				// to populate products	
			case 4:
				for (Nutrient n : nutrientList) {
					if (n.getNutrientName().toLowerCase().contains(nutrientInput.toLowerCase())) {
						nutricode1=n.getNutrientCode();
					}
				}
				for (Product p : productList) {
					ObservableMap<String, ProductNutrient> maptest= FXCollections.observableHashMap();
					maptest=p.getProductNutrients();
					Collection<ProductNutrient> pndata=new ArrayList<>();
					pndata = maptest.values();
					for (ProductNutrient pn : pndata) {
						if (pn.getNutrientCode().equals(nutricode1)) {
							NutriByte.model.searchResultList.add(p);
						}
					}
				}
				break;
				// case 5: Use ndbNumber to find the products and its product nutrients
				// to populate products
			case 5:
				for (Nutrient n : nutrientList) {
					if (n.getNutrientName().toLowerCase().contains(nutrientInput.toLowerCase())) {
						nutricode1=n.getNutrientCode();
					}
				}
				for (Product p : productList) {
					ObservableMap<String, ProductNutrient> maptest= FXCollections.observableHashMap();
					maptest=p.getProductNutrients();
					Collection<ProductNutrient> pndata=new ArrayList<>();
					pndata = maptest.values();
					if (p.getIngredients().toLowerCase().contains(ingredientInput.toLowerCase())) {
						for (ProductNutrient pn : pndata) {
							if (pn.getNutrientCode().equals(nutricode1)) {
								NutriByte.model.searchResultList.add(p);
							}
						}
					}

				}
				break;
				// case 6: Use ndbNumber to find the products and its product nutrients
				// to populate products
			case 6:
				for (Product p : productList) {
					NutriByte.model.searchResultList.add(p);
				}
				break;
				// case 7: Use ndbNumber to find the products and its product nutrients
				// to populate products
			case 7:
				for (Product p : productList) {
					if (p.getIngredients().toLowerCase().contains(ingredientInput.toLowerCase())) {
						NutriByte.model.searchResultList.add(p);
					}
				}
				break;
				// case 8: Use ndbNumber to find the products and its product nutrients
				// to populate products
			case 8:
				for (Product p : productList) {
					if (p.getProductName().toLowerCase().contains(productInput.toLowerCase())&&p.getIngredients().toLowerCase().contains(ingredientInput.toLowerCase())) {
						NutriByte.model.searchResultList.add(p);
					}
				}
				break;
			default:
				break;
			}
			NutriByte.view.productsComboBox.setItems(NutriByte.model.searchResultList);
			NutriByte.view.searchResultSizeLabel.setText(NutriByte.model.searchResultList.size()+" product(s) found");
			NutriByte.view.productsComboBox.getSelectionModel().select(0);
		}
	}


	class ClearButtonHandler implements EventHandler<ActionEvent> {
		// Clears product, nutrient, and ingredient search boxes 
		// and all products from the productsComboBox
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.dietServingSizeTextField.setStyle("-fx-text-inner-color: black;");
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.servingSizeLabel.setText("0.00");
			NutriByte.view.householdSizeLabel.setText("0.00");
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.productIngredientsTextArea.textProperty().set(null);
			NutriByte.model.searchResultList.clear();
			NutriByte.view.productsComboBox.setItems(null);
			NutriByte.view.productNutrientsTableView.setItems(null);
			NutriByte.view.searchResultSizeLabel.setText(null);
			NutriByte.model.searchResultList.clear();
			NutriByte.view.dietHouseholdUomLabel.setText(null);
			NutriByte.view.dietServingUomLabel.setText(null);
		}
	}

	class AddDietButtonHandler implements EventHandler<ActionEvent>{
		// The product selected in the productsComboBox is added to the dietProductsTableView
		@Override
		public void handle(ActionEvent event) {
			Product p = NutriByte.view.productsComboBox.getValue();
			String servingSizeInput = NutriByte.view.dietServingSizeTextField.textProperty().get();
			String houseHoldSizeInput = NutriByte.view.dietHouseholdSizeTextField.textProperty().get();
			// User doesn’t enter anything in the two textfields-dietServingSizeTextField and 
			// dietHouseholdSizeTextField. In this case, use product standard serving size as
			// the quantity in the diet
			if(servingSizeInput.isEmpty()&&houseHoldSizeInput.isEmpty()){
				if (p.getHouseholdSize()==0) {
					p.setHouseholdSize(1f);
				}
				NutriByte.person.dietProductsList.add(p);
				dietProductsListTemp.add(p);
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			}
			// User enters only in dietServingSizeTextField: Use the quantity entered by user as 
			// the diet-quantity, and calculate the household size based on the ratio of serving 
			// size vs. house hold size in the master product in Model.productsMap
			if (!servingSizeInput.isEmpty()&&houseHoldSizeInput.isEmpty()) {
				Product pAdd = p;
				pAdd.setHouseholdUom(p.getHouseholdUom());
				pAdd.setProductNutrients(p.getProductNutrients());
				pAdd.setServingSize(Float.parseFloat(servingSizeInput));
				pAdd.setServingUom(p.getServingUom());
				pAdd.setHouseholdSize(Float.parseFloat(servingSizeInput) / p.getServingSize()
						* p.getHouseholdSize());
				NutriByte.person.dietProductsList.add(pAdd);
				dietProductsListTemp.add(pAdd);
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			}
			// User enters only in dietHouseholdSizeTextField: Use the quantity entered by user as the diet-quantity, 
			// and calculate the serving size based on the ratio of serving size vs. house hold size 
			// in the master product in Model.productsMap
			if(servingSizeInput.isEmpty()&&!houseHoldSizeInput.isEmpty()){
				Product pAdd = p;
				pAdd.setHouseholdUom(p.getHouseholdUom());
				pAdd.setProductNutrients(p.getProductNutrients());
				if (p.getHouseholdSize()==0f) {
					p.setHouseholdSize(1f);
				}
				pAdd.setServingSize(Float.parseFloat(houseHoldSizeInput) / p.getHouseholdSize()
						* p.getServingSize());
				pAdd.setServingUom(p.getServingUom());
				pAdd.setHouseholdSize(Float.parseFloat(houseHoldSizeInput));
				NutriByte.person.dietProductsList.add(pAdd);
				dietProductsListTemp.add(pAdd);
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			}
			// User enters both: Give preference to the data in dietServingSizeTextField over 
			// dietHouseholdSizeTextField and compute household size based on the ratio of 
			// serving vs. household size in Model.productsMap.
			if(!servingSizeInput.isEmpty()&&!houseHoldSizeInput.isEmpty()){
				Product pAdd = p;
				pAdd.setHouseholdUom(p.getHouseholdUom());
				pAdd.setProductNutrients(p.getProductNutrients());
				pAdd.setServingSize(Float.parseFloat(houseHoldSizeInput) / p.getHouseholdSize()
						* p.getServingSize());
				if (p.getHouseholdSize()==0f) {
					p.setHouseholdSize(1f);
				}
				pAdd.setServingUom(p.getServingUom());
				pAdd.setHouseholdSize(Float.parseFloat(houseHoldSizeInput));
				NutriByte.person.dietProductsList.add(pAdd);
				dietProductsListTemp.add(pAdd);
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			}
			NutriByte.person.populateDietNutrientMap();
			NutriByte.view.nutriChart.updateChart();
		}
	}
	

	// Close the menue
	class CloseMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			NutriByte.view.root.setCenter(NutriByte.view.setupWelcomeScene());
		}
	}
	// Save the menue
	class SaveMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// Use a new button, set action with recommend nutrient button handler
			// When trying to save the file, clike the button to call 
			// exception methods
			Controller c3=new Controller();
			javafx.scene.control.Button fireButton =new javafx.scene.control.Button();
			fireButton.setOnAction(c3.new RecommendNutrientsButtonHandler());
			fireButton.fire();
			String ageTextInput = NutriByte.view.ageTextField.getText();
			String weightTextInput = NutriByte.view.weightTextField.getText();
			String heightTextInput = NutriByte.view.heightTextField.getText();
			int save = 0;
			if(ageTextInput.isEmpty()||weightTextInput.isEmpty()||heightTextInput.isEmpty()||NutriByte.view.genderComboBox.getSelectionModel().getSelectedIndex()<0){
				save=save+2;
			}
			try{
				float age = Float.parseFloat(ageTextInput);
				float weight = Float.parseFloat(weightTextInput);
				float height = Float.parseFloat(heightTextInput);
				if(!(age>0) || !(height>0) || !(weight>0)){
					save=save+2;
				}
			}catch(Exception e){
				save=save+2;
			}
			if (save==0){
				FileChooser filechooser = new FileChooser();
				filechooser.setInitialDirectory(new File("profiles/"));
				filechooser.getExtensionFilters().addAll(
						new ExtensionFilter("CSV Files", "*.csv"),
						new ExtensionFilter("XML Files", "*.xml"),
						new ExtensionFilter("All Files", "*.*"));
				Stage stage = new Stage();
				File file = filechooser.showSaveDialog(stage);
				String filename = file.getName();
				NutriByte.model.writeProfile(filename);
			}
		}
	}
	// Reverse the process done in AddDietButtonHandler
	class RemoveDietButtonHandler implements EventHandler<ActionEvent> {		
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.dietServingSizeTextField.setStyle("-fx-text-inner-color: black;");
			Product p = NutriByte.view.dietProductsTableView.getSelectionModel().getSelectedItem();
			ObservableList<Product> dietProductsListTemp1 = FXCollections.observableArrayList();
			dietProductsListTemp1=NutriByte.person.dietProductsList;
			for(Product product: dietProductsListTemp1){
				if(product.getNdbNumber().equalsIgnoreCase(p.getNdbNumber())){
					NutriByte.person.dietProductsList.remove(product);
					Controller.dietProductsListTemp.remove(product);
					break;
				}
			}
			NutriByte.person.populateDietNutrientMap();
			NutriByte.view.nutriChart.updateChart();
		}
	}
}


