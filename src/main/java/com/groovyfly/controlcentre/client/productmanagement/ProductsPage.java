/*
 * @(#)Products.java			31 Mar 2013
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
package com.groovyfly.controlcentre.client.productmanagement;

import java.math.BigDecimal;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBuilder;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBuilder;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TooltipBuilder;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;

import com.groovyfly.controlcentre.application.NavigationManager.HistoryAction;
import com.groovyfly.controlcentre.application.Page;
import com.groovyfly.controlcentre.customcontrols.ComparisionButton;
import com.groovyfly.controlcentre.customcontrols.IntegerTextfield;
import com.groovyfly.controlcentre.structure.Category;
import com.groovyfly.controlcentre.structure.Product;
import com.groovyfly.controlcentre.structure.Supplier;

/**
 * @author Chris Hatton
 */
public class ProductsPage extends Page {
	
	@Autowired
	private ProductPageModel productPageModel;

	private TextField nameTxt;

	private IntegerTextfield stockLevelTxt;
	
	private ComparisionButton comparisionButton;
	
	private ComboBox<Category> categoryCbo;
	
	private ComboBox<Supplier> supplierCbo;	
	
	private Button addProductBtn;
	
	private Button addProductAndGroupingBtn;
	
	private TableView<Product> resultsTable;

	private Button searchBtn;


	/* 
	 * @see com.groovyfly.controlcentre.application.Page#createView()
	 */
    @Override
    protected Node createView() throws Exception {
    	BorderPane pane = new BorderPane();
    	pane.setPadding(new Insets(5));
    	
    	VBox vBox = new VBox();
    	vBox.getStyleClass().add("highlightedBackground");
    	vBox.getChildren().addAll(buildSearchNode(), buildActionBtnNode());
    	
    	pane.setTop(vBox);
    	pane.setCenter(buildTableNode());
    	
    	intiPage();
    	
	    return pane;
    }
    
    private void intiPage() {
    	try {
	        List<Supplier> suppliers = productPageModel.getSuppliers();
	        List<Category> categories = productPageModel.getCategories();
	        
	        ObservableList<Supplier> observableSuppliers = FXCollections.observableArrayList(suppliers);
	        supplierCbo.setItems(observableSuppliers);
	        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
	        categoryCbo.setItems(observableCategories);
        } catch (Exception e) {
	        e.printStackTrace();
        }
    }
    
    private Node buildSearchNode() {
    	GridPane pane = new GridPane();
    	pane.setHgap(3);
    	pane.setVgap(3);
    	
    	Label nameLbl = new Label("Name Contains:");
    	nameTxt = new TextField();
    	Label stockLevelLbl = new Label("Stock Level:");
    	stockLevelTxt = new IntegerTextfield(0, Integer.MAX_VALUE, 0);
    	comparisionButton = new ComparisionButton();
    	comparisionButton.setCursor(Cursor.HAND);
    	Label categoryLbl = new Label("Category:");
        categoryCbo = ComboBoxBuilder.<Category>create()
                .id("uneditable-combobox")
                .minWidth(200)
                .build();
    	Label supplierLbl = new Label("Supplier:");
        supplierCbo = ComboBoxBuilder.<Supplier>create()
                .id("uneditable-combobox")
                .minWidth(200)
                .build();
    	
    	ImageView searchImg = new ImageView("META-INF/images/magnifier.png");
    	searchBtn = new Button("", searchImg);
    	searchBtn.setCursor(Cursor.HAND);
    	searchBtn.setTooltip(TooltipBuilder.create().text("Search").build());
    	searchBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				ProductSearchParam productSearchParam = new ProductSearchParam();
				productSearchParam.setName(nameTxt.getText());
				productSearchParam.setOperator(comparisionButton.getOperator());
				productSearchParam.setStockLevel(Integer.valueOf(stockLevelTxt.getText()));
				productSearchParam.setCategory(categoryCbo.getSelectionModel().getSelectedItem());
				productSearchParam.setSupplier(supplierCbo.getSelectionModel().getSelectedItem());
				
				try {
	                List<Product> products = productPageModel.getProducts(productSearchParam);
	                ObservableList<Product> observableArrayList = FXCollections.observableArrayList(products);
	                resultsTable.setItems(observableArrayList);
                } catch (Exception e) {
	                e.printStackTrace();
                }
			}
			
		});
    	
    	pane.add(nameLbl, 0, 0);
    	pane.add(nameTxt, 1, 0, 2, 1);
    	pane.add(stockLevelLbl, 0, 1);
    	pane.add(comparisionButton, 1, 1);
    	pane.add(stockLevelTxt, 2, 1);
    	pane.add(categoryLbl, 0, 2);
    	pane.add(categoryCbo, 1, 2, 2, 1);
    	pane.add(supplierLbl, 0, 3);
    	pane.add(supplierCbo, 1, 3, 2, 1);
    	
    	GridPane.setHalignment(searchBtn, HPos.RIGHT);
    	GridPane.setMargin(searchBtn, new Insets(3, 0, 0, 0));
    	pane.add(searchBtn, 0, 4, 3, 1);
    	
    	return pane;
    }
    
    /*
     * 
     */
    private Node buildActionBtnNode() {
    	HBox hBox = new HBox(5);
    	hBox.setPadding(new Insets(3));
    	
    	Region spacer = new Region();
    	
    	ImageView plusDocImg = new ImageView("META-INF/images/blue-document--plus.png");
    	addProductBtn = new Button("", plusDocImg);
    	addProductBtn.setCursor(Cursor.HAND);
    	addProductBtn.setTooltip(new Tooltip("New Product"));
    	addProductBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("create new product");
			}
		});
    	
    	ImageView plusDocsImg = new ImageView("META-INF/images/blue-documents-stack-plus.png");
    	addProductAndGroupingBtn = new Button("", plusDocsImg);
    	addProductAndGroupingBtn.setCursor(Cursor.HAND);
    	addProductAndGroupingBtn.setTooltip(TooltipBuilder.create().text("New Product & Grouping").build());
    	addProductAndGroupingBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("create new product and grouping");
			}
		});
    	
    	HBox.setHgrow(spacer, Priority.ALWAYS);
    	
    	hBox.getChildren().addAll(spacer, addProductBtn, addProductAndGroupingBtn);
    	
    	return hBox;
    }
    
    /*
     * 
     */
    private Node buildTableNode() {
    	BorderPane pane = new BorderPane();
    	
		resultsTable = new TableView<Product>();
		resultsTable.setTableMenuButtonVisible(true);
		resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		resultsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		ImageView emptyTableImg = new ImageView("META-INF/images/empty-table.png");
		resultsTable.setPlaceholder(emptyTableImg);
		
		TableColumn<Product, String> col0 = TableColumnBuilder.<Product, String>create() // strange syntax but it works
				.text("Name") // table column name for display
				.cellValueFactory(new PropertyValueFactory<Product, String>("name")) // factory for getting property value to display
				.build();
		TableColumn<Product, BigDecimal> col1 = TableColumnBuilder.<Product, BigDecimal>create() 
				.text("Price") 
				.cellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price")) 
				.build();
		TableColumn<Product, String> col2 = TableColumnBuilder.<Product, String>create() 
				.text("Storage Location") 
				.cellValueFactory(new PropertyValueFactory<Product, String>("storageLocation"))
				.build();
		TableColumn<Product, Integer> col3 = TableColumnBuilder.<Product, Integer>create() 
				.text("Stock Level") 
				.cellValueFactory(new PropertyValueFactory<Product, Integer>("stockLevel"))
				.build();
		
		TableColumn<Product, Integer> drillCol = new TableColumn<Product, Integer>("Drill");
		drillCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
		drillCol.setEditable(true);

		Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>> drillColumnCellFactory = new DrillCellFactory(resultsTable);
		drillCol.setCellFactory(drillColumnCellFactory);
		
		resultsTable.getColumns().add(col0);
		resultsTable.getColumns().add(col1);
		resultsTable.getColumns().add(col2);
		resultsTable.getColumns().add(col3);
		resultsTable.getColumns().add(drillCol);
		
		pane.setCenter(resultsTable);
		
		return pane;
    }
    
    /*
     * 
     */
    private class DrillCellFactory implements Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>> {
    	
    	private TableView<Product> tableView;
    	
    	/**
         * Constructor
         */
        public DrillCellFactory(TableView<Product> tableView) {
        	this.tableView = tableView;
        }
		
    	/* 
		 * @see javafx.util.Callback#call(java.lang.Object)
		 */
        @Override
        public TableCell<Product, Integer> call(TableColumn<Product, Integer> tableColumn) {
        	return new DrillTableCell(tableView, tableColumn);
        }
        
    }
    
    /*
     * 
     */
    private class DrillTableCell extends TableCell<Product, Integer> {
    	
    	private TableView<Product> tableView;
    	
    	private TableColumn<Product, Integer> tableColumn;

		/**
         * Constructor
         */
        public DrillTableCell(TableView<Product> tableView, TableColumn<Product, Integer> tableColumn) {
        	this.tableView = tableView;
			this.tableColumn = tableColumn;
        }

        /*
         * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
         */
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
			        	tableColumn.getTableView().getSelectionModel().select(getIndex());
				        Product selectedItem = tableView.getSelectionModel().getSelectedItem();

				        if (selectedItem != null) {
				        	ProductPage productPage= (ProductPage) ProductsPage.this.getNavigationManager().requestPageChange("ProductPage", HistoryAction.CLEAR_FORWARD_HISTORY, true);
					        
					        try {
					        	productPage.setProduct(selectedItem);
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
    }
}
