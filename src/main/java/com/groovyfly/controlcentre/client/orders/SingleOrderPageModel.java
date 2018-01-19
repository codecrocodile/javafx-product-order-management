/*
 * Copyright GroovyFly.com SingleOrderPageModel.java
 */
package com.groovyfly.controlcentre.client.orders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.groovyfly.controlcentre.client.printing.pdf.acroform.CN22Field;
import com.groovyfly.controlcentre.client.printing.pdf.acroform.CN23Field;
import com.groovyfly.controlcentre.client.printing.pdf.acroform.CustomsDeclaration;
import com.groovyfly.controlcentre.dao.mysql.ConfigDAO;
import com.groovyfly.controlcentre.dao.mysql.OrderDAO;
import com.groovyfly.controlcentre.structure.common.Company;
import com.groovyfly.controlcentre.structure.common.orders.Order;
import com.groovyfly.controlcentre.structure.common.orders.OrderSummary;

/**
 * @author Chris Hatton
 */
@Component("SingleOrderPageModel")
@Scope("prototype")
public class SingleOrderPageModel {
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private ConfigDAO configDAO;
	
	private Order order;

	/**
	 * @param order
	 */
    public Order getOrder(OrderSummary orderSummary) throws Exception {
    	this.order = orderDAO.getOrder(orderSummary);
    	return this.order;
    }

	public Order getOrder() {
		return order;
	}
	
	public CustomsDeclaration getRequiredCustomsDeclaration() {
		if (this.isExportedOutsideEU()) {
			if (order.getSubTotalAmount().compareTo(BigDecimal.valueOf(270.00)) > 0) {
				return CustomsDeclaration.CN23;
			} else {
				return CustomsDeclaration.CN22;
			}
			
		} else {
			return CustomsDeclaration.NONE;
		}
	}
	
	private boolean isExportedOutsideEU() {
		// TODO
		return false;
	}
	
	public Map<CN22Field, String> getCN22FieldValues() throws Exception {
		Map<CN22Field, String> values = new HashMap<>();
		// TODO
		int cnt = 1;
		
		for (CN22Field f : CN22Field.values()) {
			values.put(f, "field xxxxxxxxxxxxxxxxxxxxxxxxxyyyyyyyyyyyyyyyyyyyyyyyyyy" + cnt++);
		}
		
		values.put(CN22Field.GIFT, "Yes");
		values.put(CN22Field.COMMERCIAL_SAMPLE, "Yes");
		values.put(CN22Field.DOCUMENTS, "Yes");
		values.put(CN22Field.OTHER, "Yes");
		
		return values;
	}
	
	public Map<CN23Field, String> getCN23FieldValues() throws Exception {
		Map<CN23Field, String> values = new HashMap<>();
		// TODO
		int cnt = 1;
		
		for (CN23Field f : CN23Field.values()) {
			values.put(f, "field " + cnt++);
		}
		
		values.put(CN23Field.GIFT_CHK, "Yes");
		values.put(CN23Field.DOCUMENTS_CHK, "Yes");
		values.put(CN23Field.COMMERCIAL_CHK, "Yes");
		values.put(CN23Field.OTHER_CHK, "Yes");
		values.put(CN23Field.RETURNED_GOODS_CHK, "Yes");
		values.put(CN23Field.LICENCE_NUMBER_CHK, "Yes");
		values.put(CN23Field.CERTIFICATE_CHK, "Yes");
		values.put(CN23Field.INVOICE_CHK, "Yes");
		
		return values;
	}
	
	
	public Company getCompany() throws Exception {
		return configDAO.getCompany();
	}

}
