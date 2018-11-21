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

public class DisplayPageController {

	@FXML Button back;
	@FXML ListView<Tag> listView;
	@FXML Label caption;
	@FXML Label datetime;
	@FXML ImageView image;
	// photo who's tags user is looking at
	Photo selectedPhoto;
	
	//list of tags
	ObservableList<Tag> tagList;
	
	
	
	// calling scene
	Scene caller;
	DisplayPageController thisController = this;
	User user;
	Album selectedAlbum;
	public void start(Stage mainStage) {
		tagList = FXCollections.observableArrayList(selectedPhoto.getTags());
		
		listView.setItems(tagList);
		caption.setText(selectedPhoto.getCaption());
		caption.setText(selectedPhoto.getCaption());
		image.setImage(selectedPhoto.getImage());
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
			
		});
	}
	public void intializeVars(Scene caller,Photo selectedPhoto, Album selectedAlbum) {
		this.caller = caller; this.selectedPhoto = selectedPhoto; this.selectedAlbum = selectedAlbum;
	}

}