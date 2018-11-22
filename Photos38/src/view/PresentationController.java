package view;

import model.*;

import java.awt.Label;
import java.io.IOException;
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
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PresentationController {

	@FXML Button back;
	@FXML Button next;
	@FXML Button prev;
	@FXML ImageView image;
	// photo who's tags user is looking at
	Photo selectedPhoto;
	
	//list of tags
	
	
	
	// calling scene
	Scene caller;
	PresentationController thisController = this;
	User user;
	Album selectedAlbum;
	
	/**
	 * starts the controller
	 * @return mainStage this is the main stage of the application
	 */
	public void start(Stage mainStage) {		
		image.setImage(selectedPhoto.getImage());
		
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
			
		});
		next.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				selectedPhoto = selectedAlbum.getNextPhoto(selectedPhoto);
				image.setImage(selectedPhoto.getImage());
			}
		});
		prev.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectedPhoto = selectedAlbum.getPrevPhoto(selectedPhoto);
				image.setImage(selectedPhoto.getImage());
			}
		});
	}
	public void intializeVars(Scene caller,Photo selectedPhoto, Album selectedAlbum) {
		this.caller = caller; this.selectedPhoto = selectedPhoto; this.selectedAlbum = selectedAlbum;
	}

}