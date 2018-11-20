package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	public static User currentUser;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static ArrayList<User> getUsers() {
		return null;
	}
	
	public static User searchUser(String username) {
		return null;
	}
	
	public static void addUser(User user) {
		
	}
	
	public static void delete(User user) {
		
	}
	
	public static boolean usernameExists(String username) {
		return false;
	}
}
