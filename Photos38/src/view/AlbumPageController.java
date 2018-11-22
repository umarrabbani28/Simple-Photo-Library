package view;

import model.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.activation.MimetypesFileTypeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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
	@FXML TableView<Photo> tableView;
	@FXML TableColumn<Photo,String> captions;
	@FXML TableColumn<Photo,Image> thumbnails;
	@FXML Label albumTitle;
	
	// photo who's tags user is looking at
	Album selectedAlbum;	
	Photo selectedPhoto;
	
	ObservableList<Photo> photos;
	
	// calling scene
	Scene caller;
	MainPageController callingController;
	AlbumPageController thisController = this;

	// user logged in
	User user;
	
	public void start(Stage mainStage) {
		
		albumTitle.setText(selectedAlbum.getName());
		
		tableView.setStyle("-fx-cell-size: 500px;");
		tableView.setStyle("-fx-font-size: 1.5em;");
		
		captions.setCellValueFactory(new PropertyValueFactory<>("caption"));
		thumbnails.setCellValueFactory(new PropertyValueFactory<>("image"));
		
		ArrayList<Photo> userPhotos = selectedAlbum.getPhotos();
		photos = FXCollections.observableArrayList();
		if (userPhotos != null)
			photos.setAll(userPhotos);
		
		tableView.setItems(photos);
		
		FileChooser fileChooser = new FileChooser();
		
		thumbnails.setCellFactory(param -> {
		       //Set up the ImageView
		       ImageView imageview = new ImageView();
		       imageview.setFitHeight(50);
		       imageview.setFitWidth(50);

		       //Set up the Table
		       TableCell<Photo, Image> cell = new TableCell<Photo, Image>() {
		           public void updateItem(Image item, boolean empty) {
		             if (item != null) {
		                  imageview.setImage(item);
		             }
		           }
		        };
		        // Attach the imageview to the cell
		        cell.setGraphic(imageview);
		        return cell;
		   });
		  
		thumbnails.setCellValueFactory(new PropertyValueFactory<Photo, Image>("image"));
		
		
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					try {
						Photo selected = tableView.getSelectionModel().getSelectedItem();
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/DisplayPage.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						DisplayPageController controller = loader.getController();
						
						controller.initializeVars(mainStage.getScene(), selected);
						
						controller.start(mainStage);
						Scene displayScene = new Scene(root,800,800);
						
						mainStage.setScene(displayScene);
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}		
					
				}
				
			}
		});
		
		Caption.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Photo selected = tableView.getSelectionModel().getSelectedItem();
				
				if (selected != null) {
					TextInputDialog newCaption = new TextInputDialog();
					newCaption.setTitle("Caption");
					newCaption.setHeaderText("Enter the new caption");
					newCaption.getEditor().setText(selected.getCaption());
					
					Optional<String> result = newCaption.showAndWait();
					if (result.isPresent()) {
						selected.reCaption(result.get());
						refresh();
					}
				}
			}
			
		});
		
		// takes user back to album page
		Home.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				callingController.refresh();
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
				
				File file = fileChooser.showOpenDialog(mainStage);
				if (file != null) {
					try {
						String mimeType = Files.probeContentType(file.toPath());
						if(mimeType.substring(0,5).equalsIgnoreCase("image")){
							String fileLocation = file.toURI().toString();
							Image fileImage = new Image(fileLocation);
							Date date = new Date(file.lastModified());
							Instant instant = date.toInstant();
							LocalDateTime newDateTime = LocalDateTime.ofInstant(instant,ZoneId.systemDefault());
							
							
							Photo newPhoto = new Photo(newDateTime,fileLocation,selectedAlbum,"");
							selectedAlbum.addPhoto(newPhoto);
							photos.add(newPhoto);	
						} else {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("File Error");
							alert.setHeaderText("File chosen isn't an image!");
							alert.showAndWait();
						}
						
											
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}
					
				}
			}		
		});
		
		Remove.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// get selected photo
				// delete it from the album
				// refresh
				Photo selected = tableView.getSelectionModel().getSelectedItem();
				if (selected != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete");
					alert.setContentText("Are you sure?");

					Optional<ButtonType> confirmation = alert.showAndWait();
					if (confirmation.get() == ButtonType.OK) {
						int index = tableView.getSelectionModel().getSelectedIndex();
						if (index == 0) {
							tableView.getSelectionModel().select(1);
						}
						
						selectedAlbum.deletePhoto(selected);
						photos.remove(selected);
						
						if (index == 0 && photos.size() > 0) {
							// System.out.println("test: " +
							// listView.getSelectionModel().getSelectedIndex());
							// listView.getSelectionModel().select(index);

						} else if (index == photos.size()) {
							// listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex());
						} else {
							tableView.getSelectionModel().select(tableView.getSelectionModel().getSelectedIndex() + 1);

						}
					}
				}
				
			}
		});
		Tags.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					
					selectedPhoto = tableView.getSelectionModel().getSelectedItem();
					if (selectedPhoto != null) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/Tag.fxml"));
						AnchorPane root = (AnchorPane) loader.load();
						
						
						TagController controller = loader.getController();
						controller.initializeVars(mainStage.getScene(), user, selectedPhoto);
						
						controller.start(mainStage);
						Scene TagScene = new Scene(root,800,800);
						
						mainStage.setScene(TagScene);	
					}
									
				} catch (IOException e) {
				}
			}
		});
		Move.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					selectedPhoto = tableView.getSelectionModel().getSelectedItem();
					if (selectedPhoto != null) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/Movecopy.fxml"));
						AnchorPane root = (AnchorPane) loader.load();
						
						MovecopyController controller = loader.getController();
						controller.initializeVars(mainStage.getScene(), user, selectedPhoto, selectedAlbum,false,thisController);
						
						controller.start(mainStage);
						Scene movecopyScene = new Scene(root,800,800);
						
						mainStage.setScene(movecopyScene);
					}
					
					
				} catch (IOException e) {
				}
			}
		});
		Copy.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					selectedPhoto = tableView.getSelectionModel().getSelectedItem();
					if (selectedPhoto != null) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/Movecopy.fxml"));
						AnchorPane root = (AnchorPane) loader.load();
						
						MovecopyController controller = loader.getController();
						controller.initializeVars(mainStage.getScene(), user, selectedPhoto,selectedAlbum, true, thisController);
						
						controller.start(mainStage);
						Scene movecopyScene = new Scene(root,800,800);
						
						mainStage.setScene(movecopyScene);
					}
					
					
				} catch (IOException e) {
				}
			}
		});
		Presentation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					
					selectedPhoto = tableView.getSelectionModel().getSelectedItem();
					if (selectedPhoto != null) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/PresentationPage.fxml"));
						AnchorPane root = (AnchorPane) loader.load();
						
						PresentationController controller = loader.getController();
						controller.intializeVars(mainStage.getScene(), selectedPhoto,selectedAlbum);
						
						controller.start(mainStage);
						Scene presentationScene = new Scene(root,800,800);
						
						mainStage.setScene(presentationScene);
					}
					
					
				} catch (IOException e) {
				}
			}
		});
	}

	public void initializeVars(Scene caller,User user, Album selectedAlbum, MainPageController callingController) {
		this.caller = caller; this.user = user;this.selectedAlbum = selectedAlbum; this.callingController = callingController;
	}
	
	public void refresh() {
		ArrayList<Photo> userPhotos = selectedAlbum.getPhotos();
		photos = FXCollections.observableArrayList();
		if (userPhotos != null)
			photos.setAll(userPhotos);
		
		tableView.setItems(photos);
		tableView.refresh();
	}
	
}
