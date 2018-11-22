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

public class AddTagController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML Button newName;
	@FXML Button deleteValue;
	@FXML Button addValue;
	@FXML Button add;
	@FXML Button cancel;
	@FXML Button done;
	@FXML ComboBox<String> comboBox;
	@FXML ListView<String> listView;
	@FXML Label nameLabel;
	@FXML Label error;
	@FXML Label error2;
	
	// received from caller
	Scene caller;
	TagController callingController;
	User user;
	Photo selectedPhoto;
	
	String selectedName;
	int numValues;
	
	public void start(Stage mainStage) {
		
		listView.setStyle("-fx-font-size: 1.5em ;");
		comboBox.setStyle("-fx-font-size: 1.5em ;");
				
		ObservableList<String> values = FXCollections.observableArrayList();
		listView.setItems(values);
		
		ObservableList<String> presets = FXCollections.observableArrayList(user.getPresetTags());
		comboBox.setItems(presets);
		
		comboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				values.clear();
				numValues = 0;
				selectedName = comboBox.getValue();
				nameLabel.setText(selectedName);
			}
		});
		
		newName.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TextInputDialog getNewName = new TextInputDialog();
				getNewName.setHeaderText("Enter the new tag name");
				
				Optional<String> result = getNewName.showAndWait();
				
				if (result.isPresent()) {
					selectedName = result.get();
					user.addPresetTag(selectedName);
					presets.add(selectedName);
					values.clear();
					numValues = 0;
					
					comboBox.setPromptText(selectedName);
					nameLabel.setText(selectedName);
				}
			}
		});
		
		addValue.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (selectedName == null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tag Error");
					alert.setHeaderText("Select a tag name first!");
					alert.showAndWait();
					
				} else if (Tag.isASingleValueName(selectedName) && numValues==1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tag Error");
					alert.setHeaderText("Chosen tag can only have 1 value!");
					alert.showAndWait();
				} else {
					TextInputDialog getNewValue = new TextInputDialog();
					getNewValue.setHeaderText("Enter the value");
					
					Optional<String> result = getNewValue.showAndWait();
	
					if (result.isPresent()) {
						values.add(result.get());
						numValues++;
					}
				}	
			}
		});
		
		deleteValue.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (listView.getSelectionModel().getSelectedItem() != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete");
					alert.setContentText("Are you sure?");

					Optional<ButtonType> confirmation = alert.showAndWait();
					if (confirmation.get() == ButtonType.OK) {
						int index = listView.getSelectionModel().getSelectedIndex();
						if (index == 0) {
							listView.getSelectionModel().select(1);
						}
						
						values.remove(index);
						numValues--;
						
						if (index == 0 && values.size() > 0) {
							// System.out.println("test: " +
							// listView.getSelectionModel().getSelectedIndex());
							// listView.getSelectionModel().select(index);

						} else if (index == values.size()) {
							// listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex());
						} else {
							listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);

						}
					}
				}
			}
			
		});
		
		cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
		});

		
		done.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (selectedName == null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tag Error");
					alert.setHeaderText("A tag name must be chosen!");
					alert.showAndWait();
				} else if (values.size() == 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tag Error");
					alert.setHeaderText("A tag value must be chosen!");
					alert.showAndWait();
				} else {
					Tag newTag;
					if (Tag.isASingleValueName(selectedName)) {
						newTag = new Tag(selectedName,values.get(0));
						newTag.setSingleTag(true);
					} else {
						ArrayList<String> newValues = new ArrayList<>();
						for (String item:values) {
							newValues.add(item);
						}
						newTag = new Tag(selectedName,newValues);
						newTag.setSingleTag(false);
					}
					
					selectedPhoto.addTag(newTag);
					
					callingController.refreshList();
					mainStage.setScene(caller);
				}
			}
		});
	}
	
	// sets up state like a constructor
	public void initializeVars(Scene caller,User user,Photo selectedPhoto,TagController callingController) {
		this.caller = caller; this.user = user; this.selectedPhoto = selectedPhoto; this.callingController = callingController;
	}
}
