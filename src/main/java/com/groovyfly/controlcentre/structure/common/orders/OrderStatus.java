/*
 * Copyright GroovyFly.com OrderStatus.java
 */
package com.groovyfly.controlcentre.structure.common.orders;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Chris Hatton
 */
@Entity
@Table(name = "orderstatus_lu")
@NamedQueries({
    @NamedQuery(name="OrderStatus.findAll", query="select os from OrderStatus os")
})
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = -1623006937984108902L;

    @Id
    @Column(name = "orderStatusCode")
	private String statusCode;
	
	@Column(name = "description")
	private String statusDescription;

	/**
     * Constructor
     */
    public OrderStatus() {
    	super();
    }

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
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
	    OrderStatus other = (OrderStatus) obj;
	    if (statusCode == null) {
		    if (other.statusCode != null)
			    return false;
	    } else if (!statusCode.equals(other.statusCode))
		    return false;
	    return true;
    }
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return statusDescription;
	}

}
