package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * class representing a photo album
 * @author Umar Rabbani
 * @author Parth Desai
 */
public class Album implements Serializable{

	private static final long serialVersionUID = 1L;
	String name;
	LocalDateTime earliest;
	String earliestString;
	LocalDateTime latest;
	String latestString;
	int photoCount;
	User owner;
	ArrayList<Photo> photos;
	
	/**
	 * constructor
	 * @param name This is the name of the album
	 * @param owner This is the owner of the album
	 */
	public Album(String name, User owner) {
		this.name = name; this.owner = owner;
		photos = new ArrayList<>();
	}
	
	/**
	 * adds a photo object to the album
	 * @param photo the photo to be added
	 * @return void 
	 */
	public void addPhoto(Photo photo) {
		LocalDateTime photoDate = photo.getDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
		if (earliest == null) {
			earliest = photoDate;
			earliestString = earliest.format(formatter);
		}
		else if (photoDate.isBefore(earliest)) {
			earliest = photoDate;
			earliestString = earliest.format(formatter);
		}
		if (latest == null) {
			latest = photoDate;
			latestString = latest.format(formatter);
		}
		else if (photoDate.isAfter(latest) || latest == null) {
			latest = photoDate;
			latestString = latest.format(formatter);
		}

		photos.add(photo);
		photoCount++;
	}
	
	/**
	 * deletes a photo from the album
	 * @param photo the photo to be deleted
	 * @return void 
	 */
	public void deletePhoto(Photo photo) {
		LocalDateTime photoDate = photo.getDate();
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
		if (photos.size() == 1) {
			earliest = null;
			latest = null;
		} else if (photoDate == earliest) {
			photos.remove(photo);
			earliest = photos.get(0).date;
			earliestString = earliest.format(formatter);
			for (Photo item:photos) {
				if (item.date.isBefore(earliest)) {
					earliest = item.date;
					earliestString = earliest.format(formatter);
				}
			}
		} else if (photoDate == latest) {
			photos.remove(photo);
			latest = photos.get(0).date;
			latestString = latest.format(formatter);
			for (Photo item:photos) {
				if (item.date.isAfter(latest)) {
					latest = item.date;
					latestString = latest.format(formatter);
				}
			}
		}
		
		photos.remove(photo);
		photoCount--;
	}
	
	// getters
	/**
	 * returns the album name
	 * @return String this is the album name 
	 */
	public String getName() {
		return name;
	}
	/**
	 * returns the ealiest photo in the album by date taken
	 * @return LocalDateTime this is the earliest date in the album
	 */
	public LocalDateTime getEarliest() {
		return earliest;
	}
	/**
	 * returns the ealiest photo with date in string format
	 * @return String this is the earliest date in the album
	 */
	public String  getEarliestString() {
		return earliestString;
	}
	/**
	 * returns the latest photo in the album by date taken
	 * @return LocalDateTime this is the latest date in the album
	 */
	public LocalDateTime getLatest() {
		return latest;
	}

	/**
	 * returns the latest photo with date in string format
	 * @return LocalDateTime this is the latest date in the album
	 */ 
	public String getLatestString() {
		return latestString;
	}
	/**
	 * returns the amount of photos in the album
	 * @return LocalDateTime this is the amount of photos
	 */
	public int getPhotoCount() {
		return photoCount;
	}
	/**
	 * returns the photos
	 * @return ArrayList<Photo> this holds the photos
	 */
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	/**
	 * returns the owner
	 * @return User this is the owner
	 */
	public User getOwner() {
		return owner;
	}
	
	//setters
	/**
	 * sets the name of the album
	 * @return User this is the owner
	 */
	void setName(String name) {
		this.name = name;
	}
	
	public Photo getNextPhoto(Photo photo) {
		int index = photos.indexOf(photo);
		
		if (index == -1) {
			return null;
		}
		
		if (index < photos.size()-1) {
			return photos.get(index+1);
		} else if (index == photos.size()-1) {
			return photos.get(0);
		}
		
		return null;
	}
	
	public Photo getPrevPhoto(Photo photo) {
		int index = photos.indexOf(photo);
		
		if (index == -1)
			return null;
		
		if (index > 0) {
			return photos.get(index-1);
		} else if (index == 0) {
			return photos.get(photos.size()-1);
		}
		
		return null;
	}
	
	public String toString() {
		if (earliest == null)
			return name+"     "+photoCount+"     ";
		return name+"     "+photoCount+"     "+earliestString+" - "+latestString;
				
	}
	
}
