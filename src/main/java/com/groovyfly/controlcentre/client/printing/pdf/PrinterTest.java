/*
 * Copyright GroovyFly.com PrintingTest.java
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

import com.groovyfly.controlcentre.structure.common.ShippingAddress;

/**
 * @author Chris Hatton
 */
public class PrinterTest {

    public static void main(String[] args) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        
        if (pj.printDialog()) {
            PageFormat pf = pj.defaultPage();
            Paper paper = pf.getPaper();    
            double width = fromCMToPPI(3.6);
            double height = fromCMToPPI(8.9); 
            paper.setSize(width, height);
			paper.setImageableArea(0, 0, width, height); 
            pf.setOrientation(PageFormat.LANDSCAPE);
            pf.setPaper(paper);    
            
            
            ShippingAddress sa = new ShippingAddress();
            sa.setShippingName("Christopher George Hatton MD");
            sa.setLine1("1254 Aillort Around Corner Place");
            sa.setLine2("East Mains");
            sa.setLine3("East Kilbride");
            sa.setLine4("South Lanarkshire");
            sa.setCountry("United Kingdom");
            sa.setPostcode("G74 4LL");
            
            pj.setPrintable(new MyPrintable(sa), pf);
            
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }    
        }    
    }

    public static class MyPrintable implements Printable {
    	
    	private ShippingAddress shippingAddress;
    	
    	/**
         * Constructor
         */
        public MyPrintable(ShippingAddress shippingAddress) {
        	this.shippingAddress = shippingAddress;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {  
            int result = NO_SUCH_PAGE;  
            
            if (pageIndex < 1) {  
            	
                Graphics2D g2d = (Graphics2D) graphics;
                
                double width = pageFormat.getImageableWidth() - 12;
                double height = pageFormat.getImageableHeight() - 10; 
               
                g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
  
                g2d.translate((int) pageFormat.getImageableX() + 12,  (int) pageFormat.getImageableY());    
              
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
    }
    
    private static int getXOffset(FontMetrics fm, ShippingAddress shippingAddress, double maxWidthAvailable) throws PrinterException {
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
    
    private static int getYOffset(FontMetrics fm, int totalLines, int lineNumber, double maxHeightAvailable) throws PrinterException {
    	int toalHeightRequired = fm.getAscent() * totalLines;
    	
        if (toalHeightRequired > maxHeightAvailable) {
        	throw new PrinterException("Doesn't fit");
        } 
        
        int yOffset = (int) ((maxHeightAvailable - toalHeightRequired) / 2);
        
        return yOffset + (fm.getAscent() * lineNumber);
        
    	
    }
    
    private static boolean printCountry(ShippingAddress shippingAddress) {
    	if (shippingAddress.getCountry().equalsIgnoreCase("United Kingdom")) {
    		return false;
    	} else {
    		return true;
    	}
    }

    
    protected static double fromCMToPPI(double cm) {            
        return toPPI(cm * 0.393700787);            
    }

    protected static double toPPI(double inch) {            
        return inch * 72d;            
    }
   
}