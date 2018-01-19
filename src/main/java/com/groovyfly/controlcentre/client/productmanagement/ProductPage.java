/*
 * @(#)ProductPage.java			1 Apr 2013
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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPaneBuilder;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import jfxtras.labs.scene.control.BigDecimalField;
import jfxtras.labs.scene.control.BigDecimalFieldBuilder;

import org.springframework.stereotype.Component;

import com.groovyfly.controlcentre.application.Page;
import com.groovyfly.controlcentre.customcontrols.IntegerTextfield;
import com.groovyfly.controlcentre.structure.Category;
import com.groovyfly.controlcentre.structure.PriceRule;
import com.groovyfly.controlcentre.structure.Product;
import com.groovyfly.controlcentre.structure.ProductAttribute;
import com.groovyfly.controlcentre.structure.Supplier;
import com.groovyfly.controlcentre.structure.VatRate;

/**
 * @author Chris Hatton
 */
@Component("ProductPage")
public class ProductPage extends Page {
	
	

	private Button saveBtn;
	private ButtonBase cancelBtn;
	private ComboBox<Category> categoryCbo;
	private ListView<ProductAttribute> attributeList;
	private TextField nameTxt;
	private TextField urlTxt;
	private ComboBox<Supplier> supplierCbo;
	private BigDecimalField priceTxt;
	private ComboBox<PriceRule>  priceRuleCbo;
	private ComboBox<VatRate>  vatRateCbo;
	private TextField storageLocationTxt;
	private ComboBox<Object> statusCbo;
	private IntegerTextfield stockLevelTxt;

	/* 
	 * @see com.groovyfly.controlcentre.application.Page#createView()
	 */
    @Override
    protected Node createView() throws Exception {
    	BorderPane pane = new BorderPane();
    	
    	
    	SplitPane splitPane = SplitPaneBuilder.create()
    			.items(buildCentre(), new Label("images"))
    			.dividerPositions(new double[] {0.5, 0.5})
    			.build();
    	
    	
    	
    	pane.setCenter(splitPane);
    	pane.setBottom(buildButtonNode());
    	
    	return pane;
    }
    
    /**
	 * @param selectedItem
	 */
    public void setProduct(Product selectedItem) {
    	
    	System.out.println("selectedItem " + selectedItem.getName());
    }    

	private Node buildCentre() {
    	GridPane pane = new GridPane();
    	pane.setHgap(5);
    	pane.setVgap(5);
    	pane.setAlignment(Pos.CENTER);
    	pane.setMaxWidth(500);
    	
    	Label categoryLbl = new Label("Category:");
        categoryCbo = ComboBoxBuilder.<Category>create()
                .id("uneditable-combobox")
                .minWidth(200)
                .build();
        
        Label attributesLbl = new Label("Attributes:");
        attributeList = new ListView<>();
        attributeList.setPrefSize(200, 80);
        
        Label nameLbl = new Label("Name:");
        nameTxt = new TextField();
        
        Label urlLbl = new Label("URL Alias:");
        urlTxt = new TextField();
        
        Label supplierLbl = new Label("Supplier:");
        supplierCbo = ComboBoxBuilder.<Supplier>create()
                .id("uneditable-combobox")
                .minWidth(200)
                .build();
        
        Label priceLbl = new Label("Price:");
        priceTxt = BigDecimalFieldBuilder.create().minValue(new BigDecimal(0)).promptText("Enter price (£)").build();
        
        Label priceRuleLbl = new Label("Price Rule:");
        
        priceRuleCbo = ComboBoxBuilder.<PriceRule>create()
                .id("uneditable-combobox")
                .minWidth(200)
                .build();
        
        Label vatRateLbl = new Label("Vat Rate:");
        vatRateCbo = ComboBoxBuilder.<VatRate>create()
                .id("uneditable-combobox")
                .minWidth(200)
                .build();
        
        Label storageLocationLbl = new Label("Storage Location:");
        storageLocationTxt = new TextField();
        
        Label stockLevelLbl = new Label("Stock Level:");
        stockLevelTxt = new IntegerTextfield(0, Integer.MAX_VALUE, 0);
        Button minus1Btn = new Button("-1");
        Button plus1Btn = new Button("+1");
        Button minus12Btn = new Button("-12");
        Button plus12Btn = new Button("+12");
        HBox btnBox = new HBox(3);
        btnBox.getChildren().addAll(minus1Btn, plus1Btn, minus12Btn, plus12Btn);
        
        Label statusLbl = new Label("Status:");
        statusCbo = ComboBoxBuilder.<Object>create()
                .id("uneditable-combobox")
                .minWidth(200)
                .build();
        
        int row = 0;
        
        pane.add(categoryLbl, 0, row);
        pane.add(categoryCbo, 1, row++);
        pane.add(attributesLbl, 0, row);
        pane.add(attributeList, 1, row++);
        pane.add(nameLbl, 0, row);
        pane.add(nameTxt, 1, row++);
        pane.add(urlLbl, 0, row);
        pane.add(urlTxt, 1, row++);
        pane.add(supplierLbl, 0, row);
        pane.add(supplierCbo, 1, row++);
        pane.add(priceLbl, 0, row);
        pane.add(priceTxt, 1, row++);
        pane.add(priceRuleLbl, 0, row);
        pane.add(priceRuleCbo, 1, row++);
        pane.add(vatRateLbl, 0, row);
        pane.add(vatRateCbo, 1, row++);
        pane.add(storageLocationLbl, 0, row);
        pane.add(storageLocationTxt, 1, row++);
        pane.add(stockLevelLbl, 0, row);
        pane.add(stockLevelTxt, 1, row);
        pane.add(btnBox, 2, row++);
        pane.add(statusLbl, 0, row);
        pane.add(statusCbo, 1, row++);
    	
    	
    	return pane;
    }
	
	/*
	 * 
	 */
	private Node buildButtonNode() {
		saveBtn = new Button("Save");
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {

				
				disableButtons(true); 
			}
		});
		
		cancelBtn = new Button("Cancel");
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				disableButtons(true); 
			}
		});
		
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		btnBox.setPadding(new Insets(10));
		btnBox.setSpacing(5);

		saveBtn.setPrefWidth(100);
		saveBtn.setCursor(Cursor.HAND);
		cancelBtn.setPrefWidth(100);
		cancelBtn.setCursor(Cursor.HAND);
		
		disableButtons(true); 

		btnBox.getChildren().addAll(saveBtn, cancelBtn);

		return btnBox;
    }
	
    private void disableButtons(boolean value) {
		saveBtn.setDisable(value);
		cancelBtn.setDisable(value);
    }

}
