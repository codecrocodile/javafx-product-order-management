/*
 * Copyright GroovyFly.com Address.java
 */
package com.groovyfly.controlcentre.structure.common;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Chris Hatton
 */
@Embeddable 
@Access(AccessType.FIELD)
public class Address {

	@Column(name="addressLine1")
    protected String line1;

	@Column(name="addressLine2")
    protected String line2;

	@Column(name="addressLine3")
    protected String line3;

	@Column(name="addressLine4")
    protected String line4;

	@Column(name="country")
    protected String country;

	@Column(name="postocde") //TODO chnagfe this to correct spelling
    protected String postcode;

    /**
     * Constructor
     */
    public Address() {
        super();
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
}
