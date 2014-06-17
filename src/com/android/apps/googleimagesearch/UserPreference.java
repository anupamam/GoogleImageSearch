package com.android.apps.googleimagesearch;

import java.io.Serializable;

public class UserPreference implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9047066750175205012L;
	private String size;
	private String colorFilter;
	private String type;
	private String siteFilter;
	public String EMPTY_STRING="";
	public String DEFAULT="default";
	
	public UserPreference(String size, String colorFilter, String type, String siteFilter){
		this.size = size;
		this.colorFilter = colorFilter;
		this.type = type;
		this.siteFilter = siteFilter;
		
	}
	
	public String getSize() {
		return mapImageSize(size);
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColorFilter() {
		if(colorFilter.trim() == DEFAULT)
			return EMPTY_STRING;
		return colorFilter;
	}
	public void setFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}
	public String getType() {
		if(type.trim() == DEFAULT)
			return EMPTY_STRING;
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSiteFilter() {
		return siteFilter;
	}
	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}
	
	public String mapImageSize(String imageSize){
		if(imageSize.trim() == DEFAULT)
			return EMPTY_STRING;
		else if(imageSize == "small" || imageSize == "medium" ||  imageSize == "large" || imageSize == "xlarge"){
			return "medium";
		}
		return imageSize;
	}
	

}
