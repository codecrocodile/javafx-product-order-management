/*
 * Copyright GroovyFly.com PaypalTransactionValues.java
 */
package com.groovyfly.controlcentre.structure.common;

import java.util.Date;

/**
 * @author Chris Hatton
 */
public class PaypalTransactionValues {

	private String paymentProcessor;

	private String paypalPayerId;

	private String paypalTransationId;

	private Date paypalPaymentDate;

	private String paypalPaymentStatus;

	private String payaplPendingReason;
	
	/**
     * Constructor
     */
    public PaypalTransactionValues() {
    	super();
    }

	public String getPaymentProcessor() {
		return paymentProcessor;
	}

	public void setPaymentProcessor(String paymentProcessor) {
		this.paymentProcessor = paymentProcessor;
	}

	public String getPaypalPayerId() {
		return paypalPayerId;
	}

	public void setPaypalPayerId(String paypalPayerId) {
		this.paypalPayerId = paypalPayerId;
	}

	public String getPaypalTransationId() {
		return paypalTransationId;
	}

	public void setPaypalTransationId(String paypalTransationId) {
		this.paypalTransationId = paypalTransationId;
	}

	public Date getPaypalPaymentDate() {
		return paypalPaymentDate;
	}

	public void setPaypalPaymentDate(Date paypalPaymentDate) {
		this.paypalPaymentDate = paypalPaymentDate;
	}

	public String getPaypalPaymentStatus() {
		return paypalPaymentStatus;
	}

	public void setPaypalPaymentStatus(String paypalPaymentStatus) {
		this.paypalPaymentStatus = paypalPaymentStatus;
	}

	public String getPayaplPendingReason() {
		return payaplPendingReason;
	}

	public void setPayaplPendingReason(String payaplPendingReason) {
		this.payaplPendingReason = payaplPendingReason;
	}

}
