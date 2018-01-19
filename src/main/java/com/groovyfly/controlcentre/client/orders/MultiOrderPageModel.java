package com.groovyfly.controlcentre.client.orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.groovyfly.controlcentre.dao.mysql.OrderDAO;
import com.groovyfly.controlcentre.service.orders.OrderService;
import com.groovyfly.controlcentre.structure.common.orders.OrderSearchParam;
import com.groovyfly.controlcentre.structure.common.orders.OrderStatus;
import com.groovyfly.controlcentre.structure.common.orders.OrderSummary;

@Component("MultiOrderPageModel")
@Scope("prototype")
public class MultiOrderPageModel extends Observable {
	
	public enum UpdateEvent {
		STATUS_CHANGE
	}

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderService orderService; 
	
	private Map<OrderStatus, ObservableList<OrderSummary>> orderSummaryMap;
	
	private List<OrderStatus> orderStatuses;
	

	/**
	 * Constructor
	 */
	public MultiOrderPageModel() {
		// TODO Auto-generated constructor stub
	}

	public Map<OrderStatus, ObservableList<OrderSummary>> getOrders(OrderSearchParam searchParam) throws Exception {
		
		orderSummaryMap = new HashMap<>();
		
		List<OrderStatus> orderStatuses = this.getOrderStatuses();
		for (OrderStatus os : orderStatuses) {
			ObservableList<OrderSummary> observableArrayList = FXCollections.observableArrayList();
			
			orderSummaryMap.put(os, observableArrayList);
		}
		
		List<OrderSummary> orderSummaries = orderDAO.searchOrderSummary(searchParam);
		
		for (OrderSummary os : orderSummaries) {
			
			System.out.println("adding summary ");
			orderSummaryMap.get(os.getOrderStatus()).add(os);
		}

		return orderSummaryMap;
	}
	
	public Map<OrderStatus, ObservableList<OrderSummary>> getOrderSummaryMap() {
		return orderSummaryMap;
	}
	
	
	public List<OrderStatus> getOrderStatuses() throws Exception {
		if (this.orderStatuses == null) {
			this.orderStatuses = this.orderService.getOrderStatuses();
		}
		
		return this.orderStatuses;
	}
	
	public void doOrderSummaryStatusChange(OrderSummary orderSummary, OrderStatus oldStatus, OrderStatus newStatus) throws Exception {
		orderDAO.changeOrderStatus(orderSummary.getOrderId(), newStatus);
		
		ObservableList<OrderSummary> originList = orderSummaryMap.get(oldStatus);
		originList.remove(orderSummary);
		
		ObservableList<OrderSummary> destinationList = orderSummaryMap.get(newStatus);
		destinationList.add(orderSummary);
		
		setChanged();
		notifyObservers(UpdateEvent.STATUS_CHANGE);
	}
	
}
