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
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML TableView<Album> tableView;
	@FXML TableColumn<Album,String> nameColumn;
	@FXML TableColumn<Album,String> countColumn;
	@FXML TableColumn<Album,String> dateColumn;
	@FXML TableColumn<Album,String> from;
	@FXML TableColumn<Album,String> to;
	
	User user;
	ObservableList<Album> albums;
	Session session;
	MainPageController thisController = this;
	
	/**
	 * starts the controller
	 * @return mainStage this is the main stage of the application
	 */
	public void start(Stage mainStage) {
				
		tableView.setStyle("-fx-cell-size: 50px;");
		tableView.setStyle("-fx-font-size: 1.5em;");
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		countColumn.setCellValueFactory(new PropertyValueFactory<>("photoCount"));
		from.setCellValueFactory(new PropertyValueFactory<>("earliestString"));
		to.setCellValueFactory(new PropertyValueFactory<>("latestString"));
		
		session = application.Main.session;
		ArrayList<Album> userAlbums = user.getAlbums();
		albums = FXCollections.observableArrayList();
		if (userAlbums != null)
			albums.setAll(userAlbums);
		
		tableView.setItems(albums);
		
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					try {
						Album selected = tableView.getSelectionModel().getSelectedItem();
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/AlbumPage.fxml"));
						AnchorPane root;
						root = (AnchorPane) loader.load();

						AlbumPageController controller = loader.getController();
						controller.initializeVars(mainStage.getScene(), user, selected,thisController);
						
						controller.start(mainStage);
						Scene searchPageScene = new Scene(root,800,800);
						
						mainStage.setScene(searchPageScene);
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}		
					
				}
				
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
					controller.initializeVars(mainStage.getScene(), user, thisController);
					
					controller.start(mainStage);
					Scene searchPageScene = new Scene(root,800,800);
					
					mainStage.setScene(searchPageScene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		rename.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Album selected = tableView.getSelectionModel().getSelectedItem();

				TextInputDialog newName = new TextInputDialog();
				newName.setHeaderText("Enter new album name");
				
				Optional<String> result = newName.showAndWait();
				if (result.isPresent()) {
					if (user.albumNameExists(result.get())) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Name Error");
						alert.setHeaderText("Album name already exists!");
						alert.showAndWait();
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
				
				Album selected = tableView.getSelectionModel().getSelectedItem();
				if (selected != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete");
					alert.setHeaderText("Delete album: " + tableView.getSelectionModel().getSelectedItem().getName());
					alert.setContentText("Are you sure?");

					Optional<ButtonType> confirmation = alert.showAndWait();
					if (confirmation.get() == ButtonType.OK) {
						int index = tableView.getSelectionModel().getSelectedIndex();
						if (index == 0) {
							tableView.getSelectionModel().select(1);
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
							tableView.getSelectionModel().select(tableView.getSelectionModel().getSelectedIndex() + 1);

						}
					}
				}
			}
		});
		
		
	}
	
	/**
	 * intializes the variables used in the controller
	 * @param user This is the user that is used throughout the controller
	 * @return void 
	 */
	public void initializeVars(User user) {
		this.user = user;
	}
	
	public void refresh() {
		ArrayList<Album> userAlbums = user.getAlbums();
		albums = FXCollections.observableArrayList();
		if (userAlbums != null)
			albums.setAll(userAlbums);
		
		tableView.setItems(albums);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		countColumn.setCellValueFactory(new PropertyValueFactory<>("photoCount"));
		from.setCellValueFactory(new PropertyValueFactory<>("earliestString"));
		to.setCellValueFactory(new PropertyValueFactory<>("latestString"));
		
		tableView.refresh();

	}
	
}
