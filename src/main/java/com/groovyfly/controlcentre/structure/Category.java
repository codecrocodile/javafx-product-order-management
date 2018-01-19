/*
 * @(#)Category.java			31 Mar 2013
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
package com.groovyfly.controlcentre.structure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.groovyfly.controlcentre.structure.sitemanagement.WebPage;

/**
 * @author Chris Hatton
 */
@Entity
@Table(name="category")
public class Category {

	@Id
	@Column(name="categoryId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int categoryId;

	@Column(name="parentCategoryId")
	@Transient
    private int parentId;
    
	@Column(name="urlAlias")
    private String urlAlias;
    
	@Column(name="name")
    private String name;

	@Column(name="description")
    private String description;

	@Transient
    private String path;

	@Column(name="sortIndex")
    private int sortIndex;

	@Transient
    private boolean firstInCategory;

	@Transient
    private boolean lastInCategory;

	@Column(name="retired")
    private boolean retired;
    
	@Transient
    private int productCountInCategory;
    
	@OneToOne
	@JoinColumn(name="pageId")
    private WebPage webPage = new WebPage();
    
	@Transient
    private List<Category> subCategories = new ArrayList<Category>();

    /**
     * Constructor
     */
    public Category() {
        super();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int id) {
        this.categoryId = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    
    public String getUrlAlias() {
        return urlAlias;
    }

    public void setUrlAlias(String urlAlias) {
        this.urlAlias = urlAlias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public boolean isFirstInCategory() {
        return firstInCategory;
    }

    public void setFirstInCategory(boolean firstInCategory) {
        this.firstInCategory = firstInCategory;
    }

    public boolean isLastInCategory() {
        return lastInCategory;
    }

    public void setLastInCategory(boolean lastInCategory) {
        this.lastInCategory = lastInCategory;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }
    
    public int getProductCountInCategory() {
        return productCountInCategory;
    }

    public void setProductCountInCategory(int productCountInCategory) {
        this.productCountInCategory = productCountInCategory;
    }

    public WebPage getWebPage() {
		return webPage;
	}

	public void setWebPage(WebPage webPage) {
		this.webPage = webPage;
	}

	public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
    
    /* 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category) {
            Category other = (Category) obj;
            if (this.categoryId == other.categoryId) {
                return true;
            }
        }
        
        return false;
    }
    
    /* 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.getCategoryId();
    }

	@Override
    public String toString() {
	    return this.name;
    }
    
    
}