/*
 * @(#)SupplierServiceIF.java			1 Apr 2013
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

import com.groovyfly.controlcentre.structure.Supplier;

/**
 * @author Chris Hatton
 */
public interface SupplierServiceIF {

    public List<Supplier> getSuppliers(boolean includeRetired) throws Exception;

    public Supplier getSupplier(int id) throws Exception;

    public void saveSuppliers(List<Supplier> suppliers) throws Exception;
    
    public void saveSupplier(Supplier supplier) throws Exception;

    public void insertSupplier(Supplier supplier) throws Exception;

    public void updateSupplier(Supplier supplier) throws Exception;

    public void retireSupplier(int id) throws Exception;

}

