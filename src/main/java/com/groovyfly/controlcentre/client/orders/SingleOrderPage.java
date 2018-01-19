/*
 * Copyright GroovyFly.com SingleOrderPage.java
 */
package com.groovyfly.controlcentre.client.orders;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jfxtras.labs.dialogs.DialogFX;
import jfxtras.labs.dialogs.DialogFX.Type;

import org.springframework.beans.factory.annotation.Autowired;

import com.groovyfly.controlcentre.application.Page;
import com.groovyfly.controlcentre.client.printing.pdf.InvoiceCreator;
import com.groovyfly.controlcentre.client.printing.pdf.PackingSlipCreator;
import com.groovyfly.controlcentre.client.printing.pdf.ShippingAddressLabelPrinter;
import com.groovyfly.controlcentre.client.printing.pdf.acroform.AcroFormPrinter;
import com.groovyfly.controlcentre.client.printing.pdf.acroform.CN22Field;
import com.groovyfly.controlcentre.client.printing.pdf.acroform.CN23Field;
import com.groovyfly.controlcentre.client.printing.pdf.acroform.CustomsDeclaration;
import com.groovyfly.controlcentre.structure.common.Company;
import com.groovyfly.controlcentre.structure.common.PaypalTransactionValues;
import com.groovyfly.controlcentre.structure.common.orders.Order;
import com.groovyfly.controlcentre.structure.common.orders.OrderItem;
import com.groovyfly.controlcentre.structure.common.orders.OrderSummary;

/**
 * @author Chris Hatton
 */
public class SingleOrderPage extends Page {

	private Label orderIdLblVal = new Label("n/a");

	private Label dateCreatedLblVal = new Label("n/a");

	private Label orderStatusLblVal = new Label("n/a");

	private Label timeSinceOrderLblVal = new Label("n/a");
	
	
	private Label titleLblVal = new Label("n/a");
	
	private Label forenameLblVal = new Label("n/a");
	
	private Label surnameLblVal = new Label("n/a");
	
	private Label emailLblVal = new Label("n/a");
	
	private Label telLblVal = new Label("n/a");
	
	private Text addressText = new Text("n/a");

	
	private Label subTotalAmountLblVal  = new Label("n/a");
	
	private Label discountCodeLblVal  = new Label("n/a");
	
	private Label discountAmountLblVal  = new Label("n/a");
	
	private Label postageAndPackingAmountLblVal  = new Label("n/a");
	
	private Label vatAmountLblVal  = new Label("n/a");
	
	private Label totalAmountLblVal  = new Label("n/a");
	
	
	private Label paymentProcessorLblVal = new Label("n/a");
	
	private Label payerIdLblVal = new Label("n/a");
	
	private Label transactionIdLblVal = new Label("n/a");
	
	private Label paymentDateLblVal = new Label("n/a");
	
	private Label paymentStatusLblVal = new Label("n/a");
	
	private Label pendingReasonValLbl = new Label("n/a");
	
	
	private TableView<OrderItem> orderItemTable = new TableView<OrderItem>();
	
	
	private Button emailBtn = new Button("Email Customer");
	
	private Button copyAddressBtn = new Button("Copy Address");
	
	private Button printInvoiceBtn = new Button("Print Invoice");
	
	private Button printPackingSlipBtn = new Button("Print Packing Slip");
	
	private Button printShippingAddresslabelBtn = new Button("Print Address");
	
	private Button printReturnAddresslabelBtn = new Button("Print Return Address");
	
	private Button printCN22Btn = new Button("Print CN22");
	
	private Button printCN23Btn = new Button("Print CN23");
	
	private Button printAllBtn = new Button("Print All");

	@Autowired
	private SingleOrderPageModel singleOrderPageModel;

	/**
	 * Constructor
	 */
	public SingleOrderPage() {
		super();
	}

	/*
	 * @see com.groovyfly.controlcentre.client.Page#createView()
	 */
	@Override
	public Node createView() throws Exception {
		BorderPane pane = new BorderPane();
		
		emailBtn.setCursor(Cursor.HAND);
		emailBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent ae) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					
					try {
						Order order = singleOrderPageModel.getOrder();
						
						StringBuffer sb = new StringBuffer();
						sb.append(order.getCustomer().getEmail());
						sb.append("?subject=GroovyFly.com, RE: Order Number: " + order.getOrderId());
						sb.append("&body=Hello Mr/Mrs/Miss " + order.getCustomer().getSurname());
						
	                    URI mailtoUri = new URI("mailto", sb.toString(), null);
	                    desktop.mail(mailtoUri);
                    } catch (URISyntaxException e) {
	                    e.printStackTrace();
                    } catch (IOException e) {
	                    e.printStackTrace();
                    } 
				} else {
					// email address copied to clip board 
				}
			}
		
		});
		
		copyAddressBtn.setCursor(Cursor.HAND);
		copyAddressBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent ae) {
				Order order = singleOrderPageModel.getOrder();

				Clipboard clipboard = Clipboard.getSystemClipboard();
				ClipboardContent content = new ClipboardContent();
				content.putString(order.getShippingAddress().getAddressStringFormatted());
				clipboard.setContent(content);
				
				DialogFX d = new DialogFX(Type.INFO);
				d.setTitle("Control Centre");
				d.setModal(true);
				d.setMessage("Address copied to clipboard.");
				d.showDialog();
			}
		});
		
		printInvoiceBtn.setCursor(Cursor.HAND);
		printInvoiceBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Order order = singleOrderPageModel.getOrder();
					Company c = singleOrderPageModel.getCompany();
					
					InvoiceCreator ic = new InvoiceCreator();
					File invoice = ic.createInvoice(c, order);
	                
	                if (Desktop.isDesktopSupported()) {
	                	Desktop desktop = Desktop.getDesktop();
	                	desktop.open(invoice);
	                }
                } catch (Exception e) {
	                e.printStackTrace();
                }
			}
		});
		
		GridPane gp = new GridPane();
		ColumnConstraints cc1 = new ColumnConstraints();
		cc1.setPercentWidth(33.3);
		ColumnConstraints cc2 = new ColumnConstraints();
		cc2.setPercentWidth(33.3);
		ColumnConstraints cc3 = new ColumnConstraints();
		cc3.setPercentWidth(33.3);
		gp.getColumnConstraints().addAll(cc1, cc2, cc3);
		
		gp.add(this.buildCustomerPane(), 0, 0);
		gp.add(this.buildShippingPane(), 1, 0);
		gp.add(this.buildTotalsPane(), 2, 0);
		
		VBox containerBox = new VBox();
		containerBox.getChildren().addAll(
				this.buildBasicInfoPane(), 
				gp,
				this.buildPaymentProviderDetailsNode()
		);
		
		pane.setTop(containerBox);
		pane.setCenter(this.buildTablePane());
		pane.setBottom(this.buildButtonPane());

		return pane;
	}

	private Node buildBasicInfoPane() {
		GridPane pane = new GridPane();
		pane.setStyle("-fx-background-color: #aaa;");
		pane.setVgap(10);
		pane.setHgap(10);
		pane.setPadding(new Insets(5));

		Label orderIdLbl = new Label("Order Id:");
		Label dateCreatedLbl = new Label("Date Created:");
		Label orderStatusLbl = new Label("Order Status:");
		Label timeSinceOrderLbl = new Label("Order Time:");

		pane.add(orderIdLbl, 0, 0);
		pane.add(orderIdLblVal, 1, 0);

		pane.add(dateCreatedLbl, 2, 0);
		pane.add(dateCreatedLblVal, 3, 0);

		GridPane.setHgrow(orderStatusLbl, Priority.ALWAYS);
		GridPane.setHalignment(orderStatusLbl, HPos.RIGHT);
		pane.add(orderStatusLbl, 4, 0);
		pane.add(orderStatusLblVal, 5, 0);

		pane.add(timeSinceOrderLbl, 6, 0);
		pane.add(timeSinceOrderLblVal, 7, 0);

		return pane;
	}
	
	private Node buildCustomerPane() {
		BorderPane pane = new BorderPane();

		Label customerLbl = new Label("Customer");
		customerLbl.setMaxWidth(Double.MAX_VALUE);
		customerLbl.setAlignment(Pos.CENTER);
		customerLbl.setStyle("-fx-background-color: #ddd;");
		customerLbl.setPrefHeight(30);
		
		GridPane customerPane = new GridPane();
		customerPane.setAlignment(Pos.CENTER);
		customerPane.setHgap(5);
		customerPane.setVgap(5);
		
		Label titleLbl = new Label("Title:");
		Label forenameLbl = new Label("Forename:");
		Label surnameLbl = new Label("Surname:");
		Label emailLbl = new Label("Email:");
		Label telLbl = new Label("Tel:");
		
		GridPane.setConstraints(titleLbl, 			0, 1);
		GridPane.setConstraints(titleLblVal, 		1, 1);
		GridPane.setConstraints(forenameLbl, 		0, 2);
		GridPane.setConstraints(forenameLblVal, 	1, 2);
		GridPane.setConstraints(surnameLbl, 		0, 3);
		GridPane.setConstraints(surnameLblVal, 		1, 3);
		GridPane.setConstraints(emailLbl, 			0, 4);
		GridPane.setConstraints(emailLblVal, 		1, 4);
		GridPane.setConstraints(telLbl, 			0, 5);
		GridPane.setConstraints(telLblVal, 			1, 5);
		
		customerPane.getChildren().addAll(customerLbl, titleLbl, titleLblVal, forenameLbl, forenameLblVal, surnameLbl, surnameLblVal, emailLbl, emailLblVal, telLbl, telLblVal);
		
		pane.setTop(customerLbl);
		pane.setCenter(customerPane);
		
		emailBtn.setPrefWidth(Double.MAX_VALUE);
		pane.setBottom(emailBtn);
		
		return pane;
	}
	
	private Node buildShippingPane() {
		BorderPane pane = new BorderPane();
		
		Label shippingLbl = new Label("Shipping");
		shippingLbl.setMaxWidth(Double.MAX_VALUE);
		shippingLbl.setAlignment(Pos.CENTER);
		shippingLbl.setStyle("-fx-background-color: #ddd;");
		shippingLbl.setPrefHeight(30);

		
		GridPane shippingPane = new GridPane();
		shippingPane.setAlignment(Pos.CENTER);
		addressText.setStyle("-fx-background-color: #444;");
		addressText.setText("21 Findhorn Place\nEast Kilbride");
		GridPane.setConstraints(addressText, 		0, 0);
		
		shippingPane.getChildren().addAll(addressText);
		
		pane.setTop(shippingLbl);
		pane.setCenter(shippingPane);
		
		
		copyAddressBtn.setPrefWidth(Double.MAX_VALUE);
		pane.setBottom(copyAddressBtn);
		
		return pane;
	}

	private Node buildTotalsPane() {
		BorderPane pane = new BorderPane();
		
		Label totalsLbl = new Label("Totals");
		totalsLbl.setMaxWidth(Double.MAX_VALUE);
		totalsLbl.setAlignment(Pos.CENTER);
		totalsLbl.setStyle("-fx-background-color: #ddd;");
		totalsLbl.setPrefHeight(30);
		
		GridPane gpane = new GridPane();
		gpane.setPadding(new Insets(5));
		gpane.setAlignment(Pos.CENTER);
		gpane.setHgap(5);
		gpane.setVgap(5);
		
		Label subTotalAmountLbl = new Label("Sub-total:");
		Label discountCodeLbl = new Label("Discount Code:");
		Label discountAmountLbl = new Label("Discount:");
		Label postageAndPackingAmountLbl = new Label("Postage and Packing:");
		Label vatAmountLbl = new Label("Value Added Tax:");
		Label totalAmountLbl = new Label("Total:");
		
		GridPane.setConstraints(subTotalAmountLbl, 0, 0);
		GridPane.setConstraints(subTotalAmountLblVal, 1, 0);
		GridPane.setConstraints(discountCodeLbl, 0, 1);
		GridPane.setConstraints(discountCodeLblVal, 1, 1);
		GridPane.setConstraints(discountAmountLbl, 0, 2);
		GridPane.setConstraints(discountAmountLblVal, 1, 2);
		GridPane.setConstraints(postageAndPackingAmountLbl, 0, 3);
		GridPane.setConstraints(postageAndPackingAmountLblVal, 1, 3);
		GridPane.setConstraints(vatAmountLbl, 0, 4);
		GridPane.setConstraints(vatAmountLblVal, 1, 4);
		GridPane.setConstraints(totalAmountLbl, 0, 5);
		GridPane.setConstraints(totalAmountLblVal, 1, 5);
		
		gpane.getChildren().addAll(
				totalsLbl, subTotalAmountLbl, subTotalAmountLblVal, vatAmountLbl, vatAmountLblVal, 
				discountCodeLbl, discountCodeLblVal, discountAmountLbl, discountAmountLblVal, 
				postageAndPackingAmountLbl, postageAndPackingAmountLblVal, totalAmountLbl, totalAmountLblVal);
		
		pane.setTop(totalsLbl);
		pane.setCenter(gpane);
		
		printInvoiceBtn.setPrefWidth(Double.MAX_VALUE);
		pane.setBottom(printInvoiceBtn);
		
		return pane;
	}
	
	private Node buildPaymentProviderDetailsNode() {
		final TitledPane tp = new TitledPane();
		tp.setExpanded(false);
		tp.setText("Payment Provider");
		
		GridPane gpane = new GridPane();
		gpane.setPadding(new Insets(5));
		gpane.setAlignment(Pos.CENTER);
		gpane.setVgap(5);
		gpane.setHgap(5);
		
		Label paymentProcessorLbl = new Label("Payment Processor:");
		Label payerIdLbl = new Label("Payer ID:");
		Label transactionIdLbl = new Label("Transaction ID:");
		Label paymentDateLbl = new Label("Payment Date:");
		Label paymentStatusLbl = new Label("Payment Status:");
		Label pendingReasonLbl = new Label("Pending Reason:");
		
		gpane.add(paymentProcessorLbl, 0, 0);
		gpane.add(paymentProcessorLblVal, 1, 0);
		gpane.add(payerIdLbl, 0, 1);
		gpane.add(payerIdLblVal, 1, 1);
		gpane.add(transactionIdLbl, 0, 2);
		gpane.add(transactionIdLblVal, 1, 2);
		gpane.add(paymentDateLbl, 0, 3);
		gpane.add(paymentDateLblVal, 1, 3);
		gpane.add(paymentStatusLbl, 0, 4);
		gpane.add(paymentStatusLblVal, 1, 4);
		gpane.add(pendingReasonLbl, 0, 5);
		gpane.add(pendingReasonValLbl, 1, 5);
		
		tp.setContent(gpane);
		
		return tp;
	}

	private Node buildTablePane() {
		orderItemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn<OrderItem, String> productNameCol = new TableColumn<OrderItem, String>("Product Name");
		productNameCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("productName"));
		
		TableColumn<OrderItem, String> productSkuCol = new TableColumn<OrderItem, String>("SKU");
		productSkuCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("productSku"));
		
		TableColumn<OrderItem, Integer> quantityCol = new TableColumn<OrderItem, Integer>("Quantity");
		quantityCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("quantity"));
		
		TableColumn<OrderItem, Integer> locationCol = new TableColumn<OrderItem, Integer>("Location");
		locationCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("storageLocation"));
		
		TableColumn<OrderItem, BigDecimal> unitPriceCol = new TableColumn<OrderItem, BigDecimal>("Unit Price");
		unitPriceCol.setCellValueFactory(new PropertyValueFactory<OrderItem, BigDecimal>("unitPrice"));
		
		TableColumn<OrderItem, BigDecimal> totalPriceCol = new TableColumn<OrderItem, BigDecimal>("Total Price");
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<OrderItem, BigDecimal>("totalPrice"));
		
		TableColumn<OrderItem, String> currencyCodeCol = new TableColumn<OrderItem, String>("Currency Code");
		currencyCodeCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("currencyCode"));
		
		orderItemTable.getColumns().add(productNameCol);
		orderItemTable.getColumns().add(productSkuCol);
		orderItemTable.getColumns().add(quantityCol);
		orderItemTable.getColumns().add(locationCol);
		orderItemTable.getColumns().add(unitPriceCol);
		orderItemTable.getColumns().add(totalPriceCol);
		orderItemTable.getColumns().add(currencyCodeCol);
		
		return orderItemTable;
	}

	private Node buildButtonPane() {
		FlowPane pane = FlowPaneBuilder.create()
							.hgap(5)
							.vgap(5)
							.padding(new Insets(5))
							.orientation(Orientation.HORIZONTAL)
							.alignment(Pos.CENTER_RIGHT)
							.build();
		
		printPackingSlipBtn.setCursor(Cursor.HAND);
		printPackingSlipBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Order order = singleOrderPageModel.getOrder();
					Company c = singleOrderPageModel.getCompany();
					
	                File packingSlip = PackingSlipCreator.createPackingSlip(order, c);
	                
	                if (Desktop.isDesktopSupported()) {
	                	Desktop desktop = Desktop.getDesktop();
	                	desktop.open(packingSlip);
	                }
                } catch (Exception e) {
	                e.printStackTrace();
                }
				// TODO change status to printed
			}
		});
		
		printShippingAddresslabelBtn.setCursor(Cursor.HAND);
		printShippingAddresslabelBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("printShippingAddresslabelBtn");
				
				try {
					Order order = singleOrderPageModel.getOrder();
					
					ShippingAddressLabelPrinter p = new ShippingAddressLabelPrinter(order.getShippingAddress(), false);
					p.createLabel();

				} catch (Exception e) {
	                e.printStackTrace();
                }
			}
		});
		
		printReturnAddresslabelBtn.setCursor(Cursor.HAND);
		printReturnAddresslabelBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("printReturnAddresslabelBtn");
				
				try {
					singleOrderPageModel.getOrder();

				} catch (Exception e) {
	                e.printStackTrace();
                }
			}
		});
		
		printCN22Btn.setCursor(Cursor.HAND);
		printCN22Btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("printCN22Btn");
				
				try {
					Map<CN22Field, String> cn22FieldValues = singleOrderPageModel.getCN22FieldValues();
					
					AcroFormPrinter afp  = new AcroFormPrinter();
					File packingSlip = afp.createCN22Form(cn22FieldValues);
	                
	                if (Desktop.isDesktopSupported()) {
	                	Desktop desktop = Desktop.getDesktop();
	                	desktop.open(packingSlip);
	                }
				} catch (Exception e) {
	                e.printStackTrace();
                }
			}
		});
		
		printCN23Btn.setCursor(Cursor.HAND);
		printCN23Btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("printCN23Btn");
				
				try {
					try {
						Map<CN23Field, String> cn23FieldValues = singleOrderPageModel.getCN23FieldValues();
						
						AcroFormPrinter afp  = new AcroFormPrinter();
						File packingSlip = afp.createCN23Form(cn23FieldValues);
		                
		                if (Desktop.isDesktopSupported()) {
		                	Desktop desktop = Desktop.getDesktop();
		                	desktop.open(packingSlip);
		                }
					} catch (Exception e) {
		                e.printStackTrace();
	                }

				} catch (Exception e) {
	                e.printStackTrace();
                }
			}
		});
		
		printAllBtn.setCursor(Cursor.HAND);
		printAllBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("printAllBtn");
				
				try {
					singleOrderPageModel.getOrder();

				} catch (Exception e) {
	                e.printStackTrace();
                }
			}
		});
		
		HBox hbox = new HBox();
		HBox.setHgrow(printPackingSlipBtn, Priority.ALWAYS);
		HBox.setHgrow(printShippingAddresslabelBtn, Priority.ALWAYS);
		HBox.setHgrow(printReturnAddresslabelBtn, Priority.ALWAYS);
		HBox.setHgrow(printAllBtn, Priority.ALWAYS);
		HBox.setHgrow(printCN22Btn, Priority.ALWAYS);
		HBox.setHgrow(printCN23Btn, Priority.ALWAYS);
		printPackingSlipBtn.setMaxWidth(Double.MAX_VALUE);
		printShippingAddresslabelBtn.setMaxWidth(Double.MAX_VALUE);
		printReturnAddresslabelBtn.setMaxWidth(Double.MAX_VALUE);
		printCN22Btn.setMaxWidth(Double.MAX_VALUE);
		printCN23Btn.setMaxWidth(Double.MAX_VALUE);
		printAllBtn.setMaxWidth(Double.MAX_VALUE);
		
		hbox.getChildren().addAll(printPackingSlipBtn, printShippingAddresslabelBtn, printReturnAddresslabelBtn, printCN22Btn, printCN23Btn, printAllBtn);
		
		TilePane tile = new TilePane();
		tile.setPadding(new Insets(5, 0, 5, 0));
		tile.setVgap(4);
		tile.setHgap(4);
		tile.setPrefColumns(6);
		tile.getChildren().addAll(printPackingSlipBtn, printShippingAddresslabelBtn, printReturnAddresslabelBtn, printCN22Btn, printCN23Btn, printAllBtn);
		
		pane.getChildren().addAll(tile);
//		pane.getChildren().addAll(printPackingSlipBtn, printShippingAddresslabelBtn, printReturnAddresslabelBtn, printAllBtn);
//		LayoutUtil.equaliseButtonSizes(printPackingSlipBtn, printShippingAddresslabelBtn, printReturnAddresslabelBtn, printAllBtn);
		
		return pane;
	}
	
	public void setOrderSummary(OrderSummary orderSummary) throws Exception {
		System.out.println("SET Order = " + orderSummary.getOrderId());

		Order order = singleOrderPageModel.getOrder(orderSummary);

		// basic info
		orderIdLblVal.setText(order.getOrderId());
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		dateCreatedLblVal.setText(df.format(order.getDateCreated()));
		orderStatusLblVal.setText(order.getOrderStatus().getStatusDescription());
		long diff = System.currentTimeMillis() - order.getDateCreated().getTime();
		timeSinceOrderLblVal.setText(this.getDurationBreakdown(diff));
		
		// customer
		titleLblVal.setText(order.getCustomer().getTitle());
		forenameLblVal.setText(order.getCustomer().getForename());
		surnameLblVal.setText(order.getCustomer().getSurname());
		emailLblVal.setText(order.getCustomer().getEmail());
		telLblVal.setText(order.getCustomer().getPhoneNumber());
		
		// shippping address
		addressText.setText(order.getShippingAddress().getAddressStringFormatted());
		
		// order totals
		subTotalAmountLblVal.setText(order.getSubTotalAmount().toString());
		discountCodeLblVal.setText(order.getDiscountCode());
		if (order.getDiscountAmount() != null) {
			discountAmountLblVal.setText(order.getDiscountAmount().toString());	
		}
		postageAndPackingAmountLblVal.setText(order.getPostageAndPackingAmount().toString());
		vatAmountLblVal.setText(order.getVatAmount().toString());
		totalAmountLblVal.setText(order.getTotalAmount().toString());
		
		// order processor details
		PaypalTransactionValues paypalTransactionValues = order.getPaypalTransactionValues();
		paymentProcessorLblVal.setText(paypalTransactionValues.getPaymentProcessor());
		payerIdLblVal.setText(paypalTransactionValues.getPaypalPayerId());
		transactionIdLblVal.setText(paypalTransactionValues.getPaypalTransationId());

		paymentDateLblVal.setText(df.format(paypalTransactionValues.getPaypalPaymentDate()));
		paymentStatusLblVal.setText(paypalTransactionValues.getPaypalPaymentStatus());
		pendingReasonValLbl.setText(paypalTransactionValues.getPayaplPendingReason());
		
		// order items in table
		ObservableList<OrderItem> orderItems = FXCollections.observableArrayList(order.getOrderItems());
		orderItemTable.setItems(orderItems);
		
		
		// enable and disable buttons as required
		
		
		CustomsDeclaration requiredCustomsDeclaration = singleOrderPageModel.getRequiredCustomsDeclaration();
		switch (requiredCustomsDeclaration) {
		case NONE:
			printCN22Btn.setDisable(true);
			printCN23Btn.setDisable(true);
			break;
		case CN22:
			printCN23Btn.setDisable(true);
			break;
		case CN23:
			printCN22Btn.setDisable(true);
			break;
		}
	}

	public String getDurationBreakdown(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);

		StringBuilder sb = new StringBuilder(64);
		sb.append(days);
		sb.append("d ");
		sb.append(hours);
		sb.append("h ");
		sb.append(minutes);
		sb.append("m");

		return (sb.toString());
	}

}
