/*
 * Copyright GroovyFly.com SiteManagementIF.java
 */
package com.groovyfly.controlcentre.service.sitemanagement;

import java.util.List;

import com.groovyfly.controlcentre.client.sitemanagement.newsfeed.NewsFeedEntrySearchParam;
import com.groovyfly.controlcentre.client.sitemanagement.pages.WebPageSearchParam;
import com.groovyfly.controlcentre.structure.sitemanagement.NewsFeedEntry;
import com.groovyfly.controlcentre.structure.sitemanagement.WebPage;

/**
 * @author Chris Hatton
 */
public interface SiteManagementServiceIF {
	
	public List<NewsFeedEntry> getNewsFeedEntries(NewsFeedEntrySearchParam newsFeedEntrySearchParam) throws Exception;
	
	public void saveNewsFeedEntries(List<NewsFeedEntry> newsFeedEntries) throws Exception;
	
	/**
	 * Gets a list of pages that may be displayed on the website.
	 * 
	 * @param pageSearchParam
	 * 		- search parameters.
	 * @return List<Page> - list of pages that match the search criteria.
	 * @throws Exception
	 */
	public List<WebPage> getWebPages(WebPageSearchParam pageSearchParam) throws Exception;
	
	/**
	 * Saves a page that may be displayed on the website.
	 * 
	 * @param page
	 * 		- the website page to be saved.
	 * @throws Exception
	 */
	public void saveWebPage(WebPage page) throws Exception;
	
}
