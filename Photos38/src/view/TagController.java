package view;

import model.*;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TagController {

	@FXML Button back;
	@FXML Button add;
	@FXML Button delete;
	@FXML ListView<Tag> listView;
	
	// photo who's tags user is looking at
	Photo selectedPhoto;
	
	//list of tags
	ObservableList<Tag> tagList;
	
	// calling scene
	Scene caller;

	// user logged in
	User user;
	
	public void start(Stage mainStage) {
		
		// make obserable list of tags from selected photo tags
		// set listview to observable list
		
		tagList = FXCollections.observableArrayList(selectedPhoto.getTags());
		listView.setItems(tagList);
		
		// takes user back to album page
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
			
		});
		
		delete.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (listView.getSelectionModel().getSelectedItem() != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete");
					alert.setHeaderText("Delete " + listView.getSelectionModel().getSelectedItem().getName() + " tag");
					alert.setContentText("Are you sure?");

					Optional<ButtonType> confirmation = alert.showAndWait();
					if (confirmation.get() == ButtonType.OK) {
						int index = listView.getSelectionModel().getSelectedIndex();
						if (index == 0) {
							listView.getSelectionModel().select(1);
						}
						
						// delete tag from photo
						selectedPhoto.deleteTag(tagList.get(index));
						// delete tag from tagList
						tagList.remove(index);
						
						if (index == 0 && tagList.size() > 0) {
							// System.out.println("test: " +
							// listView.getSelectionModel().getSelectedIndex());
							// listView.getSelectionModel().select(index);

						} else if (index == tagList.size()) {
							// listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex());
						} else {
							listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);

						}
					}
				}
			}
			
		});
		
		add.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// switch scene to a form that gets tag info and creates a tag
				
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/AddTag.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					
					AddTagController controller = loader.getController();
					controller.initializeVars(mainStage.getScene(), user, selectedPhoto);
					
					controller.start(mainStage);
					Scene addTagScene = new Scene(root,400,400);
					
					mainStage.setScene(addTagScene);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void refreshList() {
		tagList = FXCollections.observableArrayList(selectedPhoto.getTags());
		listView.setItems(tagList);
	}

	public void intializeVars(Scene caller,User user,Photo selectedPhoto) {
		this.caller = caller; this.user = user; this.selectedPhoto = selectedPhoto;
	}
}
