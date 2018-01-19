/*
 * @(#)Start.java 31 Mar 2013
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

import com.groovyfly.controlcentre.application.NavigationManager.HistoryAction;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * 
 * @author Chris Hatton
 */
public class Start extends Application {

	private Scene scene;

	private BorderPane root;

	private NavigationManager navigationManager;

	private NavigationBar navigationBar;

	/*
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Control Centre");
		Image ico = new Image("META-INF/images/globe.png");
		primaryStage.getIcons().add(ico);
		primaryStage.setWidth(1025);
		primaryStage.setHeight(800);
		primaryStage.setOnCloseRequest(new WindowCloseListener());
		
		root = new BorderPane();
		
		navigationBar = new NavigationBar();
		navigationManager = new NavigationManager(this);
		navigationManager.addObserver(navigationBar);
		navigationBar.setNavigationManager(navigationManager);

		root.setTop(navigationBar.createView());
		
		scene = new Scene(root);
		scene.getStylesheets().add("/application-stylesheet.css");
		primaryStage.setScene(scene);
		
		navigationManager.requestPageChange("HomePage", HistoryAction.NONE, false);

		primaryStage.show();
	}

	/**
	 * Starts the application.
	 * 
	 * @param args
	 * 		Program arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public void setView(Node node, boolean showTransiton) {
		root.setCenter(node);

		if (showTransiton) {
			FadeTransition fadeTransition = FadeTransitionBuilder.create()
			        .duration(Duration.seconds(1))
			        .node(node)
			        .fromValue(0)
			        .toValue(1)
			        .build();
			fadeTransition.play();	
		}
	}

	/*
	 * Listens for window closing event and takes appropriate action.
	 */
	private class WindowCloseListener implements EventHandler<WindowEvent> {

		/*
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
		public void handle(WindowEvent we) {
			System.out.println("Window closed, save all the panel states");
		}

	}

}
