/*
 * Copyright GroovyFly.com FinancialReportsService.java
 */
package com.groovyfly.controlcentre.service.financial;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groovyfly.controlcentre.client.reports.financial.SalesMonthly;
import com.groovyfly.controlcentre.client.reports.financial.SalesReportSearchParam;
import com.groovyfly.controlcentre.util.DbUtil;

/**
 * @author Chris Hatton
 */
@Service("FinancialReportsService") 
@Repository 
@Transactional
public class FinancialReportsService implements FinancialReportsServiceIF {
	
    @PersistenceContext 
    private EntityManager em;

	/* 
	 * @see com.groovyfly.controlcentre.service.financial.FinancialReportsServiceIF
	 * 	#getMonthlySalesFigures(com.groovyfly.controlcentre.client.reports.financial.SalesReportSearchParam)
	 */
    @Override
    @Transactional(readOnly=true)
    public List<SalesMonthly> getMonthlySalesFigures(SalesReportSearchParam salesReportSearchParam) throws Exception {
    	List<SalesMonthly> returnList = new LinkedList<>();
    	
    	String nativeQueryString = 
    			"SELECT COUNT(1), MONTH(dateCreated) AS orderMonth, YEAR(dateCreated) AS orderYear " +
    			"FROM `order` " +
    			createMonthlySalesWhereCondition(salesReportSearchParam) + " " +
    			"GROUP BY MONTH(dateCreated),  YEAR(dateCreated) " +
    			"ORDER BY orderYear ASC, orderMonth ASC" ;
    	
    	Query nativeQuery = em.createNativeQuery(nativeQueryString);
    	List<?> resultList = nativeQuery.getResultList();
    	
    	SalesMonthly sm = null;
    	for (Object values : resultList) {
    		Object[] value = (Object[]) values;
    		
    		BigInteger salesCount = (BigInteger) value[0];
    		Integer salesMonth = (Integer) value[1];
    		Integer salesYear = (Integer) value[2];
    		sm = new SalesMonthly(salesCount.intValue(), salesMonth.intValue(), salesYear.intValue());
    		
    		returnList.add(sm);
    	}
    	
	    return returnList;
    }
    
    private String createMonthlySalesWhereCondition(SalesReportSearchParam salesReportSearchParam)  {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append(" WHERE 1 = 1");
    	
    	if (salesReportSearchParam.getAfterDate() != null) {
    		sb.append(" AND ");
    		sb.append("dateCreated > " + DbUtil.dateToSqlStringOrNull(salesReportSearchParam.getAfterDate()));
    	}
    	if (salesReportSearchParam.getBeforeDate() != null) {
    		sb.append(" AND ");
    		sb.append("dateCreated < " + DbUtil.dateToSqlStringOrNull(salesReportSearchParam.getBeforeDate()));
    	}
    	
    	return sb.toString();
    }

}
