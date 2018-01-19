/*
 * @(#)ProductPageModel.java			1 Apr 2013
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
package com.groovyfly.controlcentre.client.productmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.groovyfly.controlcentre.service.sitemanagement.CategoryServiceIF;
import com.groovyfly.controlcentre.service.sitemanagement.ProductServiceIF;
import com.groovyfly.controlcentre.service.sitemanagement.SupplierServiceIF;
import com.groovyfly.controlcentre.structure.Category;
import com.groovyfly.controlcentre.structure.Product;
import com.groovyfly.controlcentre.structure.Supplier;

/**
 * @author Chris Hatton
 */
@Component("ProductPageModel")
@Scope("prototype")
public class ProductPageModel {
	
	@Autowired
	private SupplierServiceIF supplierServiceIF;
	
	@Autowired
	private CategoryServiceIF categoryServiceIF;
	
	@Autowired
	private ProductServiceIF productServiceIF;
	

	/**
	 * @return
	 */
    public List<Supplier> getSuppliers() throws Exception {
    	List<Supplier> suppliers = supplierServiceIF.getSuppliers(false);
    	suppliers.add(0, null);
    	
	    return suppliers;
    }

	/**
	 * @return
	 */
    public List<Category> getCategories() throws Exception {
    	List<Category> categories = categoryServiceIF.getCategories(false);
    	categories.add(0, null);
    	
	    return categories;
    }

    /**
     * 
     * @param productSearchParam
     * @return
     * @throws Exception
     */
    public List<Product> getProducts(ProductSearchParam productSearchParam) throws Exception {
    	List<Product> products = productServiceIF.getProducts(productSearchParam);
    	
    	return products;
    }

}
