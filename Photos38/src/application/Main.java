package application;
	
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.*;
import view.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * The main class that runs the game!
 * 
 * @author Umar Rabbani
 * @author Parth Desai
 * @version 1.0
 */
public class Main extends Application implements Serializable {
	
	/**
	 * forced default serial number to help with serialization
	 */
	private static final long serialVersionUID = 1L;
	// main session of the login
	public static Session session;
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			session = Session.readApp();
		} catch (FileNotFoundException e) {
			session = new Session();
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/LoginPage.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			LoginController controller = loader.getController();
			
			controller.start(primaryStage);
			Scene loginScene = new Scene(root,800,800);
			
			primaryStage.setScene(loginScene);
			primaryStage.show();
			
		} catch(Exception e) {
		}
	}
	
	/**
	 * starts the application
	 */
	public static void main(String[] args) {		
		launch(args);
	}
}
