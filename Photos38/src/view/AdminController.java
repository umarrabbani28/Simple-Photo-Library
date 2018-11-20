package view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

public class AdminController {
	
	@FXML Button create;
	@FXML Button delete;
	@FXML Button quit;
	@FXML Button logout;
	@FXML ListView<User> listView;
	
	ObservableList<User> users;
	
	public void start(Stage mainStage) {
		
		users = FXCollections.observableArrayList(application.Main.getUsers());	
		listView.setItems(users);
		
		create.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TextInputDialog username = new TextInputDialog();
				username.setHeaderText("Enter the username for the new user");
				
				Optional<String> result = username.showAndWait();
				if (result.isPresent()) {
					if (application.Main.usernameExists(result.get())) {
						// show error message
					} else {
						application.Main.addUser(new User(result.get()));
						refresh();
					}
				}
			}
		});
		
		delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				User selected = listView.getSelectionModel().getSelectedItem();
				if (selected != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete Value");
					alert.setHeaderText("Delete value:" + listView.getSelectionModel().getSelectedItem());
					alert.setContentText("Are you sure?");

					Optional<ButtonType> confirmation = alert.showAndWait();
					if (confirmation.get() == ButtonType.OK) {
						int index = listView.getSelectionModel().getSelectedIndex();
						if (index == 0) {
							listView.getSelectionModel().select(1);
						}
						
						application.Main.delete(selected);
						refresh();
						
						
						if (index == 0 && users.size() > 0) {
							// System.out.println("test: " +
							// listView.getSelectionModel().getSelectedIndex());
							// listView.getSelectionModel().select(index);

						} else if (index == users.size()) {
							// listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex());
						} else {
							listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);

						}
					}
				}
			}
		});
		
		logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// take user back to login page
				// save user info 
			}
		});
		
		logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// save user info 
				// close window
			}
		});
		
		
	}
	
	public void refresh() {
		users.setAll(application.Main.getUsers());
		listView.setItems(users);
	}
	
	
}
