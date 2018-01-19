/*
 * Copyright GroovyFly.com PackingSlipCreator.java
 */
package com.groovyfly.controlcentre.client.printing.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import com.groovyfly.controlcentre.structure.common.Company;
import com.groovyfly.controlcentre.structure.common.ShippingAddress;
import com.groovyfly.controlcentre.structure.common.orders.Order;
import com.groovyfly.controlcentre.structure.common.orders.OrderItem;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * @author Chris Hatton
 */
public class PackingSlipCreator {
	
	private static Font boldFont12 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, null);
	
	private static Font footerFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.LIGHT_GRAY);
	
	public static File createPackingSlip(Order order, Company company) throws Exception {
		File tempFile = File.createTempFile("TempPackingSlip", ".pdf");
		FileOutputStream fos = new FileOutputStream(tempFile);

		Document document = new Document();
		document.setPageSize(PageSize.A4);
		document.setMargins(20, 20, 20, 20);
		PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);
		pdfWriter.setPageEvent(new PageFooterEvent(order));
		document.open();
		PackingSlipCreator.addHeader(pdfWriter, document);
		PackingSlipCreator.addShipto(order, company, pdfWriter, document);
		PackingSlipCreator.addItemTable(order, document);
		document.close();

		fos.flush();
		fos.close();
		
		InputStream is = new FileInputStream(tempFile);
		PdfReader reader = new PdfReader(is);
		File tempFileSecond = File.createTempFile("TempPackingSlipSecond", ".pdf");
		FileOutputStream fosSecond = new FileOutputStream(tempFileSecond);
		PdfStamper stamper = new PdfStamper(reader, fosSecond);
		int n = reader.getNumberOfPages();
		for (int i = 1; i <= n; i++) {
			Phrase pageNumberPh = new Phrase(String.format("Page %d of %d", i, n), footerFont);
			float xx = document.getPageSize().getWidth() - document.rightMargin();
			float y = document.bottomMargin() - 10;
			ColumnText.showTextAligned(stamper.getOverContent(i), Element.ALIGN_RIGHT, pageNumberPh, xx, y, 0);
		}
		stamper.close();
		fosSecond.flush();
		fosSecond.close();
		
		return tempFileSecond;
	}

	private static void addHeader(PdfWriter pdfWriter, Document document) throws Exception {
		URL resource = PackingSlipCreator.class.getResource("document-logo.png");
		Image logo = Image.getInstance(resource);
		logo.scaleToFit(150, 75);
		logo.setBorderWidth(2f);
		logo.setBorderColor(BaseColor.BLACK);
		logo.setAbsolutePosition(PageSize.A4.getWidth() - 150 - document.rightMargin(), PageSize.A4.getHeight() - 75 - document.topMargin());
		document.add(logo);

		Paragraph p = new Paragraph(new Phrase("Packing Slip", new Font(Font.FontFamily.TIMES_ROMAN, 26, Font.BOLD, BaseColor.LIGHT_GRAY)));
		p.setSpacingBefore(30f);
		p.setSpacingAfter(7f);
		document.add(p);
		
		LineSeparator lsTop = new LineSeparator(1f, 100f, BaseColor.BLACK, Element.ALIGN_CENTER, 0f);
		document.add(lsTop);
	}

	private static void addShipto(Order order, Company company, PdfWriter pdfWriter, Document document) throws Exception {
		
		Paragraph p = new Paragraph();
		p.setSpacingBefore(10f);
		p.setSpacingAfter(30f);
		Chunk orderNoChunk = new Chunk("Order Number: ", boldFont12);
		Chunk orderNoChunkVal = new Chunk(order.getOrderId());
		p.add(orderNoChunk);
		p.add(orderNoChunkVal);
		p.add(Chunk.NEWLINE);
		
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
		Chunk orderDateChunk = new Chunk("Order Date: ", boldFont12);
		Chunk orderDateChunkVal = new Chunk(df.format(order.getDateCreated()));
		p.add(orderDateChunk);
		p.add(orderDateChunkVal);
		
		document.add(p);
		
		Paragraph p2 = new Paragraph();
		p2.setLeading(20);
		p2.setSpacingBefore(40f);
		p2.setSpacingAfter(7f);
		p2.add(new Phrase("Shipping To", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, null)));
		p2.add(Chunk.NEWLINE);
		String shippingAddressString = getShippingAddressString(order.getShippingAddress());
		p2.add(shippingAddressString);
		
		document.add(p2);
		
		LineSeparator lsTop = new LineSeparator(1f, 100f, BaseColor.BLACK, Element.ALIGN_CENTER, 0f);
		document.add(lsTop);
		
		Phrase ph = getCompanyDetailsPhrase(company);

		float width = 0;
		for (Chunk c : ph.getChunks()) {
			if (c.getWidthPoint() > width) {
				width = c.getWidthPoint();
			}
		}
		
		
		ColumnText ct = new ColumnText(pdfWriter.getDirectContent());
		ct.setSimpleColumn(new com.itextpdf.text.Rectangle(
				document.getPageSize().getWidth() - document.leftMargin() - width, 
				0, 
				document.getPageSize().getWidth() - document.leftMargin(), 
				document.getPageSize().getHeight() - document.topMargin() - 75));
		ct.setAlignment(Element.ALIGN_RIGHT);
		ct.addElement(ph);
		ct.go();
	}
	
	private static Phrase getCompanyDetailsPhrase(Company company) {
		Phrase ph = new Phrase();
		ph.add(company.getAddress().getLine1());
		ph.add("\n");
		ph.add(company.getAddress().getLine2());
		ph.add(Chunk.NEWLINE);
		ph.add(company.getAddress().getLine3());
		ph.add(Chunk.NEWLINE);
		ph.add(company.getAddress().getLine4());
		ph.add(Chunk.NEWLINE);
		ph.add(company.getAddress().getCountry());
		ph.add(Chunk.NEWLINE);
		ph.add(company.getAddress().getPostcode());
		ph.add(Chunk.NEWLINE);
		ph.add(new Chunk(company.getWebsiteUrl(), boldFont12));
		ph.add(Chunk.NEWLINE);
		ph.add(new Chunk(company.getEmail(), boldFont12));
		
		return ph;
	}
	
	private static String getShippingAddressString(ShippingAddress address) {
		StringBuffer sb = new StringBuffer();
		
		List<String> addressStrings = new ArrayList<>();
		addressStrings.add(address.getShippingName());
		addressStrings.add(address.getLine1());
		addressStrings.add(address.getLine2());
		addressStrings.add(address.getLine3());
		addressStrings.add(address.getLine4());
		addressStrings.add(address.getCountry());
		addressStrings.add(address.getPostcode());
		
		for (String s : addressStrings) {
			if (s != null) {
				sb.append(s);
				sb.append(", ");
			}
		}
		
		return sb.substring(0, sb.length() - 2);
	}

	private static void addItemTable(Order order, Document document) throws Exception {
		List<OrderItem> orderItems = order.getOrderItems();

		PdfPTable table = new PdfPTable(5);
		table.setSpacingBefore(15);
		table.setWidthPercentage(95f);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setWidths(new float[] { 8, 3, 1, 1, 1 });

		// add header
		table.getDefaultCell().setPaddingTop(4.5f);
		table.getDefaultCell().setPaddingBottom(4.5f);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

		table.addCell("Product");
		table.addCell("Sku");
		table.addCell("Loc");
		table.addCell("Qty");

		URL resource = PackingSlipCreator.class.getResource("tick.png");
		Image instance = Image.getInstance(resource);
		instance.scaleToFit(20f, 20f);

		PdfPCell tickCell = new PdfPCell(instance);
		tickCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tickCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(tickCell); // TODO add a wee tick image in here

		table.getDefaultCell().setPaddingTop(2);
		table.getDefaultCell().setPaddingBottom(2);

		table.setHeaderRows(1);

		URL checkboxResource = PackingSlipCreator.class.getResource("checkbox.png");
		Image checkboxImage = Image.getInstance(checkboxResource);
		checkboxImage.scaleToFit(13f, 13f);

		int totalQuantity = 0;
		int rowCnt = 0;
		for (OrderItem oi : orderItems) {
			rowCnt++;
			if (rowCnt % 2 == 1) {
				table.getDefaultCell().setBackgroundColor(null);
			} else {
				table.getDefaultCell().setBackgroundColor(new BaseColor(240, 240, 240));
			}

			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell(oi.getProductName());
			table.addCell(oi.getProductSku());
			table.addCell(oi.getStorageLocation());

			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

			totalQuantity += oi.getQuantity();
			table.addCell(Integer.toString(oi.getQuantity()));

			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell checkCell = new PdfPCell(checkboxImage);
			checkCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			checkCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			if (rowCnt % 2 == 1) {
				checkCell.setBackgroundColor(null);
			} else {
				checkCell.setBackgroundColor(new BaseColor(240, 240, 240));
			}
			table.addCell(checkCell);
		}
		
//		for (int i = 0 ; i < 2000; i++) {
//			table.addCell("fff");
//		}

		PdfPCell totalCell = new PdfPCell(new Phrase("Total:"));
		totalCell.setColspan(3);
		totalCell.setPaddingTop(4f);
		totalCell.setPaddingBottom(4f);
		totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(totalCell);

		table.getDefaultCell().setPaddingTop(4f);
		table.getDefaultCell().setPaddingBottom(4f);

		table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(Integer.toString(totalQuantity));

		PdfPCell checkCell = new PdfPCell(checkboxImage);
		checkCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		checkCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		checkCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(checkCell);

		document.add(table);
	}

	/**
	 * @author Chris Hatton
	 */
	private static class PageFooterEvent extends PdfPageEventHelper {
		
		private Order order;
		
		@SuppressWarnings("unused")
        private int pageNo = 0;
		
		/**
         * Constructor
         */
        public PageFooterEvent(Order order) {
        	this.order = order;
        }

        /*
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
		public void onStartPage(PdfWriter writer, Document document) {
			this.pageNo++;
		}

		/*
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onEndPage(PdfWriter writer, Document document) {
			
			Font footerFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.LIGHT_GRAY);
			
			Phrase orderNumberPh = new Phrase("Order Number: " + order.getOrderId(), footerFont); 
			
			float x = document.leftMargin();
			float y = document.bottomMargin() - 10;
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, orderNumberPh, x, y, 0);
		}

	}

}
