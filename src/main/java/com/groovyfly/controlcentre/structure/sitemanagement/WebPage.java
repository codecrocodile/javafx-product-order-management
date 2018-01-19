/*
 * @(#)Page.java			22 Mar 2013
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
package com.groovyfly.controlcentre.structure.sitemanagement;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * This is a page that could be displayed on the website.
 *  
 * @author Chris Hatton
 */
@Entity() // states class can be persisted
@Table(name="page") // state the name of the table to be mapped to if different from the class name
@Access(AccessType.FIELD) // assigns a default access method for the provider. we can later override this for certain methods 
public class WebPage {

	/** Primary key. */
	@Id
	@Column(name="pageId")
	@GeneratedValue(strategy=GenerationType.IDENTITY) // states that the DBMS will generate the primary key
    private int pageId;
    
    /** URL used to identify the page. */
	@Column(name="urlAlias")
    private String urlAlias;
    
    /** Title of the page. */
	@Column(name="title")
    private String title;
    
    /** Key words used to describe the page. */
	@Column(name="keywords")
    @Transient // because we are overriding the access method then this stops the property from being persisted twice i.e. @Transient tells provider to ignore this field
    private String metaKeywords;
    
    /** Description of the page. */
	@Column(name="description")
    private String metaDescription;
    
    /** Element of HTML that may be displayed on the page. */
	@Column(name="html")
    private String html; 
    
    /**
     * Constructor
     */
    public WebPage() {
    	super();
    }

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public String getUrlAlias() {
		return urlAlias;
	}

	public void setUrlAlias(String urlAlias) {
		this.urlAlias = urlAlias;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Access(AccessType.PROPERTY)
	@Column(name="keywords")
	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		System.out.println("setting key words");
		
		// we might want to do this if we wanted to parse the csv string to a list
		
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
    
}
