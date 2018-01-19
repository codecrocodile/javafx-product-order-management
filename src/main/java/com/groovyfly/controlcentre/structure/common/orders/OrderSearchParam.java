/*
 * Copyright GroovyFly.com OrderSearchParam.java
 */
package com.groovyfly.controlcentre.structure.common.orders;

import java.util.Date;
import java.util.List;

/**
 * @author Chris Hatton
 */
public class OrderSearchParam {
	
	private String orderId;
	
	private String emailAddress;
	
	private Date createdAfter;
	
	private Date createdBefore;
	
	private List<OrderStatus> orderStatuses;
	
	/**
     * Constructor
     */
    public OrderSearchParam() {
    	super();
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getCreatedAfter() {
		return createdAfter;
	}

	public void setCreatedAfter(Date createdAfter) {
		this.createdAfter = createdAfter;
	}

	public Date getCreatedBefore() {
		return createdBefore;
	}

	public void setCreatedBefore(Date createdBefore) {
		this.createdBefore = createdBefore;
	}

	public List<OrderStatus> getOrderStatuses() {
		return orderStatuses;
	}

	public void setOrderStatuses(List<OrderStatus> orderStatuses) {
		this.orderStatuses = orderStatuses;
	}
    
}
