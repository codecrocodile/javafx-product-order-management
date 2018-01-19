/*
 * @(#)DatastoreViewerPage.java			24 Mar 2013
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
package com.groovyfly.controlcentre.client.sitemanagement.gae;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import org.springframework.beans.factory.annotation.Autowired;

import com.groovyfly.controlcentre.application.Page;


/**
 * @author Chris Hatton
 */
public class BlobstoreViewerPage extends Page {

	@Autowired
	private BlobstoreViewerPageModel blobtoreViewerPageModel;

	private BorderPane mainpane = new BorderPane();
	
	private BorderPane pagePane = new BorderPane();

	private ProgressIndicator p;

	private Region veil;

	private Label pageLbl;

	
	/*
	 * @see com.groovyfly.controlcentre.client.Page#createView()
	 */
	@Override
	protected Node createView() throws Exception {
		blobtoreViewerPageModel.initModel();
		
        veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
        veil.setVisible(false);
        p = new ProgressIndicator();
        p.setMaxSize(150, 150);
        p.setVisible(false);
		
		StackPane stack = new StackPane();
		stack.getChildren().addAll(pagePane, veil, p);
		
		mainpane.setCenter(stack);
		
		Node buildNavNode = buildNavNode(blobtoreViewerPageModel.getPagesToDisplay());
		mainpane.setBottom(buildNavNode);
		
		CreatePageTask task = new CreatePageTask(0);
    	p.progressProperty().bind(task.progressProperty());
    	veil.visibleProperty().bind(task.runningProperty());
    	p.visibleProperty().bind(task.runningProperty());
    	pagePane.centerProperty().bind(task.valueProperty());
    	
		Thread thread = new Thread(task);
		thread.start();
	        
		return mainpane;
	}
	
	
	private Node buildNavNode(int noOfPages) {
		HBox hBox = new HBox(10);
		hBox.setPadding(new Insets(15));
		
		Button backBtn  = new Button("Back");
		backBtn.setPrefWidth(100);
		backBtn.setCursor(Cursor.HAND);
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				turnPage(false);
			}
		});
		
		
		pageLbl = new Label("1");
		Button forwardBtn = new Button("Forward");
		forwardBtn.setPrefWidth(100);
		forwardBtn.setCursor(Cursor.HAND);
		forwardBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				turnPage(true);
			}
		});
		
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(backBtn, pageLbl, forwardBtn);
		
		return hBox;
	}
	
	private void turnPage(boolean forward) {
		int currentPageIndex = blobtoreViewerPageModel.getCurrentPageIndex();
		
		
		System.out.println("current page = " + currentPageIndex);
		
		CreatePageTask task =null;
		
		if (forward) {
			blobtoreViewerPageModel.setCurrentPageIndex(++currentPageIndex);
			task = new CreatePageTask(currentPageIndex);	
		} else {
			blobtoreViewerPageModel.setCurrentPageIndex(--currentPageIndex);
			task = new CreatePageTask(currentPageIndex);
		}
		
		
    	p.progressProperty().bind(task.progressProperty());
    	veil.visibleProperty().bind(task.runningProperty());
    	p.visibleProperty().bind(task.runningProperty());
    	pagePane.centerProperty().bind(task.valueProperty());
    	
		Thread thread = new Thread(task);
		thread.start();
		
		pageLbl.setText(Integer.toString( blobtoreViewerPageModel.getCurrentPageIndex() + 1));
		
		
		currentPageIndex = blobtoreViewerPageModel.getCurrentPageIndex();
		
		
		System.out.println("current page = " + currentPageIndex);
	}

	class CreatePageTask extends Task<Node> {       

		private int pageIndex;
		
		/**
		 * Constructor
		 */
		public CreatePageTask(int pageIndex) {
			this.pageIndex = pageIndex;
		}

		@Override 
		protected Node call() throws Exception {
			List<BlobstoreItem> blobstorePageItems = blobtoreViewerPageModel.getBlobstorePageItems(pageIndex);
			Node buildPageNode = buildPageNode(blobstorePageItems);
			
			return buildPageNode;
		}

	}
	
	private Node buildPageNode(List<BlobstoreItem> blobstoreItems) {
		FlowPane pane = new FlowPane();
		pane.setPadding(new Insets(10));
		pane.prefWrapLengthProperty().bind(mainpane.widthProperty().subtract(40));
		pane.setVgap(10);
		pane.setHgap(10);
		pane.setAlignment(Pos.CENTER);
		
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(pane);
		
		for (BlobstoreItem v : blobstoreItems) {
			pane.getChildren().add(createImageBox(v));
		}
		
		return scroll;
		
	}
	
	private Node createImageBox(BlobstoreItem blobstoreItem) {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #AAA; -fx-border-color: #222; -fx-border-radius: 5px; -fx-background-radius: 5px");
		pane.setPadding(new Insets(5));
		pane.setPrefSize(220, 340);
		
		
		if (blobstoreItem.isImage()) {
			final Image i = new Image(blobstoreItem.getServingUrl(), true);
			final ImageView v = new ImageView(i);
			i.progressProperty().addListener(new ChangeListener<Number>() {

				@Override
                public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					if (arg2.intValue() == 1) {
						scaleImage(v);
					}
                }
			});
			
			pane.setCenter(v);	
		} else {
			pane.setCenter(new Label("OTHER"));
		}
		
		GridPane gp = new GridPane();
		gp.setVgap(2);
		
		Label nameLbl = new Label("Name:");
		TextField nameValLbl = new TextField(blobstoreItem.getBlobInfo().getFilename());
		Label sizeLbl = new Label("Size:");
		TextField sizeValLbl = new TextField(Long.toString(blobstoreItem.getBlobInfo().getSize()));
		Label mimeLbl = new Label("Mine Type:");
		TextField mimeValLbl = new TextField(blobstoreItem.getBlobInfo().getContentType());
		Label urlLbl = new Label("URL:");
		TextField urlValLbl = new TextField(blobstoreItem.getServingUrl());
		
		GridPane.setHgrow(nameValLbl, Priority.ALWAYS);
		GridPane.setHgrow(sizeValLbl, Priority.ALWAYS);
		GridPane.setHgrow(mimeValLbl, Priority.ALWAYS);
		GridPane.setHgrow(urlValLbl, Priority.ALWAYS);
		
		nameValLbl.setEditable(false);
		sizeValLbl.setEditable(false);
		mimeValLbl.setEditable(false);
		urlValLbl.setEditable(false);
		
		gp.add(nameLbl, 0, 0);
		gp.add(nameValLbl, 0, 1);
		gp.add(sizeLbl, 0, 2);
		gp.add(sizeValLbl, 0, 3);
		gp.add(mimeLbl, 0, 4);
		gp.add(mimeValLbl, 0, 5);
		gp.add(urlLbl, 0, 6);
		gp.add(urlValLbl, 0, 7);
		
		pane.setBottom(gp);
		
		return pane;
	}
	
	private void scaleImage(ImageView imageView) {
		
		Image image = imageView.getImage();
		double width = image.getWidth();
		
		final double maxWidthToDisplay = 200;
		
		if (width > maxWidthToDisplay) {
			imageView.setFitWidth(maxWidthToDisplay);
			imageView.setPreserveRatio(true);	
		}
		
	}

}
