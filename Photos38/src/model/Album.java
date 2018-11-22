package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Album implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	LocalDateTime earliest;
	String earliestString;
	LocalDateTime latest;
	String latestString;
	int photoCount;
	User owner;
	ArrayList<Photo> photos;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
	
	public Album(String name, User owner) {
		this.name = name; this.owner = owner;
		photos = new ArrayList<>();
	}
	
	public void addPhoto(Photo photo) {
		LocalDateTime photoDate = photo.getDate();
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
	
	public void deletePhoto(Photo photo) {
		LocalDateTime photoDate = photo.getDate();
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
	public String getName() {
		return name;
	}
	
	public LocalDateTime getEarliest() {
		return earliest;
	}
	
	public String  getEarliestString() {
		return earliestString;
	}
	
	public LocalDateTime getLatest() {
		return latest;
	}
	
	public String getLatestString() {
		return latestString;
	}
	
	public int getPhotoCount() {
		return photoCount;
	}
	
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	
	public User getOwner() {
		return owner;
	}
	
	//setters
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
