/*
 * Copyright GroovyFly.com ShippingAddress.java
 */
package com.groovyfly.controlcentre.structure.common;

/**
 * @author Chris Hatton
 */
public class ShippingAddress extends Address {

    /** Person’s name associated with this shipping address */
    private String shippingName;
    
    /** 2 letter ISO 3166 code for the country */
    private String countryCode;
    
    private String addressStatus;
    
    /**
     * Constructor
     */
    public ShippingAddress() {
        super();
    }

    public String getShippingName() {
        return shippingName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setShippingName(String name) {
        this.shippingName = name;
    }

    public String getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(String addressStatus) {
        this.addressStatus = addressStatus;
    }
    
    public String getAddressStringFormatted() {
    	StringBuffer sb = new StringBuffer();
    	if (this.shippingName != null) {
    		sb.append(this.shippingName + "\n");
    	}
    	if (this.line1 != null) {
    		sb.append(this.line1 + "\n");
    	}
    	if (this.line2 != null) {
    		sb.append(this.line2 + "\n");
    	}
    	if (this.line3 != null) {
    		sb.append(this.line3 + "\n");
    	}
    	if (this.getLine4() != null) {
    		sb.append(this.line4 + "\n");
    	}
    	if (this.postcode != null) {
    		sb.append(this.postcode + "\n");
    	}
    	if (this.getCountry() != null) {
    		sb.append(this.country);
    	}
    	
    	return sb.toString();
    }
    
}
