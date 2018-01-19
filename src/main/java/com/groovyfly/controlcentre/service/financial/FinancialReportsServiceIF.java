/*
 * Copyright GroovyFly.com FinancialReportsIF.java
 */
package com.groovyfly.controlcentre.service.financial;

import java.util.List;

import com.groovyfly.controlcentre.client.reports.financial.SalesMonthly;
import com.groovyfly.controlcentre.client.reports.financial.SalesReportSearchParam;

/**
 * @author Chris Hatton
 */
public interface FinancialReportsServiceIF {
	
	public List<SalesMonthly> getMonthlySalesFigures(SalesReportSearchParam salesReportSearchParam) throws Exception;
	
}
