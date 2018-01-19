/*
 * Copyright GroovyFly.com AcroFormPrinter.java
 */
package com.groovyfly.controlcentre.client.printing.pdf.acroform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * This information is accurate as of the time of writing (24/02/2013).
 * <p>
 * If you are sending items outside the European Union (EU) and also to the places listed below, you need to complete a customs 
 * declarations form that needs to be attached to the outside of the package
 * 
 * <ul>
 * 		<li> Channel Islands - Jersey, Guernsey, Alderney, Herm and Sark
 * 		<li> Andorra
 * 		<li> Canary Islands
 * 		<li> Gibraltar 
 * 		<li> San Marino 
 * 		<li> Vatican City State
 * </ul>
 * 
 * <p>
 * All products posted are regarded as commercial and must be treated as such. Orders up to the value of £270.00 requires
 * a CN22 form. Orders above £270.00 require a CN23 form. An invoice should also be attached to the outside.
 * <p>
 * If you are sending commercial mail, ideally you should complete the World Customs Organisation (WCO) Harmonised System (HS) 
 * Tariff Code and Country of Origin fields in the same order as individual items are listed in the 'Quantity’ and ‘Detailed 
 * description of contents' fields.
 * <p>
 * The best tariff number I can get is "05 11 99"  // this is made up of section numbers (05 and 11) and the first two numbers of the other specific code.
 * When you use this to search on the website it works so I'm guessing this is the correct format and digits of the code to use.
 * 
 * @see http://www.ukba.homeoffice.gov.uk/customs-travel/customs/postal/#header5
 * @see http://www.royalmail.com/delivery/mail-advice/customs-information?campaignid=customs_redirect
 * @see https://www.gov.uk/trade-tariff
 *  
 * @author Chris Hatton
 */
public class AcroFormPrinter {
	
	/**
     * Constructor
     */
    public AcroFormPrinter() {
    	super();
    }
    
    public File createCN22Form(Map<CN22Field, String> fieldValues) throws Exception {
		InputStream is = AcroFormPrinter.class.getResourceAsStream("CN22-AcroForm.pdf");
		PdfReader reader = new PdfReader(is);
		File tempFileSecond = File.createTempFile("TempCN22", ".pdf");
		FileOutputStream fosSecond = new FileOutputStream(tempFileSecond);
		PdfStamper stamper = new PdfStamper(reader, fosSecond);
		
		AcroFields acroFields = stamper.getAcroFields();
		for (Entry<CN22Field, String> e : fieldValues.entrySet()) {
			acroFields.setField(e.getKey().getFieldName(), e.getValue());
		}
		stamper.setFormFlattening(true);
		
		stamper.close();
		fosSecond.flush();
		fosSecond.close();
		
		return tempFileSecond;
    }
    
    public File createCN23Form(Map<CN23Field, String> fieldValues) throws Exception {
		InputStream is = AcroFormPrinter.class.getResourceAsStream("CN23-AcroForm.pdf");
		PdfReader reader = new PdfReader(is);
		File tempFileSecond = File.createTempFile("TempCN23", ".pdf");
		FileOutputStream fosSecond = new FileOutputStream(tempFileSecond);
		PdfStamper stamper = new PdfStamper(reader, fosSecond);
		
		AcroFields acroFields = stamper.getAcroFields();
		for (Entry<CN23Field, String> e : fieldValues.entrySet()) {
			acroFields.setField(e.getKey().getFieldName(), e.getValue());
		}
		
		stamper.setFormFlattening(true);
		
		stamper.close();
		fosSecond.flush();
		fosSecond.close();
		
		return tempFileSecond;
    }
	
	
}
