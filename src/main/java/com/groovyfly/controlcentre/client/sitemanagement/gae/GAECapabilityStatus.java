/*
 * @(#)CapabilityStatus.java			25 Mar 2013
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
package com.groovyfly.controlcentre.client.sitemanagement.gae;

import java.util.Date;

import com.google.appengine.api.capabilities.CapabilityStatus;

/**
 * @author Chris Hatton
 */
public class GAECapabilityStatus {

	private String capability;
	 
	private Date scheduledDate;
	 
	private CapabilityStatus status;

	/**
	 * Constructor
	 */
    public GAECapabilityStatus(String capability, Date scheduledDate, CapabilityStatus status) {
		this.capability = capability;
		this.scheduledDate = scheduledDate;
		this.status = status;
    }

	public String getCapability() {
		return capability;
	}

	public void setCapability(String capability) {
		this.capability = capability;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public CapabilityStatus getStatus() {
		return status;
	}

	public void setStatus(CapabilityStatus status) {
		this.status = status;
	}
	
}
