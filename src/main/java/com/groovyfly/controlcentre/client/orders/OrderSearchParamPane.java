/*
 * Copyright GroovyFly.com OrderSearchParamNode.java
 */
package com.groovyfly.controlcentre.client.orders;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import jfxtras.labs.scene.control.CalendarTextField;

import com.groovyfly.controlcentre.structure.common.orders.OrderSearchParam;
import com.groovyfly.controlcentre.structure.common.orders.OrderStatus;

/**
 * @author Chris Hatton
 */
public class OrderSearchParamPane {
	
	private TextField orderIdTxt;

	private TextField emailTxt;

	private CalendarTextField fromDatePicker;

	private CalendarTextField toDatePicker;
	
	private Map<OrderStatus, CheckBox> statusToCheckBoxMap = new LinkedHashMap<OrderStatus, CheckBox>();

	private DateFormat standDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	
	private List<OrderStatus> orderStatuses;
	
	/**
	 * Constructor
	 */
	public OrderSearchParamPane(List<OrderStatus> orderStatuses) {
		super();
		this.orderStatuses = orderStatuses;
	}

	/**
	 * Returns the constructed view.
	 * 
	 * @return Pane - containing all of the fields.
	 */
	public Pane getView() {
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(5));
		gridPane.setVgap(2);
		gridPane.setHgap(2);
		
		orderIdTxt = new TextField();
		Label orderIdLbl = new Label("Order Id:");
		orderIdLbl.setLabelFor(orderIdTxt);

		emailTxt = new TextField();
		Label emailLbl = new Label("Email:");
		emailLbl.setLabelFor(emailTxt);

		fromDatePicker = new CalendarTextField();
		fromDatePicker.setDateFormat(standDateFormat);
		fromDatePicker.setCursor(Cursor.HAND);
		Label fromDateLbl = new Label("Created After:");
		fromDateLbl.setLabelFor(fromDatePicker);

		toDatePicker = new CalendarTextField();
		toDatePicker.setDateFormat(standDateFormat);
		toDatePicker.setCursor(Cursor.HAND);
		Label toDateLbl = new Label("Created Before:");
		toDateLbl.setLabelFor(toDatePicker);

		
		for (OrderStatus os : orderStatuses) {
			CheckBox cb = new CheckBox(os.getStatusDescription());
			cb.setCursor(Cursor.HAND);
			
			statusToCheckBoxMap.put(os, cb);
		}
		
		resetFields();

		HBox chkContainer = new HBox();
		chkContainer.setSpacing(5);
		chkContainer.getChildren().addAll(statusToCheckBoxMap.values());
		
		gridPane.add(orderIdLbl, 0, 0);
		gridPane.add(orderIdTxt, 1, 0);

		gridPane.add(emailLbl, 0, 1);
		gridPane.add(emailTxt, 1, 1, 2, 1);

		gridPane.add(fromDateLbl, 0, 2);
		gridPane.add(fromDatePicker, 1, 2);
		gridPane.add(toDateLbl, 0, 3);
		gridPane.add(toDatePicker, 1, 3);

		gridPane.add(chkContainer, 1, 4, 2, 1);
		
		return gridPane;
	}
	
	/**
	 * Resets all of the fields to their default values.
	 */
	public void resetFields() {
		orderIdTxt.clear();
		emailTxt.clear();
		fromDatePicker.setValue(null);
		toDatePicker.setValue(null);

		for (Entry<OrderStatus, CheckBox> e : statusToCheckBoxMap.entrySet()) {
			e.getValue().setSelected(true);
			
			// unchecked if order cancelled or posted
			if (e.getKey().getStatusCode().equalsIgnoreCase("04") || e.getKey().getStatusCode().equalsIgnoreCase("06") ) {
				e.getValue().setSelected(false);	
			}
		}
	}
	
	/**
	 * Constructs a OrderSearchParam based on the field values.
	 * 
	 * @return OrderSearchParam
	 */
	public OrderSearchParam getOrderSearchParam() {
		OrderSearchParam searchParam = new OrderSearchParam();
		searchParam.setOrderId(orderIdTxt.getText().trim());
		searchParam.setEmailAddress(emailTxt.getText().trim());
		
		if (fromDatePicker.getValue() != null) {
			searchParam.setCreatedAfter(fromDatePicker.getValue().getTime());	
		}
		
		if (toDatePicker.getValue() != null) {
			searchParam.setCreatedBefore(toDatePicker.getValue().getTime());			
		}
		
		List<OrderStatus> statusList = new ArrayList<>();
		for (Entry<OrderStatus, CheckBox> e : statusToCheckBoxMap.entrySet()) {
			if (e.getValue().isSelected()) {
				statusList.add(e.getKey());
			}
		}
		searchParam.setOrderStatuses(statusList);
		
		return searchParam;
	}
	
}
