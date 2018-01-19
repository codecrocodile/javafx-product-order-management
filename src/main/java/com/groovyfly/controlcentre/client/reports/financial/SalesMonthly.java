/*
 * @(#)SalesMonthly.java			17 Mar 2013
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
package com.groovyfly.controlcentre.client.reports.financial;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chris Hatton
 */
public class SalesMonthly implements Serializable, Comparable<SalesMonthly> {
	
    private static final long serialVersionUID = 5965010839404722733L;

	private int salesCount;
	
	private int month;
	
	private int year;
	
	private Map<Integer, String> months = new HashMap<Integer, String>();
	
	{
		months.put(1, "Jan");
		months.put(2, "Feb");
		months.put(3, "Mar");
		months.put(4, "Apr");
		months.put(5, "May");
		months.put(6, "Jun");
		months.put(7, "Jul");
		months.put(8, "Aug");
		months.put(9, "Sep");
		months.put(10, "Oct");
		months.put(11, "Nov");
		months.put(12, "Dec");
	}

	/**
	 * Constructor
	 */
    public SalesMonthly(int salesCount, int month, int year) {
	    super();
	    this.salesCount = salesCount;
	    this.month = month;
	    this.year = year;
    }

	public int getSalesCount() {
		return salesCount;
	}


	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}


	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + month;
	    long temp;
	    temp = Double.doubleToLongBits(salesCount);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    result = prime * result + year;
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
	    SalesMonthly other = (SalesMonthly) obj;
	    if (month != other.month)
		    return false;
	    if (Double.doubleToLongBits(salesCount) != Double.doubleToLongBits(other.salesCount))
		    return false;
	    if (year != other.year)
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return months.get(this.month) + "(" + year + ")";
    }

	/* 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
    @Override
    public int compareTo(SalesMonthly o) {
    	if (year > o.year) {
    		return 1;
    	} else if (year < o.year) {
			return -1;
		} else {
			if (month > o.month) {
				return 1;
			} else if (month < o.month) {
				return -1;
			} else {
				return 0;
			}
		}
    }
	
}
