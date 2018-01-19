/*
 * @(#)HomePage.java			31 Mar 2013
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
package com.groovyfly.controlcentre.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import com.groovyfly.controlcentre.application.Page;

/**
 * @author Chris Hatton
 */
public class HomePage extends Page {

	/* 
	 * @see com.groovyfly.controlcentre.application.Page#createView()
	 */
    @Override
    protected Node createView() throws Exception {
    	BorderPane pane = new BorderPane();
    	pane.setStyle("-fx-background-color: #04859D; -fx-background-image: url(\"META-INF/images/control-centre-pattern.png\");");
//    	pane.getStyleClass().add("highlightedBackground");
    	
    	Image appLogo = new Image("META-INF/images/control-centre.png");
    	ImageView appLogoView  = new ImageView(appLogo);

    	BorderPane.setAlignment(appLogoView, Pos.CENTER);
    	BorderPane.setMargin(appLogoView, new Insets(0, 0, 50, 0));
    	pane.setCenter(appLogoView);
    	
	    return pane;
    }

}
