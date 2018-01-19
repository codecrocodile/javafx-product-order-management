/*
 * @(#)CategoryService.java			1 Apr 2013
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groovyfly.controlcentre.structure.Category;

/**
 * @author Chris Hatton
 */
@Service("CategoryService")  
@Repository 
@Transactional
public class CategoryService implements CategoryServiceIF {
    
	@PersistenceContext
    private EntityManager em;

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#getCategories(boolean)
	 */
	@Transactional(readOnly=true)
    @Override
    public List<Category> getCategories(boolean includeRetired) throws Exception {
    	String qry = "SELECT c FROM Category c WHERE c.categoryId <> 1";
    	TypedQuery<Category> allPagesQuery = em.createQuery(qry, Category.class);
    	List<Category> resultList = allPagesQuery.getResultList();
    	
	    return resultList;
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#getCategory(int)
	 */
    @Override
    public Category getCategory(int categoryId) throws Exception {
	    return null;
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#getCategoryForPageId(int)
	 */
    @Override
    public Category getCategoryForPageId(int pageId) throws Exception {
	    return null;
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#insertCategory(com.groovyfly.controlcentre.structure.Category)
	 */
    @Override
    public void insertCategory(Category category) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#updateCategory(com.groovyfly.controlcentre.structure.Category)
	 */
    @Override
    public void updateCategory(Category category) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#deleteCategory(int)
	 */
    @Override
    public void deleteCategory(int id) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#moveUpIndex(int)
	 */
    @Override
    public void moveUpIndex(int categoryId) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#moveDownIndex(int)
	 */
    @Override
    public void moveDownIndex(int categoryId) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#getProductCountInCategory(int)
	 */
    @Override
    public int getProductCountInCategory(int categoryId) throws Exception {
	    return 0;
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#getCategoryCountInCategory(int)
	 */
    @Override
    public int getCategoryCountInCategory(int categoryId) throws Exception {
	    return 0;
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF#isUrlAliasUnique(java.lang.String, com.groovyfly.controlcentre.structure.Category)
	 */
    @Override
    public boolean isUrlAliasUnique(String urlAlias, Category category) throws Exception {
	    return false;
    }

}
