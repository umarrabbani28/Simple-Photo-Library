package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username;
	ArrayList<Album> albums;
	int albumCount;
	
	// saved preset tags used on photos
	String[] presetTags = {"location","people"};
	
	public User(String username) {
		this.username = username;
		albums = new ArrayList<>();
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
	
	public String[] getPresetTags() {
		return this.presetTags;
	}
	
	public void addPresetTag(String presetTag) {
		String[] newString = new String[this.presetTags.length + 1];
		for (int i=0;i<presetTags.length;i++) {
			newString[i] = presetTags[i];
		}
		newString[newString.length-1] = presetTag;
		
		this.presetTags = newString;
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
	
	public String toString() {
		return username;
	}
}
