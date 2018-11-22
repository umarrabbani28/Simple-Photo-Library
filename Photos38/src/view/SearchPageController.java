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
					// error
				} else {	
					// take user to search results page
					try {
						searchResults = user.searchByDate(start, end);
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						SearchResultsController controller = loader.getController();
						controller.initializeVars(caller, user, searchResults);
						
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
				
				if ((firstName != null && firstValue == null) || (firstName == null && firstValue != null)) {
					// first tag incomplete error
				}
				if ((secondName != null && secondValue == null) || (secondName == null && secondValue != null)) {
					// second tag incomplete error
				}
				if ((firstName == null && firstValue == null) && (secondName == null && secondValue == null)) {
					// no tag error
				}
				if ((firstName != null && firstValue != null) && (secondName == null && secondValue == null)) {
					// call searchBySingleTag(firstName,firstValue)
					// take user to search results
					try {
						results = user.searchBySingleTag(firstName, firstValue);
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						SearchResultsController controller = loader.getController();
						controller.initializeVars(caller, user, results);
						
						controller.start(mainStage);
						Scene resultScene = new Scene(root,800,800);
						
						mainStage.setScene(resultScene);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if ((secondName != null && secondValue != null) && (firstName == null && firstValue == null)) {
					// call searchBySingleTag(secondName,secondValue)
					// take user to search results
					try {
						results = user.searchBySingleTag(secondName, secondValue);
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						SearchResultsController controller = loader.getController();
						controller.initializeVars(caller, user, results);
						
						controller.start(mainStage);
						Scene resultScene = new Scene(root,800,800);
						
						mainStage.setScene(resultScene);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if ((firstName != null && firstValue != null) && (secondName != null && secondValue != null)) {
					// get choice
					// call appropriate double tag method
					// take user to search results
					
					try {
						String userChoice = choice.getValue();
						
						if (userChoice == null) {
							// error
						}
						
						if (userChoice.equals("Or")) {
							results = user.searchByTwoTagsOr(firstName, firstValue, secondName, secondValue);
						} if (userChoice.equals("And")) {
							results = user.searchByTwoTagsAnd(firstName, firstValue, secondName, secondValue);
						}
												
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						SearchResultsController controller = loader.getController();
						controller.initializeVars(caller, user, results);
						
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
	
	public void initializeVars(Scene caller,User user) {
		this.caller = caller;
		this.user = user;
	}
}
