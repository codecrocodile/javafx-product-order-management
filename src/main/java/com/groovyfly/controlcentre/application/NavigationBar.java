/*
 * @(#)NavigationBar.java 31 Mar 2013
 * 
 * Copyright (c) 2012-2013 Groovy Fly. 3 Aillort place, East Mains, East
 * Kilbride, Scotland. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Groovy Fly.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Groovy Fly.
 */
package com.groovyfly.controlcentre.application;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import com.groovyfly.controlcentre.application.NavigationManager.HistoryAction;

/**
 * Navigation bar sits at the top of the application frame and holds a menu and tool bar for navigation.
 * 
 * @author Chris Hatton
 */
public class NavigationBar implements Observer {

	private HBox toolbar;

	private Button backBtn;

	private Button forwardBtn;
	
	private Button homeBtn;

	private NavigationManager navigationManager;
	
	
	/**
     * Constructor
     */
    public NavigationBar() {
		super();
    }
    
	public NavigationManager getNavigationManager() {
		return navigationManager;
	}

	public void setNavigationManager(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}

	/*
	 * @see com.groovyfly.controlcentre.client.Page#createView()
	 */
	protected Node createView() throws Exception {
		VBox hbox = new VBox();
		
		MenuBar menuBar = new MenuBar();
		
		Menu salesMenu = buildSalesMenu();
		Menu siteManagement = this.buildSiteManagementMenu();
		Menu reportsMenu = buildReportsMenu();

		menuBar.getMenus().addAll(salesMenu, siteManagement, reportsMenu);

		Node navigationBar = this.createNavigationBar();
		
		hbox.getChildren().addAll(menuBar, navigationBar);

		return hbox;
	}

	private Node createNavigationBar() {
		toolbar = new HBox(5);
		toolbar.setId("app-toolbar");

		ImageView backView = new ImageView("META-INF/images/back.png");
		backView.setFitHeight(20);
		backView.setPreserveRatio(true);
		backBtn = new Button("", backView);
		backBtn.setCursor(Cursor.HAND);
		backBtn.setDisable(true);
		backBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				navigationManager.goBack();
			}
		});

		ImageView forwardView = new ImageView("META-INF/images/forward.png");
		forwardView.setFitHeight(20);
		forwardView.setPreserveRatio(true);
		forwardBtn = new Button("", forwardView);
		forwardBtn.setCursor(Cursor.HAND);
		forwardBtn.setDisable(true);
		forwardBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				navigationManager.goForward();
			}
		});

		ImageView imageView = new ImageView("META-INF/images/home.png");
		imageView.setFitHeight(20);
		imageView.setPreserveRatio(true);
		homeBtn = new Button("", imageView);
		homeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				navigationManager.requestPageChange("HomePage", HistoryAction.CLEAR_HISTORY, true);
			}
		});
		homeBtn.setCursor(Cursor.HAND);
		
		Region spacerBox = new Region();
		spacerBox.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(spacerBox, Priority.ALWAYS);
		
		toolbar.getChildren().addAll(backBtn, forwardBtn, spacerBox, homeBtn);

		return toolbar;
	}
	
	private Menu buildSalesMenu() {
		MenuItem processOrdersMenuItem = new MenuItem("Orders");
		processOrdersMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				navigationManager.requestPageChange("MultiOrderPage", HistoryAction.CLEAR_HISTORY, true);
			}

		});

		Menu salesMenu = MenuBuilder.create().text("Sales").items(processOrdersMenuItem).build();
		
		return salesMenu;
	}
	
	private Menu buildSiteManagementMenu() {
		MenuItem newsFeedMenuItem = new MenuItem("News Feed");
		newsFeedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent event) {
		    	navigationManager.requestPageChange("NewsFeedPage", HistoryAction.CLEAR_HISTORY, true);
		    }
		});
		MenuItem pageManagementMenuItem = new MenuItem("Page Management");
		pageManagementMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent event) {
		    	navigationManager.requestPageChange("WebPageManagementPage", HistoryAction.CLEAR_HISTORY, true);
		    }
		});
		
		
		MenuItem productsMenuItem = new MenuItem("Products");
		productsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent event) {
		    	navigationManager.requestPageChange("ProductsPage", HistoryAction.CLEAR_HISTORY, true);
		    }
		});
		Menu productManagementMenu = MenuBuilder.create().text("Product Management").items(productsMenuItem).build();
		
		
		MenuItem blobStoreViewerMenuItem = new MenuItem("Blob Store Viewer");
		blobStoreViewerMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent event) {
		    	navigationManager.requestPageChange("BlobstoreViewerPage", HistoryAction.CLEAR_HISTORY, true);
		    }
		});
		MenuItem capabilityCheckMenuItem = new MenuItem("Capability Check");
		capabilityCheckMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent event) {
		    	navigationManager.requestPageChange("CapabilityCheckPage", HistoryAction.CLEAR_HISTORY, true);
		    }
		});
		Menu googleAppEngineMenu = MenuBuilder.create().text("Google App Engine").items(blobStoreViewerMenuItem, capabilityCheckMenuItem).build();
		
		Menu siteManagementMenu = MenuBuilder.create().text("Site Management").items(newsFeedMenuItem, pageManagementMenuItem, productManagementMenu, googleAppEngineMenu).build();

		return siteManagementMenu;
	}
	
	private Menu buildReportsMenu() {
		MenuItem monthlySalesMenuItem = new MenuItem("Monthly Sales Report");
		monthlySalesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent event) {
		    	navigationManager.requestPageChange("SalesReportMonthly", HistoryAction.CLEAR_HISTORY, true);
		    }
		});
		Menu reportsMenu = MenuBuilder.create().text("Reports").items(monthlySalesMenuItem).build();

		return reportsMenu;
	}

	/*
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof NavigationManager) {
			NavigationManager navMan = (NavigationManager) o;

			NavigationManager.UpdateEvent event = (NavigationManager.UpdateEvent) arg;
			if (event == NavigationManager.UpdateEvent.PAGE_CHANGE) {
				boolean canGoForward = navMan.canGoForward();
				if (canGoForward) {
					forwardBtn.setDisable(false);
				} else {
					forwardBtn.setDisable(true);
				}
				boolean canGoBack = navMan.canGoBack();
				if (canGoBack) {
					backBtn.setDisable(false);
				} else {
					backBtn.setDisable(true);
				}

			}
		}
	}

}
