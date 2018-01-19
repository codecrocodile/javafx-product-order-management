/*
 * @(#)DbUtilTest.java			30 Mar 2013
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
package com.groovyfly.controlcentre.client.sitemanagement.newsfeed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.groovyfly.controlcentre.service.sitemanagement.SiteManagementServiceIF;
import com.groovyfly.controlcentre.structure.sitemanagement.NewsFeedEntry;


/**
 * @author Chris Hatton
 */
public class NewsFeedPageModelTest extends AbstractControllerTest  {
	
	private SiteManagementServiceIF siteManagementServiceIF;

	private List<NewsFeedEntry> newsFeedEntries = new ArrayList<>();
	
	@Before
	public void initNewsFeedEntries() {
		NewsFeedEntry nfe = new NewsFeedEntry();
		nfe.setNewsFeedEntryId(100);
		nfe.setTitle("Test Title");
		nfe.setPublicationDate(new Date());
		nfe.setLink("http://www.codecrocodile.com");
		nfe.setContent("Test Content");
		nfe.setRetired(false);
		
		newsFeedEntries.add(nfe);
	}
	
    @Test
    public void testGetNewsFeedEntries() throws Exception {
    	siteManagementServiceIF = mock(SiteManagementServiceIF.class);
        when(siteManagementServiceIF.getNewsFeedEntries(null)).thenReturn(newsFeedEntries);

        NewsFeedPageModel nfpm = new NewsFeedPageModel();
        ReflectionTestUtils.setField(nfpm, "siteManagementServiceIF", siteManagementServiceIF);

        List<NewsFeedEntry> newsFeedEntriesResults = nfpm.getNewsFeedEntries(null);

        assertNotNull(newsFeedEntriesResults);
        assertEquals(1, newsFeedEntriesResults.size());
    }
	
}
