package com.groovyfly.controlcentre.application;


import javafx.scene.Node;

public abstract class Page {

	protected Node pageView;

	protected NavigationManager navigationManager;

	public Node getPageView() throws Exception {
		if (pageView == null) {
			this.pageView = this.createView();
		}
		return pageView;
	}

	public NavigationManager getNavigationManager() {
		return navigationManager;
	}

	public void setNavigationManager(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}

	public boolean hasUnsavedChanges() throws Exception {
		return false;
	}

	public boolean saveChanges() throws Exception {
		return true;
	}

	/**
	 * This should never be called directly. If you want the view of a page then
	 * call getPageView().
	 * 
	 * @return Node
	 * @throws Exception
	 */
	protected abstract Node createView() throws Exception;

	public void refreshView() {}
}
