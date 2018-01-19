/*
 * Copyright GroovyFly.com Order.java
 */
package com.groovyfly.controlcentre.structure.common.orders;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Chris Hatton
 */
public class OrderSummary {

	private String orderId;

	private OrderStatus orderStatus;

	private String country;

	private BigDecimal subTotalAmount = new BigDecimal(0);;
	
    private BigDecimal vatAmount = new BigDecimal(0);
    
    private String discountCode;
    
    private BigDecimal discountAmount = new BigDecimal(0);

	private BigDecimal postageAndPackingAmount = new BigDecimal(0);;

	private BigDecimal totalAmount = new BigDecimal(0);;

	private Date dateCreated;

	/**
	 * Constructor
	 */
	public OrderSummary() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public BigDecimal getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(BigDecimal subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getPostageAndPackingAmount() {
		return postageAndPackingAmount;
	}

	public void setPostageAndPackingAmount(BigDecimal postageAndPackingAmount) {
		this.postageAndPackingAmount = postageAndPackingAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    OrderSummary other = (OrderSummary) obj;
	    if (orderId == null) {
		    if (other.orderId != null)
			    return false;
	    } else if (!orderId.equals(other.orderId))
		    return false;
	    return true;
    }
	
}
