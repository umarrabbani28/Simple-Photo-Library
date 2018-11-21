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

public class Main extends Application implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// main session of the login
	public static Session session;
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			session = Session.readApp();
			System.out.println(session.getUsers());
		} catch (FileNotFoundException e) {
			System.out.println("came here");
			session = new Session();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/LoginPage.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			LoginController controller = loader.getController();
			
			controller.start(primaryStage);
			Scene loginScene = new Scene(root,400,400);
			
			primaryStage.setScene(loginScene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {		
		launch(args);
	}
}
