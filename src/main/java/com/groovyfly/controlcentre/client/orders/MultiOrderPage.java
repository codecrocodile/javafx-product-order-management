package com.groovyfly.controlcentre.client.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import org.springframework.beans.factory.annotation.Autowired;

import com.groovyfly.controlcentre.application.Page;
import com.groovyfly.controlcentre.client.orders.MultiOrderPageModel.UpdateEvent;
import com.groovyfly.controlcentre.structure.common.orders.OrderStatus;
import com.groovyfly.controlcentre.structure.common.orders.OrderSummary;

/**
 * 
 * @author Chris Hatton
 */
public class MultiOrderPage extends Page implements Observer {

	private MultiOrderPageModel ordersPageModel;
	
	private Map<OrderStatus, TableView<OrderSummary>> orderTableMap = new HashMap<>();
	
	private Map<OrderStatus, Tab> orderTabMap = new HashMap<>();

	private OrderSearchParamPane searchPane;

	/**
	 * Constructor
	 */
	public MultiOrderPage() {
		super();
	}

	@Autowired
	public void setOrdersPageModel(MultiOrderPageModel ordersPageModel) {
		this.ordersPageModel = ordersPageModel;
		ordersPageModel.addObserver(this);
	}

	@Override
	public Node createView() throws Exception {
		BorderPane pane = new BorderPane();

		pane.setTop(this.buildTop());
		pane.setCenter(this.builCenter());
		pane.setBottom(this.buildBottom());

		return pane;
	}

	private Node buildTop() {
		final TitledPane tp = new TitledPane();
		tp.setText("Search Conditions");

		Button resetBtn = new Button("Reset");
		resetBtn.setCursor(Cursor.HAND);
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				MultiOrderPage.this.searchPane.resetFields();
			}

		});

		Button searchBtn = new Button("Search");
		searchBtn.setCursor(Cursor.HAND);
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				
				Map<OrderStatus, ObservableList<OrderSummary>> orders = null;
				try {
					orders = ordersPageModel.getOrders(MultiOrderPage.this.searchPane.getOrderSearchParam());
					
					for (Entry<OrderStatus, ObservableList<OrderSummary>> e : orders.entrySet()) {
						TableView<OrderSummary> tableView = orderTableMap.get(e.getKey());
						tableView.setItems(e.getValue());
					}
					
					changeTabNames();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO show error dialog
				}

				new Thread(new Runnable() {
					public void run() {
						Platform.runLater(new Runnable() {
							public void run() {
								tp.setExpanded(false);
							}
						});
					}
				}).start();
			}
		});

		
		BorderPane bp = new BorderPane();
		
		
		List<OrderStatus> orderStatuses = new ArrayList<>();
        try {
	        orderStatuses = this.ordersPageModel.getOrderStatuses();
        } catch (Exception e) {
	        e.printStackTrace();
	        
	        // TODO show dialog
        }
		searchPane = new OrderSearchParamPane(orderStatuses);
		
		HBox btnContainer = new HBox();
		btnContainer.setPadding(new Insets(10, 10, 10, 10));
		btnContainer.setSpacing(5);
		btnContainer.setAlignment(Pos.BOTTOM_RIGHT);
		resetBtn.setMinWidth(70);
		searchBtn.setMinWidth(70);
		btnContainer.getChildren().addAll(resetBtn, searchBtn);
		
		bp.setCenter(searchPane.getView());
		bp.setBottom(btnContainer);

		tp.setContent(bp);

		return tp;
	}

	private Node builCenter() {
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.setTabMinWidth(100);
		
		List<OrderStatus> orderStatuses = null;
        try {
	        orderStatuses = this.ordersPageModel.getOrderStatuses();
        } catch (Exception e) {
	        e.printStackTrace();
	        
	        // TODO show dialog
        }

        List<Tab> tabs = new ArrayList<>();
        for (OrderStatus os : orderStatuses) {
        	tabs.add(this.getOnOrderTab(os, orderStatuses));
        }
        
        tabPane.getTabs().addAll(tabs);

		return tabPane;
	}

	private Tab getOnOrderTab(OrderStatus orderStatus, List<OrderStatus> orderStatuses) {
		Tab orderTab = new Tab(orderStatus.getStatusDescription() + " ( 0 )");

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10));

		TableView<OrderSummary> orderTable = OrderTableFactory.createOrderTable(this, ordersPageModel, orderStatuses, orderStatus);
		orderTableMap.put(orderStatus, orderTable);

		pane.setCenter(orderTable);

		orderTab.setContent(pane);
		
		this.orderTabMap.put(orderStatus, orderTab);

		return orderTab;
	}

	private Node buildBottom() {
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		btnBox.setPadding(new Insets(10));
		btnBox.setSpacing(5);

		Button saveBtn = new Button("Save");
		saveBtn.setPrefWidth(100);
		saveBtn.setCursor(Cursor.HAND);
		saveBtn.setDisable(true);

		Button cancelBtn = new Button("Cancel");
		cancelBtn.setPrefWidth(100);
		cancelBtn.setCursor(Cursor.HAND);
		cancelBtn.setDisable(true);

		btnBox.getChildren().addAll(saveBtn, cancelBtn);

		return btnBox;
	}
	
	private void changeTabNames() {
		for (Entry<OrderStatus, ObservableList<OrderSummary>> e : ordersPageModel.getOrderSummaryMap().entrySet()) {
    		orderTabMap.get(e.getKey()).setText(e.getKey().getStatusDescription() + " ( " + e.getValue().size() + " )");
    	}	
	}

	/* 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
    @Override
    public void update(Observable o, Object arg) {
    	MultiOrderPageModel.UpdateEvent ue = (MultiOrderPageModel.UpdateEvent) arg;
    	
    	if (ue == UpdateEvent.STATUS_CHANGE) {
    		changeTabNames();
    	}
    }
	
}
