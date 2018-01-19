/*
 * @(#)DatastoreItem.java			29 Mar 2013
 *
 * Copyright (c) 2012-2013 Groovy Fly.
 * 3 Aillort place, East Mains, East Kilbride, Scotland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Groovy 
 * Fly. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Groovy Fly.
 */
package com.groovyfly.controlcentre.client.sitemanagement.gae;

import com.google.appengine.api.blobstore.BlobInfo;

/**
 * @author Chris Hatton
 */
public class BlobstoreItem {
	
	private BlobInfo blobInfo;
	
	private boolean isUsed;
	
	private String servingUrl;

	private boolean isImage;
	
	/**
     * Constructor
     */
    public BlobstoreItem() {
    	super();
    }

	/**
	 * Constructor
	 */
    public BlobstoreItem(BlobInfo blobInfo, String servingUrl, boolean isImage, boolean isUsed) {
	    super();
	    this.blobInfo = blobInfo;
	    this.servingUrl = servingUrl;
		this.isImage = isImage;
	    this.isUsed = isUsed;
    }

	public BlobInfo getBlobInfo() {
		return blobInfo;
	}

	public void setBlobInfo(BlobInfo blobInfo) {
		this.blobInfo = blobInfo;
	}
	

	public String getServingUrl() {
		return servingUrl;
	}

	public void setServingUrl(String servingUrl) {
		this.servingUrl = servingUrl;
	}

	public boolean isImage() {
		return isImage;
	}

	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

}
