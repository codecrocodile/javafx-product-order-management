/*
 * Copyright GroovyFly.com NewsFeedentrySearchParam.java
 */
package com.groovyfly.controlcentre.client.sitemanagement.newsfeed;

import java.util.Date;

/**
 * @author Chris Hatton
 */
public class NewsFeedEntrySearchParam {

	private Date afterDate;
	
	private Date beforeDate;
	
	private boolean showRetired;

	public Date getAfterDate() {
		return afterDate;
	}

	public void setAfterDate(Date afterDate) {
		this.afterDate = afterDate;
	}

	public Date getBeforeDate() {
		return beforeDate;
	}

	public void setBeforeDate(Date beforeDate) {
		this.beforeDate = beforeDate;
	}

	public boolean isShowRetired() {
		return showRetired;
	}

	public void setShowRetired(boolean showRetired) {
		this.showRetired = showRetired;
	}
	
}
