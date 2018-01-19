/*
 * @(#)PageManagementPage.java			22 Mar 2013
 *
 * Copyright (c) 2012-2013 Groovy Fly.
 * 3 Aillort place, East Mains, East Kilbride, Scotland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Groovy 
 * Fly. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Groovy Fly.
 */
package com.groovyfly.controlcentre.client.sitemanagement.pages;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBuilder;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;

import com.groovyfly.controlcentre.application.Page;
import com.groovyfly.controlcentre.application.NavigationManager.HistoryAction;
import com.groovyfly.controlcentre.structure.sitemanagement.WebPage;

/**
 * @author Chris Hatton
 */
public class WebPageManagementPage extends Page {
	
	@Autowired
	private WebPageManagementPageModel webPageManagementPageModel;
	
	private TableView<WebPage> resultsTable;

	/* 
	 * @see com.groovyfly.controlcentre.client.Page#createView()
	 */
    @Override
    protected Node createView() throws Exception {
    	BorderPane pane = new BorderPane();
    	
    	pane.setTop(buildSearchNode());
    	pane.setCenter(buildResultsTableNode());
    	
	    return pane;
    }
    
    private Node buildSearchNode() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10));
		
    	Button b = new Button("Show All Web Pages");
    	b.setCursor(Cursor.HAND);
    	b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				try {
					WebPageSearchParam searchParam = new WebPageSearchParam();
					
	                List<WebPage> pages = webPageManagementPageModel.getWebPages(searchParam);
	                
	                ObservableList<WebPage> observableArrayList = FXCollections.observableArrayList(pages);
	                resultsTable.setItems(observableArrayList);
                } catch (Exception e) {
	                e.printStackTrace();
                }
			}
		});
    	
    	pane.setCenter(b);
		
		return pane;
	}
    
    private Node buildResultsTableNode() {
    	BorderPane pane = new BorderPane();
    	
		resultsTable = new TableView<WebPage>();
		resultsTable.setTableMenuButtonVisible(true);
		resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		resultsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		ImageView emptyTableImg = new ImageView("META-INF/images/empty-table.png");
		resultsTable.setPlaceholder(emptyTableImg);
		
		TableColumn<WebPage, Integer> col0 = TableColumnBuilder.<WebPage, Integer>create() // strange syntax but it works
				.text("Page Id") // table column name for display
				.cellValueFactory(new PropertyValueFactory<WebPage, Integer>("pageId")) // factory for getting property value to display
				.visible(false)
				.build();
		TableColumn<WebPage, String> col1 = TableColumnBuilder.<WebPage, String>create() 
				.text("URL Alias") 
				.cellValueFactory(new PropertyValueFactory<WebPage, String>("urlAlias")) 
				.build();
		TableColumn<WebPage, String> col2 = TableColumnBuilder.<WebPage, String>create() 
				.text("Title") // table column name for display
				.cellValueFactory(new PropertyValueFactory<WebPage, String>("title"))
				.build();
		
		TableColumn<WebPage, Integer> drillCol = new TableColumn<WebPage, Integer>("Drill");
		drillCol.setCellValueFactory(new PropertyValueFactory<WebPage, Integer>("pageId"));
		drillCol.setEditable(true);

		Callback<TableColumn<WebPage, Integer>, TableCell<WebPage, Integer>> drillColumnCellFactory =
		        new Callback<TableColumn<WebPage, Integer>, TableCell<WebPage, Integer>>() {

			        public TableCell<WebPage, Integer> call(final TableColumn<WebPage, Integer> param) {

				        final TableCell<WebPage, Integer> cell = new TableCell<WebPage, Integer>() {

					        public void updateItem(Integer item, boolean empty) {
						        super.updateItem(item, empty);

						        if (empty) {
							        setText(null);
							        setGraphic(null);
						        } else {

						        	ImageView drillImg = new ImageView("META-INF/images/magnifier--arrow.png");
							        final Button btnPrint = new Button("", drillImg);
							        btnPrint.setCursor(Cursor.HAND);
							        btnPrint.setAlignment(Pos.CENTER);
							        btnPrint.setOnAction(new EventHandler<ActionEvent>() {

								        public void handle(ActionEvent event) {

									        param.getTableView().getSelectionModel().select(getIndex());

									        WebPage item = resultsTable.getSelectionModel().getSelectedItem();

									        if (item != null) {
									        	EditWebPagePage singleOrderPage = (EditWebPagePage) WebPageManagementPage.this.getNavigationManager().requestPageChange("EditWebPagePage", HistoryAction.CLEAR_FORWARD_HISTORY, true);
										        
										        try {
										        	singleOrderPage.setWebPage(item);
										        } catch (Exception e) {
											        e.printStackTrace();
										        }
									        }

								        }
							        });

							        this.setAlignment(Pos.CENTER);
							        setGraphic(btnPrint);
							        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						        }
					        }
				        };
				        
				        return cell;
			        }
		        };
		drillCol.setCellFactory(drillColumnCellFactory);
		
		resultsTable.getColumns().add(col0);
		resultsTable.getColumns().add(col1);
		resultsTable.getColumns().add(col2);
		resultsTable.getColumns().add(drillCol);
		
		pane.setCenter(resultsTable);
		
		return pane;
    }

    /*
     * @see com.groovyfly.controlcentre.client.Page#refreshView()
     */
	@Override
    public void refreshView() {
		try {
			
			final List<WebPage> pages = webPageManagementPageModel.getWebPages(null);
			 
			// bug in the javafx table view. this seems to sort it.
			resultsTable.setItems(null); 
			resultsTable.layout(); 
			resultsTable.setItems(FXCollections.observableList(pages)); 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
