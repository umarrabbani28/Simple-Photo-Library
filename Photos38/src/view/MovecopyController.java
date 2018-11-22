package view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
public class MovecopyController {

	@FXML Button back;
	@FXML Button move;
	@FXML ObservableList<Album> albums;
	@FXML TableView<Album> tableView;
	@FXML TableColumn<Album,String> nameColumn;
	@FXML TableColumn<Album,String> countColumn;
	@FXML TableColumn<Album,String> dateColumn;
	@FXML TableColumn<Album,String> from;
	@FXML TableColumn<Album,String> to;
	Scene caller;
	MovecopyController thisController = this;
	User user;
	boolean isCopy;
	Photo selectedPhoto;
	Album selectedAlbum;
	AlbumPageController controller;
	
	/**
	 * starts the controller
	 * @return mainStage this is the main stage of the application
	 */
	public void start(Stage mainStage) {
		if(isCopy == true) {
			move.setText("Copy");
		}
		else {
			move.setText("Move");
		}
		
		albums = FXCollections.observableArrayList();
		ArrayList<Album> photosAlbums = selectedPhoto.getAlbums();
		ArrayList<Album> userAlbums = user.getAlbums();
		
	//	userAlbums.removeAll(photosAlbums);
		if (userAlbums!=null) {
			albums.addAll(userAlbums);
		}
		
		tableView.setStyle("-fx-cell-size: 50px;");
		tableView.setStyle("-fx-font-size: 1.5em;");
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		countColumn.setCellValueFactory(new PropertyValueFactory<>("photoCount"));
		from.setCellValueFactory(new PropertyValueFactory<>("earliestString"));
		to.setCellValueFactory(new PropertyValueFactory<>("latestString"));
		
		tableView.setItems(albums);
		
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
			
		});
		move.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Album selected = tableView.getSelectionModel().getSelectedItem();
				
				if (selected != null) {
					if(isCopy == true) {
						selectedPhoto.copyTo(selected);
					}
					else {
						selectedPhoto.moveTo(selected, selectedAlbum);

					}
					controller.refresh();
					mainStage.setScene(caller);
				}
				
			}
			
		});
		
	}
	public void initializeVars(Scene caller,User user,Photo selectedPhoto,Album selectedAlbum ,Boolean isCopy, AlbumPageController controller) {
		this.caller = caller; this.user = user; this.selectedPhoto = selectedPhoto;this.isCopy = isCopy; this.selectedAlbum = selectedAlbum; this.controller = controller;
	}
}