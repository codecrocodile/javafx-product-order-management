/*
 * Copyright GroovyFly.com ConfigDAO.java
 */
package com.groovyfly.controlcentre.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groovyfly.controlcentre.structure.common.Address;
import com.groovyfly.controlcentre.structure.common.Company;

/**
 * @author Chris Hatton
 */
@Repository("ConfigDAO")
public class ConfigDAO {
	
    private final String COMPANY_NAME = "companyName";
    private final String COMPANY_ADDRESS_LINE_1 = "companyAddressLine1";
    private final String COMPANY_ADDRESS_LINE_2 = "companyAddressLine2";
    private final String COMPANY_ADDRESS_LINE_3 = "companyAddressLine3";
    private final String COMPANY_ADDRESS_LINE_4 = "companyAddressLine4";
    private final String COMPANY_COUNTRY = "companyCountry";
    private final String COMPANY_POSTCODE = "companyPostcode";
    private final String CUSTOMER_SERVICE_EMAIL = "customerServiceEmail";
    private final String WEBSITE_URL = "websiteUrl";
    private final String CUSTOMER_SERVICE_PHONE_NUMBER = "customerServicePhoneNumber";
    private final String IS_VAT_REGISTERED = "isVatRegisterd";
    private final String VAT_NUMBER = "vatNumber";

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unused")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
	}
	
	public Company getCompany() throws Exception {
		
        String sql = 
                "SELECT name, value " +
                "FROM configuration";
            
        return jdbcTemplate.query(sql, new ResultSetExtractor<Company>() {

			@Override
            public Company extractData(ResultSet rs) throws SQLException, DataAccessException {
		        Company c = new Company();
		        Address a = new Address();
		        c.setAddress(a);
				
	            while (rs.next()) {
	                String name = rs.getString(1);
	                String value = rs.getString(2);
	                if (name.equalsIgnoreCase(COMPANY_NAME)) {
	                    c.setName(value);
	                } else if (name.equalsIgnoreCase(COMPANY_ADDRESS_LINE_1)) {
	                    a.setLine1(value);
	                } else if (name.equalsIgnoreCase(COMPANY_ADDRESS_LINE_2)) {
	                    a.setLine2(value);
	                } else if (name.equalsIgnoreCase(COMPANY_ADDRESS_LINE_3)) {
	                    a.setLine3(value);
	                } else if (name.equalsIgnoreCase(COMPANY_ADDRESS_LINE_4)) {
	                    a.setLine4(value);
	                } else if (name.equalsIgnoreCase(COMPANY_COUNTRY)) {
	                    a.setCountry(value);
	                } else if (name.equalsIgnoreCase(COMPANY_POSTCODE)) {
	                    a.setPostcode(value);
	                } else if (name.equalsIgnoreCase(CUSTOMER_SERVICE_EMAIL)) {
	                    c.setEmail(value);
	                } else if (name.equalsIgnoreCase(WEBSITE_URL)) {
	                    c.setWebsiteUrl(value);
	                } else if (name.equalsIgnoreCase(CUSTOMER_SERVICE_PHONE_NUMBER)) {
	                    c.setPhonenNumber(value);
	                } else if (name.equalsIgnoreCase(IS_VAT_REGISTERED)) {
	                    c.setVatRegistered(Boolean.valueOf(value));
	                } else if (name.equalsIgnoreCase(VAT_NUMBER)) {
	                    c.setVatNumber(value);
	                }
	            }
	            
	            return c;
			}
        });
	}
	
}
