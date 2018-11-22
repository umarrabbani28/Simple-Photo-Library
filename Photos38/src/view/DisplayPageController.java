package view;

import model.*;

import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DisplayPageController {

	@FXML Button back;
	@FXML TableView<Tag> tableView;
	@FXML TableColumn<Tag,String> nameColumn;
	@FXML TableColumn<Tag,String> valueColumn;
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

	/**
	 * starts the controller
	 * @return mainStage this is the main stage of the application
	 */
	public void start(Stage mainStage) {
		
		ArrayList<Tag> userTags = selectedPhoto.getTags();
		tagList = FXCollections.observableArrayList();
		if (userTags != null) {
			tagList.setAll(userTags);
		}
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("values"));

		tableView.setItems(tagList);
		tableView.setStyle("-fx-cell-size: 50px;");
		tableView.setStyle("-fx-font-size: 1.5em;");
		
		caption.setText(selectedPhoto.getCaption());
		datetime.setText(selectedPhoto.getDateString());
		image.setImage(selectedPhoto.getImage());
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
			
		});
	}
	
	/**
	 * intializes the variables used in the controller
	 * @param caller This is the calling scene
	 * @param selectedPhoto This is the photo that is used throughout the controller
	 * @return void 
	 */
	public void initializeVars(Scene caller,Photo selectedPhoto) {
		this.caller = caller; this.selectedPhoto = selectedPhoto;
	}

}