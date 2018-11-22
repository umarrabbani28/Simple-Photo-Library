package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	ArrayList<String> values;
	static final String[] singleValueNames = {"location"};
	String singleValue;
	boolean isSingleValue;
	
	public Tag(String name, ArrayList<String> values) {
		this.name = name; this.values = values; this.isSingleValue = false;
		
	}
	
	public Tag(String name, String singleValue) {
		values = new ArrayList<>();
		this.name = name; this.singleValue = singleValue; this.isSingleValue = true;
		this.values.add(singleValue);
	}
	//getters
	public String getName() {return name;}
	public ArrayList<String> getValues() {return values;}
	public String getSingleValue() {return singleValue;}
	public boolean isSingleTag() {return isSingleValue;}
	public void setSingleTag(boolean value) {this.isSingleValue = value;}
	public String[] getSingleValueNames() {return singleValueNames;}
	
	/*//setters
	public boolean setName(String name) {
		// going from single value tag (location) to multi-value tag
		// need to put singleValue into values
		if (this.isSingleValue && !isASingleValueName(name)) {
			isSingleValue = false;
			values = new ArrayList<String>();
			values.add(singleValue);
		} else if (!this.isSingleValue && isASingleValueName(name)) {
			isSingleValue = true;
			singleValue = values.get(0);
			values = new ArrayList<String>();
		}
		
		this.name = name;
		
		
		return true;
	}
	public boolean setValues(ArrayList<String> values) {
		this.values = values;
		return false;
	}
	public boolean setValue(String singleValue) {
		this.singleValue = singleValue;
		return false;
	}*/
	
	public static boolean isASingleValueName(String name) {
		for (String item:singleValueNames) {
			if (name.equals(item))
				return true;
		}
		return false;
	}
	
	
}
