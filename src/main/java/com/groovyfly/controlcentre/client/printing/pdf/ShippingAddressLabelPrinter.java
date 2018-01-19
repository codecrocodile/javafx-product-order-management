/*
 * Copyright GroovyFly.com ShippingAddressLabelPrinter.java
 */
package com.groovyfly.controlcentre.client.printing.pdf;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.PrintService;

import com.groovyfly.controlcentre.structure.common.ShippingAddress;

/**
 * This class prints a shipping address label on the DYMO LabelWriter 450 Twin Turbo. It will print on the 
 * DYMO Large Address Label (36mm X 89mm).
 * 
 * @author Chris Hatton
 */
public class ShippingAddressLabelPrinter {
	
	/** Name of the printer */
	private static String PRINTER_NAME = "DYMO LabelWriter 450 Twin Turbo";
	
	/** Label width */
	private static double LABEL_WIDTH_CM = 3.6;
	
	/** Label height */
	private static double LABEL_HEIGHT_CM = 8.9;

	/** Address to be printed */
	private ShippingAddress shippingAddress;
	
	/** Direct print option */
	private boolean directPrint;
	
	/**
     * Constructor
     */
    public ShippingAddressLabelPrinter(ShippingAddress shippingAddress, boolean directPrint) {
    	this.shippingAddress = shippingAddress;
    	this.directPrint = directPrint;
    }
    
    public void createLabel() throws Exception {
    	
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName("Shipping Address Label Printing");
    	
    	PrintService printService = null;
    	for (PrintService ps : PrinterJob.lookupPrintServices()) {
    		if (ps.getName().equalsIgnoreCase(PRINTER_NAME)) {
    			printService = ps;
    		}
    	}
    	
    	if (printService == null) {
    		throw new Exception("Printer not located.");
    	}
    	
    	pj.setPrintService(printService);
    	
    	boolean print = true;
    	if (directPrint) {
    		print = pj.printDialog();
    	}
        
        if (print) {
            PageFormat pf = pj.defaultPage();
            Paper paper = pf.getPaper();    
            double width = fromCMToPPI(LABEL_WIDTH_CM);
            double height = fromCMToPPI(LABEL_HEIGHT_CM); 
            paper.setSize(width, height);
			paper.setImageableArea(0, 0, width, height); 
            pf.setOrientation(PageFormat.LANDSCAPE);
            pf.setPaper(paper);    
            pj.setPrintable(new LargeShippingAddressLabel(shippingAddress), pf);
            
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                
                throw new Exception("Printer error. " + ex.getMessage());
            }    
        }     	
    }
    
    private double fromCMToPPI(double cm) {            
        return toPPI(cm * 0.393700787);            
    }

    private double toPPI(double inch) {            
        return inch * 72d;            
    }
    
}


class LargeShippingAddressLabel implements Printable {
	
	/** Address to be printed */
	private ShippingAddress shippingAddress;
	
	/**
     * Constructor
     */
    public LargeShippingAddressLabel(ShippingAddress shippingAddress) {
    	this.shippingAddress = shippingAddress;
    }

    /*
     * DYMO printer won't return hardware margins or there is a bug with java so I needed to hack 
     * translation and printable area to get it to work (seems to be a known problem with java).
     * 
     * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {  
        int result = NO_SUCH_PAGE;  
        
        if (pageIndex < 1) {  // print only one at a time
        	
            Graphics2D g2d = (Graphics2D) graphics;
            
            double width = pageFormat.getImageableWidth() - 12; // hack for DYMO
            double height = pageFormat.getImageableHeight() - 10; // hack for DYMO 
            g2d.translate((int) pageFormat.getImageableX() + 12,  (int) pageFormat.getImageableY()); // hack for DYMO   
            
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
            FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
            int xOffset = getXOffset(fm, shippingAddress, width);
            
            g2d.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            if (printCountry(shippingAddress)) {
            	g2d.drawString(shippingAddress.getShippingName(), 			xOffset, getYOffset(fm, 6, 1, height));
                g2d.drawString(shippingAddress.getLine1(), 					xOffset, getYOffset(fm, 6, 2, height));
                g2d.drawString(shippingAddress.getLine3(), 					xOffset, getYOffset(fm, 6, 3, height));
                g2d.drawString(shippingAddress.getLine4(), 					xOffset, getYOffset(fm, 6, 4, height));
                g2d.drawString(shippingAddress.getPostcode(), 				xOffset, getYOffset(fm, 6, 5, height));
                g2d.drawString(shippingAddress.getCountry().toUpperCase(), 	xOffset, getYOffset(fm, 6, 6, height));	
            } else {
            	g2d.drawString(shippingAddress.getShippingName(), 			xOffset, getYOffset(fm, 5, 1, height));
                g2d.drawString(shippingAddress.getLine1(), 					xOffset, getYOffset(fm, 5, 2, height));
                g2d.drawString(shippingAddress.getLine3(), 					xOffset, getYOffset(fm, 5, 3, height));
                g2d.drawString(shippingAddress.getLine4(), 					xOffset, getYOffset(fm, 5, 4, height));
                g2d.drawString(shippingAddress.getPostcode(), 				xOffset, getYOffset(fm, 5, 5, height));
            }
            
            result = PAGE_EXISTS;    
        } 
        
        return result;    
    }
    
    /*
     * Offset for centring.
     */
    private int getXOffset(FontMetrics fm, ShippingAddress shippingAddress, double maxWidthAvailable) throws PrinterException {
    	int[] lengths = new int[6];
    	
    	lengths[0] = fm.stringWidth(shippingAddress.getShippingName());
    	lengths[1] = fm.stringWidth(shippingAddress.getLine1());
    	lengths[2] = fm.stringWidth(shippingAddress.getLine3());
    	lengths[3] = fm.stringWidth(shippingAddress.getLine4());
    	lengths[4] = fm.stringWidth(shippingAddress.getPostcode());
    	lengths[5] = fm.stringWidth(shippingAddress.getCountry());
    	
    	int maxWidth = 0;
    	for (int i : lengths) {
    		if (i > maxWidth) {
    			maxWidth = i;
    		}
    	}
    	
        if (maxWidth > maxWidthAvailable) {
        	throw new PrinterException("Doesn't fit");
        } 
        
        int xOffset = (int) ((maxWidthAvailable - maxWidth) / 2);
       
    	return xOffset;
    }

    /*
     * Offset for centring.
     */
    private int getYOffset(FontMetrics fm, int totalLines, int lineNumber, double maxHeightAvailable) throws PrinterException {
    	int toalHeightRequired = fm.getAscent() * totalLines;
    	
        if (toalHeightRequired > maxHeightAvailable) {
        	throw new PrinterException("Doesn't fit");
        } 
        
        int yOffset = (int) ((maxHeightAvailable - toalHeightRequired) / 2);
        
        return yOffset + (fm.getAscent() * lineNumber);
    }

    /*
     * UK addresses don't need the country printed.
     */
    private boolean printCountry(ShippingAddress shippingAddress) {
    	if (shippingAddress.getCountry().equalsIgnoreCase("United Kingdom")) {
    		return false;
    	} else {
    		return true;
    	}
    }

}

