package view;

import java.io.IOException;
import java.io.Serializable;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

public class LoginController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML Button login;
	@FXML TextField username;
	
	Session session;
	
	public void start(Stage mainStage) {
		
		session = application.Main.session;
		
		login.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// get value in textfield and log that user in
				// search saved json for matching username
				String entered = username.getText();
				if (entered == null) {
					// show error message
				} else {
					
					if (entered.equals("admin")|| entered.equals("Admin")) {
						// login as admin
							// take user to admin page
							
							session.setAdmin(true);	
							
							try {
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(getClass().getResource("/view/AdminPage.fxml"));
								AnchorPane root = (AnchorPane) loader.load();
								
								AdminController controller = loader.getController();
								controller.initializeVars(mainStage.getScene());
								
								controller.start(mainStage);
								Scene mainPageScene = new Scene(root,400,400);
								
								mainStage.setScene(mainPageScene);
								
								
							} catch (IOException e) {
								e.printStackTrace();
							}
							
					} else if (entered.equals("stock") || entered.equals("Stock")) {
						// do stock stuff
						
					} else {
						// regular login
						// search for user with that username
						// load their data
							// basically create a new user object and populate it with info saved under that username
						// switch to main page
						
						User curr = session.getUser(entered);
						if (curr == null) {
							// user not found, show error message
						} else {
							session.setUser(curr);
							session.setAdmin(false);
							
							try {
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(getClass().getResource("/view/MainPage.fxml"));
								AnchorPane root = (AnchorPane) loader.load();
								
								MainPageController controller = loader.getController();
								controller.initializeVars(session.getCurrUser());
								
								controller.start(mainStage);
								Scene mainPageScene = new Scene(root,400,400);
								
								mainStage.setScene(mainPageScene);
								
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
					}
					
				}
			}
		});
		
	}
	
	public void intializeVars(Session session) {
		this.session = session;
	}
	
	
}
