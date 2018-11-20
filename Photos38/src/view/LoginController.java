package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML Button login;
	@FXML TextField username;
	
	public void start(Stage mainStage) {
		
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
					} else if (entered.equals("stock") || entered.equals("Stock")) {
						// do stock stuff
						
					} else {
						// regular login
						// search for user with that username
						// load their data
							// basically create a new user object and populate it with info saved under that username
						// switch to main page
						
					}
					
				}
			}
		});
		
	}
	
	
}
