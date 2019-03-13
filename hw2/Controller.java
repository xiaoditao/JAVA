//Name: Xiaodi Tao AndrewID: xiaodit
package hw2;

import java.io.File;

import hw2.NutriProfiler.PhysicalActivityEnum;
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
	// Takes all the data from the GUI controls 
	class RecommendNutrientsButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// Sets variables
			float age;
			float weight;
			float height;
			float physicalActivityLevel = 0;
			float defaultFloat = 0f;
			String inputAge = NutriByte.view.ageTextField.getText();
			String inputWeight = NutriByte.view.weightTextField.getText();
			String inputHeight = NutriByte.view.heightTextField.getText();
			String inputGender = NutriByte.view.genderComboBox.getSelectionModel().getSelectedItem();
			String inputPhysicalActivityLevel = NutriByte.view.physicalActivityComboBox.getSelectionModel().getSelectedItem();
			String ingredientsToWatch = NutriByte.view.ingredientsToWatchTextArea.getText();
			// Deals with missing value so when Clicking Recommend Nutrients button with some
			// or all missing data should not throw any exceptionson
			// Also, for some missing value, set it to an assumed value
			// If it is not a null value, then take the data from GUI 
			if((inputAge==null||inputAge.trim().length()==0||inputAge == "")
					&&(inputWeight==null||inputWeight.trim().length()==0||inputWeight == "")
					&&(inputHeight==null||inputHeight.trim().length()==0||inputHeight == "")) {
				return;
			}
			if (inputGender==null||inputGender.trim().length()==0||inputGender == "") {
				return;
			}
			// Gets the data of age, weight, height
			if(inputAge==null||inputAge.trim().length()==0||inputAge == "") {
				age = defaultFloat;
			}else{
				age = Float.parseFloat(inputAge);
			}
			if(inputWeight==null||inputWeight.trim().length()==0||inputWeight == "") {
				weight = defaultFloat;
			}else {
				weight = Float.parseFloat(inputWeight);
			}
			if(inputHeight==null||inputHeight.trim().length()==0||inputHeight == "") {
				height = defaultFloat;
			}else {
				height = Float.parseFloat(inputHeight);
			}
			// Gets the data of physical activity level
			// Creates Person object
			if(inputPhysicalActivityLevel==null||inputPhysicalActivityLevel.trim().length()==0 || inputPhysicalActivityLevel =="") {
				physicalActivityLevel =1f;
			}else {
				for (PhysicalActivityEnum item : PhysicalActivityEnum.values()) {
					if(inputPhysicalActivityLevel.toLowerCase().equals(item.getName().toLowerCase())) {
						physicalActivityLevel = item.getPhysicalActivityLevel();
					}
				}	
			}
			if(inputGender.toLowerCase().equals("female")) {
				NutriByte.person = new Female(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			}
			if (inputGender.toLowerCase().equals("male")) {
				NutriByte.person = new Male(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			}			
			NutriProfiler.createNutriProfile(NutriByte.person);
			NutriByte.view.recommendedNutrientsTableView.setItems(NutriProfiler.recommendedNutrientsList);
		}
	}

	class OpenMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//Opens the FileChooser for the user to choose a profile data file that can be .csv or .xml. 
			//Passes the selected file’s file name to the Model’s readProfile() method. 
			//Displays the profile data in the GUI. 
			//Invokes NutriProfiler’s createNutriProfile() method to populate the recommendedNutrientsList.
			Stage stage =new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select file");
			fileChooser.setInitialDirectory(new File("profiles")); //local path 
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("CSV Files", "*.csv"),
					new ExtensionFilter("XML Files", "*.xml"));
			File filePath = null;
			// Passes the file's name
			if ((filePath = fileChooser.showOpenDialog(stage)) != null){
				String path = filePath.getAbsolutePath();
				NutriByte.model.readProfile(path);
				// Identify the gender, then passes all the data
				if(NutriByte.person instanceof Male) {
					NutriByte.view.genderComboBox.getSelectionModel().select(1);
				}
				if (NutriByte.person instanceof Female) {
					NutriByte.view.genderComboBox.getSelectionModel().select(0);
				}
				String physicalName="";
				for (PhysicalActivityEnum item : PhysicalActivityEnum.values()) {
					if (NutriByte.person.physicalActivityLevel==item.getPhysicalActivityLevel()) {
						physicalName=item.getName().toLowerCase();
						break;
					}
				}
				switch (physicalName) {
				case "sedentary":
					NutriByte.view.physicalActivityComboBox.getSelectionModel().select(0);
					break;
				case "low active":
					NutriByte.view.physicalActivityComboBox.getSelectionModel().select(1);
					break;
				case "active":
					NutriByte.view.physicalActivityComboBox.getSelectionModel().select(2);
					break;
				case "very active":
					NutriByte.view.physicalActivityComboBox.getSelectionModel().select(3);
					break;
				default:
					break;
				}	
				// Formats the float data with two decimal points
				NutriByte.view.weightTextField.setText(String.format("%.2f",NutriByte.person.weight));
				NutriByte.view.heightTextField.setText(String.format("%.2f",NutriByte.person.height));
				NutriByte.view.ageTextField.setText(String.format("%.2f", NutriByte.person.age));
				NutriByte.view.ingredientsToWatchTextArea.setText(NutriByte.person.ingredientsToWatch);
				NutriProfiler.createNutriProfile(NutriByte.person);
				NutriByte.view.recommendedNutrientsTableView.setItems(NutriProfiler.recommendedNutrientsList);
			} 

		}
	}

	class NewMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//write your code here
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			NutriByte.view.initializePrompts();
			NutriProfiler.recommendedNutrientsList.clear();
		}
	}

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
}
