package view;

import model.*;

import java.io.IOException;
import java.io.Serializable;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AlbumPageController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML Button Home;
	@FXML Button Add;
	@FXML Button Remove;
	@FXML Button Move;
	@FXML Button Copy;
	@FXML Button Tags;
	@FXML Button Presentation;
	@FXML Button Caption;
	// photo who's tags user is looking at
	Album selectedAlbum;	
	Photo selectedPhoto;
	// calling scene
	Scene caller;
	AlbumPageController thisController = this;

	// user logged in
	User user;
	
	public void start(Stage mainStage) {
		
		// takes user back to album page
		Home.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
			
		});
		
		Add.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// open directory to select image file
				// create photo object, set image to file, set location to file location etc, set photo.album to current album
				// album.addphoto(new photo object)
				// refresh
				
			}
			
		});
		
		Remove.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// get selected photo
				// delete it from the album
				// refresh
				
			}
		});
		Tags.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/Tag.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					
					
					TagController controller = loader.getController();
					controller.initializeVars(mainStage.getScene(), user, selectedPhoto);
					
					controller.start(mainStage);
					Scene TagScene = new Scene(root,800,800);
					
					mainStage.setScene(TagScene);					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		Move.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/Movecopy.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					
					MovecopyController controller = loader.getController();
					controller.initializeVars(mainStage.getScene(), user, selectedPhoto,false);
					
					controller.start(mainStage);
					Scene movecopyScene = new Scene(root,800,800);
					
					mainStage.setScene(movecopyScene);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		Copy.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/Movecopy.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					
					MovecopyController controller = loader.getController();
					controller.initializeVars(mainStage.getScene(), user, selectedPhoto,true);
					
					controller.start(mainStage);
					Scene movecopyScene = new Scene(root,800,800);
					
					mainStage.setScene(movecopyScene);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		Presentation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/Presentation.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					
					PresentationController controller = loader.getController();
					controller.intializeVars(mainStage.getScene(), selectedPhoto,selectedAlbum);
					
					controller.start(mainStage);
					Scene presentationScene = new Scene(root,800,800);
					
					mainStage.setScene(presentationScene);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void initializeVars(Scene caller,User user, Album selectedAlbum) {
		this.caller = caller; this.user = user;this.selectedAlbum = selectedAlbum;
	}
}
