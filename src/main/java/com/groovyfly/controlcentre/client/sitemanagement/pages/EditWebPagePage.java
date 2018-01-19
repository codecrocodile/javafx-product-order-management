/*
 * @(#)EditPageMetaDataPage.java			22 Mar 2013
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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import org.springframework.beans.factory.annotation.Autowired;

import com.groovyfly.controlcentre.application.Page;
import com.groovyfly.controlcentre.service.sitemanagement.SiteManagementServiceIF;
import com.groovyfly.controlcentre.structure.sitemanagement.WebPage;

/**
 * @author Chris Hatton
 */
public class EditWebPagePage extends Page {
	
	@Autowired
	private SiteManagementServiceIF siteManagementServiceIF;
	
	private WebPage webPage;

	private TextField titleTxt;
	
	private TextArea keywordsTxa;
	
	private TextArea descTxa;
	
	private HTMLEditor htmlEditor;

	private Label titleLenValLbl;

	private Label keywordsLenValLbl;

	private Label descLenValLbl;
	
	private Button saveBtn;

	private Button cancelBtn;
	
	private ChangeListener<Number> titleLengthListener = new ChangeListener<Number>() {
		@Override
        public void changed(ObservableValue<? extends Number > arg0, Number  arg1, Number  arg2) {
			disableButtons(false);
			titleLenValLbl.setText(arg2.toString());
		}
	};
	
	private ChangeListener<Number> descLengthListener = new ChangeListener<Number>() {
		@Override
        public void changed(ObservableValue<? extends Number > arg0, Number  arg1, Number  arg2) {
			disableButtons(false);
			descLenValLbl.setText(arg2.toString());
		}
	};
	
	private ChangeListener<Number> keywordsLengthListener = new ChangeListener<Number>() {
		@Override
        public void changed(ObservableValue<? extends Number > arg0, Number  arg1, Number  arg2) {
			disableButtons(false);
			keywordsLenValLbl.setText(Integer.toString(keywordsInString(keywordsTxa.getText())));
		}
	};
	
	private EventHandler<KeyEvent> webViewListener = new EventHandler<KeyEvent>() {
		@Override
        public void handle(KeyEvent ke) {
			disableButtons(false);
        }
	};
	
	EventHandler<ContextMenuEvent> webViewContetextListener = new EventHandler<ContextMenuEvent>() {
		@Override
        public void handle(ContextMenuEvent cme) {
			disableButtons(false);
		}
	};
	
	String valStyle = "-fx-font-weight:bold; -fx-font-size:16px;";
	String hintStyle = "-fx-font:lighter; -fx-font-size:10px;";

	private void addListeners() {
		titleTxt.lengthProperty().addListener(titleLengthListener);
		keywordsTxa.lengthProperty().addListener(keywordsLengthListener);
		descTxa.lengthProperty().addListener(descLengthListener);
		WebView webview = (WebView) htmlEditor.lookup("WebView");
		webview.setOnKeyTyped(webViewListener);
		webview.setOnContextMenuRequested(webViewContetextListener);
	}
	
	private void removeListeners() {
		titleTxt.lengthProperty().removeListener(titleLengthListener);
		keywordsTxa.lengthProperty().removeListener(keywordsLengthListener);
		descTxa.lengthProperty().removeListener(descLengthListener);
		WebView webview = (WebView) htmlEditor.lookup("WebView");
		webview.removeEventHandler(KeyEvent.ANY, webViewListener);
	}

	/* 
	 * @see com.groovyfly.controlcentre.client.Page#createView()
	 */
    @Override
    protected Node createView() throws Exception {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(5));
		pane.setMinSize(0, 0);
		
		pane.setTop(buildEditNode());
		pane.setCenter(buildHtmlNode());
		pane.setBottom(buildBtnNode());
		
		return pane;
    }
    
    private Node buildEditNode() {
		VBox vBox = new VBox(5);
		
		titleTxt = new TextField();
		Label titleLbl = new Label("Meta Title");
		titleLbl.setLabelFor(titleTxt);
		
		keywordsTxa = new TextArea();
		keywordsTxa.setPrefRowCount(2);
		Label keywordsLbl = new Label("Meta Keywords");
		keywordsLbl.setLabelFor(keywordsTxa);
		
		descTxa = new TextArea();
		descTxa.setPrefRowCount(3);
		Label descLbl = new Label("Meta Description");
		descLbl.setLabelFor(descTxa);
		
		vBox.getChildren().addAll(titleLbl, titleTxt, keywordsLbl, keywordsTxa, descLbl, descTxa);
		
		return vBox;
	}
    
    private Node buildHtmlNode() {
		BorderPane pane = new BorderPane();
		
		htmlEditor = new HTMLEditor();
		Label htmlLbl = new Label("HTML");
		htmlLbl.setLabelFor(htmlEditor);
		
		BorderPane.setMargin(htmlLbl, new Insets(5, 0, 5, 0));
		pane.setTop(htmlLbl);
		
		pane.setCenter(htmlEditor);
		
		pane.setBottom(buildStatsNode());
		
		return pane;
	}
    
    private Node buildStatsNode() {
    	GridPane pane = new GridPane();
    	pane.setPadding(new Insets(10));
    	pane.setHgap(5);
    	pane.setVgap(3);
    	
    	pane.getStyleClass().add("highlightedBackground");

    	Label titleLenLbl = new Label("Meta Title Length:");
    	Label keywordsLenLbl = new Label("Meta Keywords Length:");
    	Label descLenLbl = new Label("Meta Description Length:");
    	
    	titleLenValLbl = new Label("XX");
    	titleLenValLbl.setStyle(valStyle);
    	keywordsLenValLbl = new Label("XX");
    	keywordsLenValLbl.setStyle(valStyle);
    	descLenValLbl = new Label("XX");
    	descLenValLbl.setStyle(valStyle);
    	
    	Label titleHintLbl = new Label("Optimally no more than 70 characters in length to describe the page.");
    	titleHintLbl.setStyle(hintStyle);
    	Label keywordsHintLbl = new Label("Optimally 10 or 15 terms that most accurately describe the the page.");
    	keywordsHintLbl.setStyle(hintStyle);
    	Label descHintLbl = new Label("Optimally be between 150-160 characters to describe the page.");
    	descHintLbl.setStyle(hintStyle);
    	
    	GridPane.setHgrow(titleLenLbl, Priority.ALWAYS);
    	GridPane.setHgrow(keywordsLenLbl, Priority.ALWAYS);
    	GridPane.setHgrow(descLenLbl, Priority.ALWAYS);
    	GridPane.setHalignment(titleLenLbl, HPos.RIGHT);
    	GridPane.setHalignment(keywordsLenLbl, HPos.RIGHT);
    	GridPane.setHalignment(descLenLbl, HPos.RIGHT);
    	
    	GridPane.setHgrow(titleHintLbl, Priority.ALWAYS);
    	GridPane.setHgrow(keywordsHintLbl, Priority.ALWAYS);
    	GridPane.setHgrow(descHintLbl, Priority.ALWAYS);
    	
    	pane.add(titleLenLbl, 		0, 0);
    	pane.add(titleLenValLbl, 	1, 0);
    	pane.add(titleHintLbl, 		2, 0);
    	
    	pane.add(keywordsLenLbl, 		0, 1);
    	pane.add(keywordsLenValLbl, 	1, 1);
    	pane.add(keywordsHintLbl, 		2, 1);
    	
    	pane.add(descLenLbl, 		0, 2);
    	pane.add(descLenValLbl, 	1, 2);
    	pane.add(descHintLbl, 		2, 2);
		
		return pane;
	}
    
    private Node buildBtnNode() {
		saveBtn = new Button("Save");
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				String titleString = titleTxt.getText();
		    	String keywordsString = keywordsTxa.getText();
		    	String descriptionString = descTxa.getText();
		    	String htmlText = htmlEditor.getHtmlText();
		    	
		    	System.out.println(htmlText);
		    	
		    	
		    	webPage.setTitle(titleString);
		    	webPage.setMetaKeywords(keywordsString);
		    	webPage.setMetaDescription(descriptionString);
		    	webPage.setHtml(htmlText);
				
				try {
	                siteManagementServiceIF.saveWebPage(webPage);
                } catch (Exception e) {
	                e.printStackTrace();
                }
				
				saveBtn.setDisable(true);
				cancelBtn.setDisable(true);
			}
		});
		
		cancelBtn = new Button("Cancel");
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				saveBtn.setDisable(true);
				cancelBtn.setDisable(true);
				
				setWebPage(webPage);
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
    
    private int keywordsInString(String string) {
    	if (string.trim().equals("")) {
    		return 0;
    	} else {
    		String[] split = string.split(",");

    		return split.length;	
    	}
    }
    
    private void disableButtons(boolean value) {
		saveBtn.setDisable(value);
		cancelBtn.setDisable(value);
    }
    
    public void setWebPage(WebPage webPage) {
    	
    	removeListeners();
    	
    	this.webPage = webPage;
		titleTxt.setText(webPage.getTitle());
    	keywordsTxa.setText(webPage.getMetaKeywords());
    	descTxa.setText(webPage.getMetaDescription());
    	htmlEditor.setHtmlText(webPage.getHtml());
    	
    	
    	titleLenValLbl.setText(Integer.toString(titleTxt.lengthProperty().intValue()));
    	keywordsLenValLbl.setText(Integer.toString(keywordsInString(keywordsTxa.getText())));
    	descLenValLbl.setText(Integer.toString(descTxa.lengthProperty().intValue()));
    	
    	addListeners();
    }

}
