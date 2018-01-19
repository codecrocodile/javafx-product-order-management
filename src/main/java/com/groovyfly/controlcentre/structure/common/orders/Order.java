/*
 * Copyright GroovyFly.com Order.java
 */
package com.groovyfly.controlcentre.structure.common.orders;

import java.util.ArrayList;
import java.util.List;

import com.groovyfly.controlcentre.structure.common.Customer;
import com.groovyfly.controlcentre.structure.common.PaypalTransactionValues;
import com.groovyfly.controlcentre.structure.common.ShippingAddress;

/**
 * @author Chris Hatton
 */
public class Order extends OrderSummary {
	
    private Customer customer;
    
    private ShippingAddress shippingAddress;

    private PaypalTransactionValues paypalTransactionValues;
    
	private List<OrderItem> orderItems = new ArrayList<>();
	
	/**
     * Constructor
     */
    public Order() {
    	super();
    }
    
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public PaypalTransactionValues getPaypalTransactionValues() {
		return paypalTransactionValues;
	}

	public void setPaypalTransactionValues(PaypalTransactionValues paypalTransactionValues) {
		this.paypalTransactionValues = paypalTransactionValues;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
