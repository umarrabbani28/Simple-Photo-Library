package model;

import java.util.ArrayList;

public class User {
	
	String username;
	ArrayList<Album> albums;
	int albumCount;
	
	public User(String username) {
		this.username = username;
	}
	
	public boolean createAlbum(String albumName) {
		if (!albumNameExists(albumName)) {
			Album newAlbum = new Album(albumName, this);
			albums.add(newAlbum);
			albumCount++;
			return true;
		}
			
		return false;
	}
	
	public boolean rename(Album album, String name) {
		if (!albumNameExists(name)) {
			album.setName(name);
			return true;
		}
		return false;
	}
	
	public void deleteAlbum(Album album) {
		albums.remove(album);
		albumCount--;
	}
	
	public String getUsername() {
		return username;
	}
	
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	public void setName(String username) {
		this.username = username;
	}
	
	public boolean albumNameExists(String name) {
		for (Album item:albums) {
			if (name.equals(item.getName()))
				return true;
		}
		return false;
	}
}
