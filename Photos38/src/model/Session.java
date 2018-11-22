package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.LoginController;
import view.SearchPageController;

public class Session implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User currUser;
	ArrayList<User> allUsers;
	
	static boolean isAdmin = false;
	
	// used for serialization
	public final static String storeName = "data/storage.dat";
	
	public Session() {
		allUsers = new ArrayList<>();
	}
	
	// serializes list of all users to the file
	public void writeApp() throws IOException {
		ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(storeName));
		outStream.writeObject(this);
	}
	
	public static Session readApp() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(storeName));
		Session newSession = (Session)inStream.readObject();
		return newSession;
	}
	
	public ArrayList<User> getUsers() {
		return allUsers;
	}
	
	public User getUser(String username) {
		for (User item:allUsers) {
			if (item.getUsername().equals(username)) {
				return item;
			}
		}
		return null;
	}
	
	public User getCurrUser() {
		return currUser;
	}
	
	public void setUser(User user) {
		currUser = user;
	}
	
	public void addUser(User user) {
		allUsers.add(user);
	}
	
	public void delete(User user) {
		allUsers.remove(user);
	}
	
	public void login() {
		
	}
	
	public void logout(Stage stage) throws Exception {
		
		writeApp();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/LoginPage.fxml"));
		AnchorPane root;
		root = (AnchorPane) loader.load();

		LoginController controller = loader.getController();
		
		controller.start(stage);
		Scene searchPageScene = new Scene(root,800,800);
		
		stage.setScene(searchPageScene);
	}
	
	public void quit() throws Exception {
		writeApp();
		Platform.exit();
	}
	
	public boolean usernameExists(String username) {
		for (User item:allUsers) {
			if (item.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public void setAdmin(boolean value) {
		isAdmin = value;
	}
	
}
