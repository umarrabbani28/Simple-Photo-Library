package view;

import java.io.FileNotFoundException;
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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

public class MainPageController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML Button create;
	@FXML Button search;
	@FXML Button rename;
	@FXML Button quit;
	@FXML Button logout;
	@FXML Button delete;
	@FXML ListView<Album> listView;
	
	User user;
	ObservableList<Album> albums;
	Session session;
	
	public void start(Stage mainStage) {
				
		session = application.Main.session;
		ArrayList<Album> userAlbums = user.getAlbums();
		albums = FXCollections.observableArrayList();
		if (userAlbums != null)
			albums.setAll(userAlbums);
		
		listView.setItems(albums);
		
		create.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TextInputDialog albumName = new TextInputDialog();
				albumName.setHeaderText("Enter the name of the new album");
				
				Optional<String> result = albumName.showAndWait();
				if (result.isPresent()) {
					if (user.albumNameExists(result.get())) {
						// set error text to tell user that album name already exists
					} else {
						user.createAlbum(result.get());
						refresh();
					}
				}	
			}
		});
		
		logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// take user back to login page
				// save updates in json to file
				try {
					session.logout(mainStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		quit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// save updates in json to file
				// close window
				try {
					session.quit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		search.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// take user to search page
			
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/SearchPage.fxml"));
					AnchorPane root;
					root = (AnchorPane) loader.load();

					SearchPageController controller = loader.getController();
				//	controller.initializeVars(mainStage.getScene(), user, selectedPhoto,thisController);
					
					controller.start(mainStage);
					Scene searchPageScene = new Scene(root,400,400);
					
					mainStage.setScene(searchPageScene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		rename.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Album selected = listView.getSelectionModel().getSelectedItem();

				TextInputDialog newName = new TextInputDialog();
				newName.setHeaderText("Enter new album name");
				
				Optional<String> result = newName.showAndWait();
				if (result.isPresent()) {
					if (user.albumNameExists(result.get())) {
						// show error text
					} else {
						if (selected != null) {
							user.rename(selected, result.get());
							refresh();
						}
					}
				}
			}
		});
		
		delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				Album selected = listView.getSelectionModel().getSelectedItem();
				if (selected != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete Value");
					alert.setHeaderText("Delete value:" + listView.getSelectionModel().getSelectedItem());
					alert.setContentText("Are you sure?");

					Optional<ButtonType> confirmation = alert.showAndWait();
					if (confirmation.get() == ButtonType.OK) {
						int index = listView.getSelectionModel().getSelectedIndex();
						if (index == 0) {
							listView.getSelectionModel().select(1);
						}
						
						user.deleteAlbum(selected);
						refresh();
						
						
						if (index == 0 && albums.size() > 0) {
							// System.out.println("test: " +
							// listView.getSelectionModel().getSelectedIndex());
							// listView.getSelectionModel().select(index);

						} else if (index == albums.size()) {
							// listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex());
						} else {
							listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);

						}
					}
				}
			}
		});
		
		
	}
	
	public void initializeVars(User user) {
		this.user = user;
	}
	
	public void refresh() {
		albums.setAll(user.getAlbums());
		listView.setItems(albums);
	}
	
}
