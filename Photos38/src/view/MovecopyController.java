package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
public class MovecopyController {

	@FXML Button back;
	@FXML Button move;
	@FXML ObservableList<Album> albums;
	@FXML ListView<Album> album_list;
	Scene caller;
	MovecopyController thisController = this;
	User user;
	boolean isCopy;
	Photo selectedPhoto;
	Album selectedAlbum;
	public void start(Stage mainStage) {
		if(isCopy == true) {
			move.setText("copy");
		}
		else {
			move.setText("move");
		}
		albums = FXCollections.observableArrayList(user.getAlbums());
		album_list.setItems(albums);
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(caller);
			}
			
		});
		move.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(isCopy == true) {
					selectedPhoto.copyTo(album_list.getSelectionModel().getSelectedItem());
				}
				else {
					selectedPhoto.moveTo(album_list.getSelectionModel().getSelectedItem(), selectedAlbum);

				}
				mainStage.setScene(caller);
			}
			
		});
		
	}
}