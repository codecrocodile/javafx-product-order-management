/*
 * @(#)CategoryServiceIF.java			1 Apr 2013
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
package com.groovyfly.controlcentre.service.sitemanagement;

import java.util.List;

import com.groovyfly.controlcentre.structure.Category;

/**
 * @author Chris Hatton
 */
public interface CategoryServiceIF {

    /**
     * Gets all the category nodes from the category tree.
     * 
     * We will need these as a list when adding a new category so that we can
     * select a parent category node for the new category position in the tree.
     */
    public List<Category> getCategories(boolean includeRetired) throws Exception;

    public Category getCategory(int categoryId) throws Exception;
    
    public Category getCategoryForPageId(int pageId) throws Exception;

    public void insertCategory(Category category) throws Exception;

    public void updateCategory(Category category) throws Exception;

    public void deleteCategory(int id) throws Exception;

    public void moveUpIndex(int categoryId) throws Exception;

    public void moveDownIndex(int categoryId) throws Exception;

    public int getProductCountInCategory(int categoryId) throws Exception;

    public int getCategoryCountInCategory(int categoryId) throws Exception;
    
    public boolean isUrlAliasUnique(String urlAlias, Category category) throws Exception;
    
}