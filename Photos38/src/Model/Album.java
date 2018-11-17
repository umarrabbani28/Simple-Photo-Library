package Model;

import java.util.ArrayList;
import java.util.Date;

public class Album {
	String name;
	Date earliest;
	Date latest;
	int photoCount;
	User owner;
	ArrayList<Photo> photos;
	
	public Album(String name, User owner) {
		this.name = name; this.owner = owner;
	}
	
	public void addPhoto(Photo photo) {
		Date photoDate = photo.getDate();
		if (photoDate.before(earliest) || earliest == null) {
			earliest = photoDate;
		}
		if (photoDate.after(latest) || latest == null) {
			latest = photoDate;
		}
		
		photos.add(photo);
		photoCount++;
	}
	
	public void deletePhoto(Photo photo) {
		Date photoDate = photo.getDate();
		if (photos.size() == 1) {
			earliest = null;
			latest = null;
		} else if (photoDate == earliest) {
			photos.remove(photo);
			earliest = photos.get(0).date;
			for (Photo item:photos) {
				if (item.date.before(earliest))
					earliest = item.date;
			}
		} else if (photoDate == latest) {
			photos.remove(photo);
			latest = photos.get(0).date;
			for (Photo item:photos) {
				if (item.date.after(latest))
					latest = item.date;
			}
		}
		
		photos.remove(photo);
		photoCount--;
	}
	
	// getters
	public String getName() {
		return name;
	}
	
	public Date getEarliest() {
		return earliest;
	}
	
	public Date latest() {
		return latest;
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
	
	
}
