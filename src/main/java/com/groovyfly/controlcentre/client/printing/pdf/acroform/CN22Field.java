/*
 * Copyright GroovyFly.com CN22Field.java
 */
package com.groovyfly.controlcentre.client.printing.pdf.acroform;

/**
 * @author Chris Hatton
 */
public enum CN22Field {
	
	GIFT("GiftChk"),
	
	COMMERCIAL_SAMPLE("CommercialSampleChk"),
	
	DOCUMENTS("DocumentChk"),
	
	OTHER("OtherChk"),
	
	DETAILS_1("Details1"),
	
	WEIGHT_KG_1("WeightKg1"),
	
	VALUE_1("Value1"),
	
	DETAILS_2("Details2"),
	
	WEIGHT_KG_2("WeightKg2"),
	
	VALUE_2("Value2"),
	
	DETAILS_3("Details3"),
	
	WEIGHT_KG_3("WeightKg3"),
	
	VALUE_3("Value3"),
	
	TARIFF_NUM_AND_ORIGIN("TariffNumAndOrigin"),
	
	TOTAL_WEIGHT("TotalWeight"),
	
	TOTAL_VALUE("TotalValue"),
	
	SIGNATURE_AND_DATE("SignatureAndDate");
	
	/** Name of the field on the form */
	private String fieldName;
	
	CN22Field(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
	
}