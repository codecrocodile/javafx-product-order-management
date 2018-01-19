/*
 * @(#)PageManagementPageModel.java			22 Mar 2013
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
package com.groovyfly.controlcentre.client.sitemanagement.pages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.groovyfly.controlcentre.service.sitemanagement.SiteManagementServiceIF;
import com.groovyfly.controlcentre.structure.sitemanagement.WebPage;

/**
 * @author Chris Hatton
 */
@Component("WebPageManagementPageModel")
@Scope("prototype")
public class WebPageManagementPageModel {
	
	@Autowired
	private SiteManagementServiceIF siteManagementServiceIF;
	
	private List<WebPage> webPages;
	

	/**
	 * 
	 * @param pageSearchParam
	 * @return
	 * @throws Exception
	 */
	List<WebPage> getWebPages(WebPageSearchParam pageSearchParam) throws Exception {
		if (pageSearchParam != null) {
			webPages = siteManagementServiceIF.getWebPages(null);
		}
		
		return webPages;
	}
	 

}
