//Name: Xiaodi Tao AndrewID: xiaodit
package hw3;



import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NutriByte extends Application{
	static Model model = new Model();  	//made static to make accessible in the controller
	static View view = new View();		//made static to make accessible in the controller
	static Person person;				//made static to make accessible in the controller


	Controller controller = new Controller();	//all event handlers 

	/**Uncomment the following three lines if you want to try out the full-size data files */
//	static final String PRODUCT_FILE = "data/Products.csv";
//	static final String NUTRIENT_FILE = "data/Nutrients.csv";
//	static final String SERVING_SIZE_FILE = "data/ServingSize.csv";

	/**The following constants refer to the data files to be used for this application */
			static final String PRODUCT_FILE = "data/Nutri2Products.csv";
			static final String NUTRIENT_FILE = "data/Nutri2Nutrients.csv";
			static final String SERVING_SIZE_FILE = "data/Nutri2ServingSize.csv";

	static final String NUTRIBYTE_IMAGE_FILE = "NutriByteLogo.png"; //Refers to the file holding NutriByte logo image 

	static final String NUTRIBYTE_PROFILE_PATH = "profiles";  //folder that has profile data files

	static final int NUTRIBYTE_SCREEN_WIDTH = 1015;
	static final int NUTRIBYTE_SCREEN_HEIGHT = 675;

	@Override
	public void start(Stage stage) throws Exception {
		model.readProducts(PRODUCT_FILE);
		model.readNutrients(NUTRIENT_FILE);
		model.readServingSizes(SERVING_SIZE_FILE );
		view.setupMenus();
		view.setupNutriTrackerGrid();
		view.root.setCenter(view.setupWelcomeScene());
		Background b = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		view.root.setBackground(b);
		Scene scene = new Scene (view.root, NUTRIBYTE_SCREEN_WIDTH, NUTRIBYTE_SCREEN_HEIGHT);
		view.root.requestFocus();  //this keeps focus on entire window and allows the textfield-prompt to be visible
		setupBindings();
		stage.setTitle("NutriByte 3.0");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	// Setup bindings in JavaFX
	void setupBindings() {
		// Menu handlers
		view.newNutriProfileMenuItem.setOnAction(controller.new NewMenuItemHandler());
		view.openNutriProfileMenuItem.setOnAction(controller.new OpenMenuItemHandler());
		view.exitNutriProfileMenuItem.setOnAction(event -> Platform.exit());
		view.aboutMenuItem.setOnAction(controller.new AboutMenuItemHandler());
		view.createProfileButton.setOnAction(controller.new RecommendNutrientsButtonHandler());
		view.productsComboBox.setItems(model.searchResultList);
		// Set up columns
		view.productNutrientNameColumn.setCellValueFactory(productNutrientNameCallback);
		view.productNutrientQuantityColumn.setCellValueFactory(productNutrientQuantityCallback);
		view.productNutrientUomColumn.setCellValueFactory(productNutrientUomCallback);
		view.recommendedNutrientNameColumn.setCellValueFactory(recommendedNutrientNameCallback);
		view.recommendedNutrientQuantityColumn.setCellValueFactory(recommendedNutrientQuantityCallback);
		view.recommendedNutrientUomColumn.setCellValueFactory(recommendedNutrientUomCallback);
		// Buttons set on actions
		view.saveNutriProfileMenuItem.setOnAction(controller.new SaveMenuItemHandler());
		view.searchButton.setOnAction(controller.new SearchButtonHandler());
		view.clearButton.setOnAction(controller.new ClearButtonHandler());
		view.addDietButton.setOnAction(controller.new AddDietButtonHandler());
		view.removeDietButton.setOnAction(controller.new RemoveDietButtonHandler());
		view.closeNutriProfileMenuItem.setOnAction(controller.new CloseMenuItemHandler());
		// Set the text fields to be red
		view.ageTextField.setStyle("-fx-text-fill: red");
		view.heightTextField.setStyle("-fx-text-fill: red");
		view.weightTextField.setStyle("-fx-text-fill: red");

		// Diet serving size text field listener
		view.dietServingSizeTextField.textProperty().addListener((observable,oldPerson,newPerson)->{
			if(newPerson!=null)
			{		  
				try {
					view.ageTextField.setStyle("-fx-text-inner-color: black;");
				}catch (NumberFormatException e) {
				}
			}
		});


		// Selecting any product in the combo-box should display the product’s nutrients 
		// in the productNutrientsTableView. Its ingredients are displayed in the productIngredientsTextArea,
		// its serving size with serving Uom and household size with householdUom
		view.productsComboBox.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			if(!(view.productsComboBox.getSelectionModel().getSelectedIndex()<0)){
				ObservableList<Product.ProductNutrient> ProductNutrientList1 = FXCollections.observableArrayList();
				for(Product.ProductNutrient pp: view.productsComboBox.getValue().getProductNutrients().values()) {
					ProductNutrientList1.add(pp);
				}
				NutriByte.view.productNutrientsTableView.setItems(ProductNutrientList1);
				String showIngredient = "Product Ingredients: "+view.productsComboBox.getValue().getIngredients();
				view.productIngredientsTextArea.textProperty().set(showIngredient);
				view.householdSizeLabel.setText(String.format("%.2f",view.productsComboBox.getValue().getHouseholdSize())+" "+view.productsComboBox.getValue().getHouseholdUom());
				view.servingSizeLabel.setText(String.format("%.2f",view.productsComboBox.getValue().getServingSize())+" "+view.productsComboBox.getValue().getServingUom());
				view.dietServingUomLabel.setText(view.productsComboBox.getValue().getServingUom());
				view.dietHouseholdUomLabel.setText(view.productsComboBox.getValue().getHouseholdUom());				
			}
		});
		// Diet household size text field listener
		view.dietHouseholdSizeTextField.textProperty().addListener((observable,oldPerson,newPerson)->{
			if(newPerson!=null)
			{
				try {
					view.ageTextField.setStyle("-fx-text-inner-color: black;");
				}catch (NumberFormatException e) {
				}
			}
		});
		// Gender combo box listener
		view.genderComboBox.valueProperty().addListener((observable, oldvalue, newvalue) ->{
			if(view.genderComboBox.getSelectionModel().getSelectedIndex()>=0&&personValidFlag()==0){
				setbutton();
			}
		});
		// Age combo box listener
		view.ageTextField.textProperty().addListener((observable, oldvalue, newvalue) -> {
			try {
				if (isFloat(newvalue)) {
					if (Float.parseFloat(newvalue)>=0) {
						view.ageTextField.setStyle("-fx-text-fill: black");
					}
				} else {
					view.ageTextField.setStyle("-fx-text-fill: red");
				}
			}catch (NumberFormatException e) {

			}
			if(personValidFlag()==0)
				setbutton();
		});
		// Height combo box listener
		view.heightTextField.textProperty().addListener((observable, oldvalue, newvalue) ->{
			try {
				if (isFloat(newvalue)) {
					if (Float.parseFloat(newvalue)>=0) {
						view.heightTextField.setStyle("-fx-text-fill: black");
					}
				} else {
					view.heightTextField.setStyle("-fx-text-fill: red");
				}
			}catch (NumberFormatException e) {

			}
			if(personValidFlag()==0)
				setbutton();
		});
		// Weight combo box listener
		view.weightTextField.textProperty().addListener((observable, oldvalue, newvalue) ->{
			try {
				if (isFloat(newvalue)) {
					if (Float.parseFloat(newvalue)>=0) {
						view.weightTextField.setStyle("-fx-text-fill: black");
					}
				} else {
					view.weightTextField.setStyle("-fx-text-fill: red");
				}
			}catch (NumberFormatException e) {

			}
			if(personValidFlag()==0)
				setbutton();
		});
		// Physical activity combo box listener
		view.physicalActivityComboBox.valueProperty().addListener((observable, oldvalue, newvalue) ->{
			if(!(view.physicalActivityComboBox.getSelectionModel().getSelectedIndex()<0)&&personValidFlag()==0){
				setbutton();
			}
		});
	}
	public int personValidFlag(){
		int count = 0;
		try {
			if(NutriByte.view.genderComboBox.getSelectionModel().getSelectedIndex()!=0&&NutriByte.view.genderComboBox.getSelectionModel().getSelectedIndex()!=1)
				count++;
			if(NutriByte.view.ageTextField.textProperty().get()==null||NutriByte.view.ageTextField.textProperty().get().length()==0)
				count++;
			if(Float.parseFloat(NutriByte.view.ageTextField.textProperty().get().trim())<=0)
				count++;
			if( NutriByte.view.heightTextField.textProperty().get()==null|| NutriByte.view.heightTextField.textProperty().get().length()==0)
				count++;
			float a = Float.parseFloat(NutriByte.view.ageTextField.textProperty().get());
			float b = Float.parseFloat(NutriByte.view.weightTextField.textProperty().get());
			float c = Float.parseFloat(NutriByte.view.heightTextField.textProperty().get());
			float[] test = new float[3];
			test[0] = a;
			test[1] = b;
			test[2] = c;
			for (float f : test) {
				if (f<=0) {
					count=count+2;
				}
			}
			if(NutriByte.view.physicalActivityComboBox.getSelectionModel().getSelectedIndex()<0)
				count++;
		}catch (NumberFormatException e2){
			count=count+2;
		}
		catch (Exception e) {
			count=count+2;
			e.printStackTrace();
		}
		return count;
	}
	// Gets the nutrient object from nutrientsMap and get the nutrientNameProperty 
	// according to each nutrient object
	// Converts the value to a string to print a float value with only two decimal points
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientQuantityCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			return new SimpleStringProperty(String.format("%.2f",arg0.getValue().getNutrientQuantity()));

		}
	};
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientNameCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			return Model.nutrientsMap.get(arg0.getValue().getNutrientCode()).nutrientNameProperty();
		}
	};
	// Finds the nutrient’s unit of measure from nutrientMap and returns it
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientUomCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());
			ObservableValue<String> res = nutrient.nutrientUomProperty();
			return res;
		}
	};
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientUomCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
			return Model.nutrientsMap.get(arg0.getValue().getNutrientCode()).nutrientUomProperty();
		}
	};
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientNameCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());
			return nutrient.nutrientNameProperty();
		}
	};
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientQuantityCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
			return new SimpleStringProperty(String.format("%.2f",arg0.getValue().getNutrientQuantity()));
		}
	};
	// Set a new button to get the action of recommend nutrient button handler
	// click the button to call exception methods
	public void setbutton() {
		javafx.scene.control.Button fireButton =new javafx.scene.control.Button();
		fireButton.setOnAction(controller.new RecommendNutrientsButtonHandler());
		fireButton.fire();
	}
	// Set a method to decide whether the input is float
	public boolean isFloat(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}
}
