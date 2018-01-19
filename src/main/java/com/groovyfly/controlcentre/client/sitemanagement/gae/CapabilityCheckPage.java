/*
 * @(#)CapabilityCheckPage.java			24 Mar 2013
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

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.capabilities.CapabilityStatus;
import com.groovyfly.controlcentre.application.Page;

/**
 * @author Chris Hatton
 */
public class CapabilityCheckPage extends Page {
	
	@Autowired
	private CapabilityCheckPageModel capabilityCheckPageModel;
	
	private Map<CapabilityStatus, String> capMap = new HashMap<>();
	
	{
		capMap.put(CapabilityStatus.DISABLED, "META-INF/images/gae_status/smiley-cry.png");
		capMap.put(CapabilityStatus.ENABLED, "META-INF/images/gae_status/smiley-mr-green.png");
		capMap.put(CapabilityStatus.SCHEDULED_MAINTENANCE, "META-INF/images/gae_status/alarm-clock--exclamation.png");
		capMap.put(CapabilityStatus.UNKNOWN, "META-INF/images/gae_status/smiley-confuse.png");
	}
	
	
	

	/* 
	 * @see com.groovyfly.controlcentre.client.Page#createView()
	 */
    @Override
    protected Node createView() throws Exception {
    	GridPane apane = new GridPane();
    	apane.setAlignment(Pos.CENTER);
    	
    	GridPane pane = new GridPane();
    	pane.setPadding(new Insets(20));
    	pane.getStyleClass().add("highlightedBackground");
    	pane.setHgap(10);
    	pane.setVgap(20);
    	pane.setAlignment(Pos.CENTER);
    	
    	List<GAECapabilityStatus> gaeCapabilitySnapShot = capabilityCheckPageModel.getGAECapabilitySnapShot();
    	int row = -1;
    	
    	for (GAECapabilityStatus s : gaeCapabilitySnapShot) {
    		row++;
    		
        	Label capabilityLbl = new Label(s.getCapability());
        	Label capabilityLbl2 = new Label(s.getStatus().name());
        	ImageView imageView = new ImageView(capMap.get(s.getStatus()));
        	Label capabilityLbl3 = new Label("");
        	DateFormat df = DateFormat.getDateTimeInstance();
        	if (s.getScheduledDate() != null) {
        		capabilityLbl3 = new Label(df.format(s.getScheduledDate()));
        	}
        	
        	pane.add(capabilityLbl, 1, row);
        	pane.add(capabilityLbl2, 2, row);
        	pane.add(capabilityLbl3, 3, row);
        	pane.add(imageView, 4, row);
    	}
    	
    	apane.add(pane, 0, 0);

    	
    	return apane;
    }

}
