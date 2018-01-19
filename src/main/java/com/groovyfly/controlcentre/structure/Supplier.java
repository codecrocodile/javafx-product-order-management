/*
 * @(#)Supplier.java			31 Mar 2013
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
package com.groovyfly.controlcentre.structure;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.groovyfly.controlcentre.structure.common.Address;
import com.groovyfly.controlcentre.structure.common.Person;

/**
 * @author Chris Hatton
 */
@Entity()
@Table(name="supplier") 
@Access(AccessType.FIELD)
public class Supplier {

	@Id
	@Column(name="supplierId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int supplierId;

	@Column(name="shortCode")
    private String shortCode;

	@Column(name="companyName")
    private String companyName;

	@Embedded
    @AttributeOverrides({
        @AttributeOverride(name="title", column=@Column(name="contactTitle")),
        @AttributeOverride(name="forename", column=@Column(name="contactForname")),
        @AttributeOverride(name="surname", column=@Column(name="contactSurname"))
    })
    private Person contactPerson;

	@Embedded
    private Address address;

    @Column(name="tel")
    private String tel;

    @Column(name="mobile")
    private String mobile;

    @Column(name="fax")
    private String fax;

    @Column(name="email")
    private String email;

    @Column(name="notes")
    private String notes;

    @Column(name="retired")
    private boolean retired;

    /**
     * Constructor
     */
    public Supplier() {
        super();
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Person getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(Person contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }
    
    /* 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.companyName;
    }
    
}