/*
 * @(#)Product.java			31 Mar 2013
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

import java.math.BigDecimal;

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
@Table(name="product")
public class Product {
	  
	@Id
	@Column(name="productId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int productId;
    
	@Column(name="urlAlias")
    private String urlAlias;
    
    /** this is for the attribute configuration i.e. configurable / non-configurable */
	@Column(name="groupingConfigId")
	@Transient
    private int productGroupingConfigId;
    
//    private int attributeId1;
//    
//    private int attributeId2;
//    
//    private int attributeId3;
//    
//    private int attributeValueId1;
//    
//    private int attributeValueId2;
//    
//    private int attributeValueId3;
    
//    private ProductAttribute attribute1;
    
//    private ProductAttribute attribute2;
    
//    private ProductAttribute attribute3;
    
	@Column(name="name")
    private String name;
    
//    private int categoryId;
    
	@Column(name="description")
    private String description;
    
//    private ProductImages images = new ProductImages();
    
	@OneToOne
	@JoinColumn(name="supplierId")
    private Supplier supplier;
    
	@Column(name="sku")
    private String sku;
    
	@Column(name="price")
    private BigDecimal price = new BigDecimal(0.00);
    
	@Column(name="priceRuleId")
	@Transient
    private int priceRuleId;
    
	@Column(name="vatRateId")
	@Transient
    private int vatRuleId;
    
	@Column(name="storageLocation")
    private String storageLocation;
    
	@Column(name="stockLevel")
	@Transient
    private int stockLevel;
    
	@Column(name="productStatusId")
	@Transient
    private int statusId;
    
	@Column(name="averageStarRating")
    private byte averageStarRating;
    
	@OneToOne
	@JoinColumn(name="pageId")
    private WebPage webPage = new WebPage();
    
//    private int inNoOfGroupings;
    
    @Column(name="retired")
    private boolean retired;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getUrlAlias() {
		return urlAlias;
	}

	public void setUrlAlias(String urlAlias) {
		this.urlAlias = urlAlias;
	}

	public int getProductGroupingConfigId() {
		return productGroupingConfigId;
	}

	public void setProductGroupingConfigId(int productGroupingConfigId) {
		this.productGroupingConfigId = productGroupingConfigId;
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getPriceRuleId() {
		return priceRuleId;
	}

	public void setPriceRuleId(int priceRuleId) {
		this.priceRuleId = priceRuleId;
	}

	public int getVatRuleId() {
		return vatRuleId;
	}

	public void setVatRuleId(int vatRuleId) {
		this.vatRuleId = vatRuleId;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public int getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public byte getAverageStarRating() {
		return averageStarRating;
	}

	public void setAverageStarRating(byte averageStarRating) {
		this.averageStarRating = averageStarRating;
	}

	public WebPage getWebPage() {
		return webPage;
	}

	public void setWebPage(WebPage webPage) {
		this.webPage = webPage;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}
    
}
