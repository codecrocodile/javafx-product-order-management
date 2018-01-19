/*
 * @(#)DatastoreViewerPageModel.java			24 Mar 2013
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import com.groovyfly.controlcentre.client.config.GoogleAppEngineProperties;

/**
 * @author Chris Hatton
 */
@Component("DatastoreViewerPageModel")
@Scope("prototype")
public class BlobstoreViewerPageModel {
	
	@Autowired
	private GoogleAppEngineProperties googleAppEngineProperties;
	
	public static final int DEFUALT_IMAGES_PER_PAGE = 15;
	
	private int itemsPerPage = DEFUALT_IMAGES_PER_PAGE;
	
	private int pagesToDisplay;
	
	private Set<String> supportedImageMimeTypes = new HashSet<>();
	
	private Set<String> servingImageUrlsUsed = new HashSet<>();
	
	private List<BlobstoreItem> blobstoreItems;
	
	private int currentPageIndex;
	
	/**
     * Constructor
     */
    public BlobstoreViewerPageModel() {
    	supportedImageMimeTypes.add("image/jpeg");
    	supportedImageMimeTypes.add("image/gif");
    	supportedImageMimeTypes.add("image/png");
    }
    
    
    
    public int getCurrentPageIndex() {
		return currentPageIndex;
	}



	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}



	/**
     * 
     * @throws Exception
     */
    public void initModel() throws Exception {
    	if (blobstoreItems == null) {
    		blobstoreItems = getAllBlobstoreItems();
    	}
    	
    	if (blobstoreItems.size() == 0) {
    		pagesToDisplay = 0;
    	} else {
    		pagesToDisplay = (blobstoreItems.size() / DEFUALT_IMAGES_PER_PAGE) + 1;
    	}
    	
    }
    
    /**
     * 
     * @return
     */
	public int getPagesToDisplay() {
		return pagesToDisplay;
	}

	/*
	 * 
	 */
	private List<BlobstoreItem> getAllBlobstoreItems() throws Exception {
		getServingImageUrlsUsed();
		 
		List<BlobstoreItem> items = new ArrayList<>();

		RemoteApiOptions options = new RemoteApiOptions()
		        .server(googleAppEngineProperties.getServer(), googleAppEngineProperties.getPort())
		        .credentials(googleAppEngineProperties.getUsername(), googleAppEngineProperties.getUsername());
		RemoteApiInstaller installer = new RemoteApiInstaller();
		installer.install(options);
		
		try {
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
			Iterator<BlobInfo> queryBlobInfos = blobInfoFactory.queryBlobInfos();

			BlobstoreItem newBlobstoreItem = null;
			while (queryBlobInfos.hasNext()) {
				BlobInfo blobInfo = queryBlobInfos.next();
				
				for (int i = 0; i < 10; i++) {
					
					
					newBlobstoreItem = new BlobstoreItem();
					newBlobstoreItem.setBlobInfo(blobInfo);
					
					if (supportedImageMimeTypes.contains(blobInfo.getContentType())) {
						ServingUrlOptions withBlobKey = ServingUrlOptions.Builder.withBlobKey(blobInfo.getBlobKey());
						String servingUrl = imagesService.getServingUrl(withBlobKey);
						
						newBlobstoreItem.setImage(true);
						newBlobstoreItem.setServingUrl(servingUrl);
						
						if (servingImageUrlsUsed.contains(servingUrl)) {
							newBlobstoreItem.setUsed(true);
						}
					}
					
					items.add(newBlobstoreItem);
				}

			}
		} finally {
			installer.uninstall();
		}
		
		return items;
	}

	private void getServingImageUrlsUsed() throws Exception {

	}
	
	/**
	 * 
	 * @param pageIndex
	 * @return
	 */
	public List<BlobstoreItem> getBlobstorePageItems(int pageIndex) {
		
		System.out.println("request page index " + pageIndex);
		
		int startIndex = pageIndex * itemsPerPage;
		int endIndex = startIndex + itemsPerPage;
		
		BlobstoreItem[] array = blobstoreItems.toArray(new BlobstoreItem[0]);
		
		if (array.length < endIndex) {
			endIndex = array.length;
		}
		
		System.out.println("bsi = " + array.length);
		System.out.println("s = " + startIndex);
		System.out.println("e = " + endIndex);
		
		List<BlobstoreItem> resultList = new ArrayList<>();
		for (int i = startIndex; i < endIndex; i++) {
			resultList.add(array[i]);
		}
		
		return resultList;
	}

	public Map<String, BlobstoreItem> getBlobstoreItemsByMime() throws Exception {
		return null;
	}

}
