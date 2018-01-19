/*
 * @(#)SupplierService.java			1 Apr 2013
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
package com.groovyfly.controlcentre.service.sitemanagement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groovyfly.controlcentre.structure.Supplier;

/**
 * @author Chris Hatton
 */
@Service("SupplierService")  
@Repository 
@Transactional
public class SupplierService implements SupplierServiceIF {
	
    @PersistenceContext
    private EntityManager em;

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SupplierServiceIF#getSuppliers(boolean)
	 */
    @Transactional(readOnly=true)
    @Override
    public List<Supplier> getSuppliers(boolean includeRetired) throws Exception {
    	String qry = "SELECT s FROM Supplier s";
    	TypedQuery<Supplier> allPagesQuery = em.createQuery(qry, Supplier.class);
    	List<Supplier> resultList = allPagesQuery.getResultList();
    	
	    return resultList;
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SupplierServiceIF#getSupplier(int)
	 */
    @Override
    public Supplier getSupplier(int id) throws Exception {
	    return null;
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SupplierServiceIF#saveSuppliers(java.util.List)
	 */
    @Override
    public void saveSuppliers(List<Supplier> suppliers) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SupplierServiceIF#saveSupplier(com.groovyfly.controlcentre.structure.Supplier)
	 */
    @Override
    public void saveSupplier(Supplier supplier) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SupplierServiceIF#insertSupplier(com.groovyfly.controlcentre.structure.Supplier)
	 */
    @Override
    public void insertSupplier(Supplier supplier) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SupplierServiceIF#updateSupplier(com.groovyfly.controlcentre.structure.Supplier)
	 */
    @Override
    public void updateSupplier(Supplier supplier) throws Exception {
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SupplierServiceIF#retireSupplier(int)
	 */
    @Override
    public void retireSupplier(int id) throws Exception {
    }

}
