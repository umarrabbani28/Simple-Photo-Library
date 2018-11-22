package view;

import java.io.Serializable;
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

public class SearchResultsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@FXML Button back;
	@FXML Button create;
	
	ArrayList<Photo> results;
	User user;
	Scene home;
	
	public void start(Stage mainStage) {
		
		// set tableview of results
		
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(home);
			}
			
		});
		
		create.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				TextInputDialog albumName = new TextInputDialog();
				albumName.setHeaderText("Enter the name of the new album");
				
				Optional<String> result = albumName.showAndWait();
				if (result.isPresent()) {
					if (user.albumNameExists(result.get())) {
						// set error text to tell user that album name already exists
					} else {
						Album newAlbum = user.createAlbum(result.get());
						for (Photo photo:results) {
							newAlbum.addPhoto(photo);
						}
						mainStage.setScene(home);
					}
				}	
			}	
		});	
	}
	
	public void initializeVars(Scene home,User user,ArrayList<Photo> results) {
		this.home = home; this.user = user; this.results = results;
	}
}
