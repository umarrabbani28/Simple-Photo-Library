package view;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import model.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.stage.Stage;

public class SearchPageController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@FXML Button back;
	@FXML Button dateSearch;
	@FXML Button tagSearch;
	@FXML TextField name1;
	@FXML TextField name2;
	@FXML TextField value1;
	@FXML TextField value2;
	@FXML DatePicker startDate;
	@FXML DatePicker endDate;
	@FXML ChoiceBox<String> choice;
	
	// user who's photos are searched
	User user;
	
	Scene caller;
	MainPageController callingController;

	/**
	 * starts the controller
	 * @return mainStage this is the main stage of the application
	 */
	public void start(Stage mainStage) {
		
		choice.getItems().add("And");
		choice.getItems().add("Or");
		choice.setStyle("-fx-font-size: 1.5em;");
		
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
			
		});
		
		dateSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				LocalDateTime start = startDate.getValue().atStartOfDay();
				LocalDateTime end = endDate.getValue().plusDays(1).atStartOfDay();
				ArrayList<Photo> searchResults = new ArrayList<>();
				
				if (start == null || end == null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Date Error");
					alert.setHeaderText("Dates must be entered!");
					alert.showAndWait();
				} else {	
					// take user to search results page
					try {
						searchResults = user.searchByDate(start, end);
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						SearchResultsController controller = loader.getController();
						controller.initializeVars(caller, user, searchResults, callingController);
						
						controller.start(mainStage);
						Scene resultScene = new Scene(root,800,800);
						
						mainStage.setScene(resultScene);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}	
			
		});
		
		tagSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String firstName = name1.getText();
				String secondName = name2.getText();
				String firstValue = value1.getText();
				String secondValue = value2.getText();
				ArrayList<Photo> results = new ArrayList<>();
				
				if ((!firstName.equals("") && firstValue.equals("")) || (firstName.equals("") && !firstValue.equals(""))) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tag Error");
					alert.setHeaderText("Must complete the tag!");
					alert.showAndWait();
				}
				if ((!secondName.equals("") && secondValue.equals("")) || (secondName.equals("") && !secondValue.equals(""))) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tag Error");
					alert.setHeaderText("Must complete the tag!");
					alert.showAndWait();
				}
				if ((firstName.equals("") && firstValue.equals("")) && (secondName.equals("") && secondValue.equals(""))) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tag Error");
					alert.setHeaderText("Must select at least one tag!");
					alert.showAndWait();
				}
				if ((!firstName.equals("") && !firstValue.equals("")) && (secondName.equals("")&& secondValue.equals(""))) {
					// call searchBySingleTag(firstName,firstValue)
					// take user to search results
					try {
						results = user.searchBySingleTag(firstName, firstValue);
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						SearchResultsController controller = loader.getController();
						controller.initializeVars(caller, user, results, callingController);
						
						controller.start(mainStage);
						Scene resultScene = new Scene(root,800,800);
						
						mainStage.setScene(resultScene);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if ((!secondName.equals("") && !secondValue.equals("")) && (firstName.equals("") && firstValue.equals(""))) {
					// call searchBySingleTag(secondName,secondValue)
					// take user to search results
					try {
						results = user.searchBySingleTag(secondName, secondValue);
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						SearchResultsController controller = loader.getController();
						controller.initializeVars(caller, user, results, callingController);
						
						controller.start(mainStage);
						Scene resultScene = new Scene(root,800,800);
						
						mainStage.setScene(resultScene);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if ((!firstName.equals("") && !firstValue.equals("")) && (!secondName.equals("")&& !secondValue.equals(""))) {
					// get choice
					// call appropriate double tag method
					// take user to search results
					
					try {
						String userChoice = choice.getValue();
						
						if (userChoice == null) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Tag Error");
							alert.setHeaderText("Must select search type (And/Or)!");
							alert.showAndWait();
						}
						
						else if (userChoice.equals("Or")) {
							results = user.searchByTwoTagsOr(firstName, firstValue, secondName, secondValue);
						} else if (userChoice.equals("And")) {
							results = user.searchByTwoTagsAnd(firstName, firstValue, secondName, secondValue);
						}
												
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						SearchResultsController controller = loader.getController();
						controller.initializeVars(caller, user, results,callingController);
						
						controller.start(mainStage);
						Scene resultScene = new Scene(root,800,800);
						
						mainStage.setScene(resultScene);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		
	}
	
	public void initializeVars(Scene caller,User user, MainPageController callingController) {
		this.caller = caller;
		this.user = user;
		this.callingController = callingController;
	}
}
