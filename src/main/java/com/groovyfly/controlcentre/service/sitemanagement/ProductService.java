/*
 * @(#)ProductService.java			1 Apr 2013
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

import com.groovyfly.controlcentre.client.productmanagement.ProductSearchParam;
import com.groovyfly.controlcentre.structure.Product;

/**
 * @author Chris Hatton
 */
@Service("ProductService")
@Repository
@Transactional
public class ProductService implements ProductServiceIF {

	
	@PersistenceContext
	private EntityManager em;
	
	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.ProductServiceIF
	 * 	#getProducts(com.groovyfly.controlcentre.client.productmanagement.ProductSearchParam)
	 */
	@Transactional(readOnly=true)
    @Override
    public List<Product> getProducts(ProductSearchParam productSearchParam) throws Exception {
    	String qry = "SELECT p FROM Product p";
    	TypedQuery<Product> allPagesQuery = em.createQuery(qry, Product.class);
    	List<Product> resultList = allPagesQuery.getResultList();
    	
	    return resultList;
    }

}
