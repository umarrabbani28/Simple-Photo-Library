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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML TableView<Photo> tableView;
	@FXML TableColumn<Photo,Image> thumbnails;
	@FXML TableColumn<Photo,String> captions;
	
	ArrayList<Photo> results;
	User user;
	Scene home;
	ObservableList<Photo> photos;
	MainPageController callingController;
	
	/**
	 * starts the controller
	 * @return mainStage this is the main stage of the application
	 */
	public void start(Stage mainStage) {
				
		tableView.setStyle("-fx-cell-size: 500px;");
		tableView.setStyle("-fx-font-size: 1.5em;");

		photos = FXCollections.observableArrayList();
		if (results != null)
			photos.setAll(results);
		
		tableView.setItems(photos);
		captions.setCellValueFactory(new PropertyValueFactory<>("album"));

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
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Name Error");
						alert.setHeaderText("Album name already exists!");
						alert.showAndWait();
					} else {
						Album newAlbum = user.createAlbum(result.get());
						for (Photo photo:results) {
							newAlbum.addPhoto(photo);
						}
						callingController.refresh();
						mainStage.setScene(home);
					}
				}	
			}	
		});	
	}
	
	public void initializeVars(Scene home,User user,ArrayList<Photo> results, MainPageController callingController) {
		this.home = home; this.user = user; this.results = results; this.callingController = callingController;
	}
}
