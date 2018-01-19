/*
 * @(#)ProductServiceIF.java			1 Apr 2013
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

import com.groovyfly.controlcentre.client.productmanagement.ProductSearchParam;
import com.groovyfly.controlcentre.structure.Product;

/**
 * @author Chris Hatton
 */
public interface ProductServiceIF {
	
	
	public List<Product> getProducts(ProductSearchParam productSearchParam) throws Exception;

}
