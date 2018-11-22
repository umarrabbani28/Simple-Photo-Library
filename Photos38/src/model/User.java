package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	
	public Album createAlbum(String albumName) {
		if (!albumNameExists(albumName)) {
			Album newAlbum = new Album(albumName, this);
			albums.add(newAlbum);
			albumCount++;
			return newAlbum;
		}
			
		return null;
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
	
	public ArrayList<Photo> searchByDate(LocalDateTime start,LocalDateTime end){
		ArrayList<Photo> results = new ArrayList<>();
		for (Album album:albums) {
			for (Photo photo:album.getPhotos()) {
				if ((photo.getDate().isAfter(start) || photo.getDate().equals(start)) && (photo.getDate().isBefore(end))){
					results.add(photo);
				}
			}
		}
		
		Set<Photo> noDuplicates = new HashSet<>();
		noDuplicates.addAll(results);
		results.clear();
		results.addAll(noDuplicates);
		
		return results;
	}
	
	public ArrayList<Photo> searchBySingleTag(String name, String value){
		ArrayList<Photo> results = new ArrayList<>();
		for (Album album:albums) {
			for (Photo photo:album.getPhotos()) {
				for (Tag tag:photo.getTags()) {
					if (tag.getName().equals(name)) {
						for (String tagValue:tag.getValues()) {
							if (tagValue.equals(value)) {
								results.add(photo);
							}
						}
					}
				}
			}
		}
		
		Set<Photo> noDuplicates = new HashSet<>();
		noDuplicates.addAll(results);
		results.clear();
		results.addAll(noDuplicates);
		
		return results;
	}
	
	public ArrayList<Photo> searchByTwoTagsAnd(String name1,String value1,String name2,String value2){
		ArrayList<Photo> results = new ArrayList<>();
		ArrayList<Photo> first = new ArrayList<>();
		ArrayList<Photo> second = new ArrayList<>();
		
		for (Album album:albums) {
			for (Photo photo:album.getPhotos()) {
				for (Tag tag:photo.getTags()) {
					if (tag.getName().equals(name1)) {
						for (String tagValue:tag.getValues()) {
							if (tagValue.equals(value1)) {
								first.add(photo);
							}
						}
					}
					if (tag.getName().equals(name2)) {
						for (String tagValue:tag.getValues()) {
							if (tagValue.equals(value2)) {
								second.add(photo);
							}
						}
					}
				}
			}
		}
		
		Set<Photo> noDuplicatesFirst = new HashSet<>();
		noDuplicatesFirst.addAll(first);
		first.clear();
		first.addAll(noDuplicatesFirst);
		
		Set<Photo> noDuplicatesSecond = new HashSet<>();
		noDuplicatesSecond.addAll(second);
		second.clear();
		second.addAll(noDuplicatesSecond);
		
		for (Photo photo1:first) {
			for (Photo photo2:second) {
				if (photo1.equals(photo2)) {
					results.add(photo1);
				}
			}
		}
	
		return results;
	}
	
	public ArrayList<Photo> searchByTwoTagsOr(String name1,String value1,String name2,String value2){
		ArrayList<Photo> results = new ArrayList<>();
		
		for (Album album:albums) {
			for (Photo photo:album.getPhotos()) {
				for (Tag tag:photo.getTags()) {
					if (tag.getName().equals(name1)) {
						for (String tagValue:tag.getValues()) {
							if (tagValue.equals(value1)) {
								results.add(photo);
							}
						}
					}
					if (tag.getName().equals(name2)) {
						for (String tagValue:tag.getValues()) {
							if (tagValue.equals(value2)) {
								results.add(photo);
							}
						}
					}
				}
			}
		}
		
		Set<Photo> noDuplicates = new HashSet<>();
		noDuplicates.addAll(results);
		results.clear();
		results.addAll(noDuplicates);
		
		return results;
	}
	
	public String toString() {
		return username;
	}
}
