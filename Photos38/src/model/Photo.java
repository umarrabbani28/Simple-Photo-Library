package model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.image.Image;

public class Photo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Image image;
	String location;
	Date date;
	String caption;
	
	ArrayList<Album> albums;
	ArrayList<Tag> tags;
	
	public Photo(Image image,Date date,String location,Album album,String caption) {
		this.image = image; this.location = location; this.date = date; this.albums.set(0, album); this.caption = caption;
	}
	
	public boolean addTag(Tag tag) {
		if (!isTagRepeating(tag)) {
			tags.add(tag);
			return true;
		}
		return false;
	}
	public void deleteTag(Tag tag) {
		tags.remove(tag);
	}
	boolean isTagRepeating(Tag tag) {
		for (Tag item:tags) {
			if (tag.isSingleValue && item.isSingleValue && tag.getName().equals(item.getName()) && tag.getSingleValue().equals(item.getSingleValue())) {
				return true;
			}
			if (!tag.isSingleValue && !item.isSingleValue && tag.getName().equals(item.getName()) && tag.getValues().equals(item.getValues())) {
				return true;
			}
		}
		return false;
	}
	
	// getters
	public ArrayList<Album> getAlbums() { return albums;}
	public Date getDate() { return date;}
	public String getCaption() {return caption;}
	public String getLocation() {return location;}
	public ArrayList<Tag> getTags(){return tags;}
	public Image getImage() {return image;}
	
	//setters
	public void reCaption(String caption) {
		this.caption = caption;
	}
	
	public void copyTo(Album album) {
		this.albums.add(album);
		album.addPhoto(this);
	}
	
	public void moveTo(Album moveTo,Album moveFrom) {
		this.albums.remove(moveFrom);
		this.albums.add(moveTo);
		moveTo.addPhoto(this);
		moveFrom.deletePhoto(this);
		
	}
	
	
}
