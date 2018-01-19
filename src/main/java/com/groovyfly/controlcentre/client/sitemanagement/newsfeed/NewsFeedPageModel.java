/*
 * Copyright GroovyFly.com NewsFeedPageModel.java
 */
package com.groovyfly.controlcentre.client.sitemanagement.newsfeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.groovyfly.controlcentre.service.sitemanagement.SiteManagementServiceIF;
import com.groovyfly.controlcentre.structure.sitemanagement.NewsFeedEntry;

/**
 * @author Chris Hatton
 */
@Component("NewsFeedPageModel")
@Scope("prototype")
public class NewsFeedPageModel {
	
	@Autowired
	public SiteManagementServiceIF siteManagementServiceIF;

	private List<NewsFeedEntry> newsFeedEntries = new ArrayList<>();
	
	/**
     * Constructor
     */
    public NewsFeedPageModel() {
    
    }
    
    public List<NewsFeedEntry> getNewsFeedEntries(NewsFeedEntrySearchParam newsFeedSearchParam) throws Exception {
    	newsFeedEntries = siteManagementServiceIF.getNewsFeedEntries(newsFeedSearchParam);
    	sortNewsFeedEntries();
    	
    	return newsFeedEntries;
    }
    
    public List<NewsFeedEntry> getOriginalNewsFeedEntries() throws Exception {
    	
    	List<NewsFeedEntry> toRemove = new ArrayList<>();
    	for (NewsFeedEntry e : newsFeedEntries) {
    		if (e.getNewsFeedEntryId() == 0) {
    			toRemove.add(e);
    		}
    	}
    	for (NewsFeedEntry e : toRemove) {
    		newsFeedEntries.remove(e);
    	}
    	
    	sortNewsFeedEntries();
    	
    	return newsFeedEntries;
    }
    
    
    private void sortNewsFeedEntries() {
    	Collections.sort(newsFeedEntries, new Comparator<NewsFeedEntry>() {

			@Override
            public int compare(NewsFeedEntry o1, NewsFeedEntry o2) {
				
				if (o1.getPublicationDate().compareTo(o2.getPublicationDate()) > 0) {
					return -1;
				} else if (o1.getPublicationDate().compareTo(o2.getPublicationDate()) < 0) {
					return 1;
				} else {
					return 0;	
				}
	            
            }
		
    	});
    }
    
    public List<NewsFeedEntry> addNewsFeedEntry(NewsFeedEntry newsFeedEntry) {
    	
    	newsFeedEntries.add(newsFeedEntry);
    	sortNewsFeedEntries();
    	
    	return newsFeedEntries;
    }


    public void saveNewsFeedEntries() throws Exception {
    	siteManagementServiceIF.saveNewsFeedEntries(newsFeedEntries);
    }
    
    public void cancelAllChanges() throws Exception {
    	System.out.println("cancelAllChanges()");
    }
}
