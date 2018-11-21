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

	@FXML Button Home;
	@FXML Button Add;
	@FXML Button Remove;
	@FXML Button Move;
	@FXML Button Copy;
	@FXML Button Tags;
	@FXML Button Presentation;
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
				
			}
			
		});
		
		Remove.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
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
					Scene TagScene = new Scene(root,400,400);
					
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
					Scene movecopyScene = new Scene(root,400,400);
					
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
					Scene movecopyScene = new Scene(root,400,400);
					
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
					
					presentationController controller = loader.getController();
					controller.intializeVars(mainStage.getScene(), selectedPhoto,selectedAlbum);
					
					controller.start(mainStage);
					Scene movecopyScene = new Scene(root,400,400);
					
					mainStage.setScene(movecopyScene);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void intializeVars(Scene caller,User user,Photo selectedPhoto) {
		this.caller = caller; this.user = user; this.selectedPhoto = selectedPhoto;
	}
}
