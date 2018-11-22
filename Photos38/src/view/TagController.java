package view;

import model.*;

import java.io.IOException;
import java.io.Serializable;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TagController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML Button back;
	@FXML Button add;
	@FXML Button delete;
	@FXML TableView<Tag> tableView;
	@FXML TableColumn<Tag,String> nameColumn;
	@FXML TableColumn<Tag,String> valueColumn;
	
	// photo who's tags user is looking at
	Photo selectedPhoto;
	
	//list of tags
	ObservableList<Tag> tagList;
	
	// calling scene
	Scene caller;
	TagController thisController = this;

	// user logged in
	User user;
	
	/**
	 * starts the controller
	 * @return mainStage this is the main stage of the application
	 */
	public void start(Stage mainStage) {
		
		// make obserable list of tags from selected photo tags
		// set listview to observable list
		
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("values"));

		tableView.setItems(tagList);
		tableView.setStyle("-fx-cell-size: 50px;");
		tableView.setStyle("-fx-font-size: 1.5em;");
		
		ArrayList<Tag> photoTags = selectedPhoto.getTags();	
		tagList = FXCollections.observableArrayList();
		if (photoTags != null)
			tagList.setAll(photoTags);
		
		tableView.setItems(tagList);
		
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
				if (tableView.getSelectionModel().getSelectedItem() != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete");
					alert.setHeaderText("Delete " + tableView.getSelectionModel().getSelectedItem().getName() + " tag");
					alert.setContentText("Are you sure?");

					Optional<ButtonType> confirmation = alert.showAndWait();
					if (confirmation.get() == ButtonType.OK) {
						int index = tableView.getSelectionModel().getSelectedIndex();
						if (index == 0) {
							tableView.getSelectionModel().select(1);
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
							tableView.getSelectionModel().select(tableView.getSelectionModel().getSelectedIndex() + 1);

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
					controller.initializeVars(mainStage.getScene(), user, selectedPhoto,thisController);
					
					controller.start(mainStage);
					Scene addTagScene = new Scene(root,800,800);
					
					mainStage.setScene(addTagScene);
					
				} catch (IOException e) {
				}
			}
		});
	}
	
	public void refreshList() {
		ArrayList<Tag> photoTags = selectedPhoto.getTags();	
		tagList = FXCollections.observableArrayList();
		if (photoTags != null)
			tagList.setAll(photoTags);
		
		tableView.setItems(tagList);
	}

	public void initializeVars(Scene caller,User user,Photo selectedPhoto) {
		this.caller = caller; this.user = user; this.selectedPhoto = selectedPhoto;
	}
}
