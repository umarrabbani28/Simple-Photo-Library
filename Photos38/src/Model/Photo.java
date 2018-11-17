package Model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public class Photo {
	BufferedImage image;
	String location;
	Date date;
	String caption;
	
	ArrayList<Album> albums;
	ArrayList<Tag> tags;
	
	public Photo(BufferedImage image,Date date,String location,Album album,String caption) {
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
	public BufferedImage getImage() {return image;}
	
	//setters
	public void reCaption(String caption) {
		this.caption = caption;
	}
	
	public void copyTo(Album album) {
		this.albums.add(album);
		album.addPhoto(this);
	}
	
}
