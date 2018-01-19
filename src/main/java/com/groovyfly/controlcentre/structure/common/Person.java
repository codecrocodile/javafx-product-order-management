/*
 * Copyright GroovyFly.com Person.java
 */
package com.groovyfly.controlcentre.structure.common;

import java.util.Date;

import javax.mail.Address;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * @author Chris Hatton
 */
@Embeddable 
@Access(AccessType.FIELD)
public class Person {
	
    private String title;

    private String forename;

    private String surname;

    @Transient
    private Date dob;
    
    @Transient
    private Address address;
    
    @Transient
    private String email;
    
    @Transient
    private String phoneNumber;
    
    @Transient
    private String mobilePhoneNumber;

    /**
     * Constructor
     */
    public Person() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
}
