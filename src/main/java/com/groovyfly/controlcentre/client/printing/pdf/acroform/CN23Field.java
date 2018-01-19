/*
 * Copyright GroovyFly.com CN23Field.java
 */
package com.groovyfly.controlcentre.client.printing.pdf.acroform;

/**
 * @author Chris Hatton
 */
public enum CN23Field {
	
	FROM_NAME("FromName"),
	FROM_BUSINESS("FromBusiness"),
	SENDERS_CUSTOMER_REFERENCE("SendersCustomsReference"),
	FROM_STREET("FromStreet"),
	FROM_POSTCODE("FromPostCode"),
	FROM_CITY("FromCity"),
	FROM_COUNTRY("FromCountry"),
	
	TO_NAME("ToName"),
	TO_BUSINESS("ToBusiness"),
	TO_STREET("ToStreet"),
	TO_POSTCODE("ToPostCode"),
	TO_CITY("ToCity"),
	TO_COUNTRY("ToCountry"),
	IMPORTER_REFERENCE("ImporterReferance"),
	IMPORTER_TEL_FAX_EMAIL("ImporterTelFaxEmail"),
	
	CONTENT_DESC_1("ContentDescription1"),
	QTY_1("Quantity1"),
	NET_WEIGHT_1("NetWeight1"),
	VALUE_1("Value1"),
	TARIFF_NUMBER_1("TariffNumber1"),
	ORIGIN_COUNTRY_1("Origin1"),
	
	CONTENT_DESC_2("ContentDescription2"),
	QTY_2("Quantity2"),
	NET_WEIGHT_2("NetWeight2"),
	VALUE_2("Value2"),
	TARIFF_NUMBER_2("TariffNumber2"),
	ORIGIN_COUNTRY_2("Origin2"),
	
	CONTENT_DESC_3("ContentDescription3"),
	QTY_3("Quantity3"),
	NET_WEIGHT_3("NetWeight3"),
	VALUE_3("Value3"),
	TARIFF_NUMBER_3("TariffNumber3"),
	ORIGIN_COUNTRY_3("Origin3"),
	
	
	CONTENT_DESC_4("ContentDescription4"),
	QTY_4("Quantity4"),
	NET_WEIGHT_4("NetWeight4"),
	VALUE_4("Value4"),
	TARIFF_NUMBER_4("TariffNumber4"),
	ORIGIN_COUNTRY_4("Origin4"),
	
	TOTAL_WEIGHT("TotalWeight"),
	TOTOAL_VALUE("TotalValue"),
	POSTAL_FEES("PostalFees"),
	GIFT_CHK("GiftChk"),
	DOCUMENTS_CHK("DocumentsChk"),
	COMMERCIAL_CHK("CommercalSampleChk"),
	RETURNED_GOODS_CHK("ReturnedGoodsChk"),
	OTHER_CHK("OtherChk"),
	EXPLAINATION("Explaination"),
	OFFICE_AND_DATE("OfficeAndDate"),
	COMMENTS("Comments"),
	LICENCE_NUMBER_CHK("LicenceNoChk"),
	LICENCE_NUMBER("LicenceNumber"),
	CERTIFICATE_CHK("CertificateChk"),
	CERTIFICATE_NUMBER("CertificateNumber"),
	INVOICE_CHK("InvoiceChk"),
	INVOICE("Invoice"),
	DATE_AND_SIGNATURE("DateAndSignature");
	
	
	/** Name of the field on the form */
	private String fieldName;
	
	CN23Field(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}

}
