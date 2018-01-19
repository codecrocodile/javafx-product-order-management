/*
 * @(#)CapabilityCheckPageModel.java			24 Mar 2013
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.appengine.api.capabilities.CapabilitiesService;
import com.google.appengine.api.capabilities.CapabilitiesServiceFactory;
import com.google.appengine.api.capabilities.Capability;
import com.google.appengine.api.capabilities.CapabilityState;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import com.groovyfly.controlcentre.client.config.GoogleAppEngineProperties;

/**
 * @author Chris Hatton
 */
@Component("CapabilityCheckPageModel")
@Scope("prototype")
public class CapabilityCheckPageModel {
	
	@Autowired
	private GoogleAppEngineProperties googleAppEngineProperties;

	public List<GAECapabilityStatus> getGAECapabilitySnapShot() throws IOException {
		List<GAECapabilityStatus> resultList = new ArrayList<>();

		RemoteApiOptions options = new RemoteApiOptions() 
		        .server(googleAppEngineProperties.getServer(), googleAppEngineProperties.getPort())
		        .credentials(googleAppEngineProperties.getUsername(), googleAppEngineProperties.getPassword());
		
		RemoteApiInstaller installer = new RemoteApiInstaller();
		installer.install(options);
		
		try {
			CapabilitiesService service = CapabilitiesServiceFactory.getCapabilitiesService();
			
			CapabilityState capabilityState = null; 
			GAECapabilityStatus gaeCapabilityStatus = null;
			
			capabilityState = service.getStatus(Capability.BLOBSTORE);
			gaeCapabilityStatus = new GAECapabilityStatus("BLOBSTORE", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.DATASTORE);
			gaeCapabilityStatus = new GAECapabilityStatus("DATASTORE", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.DATASTORE_WRITE);
			gaeCapabilityStatus = new GAECapabilityStatus("DATASTORE_WRITE", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.IMAGES);
			gaeCapabilityStatus = new GAECapabilityStatus("IMAGES", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.MAIL);
			gaeCapabilityStatus = new GAECapabilityStatus("MAIL", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.MEMCACHE);
			gaeCapabilityStatus = new GAECapabilityStatus("MEMCACHE", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.PROSPECTIVE_SEARCH);
			gaeCapabilityStatus = new GAECapabilityStatus("PROSPECTIVE_SEARCH", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.TASKQUEUE);
			gaeCapabilityStatus = new GAECapabilityStatus("TASKQUEUE", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.URL_FETCH);
			gaeCapabilityStatus = new GAECapabilityStatus("TASKQUEUE", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
			capabilityState = service.getStatus(Capability.XMPP);
			gaeCapabilityStatus = new GAECapabilityStatus("XMPP", capabilityState.getScheduledDate(), capabilityState.getStatus());
			resultList.add(gaeCapabilityStatus);
			
		} finally {
			installer.uninstall();
		}
		
		return resultList;
	}

}
