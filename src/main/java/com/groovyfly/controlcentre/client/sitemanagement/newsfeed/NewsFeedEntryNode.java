/*
 * Copyright GroovyFly.com NewsNode.java
 */
package com.groovyfly.controlcentre.client.sitemanagement.newsfeed;

import java.util.Calendar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.CalendarTextField;

import com.groovyfly.controlcentre.structure.sitemanagement.NewsFeedEntry;

/**
 * @author Chris Hatton
 */
public class NewsFeedEntryNode {
	
	private EventHandler<Event> evh;

	private NewsFeedEntry newsFeedEntry;
	
	private TextField titleTxt;
	
	private TextField linkTxt;
	
	private TextArea contentTxa;
	
	private CalendarTextField pubDateTxt;
	
	private CheckBox retiredChk;

	
	/**
	 * Constructor
	 */
	public NewsFeedEntryNode(NewsFeedEntry newsFeedEntry) {
		this.newsFeedEntry = newsFeedEntry;
	}

	public Node createView(boolean evenRow) {
		VBox node = new VBox();

		if (evenRow) {
			node.setStyle("-fx-background-color: #61B7CF ;");
		} else {
			node.setStyle("-fx-background-color: #6F83D6 ;");
		}

		node.setPadding(new Insets(10, 10, 10, 10));
		node.setSpacing(3);

		Label titleLbl = new Label("Title:");
		titleTxt = new TextField();

		Label linkLbl = new Label("Link:");
		linkTxt = new TextField();

		Label contentLbl = new Label("Content:");
		contentTxa = new TextArea();

		Label pubDateLbl = new Label("Publication Date:");
		pubDateTxt = new CalendarTextField();

		retiredChk = new CheckBox("Retired");
		retiredChk.setAlignment(Pos.CENTER_RIGHT);
		retiredChk.setPrefWidth(Integer.MAX_VALUE);
		VBox.setMargin(retiredChk, new Insets(10, 0, 0, 0));
		VBox.setVgrow(retiredChk, Priority.ALWAYS);

		node.getChildren().addAll(titleLbl, titleTxt, linkLbl, linkTxt, contentLbl, contentTxa, pubDateLbl, pubDateTxt, retiredChk);
		
		setFields();
		addListener();

		return node;
	}
	
	private void addListener() {
		titleTxt.setOnKeyTyped(new EventHandler<Event>() {

			@Override
            public void handle(Event arg0) {
				evh.handle(new ActionEvent());
            }
		});
		
		linkTxt.setOnKeyTyped(new EventHandler<Event>() {

			@Override
            public void handle(Event arg0) {
				evh.handle(new ActionEvent());
            }
		});
		contentTxa.setOnKeyTyped(new EventHandler<Event>() {

			@Override
            public void handle(Event arg0) {
				evh.handle(new ActionEvent());
            }
		});
		
		pubDateTxt.valueProperty().addListener(new ChangeListener<Calendar>() {

			@Override
            public void changed(ObservableValue<? extends Calendar> arg0, Calendar arg1, Calendar arg2) {
				evh.handle(new ActionEvent());
            }
		
		});
		
		retiredChk.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				evh.handle(new ActionEvent());
			}
		});
	}
	
	public void addEventHandler(EventHandler<Event> evh) {
		this.evh = evh;
	}

	public NewsFeedEntry updateEntry() {
		newsFeedEntry.setTitle(titleTxt.getText());
		newsFeedEntry.setLink(linkTxt.getText());
		newsFeedEntry.setContent(contentTxa.getText());
		if (pubDateTxt.getValue() != null) {
			newsFeedEntry.setPublicationDate(pubDateTxt.getValue().getTime());
		} else {
			newsFeedEntry.setPublicationDate(null);	
		}
		
		newsFeedEntry.setRetired(retiredChk.isSelected());

		return newsFeedEntry;
	}
	
	public void setFields() {
		titleTxt.setText(newsFeedEntry.getTitle());
		linkTxt.setText(newsFeedEntry.getLink());
		contentTxa.setText(newsFeedEntry.getContent());
		
		Calendar publicationCalendar = Calendar.getInstance();
		publicationCalendar.setTime(newsFeedEntry.getPublicationDate());
		pubDateTxt.setValue(publicationCalendar);
		
		retiredChk.setSelected(newsFeedEntry.isRetired());
	}
}
