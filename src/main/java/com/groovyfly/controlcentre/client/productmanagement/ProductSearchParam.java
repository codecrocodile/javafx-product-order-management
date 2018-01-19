/*
 * @(#)ProductSearchParam.java			1 Apr 2013
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

import com.groovyfly.controlcentre.customcontrols.ComparisionButton;
import com.groovyfly.controlcentre.structure.Category;
import com.groovyfly.controlcentre.structure.Supplier;

/**
 * @author Chris Hatton
 */
public class ProductSearchParam {
	
	private String name;
	
	private ComparisionButton.Operator operator;
	
	private int stockLevel;
	
	private Category category;
	
	private Supplier supplier;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ComparisionButton.Operator getOperator() {
		return operator;
	}

	public void setOperator(ComparisionButton.Operator operator) {
		this.operator = operator;
	}

	public int getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
}
