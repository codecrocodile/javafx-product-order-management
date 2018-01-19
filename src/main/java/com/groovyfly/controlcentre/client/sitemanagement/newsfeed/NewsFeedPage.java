/*
 * Copyright GroovyFly.com NewsFeedPage.java
 */
package com.groovyfly.controlcentre.client.sitemanagement.newsfeed;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPaneBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import jfxtras.labs.dialogs.DialogFX;
import jfxtras.labs.dialogs.DialogFXBuilder;
import jfxtras.labs.scene.control.CalendarTextField;

import org.springframework.beans.factory.annotation.Autowired;

import com.groovyfly.controlcentre.application.Page;
import com.groovyfly.controlcentre.structure.sitemanagement.NewsFeedEntry;

/**
 * @author Chris Hatton
 */
public class NewsFeedPage extends Page {

	@Autowired
	private NewsFeedPageModel pageModel;

	private VBox newsFeedEntryNodes;

	// new entry fields
	
	private TextField titleTxt;

	private TextField linkTxt;

	private TextArea contentTxa;

	private CalendarTextField pubDateTxt;

	private Button addBtn;

	private Button resetBtn;
	
	// search fields

	private CalendarTextField pubDateAfterTxt;

	private CalendarTextField pubDateBeforeTxt;

	private CheckBox showRetiredChk;

	private Button saveBtn;

	private Button cancelBtn;

	private List<NewsFeedEntryNode> newsFeedEntryNodes2 = new ArrayList<>();

	private Button refreshBtn;

	private ScrollPane scroll;
	
	/*
	 * @see com.groovyfly.controlcentre.client.Page#createView()
	 */
	@Override
	protected Node createView() throws Exception {
		SplitPane splitPane = SplitPaneBuilder.create().items(
		        buildLeftNode(),
		        buildRightNode()).dividerPositions(new double[] { 0.33 })
		        .build();

		return splitPane;
	}

	private Node buildLeftNode() {
		VBox node = new VBox();
		node.setPadding(new Insets(10, 10, 10, 10));
		node.setSpacing(3);

		Label titleLbl = new Label("Title:");
		titleTxt = new TextField();
		titleTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				addBtn.setDisable(false);
				resetBtn.setDisable(false);
			}
		});

		Label linkLbl = new Label("Link:");
		linkTxt = new TextField("www.groovyfly.com");
		linkTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				addBtn.setDisable(false);
				resetBtn.setDisable(false);
			}
		});

		Label contentLbl = new Label("Content:");
		contentTxa = new TextArea();
		contentTxa.textProperty().addListener(new ChangeListener<String>() {
			@Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				addBtn.setDisable(false);
				resetBtn.setDisable(false);
			}
		});

		VBox.setVgrow(contentTxa, Priority.ALWAYS);

		Label pubDateLbl = new Label("Publication Date:");
		pubDateTxt = new CalendarTextField();
		pubDateTxt.setValue(Calendar.getInstance());
		pubDateTxt.valueProperty().addListener(new ChangeListener<Calendar>() {

			@Override
            public void changed(ObservableValue<? extends Calendar> arg0, Calendar arg1, Calendar arg2) {
				addBtn.setDisable(false);
				resetBtn.setDisable(false);
			}
		});

		TilePane tile = new TilePane();
		tile.setPadding(new Insets(5, 0, 5, 0));
		tile.setVgap(4);
		tile.setHgap(4);
		tile.setPrefColumns(2);
		tile.setAlignment(Pos.CENTER_RIGHT);

		addBtn = new Button("Add");
		addBtn.setCursor(Cursor.HAND);
		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				NewsFeedEntry e = createNewsFeedEntry();

				if (e != null) {
					resetInputFields();
					List<NewsFeedEntry> newsFeedEntries = pageModel.addNewsFeedEntry(e);

					Node nodeToScale = null;

					newsFeedEntryNodes.getChildren().clear();
					scroll.setVvalue(0);
					NewsFeedEntryNode node = null;
					int row = 0;
					for (NewsFeedEntry nfe : newsFeedEntries) {
						++row;
						node = new NewsFeedEntryNode(nfe);
						newsFeedEntryNodes2.add(node);
						Node view = node.createView(row % 2 == 0);
						newsFeedEntryNodes.getChildren().add(view);

						if (nfe == e) {
							nodeToScale = view;
						}
					}

					ScaleTransition scaleTransition = ScaleTransitionBuilder.create()
					        .node(nodeToScale)
					        .duration(Duration.seconds(0.5))
					        .fromX(0.5)
					        .fromY(0.5)
					        .toX(1)
					        .toY(1)
					        .build();
					scaleTransition.play();
					
					saveBtn.setDisable(false);
					cancelBtn.setDisable(false);
				}
			}

		});

		resetBtn = new Button("Reset");
		resetBtn.setCursor(Cursor.HAND);
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				resetInputFields();
				resetBtn.setDisable(true);
				addBtn.setDisable(true);
				titleTxt.requestFocus();
			}

		});
		
		resetBtn.setDisable(true);
		addBtn.setDisable(true);

		tile.getChildren().addAll(resetBtn, addBtn);
		node.getChildren().addAll(titleLbl, titleTxt, linkLbl, linkTxt, contentLbl, contentTxa, pubDateLbl, pubDateTxt, tile);

		return node;
	}

	private NewsFeedEntry createNewsFeedEntry() {

		if (isNewsFeedEntryValid()) {
			NewsFeedEntry e = new NewsFeedEntry();
			e.setTitle(titleTxt.getText());
			e.setLink(linkTxt.getText());
			e.setContent(contentTxa.getText());
			e.setPublicationDate(pubDateTxt.getValue().getTime());

			return e;
		} else {
			DialogFX d = new DialogFX(jfxtras.labs.dialogs.DialogFX.Type.INFO);
			d.setMessage("Data entered is incorrect");
			d.showDialog();
		}

		return null;
	}

	private boolean isNewsFeedEntryValid() {
		if (titleTxt.getText().trim().equals("")
		        || linkTxt.getText().trim().equals("")
		        || contentTxa.getText().trim().equals("")
		        || pubDateTxt.getValue() == null) {

			return false;
		}

		return true;
	}

	private void resetInputFields() {
		titleTxt.setText("");
		linkTxt.setText("www.groovyfly.com");
		contentTxa.setText("");
		pubDateTxt.setValue(Calendar.getInstance());
	}

	private Node buildRightNode() {
		BorderPane node = new BorderPane();
		node.setPadding(new Insets(5));

		newsFeedEntryNodes = new VBox(5);
		newsFeedEntryNodes.setFillWidth(true);

		scroll = new ScrollPane();
		scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scroll.setFitToWidth(true);
		scroll.setContent(newsFeedEntryNodes);

		node.setTop(buildSearchParmNode());
		node.setCenter(scroll);
		node.setBottom(buildButtonNode());

		return node;
	}

	private Node buildSearchParmNode() {
		GridPane node = new GridPane();
		node.setPadding(new Insets(5));
		node.setVgap(5);
		node.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.ALWAYS);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHgrow(Priority.ALWAYS);
		node.getColumnConstraints().addAll(column1, column2);

		Label pubDateLbl = new Label("After Date:");
		pubDateAfterTxt = new CalendarTextField();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		pubDateAfterTxt.setValue(calendar);

		Label pubDateLbl2 = new Label("Before Date:");
		pubDateBeforeTxt = new CalendarTextField();

		showRetiredChk = new CheckBox("Show Retired");

		refreshBtn = new Button("Refresh");
		refreshBtn.setCursor(Cursor.HAND);
		refreshBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				if (saveBtn.isDisable() == false) {
	                int i = DialogFXBuilder.create()
	                .type(jfxtras.labs.dialogs.DialogFX.Type.QUESTION)
                	.message("You have unsaved changes. Do you want to save these?")
                	.build().showDialog();
	               
	                if (i == 0) { // user replied yes
	                	return;
	                }
				}
				
				NewsFeedEntrySearchParam searchParam = createSearchParam();
				
                try {
                	List<NewsFeedEntry> newsFeedEntries = pageModel.getNewsFeedEntries(searchParam);
                	displayNewsFeedEntries(newsFeedEntries);
                } catch (Exception e1) {
	                e1.printStackTrace();
	                DialogFXBuilder.create()
                		.message("Error searching for news feed entries.")
                		.build().showDialog();
                }

			}

		});

		GridPane.setHalignment(refreshBtn, HPos.RIGHT);

		node.add(pubDateLbl, 0, 0);
		node.add(pubDateLbl2, 1, 0);
		node.add(pubDateAfterTxt, 0, 1);
		node.add(pubDateBeforeTxt, 1, 1);
		node.add(showRetiredChk, 0, 2, 2, 1);

		node.add(refreshBtn, 0, 3, 2, 1);

		return node;
	}
	
	public void displayNewsFeedEntries(List<NewsFeedEntry> newsFeedEntries) {
		newsFeedEntryNodes.getChildren().clear();
		newsFeedEntryNodes2.clear();
		scroll.setVvalue(0);
		
		EventHandler<Event> eventHandler = new EventHandler<Event>() {

			@Override
            public void handle(Event evnt) {
				saveBtn.setDisable(false);
				cancelBtn.setDisable(false);
            }
			
		};

		NewsFeedEntryNode node = null;
		int row = 0;
		for (NewsFeedEntry e : newsFeedEntries) {
			++row;
			node = new NewsFeedEntryNode(e);
			node.addEventHandler(eventHandler);
			newsFeedEntryNodes2.add(node);
			newsFeedEntryNodes.getChildren().add(node.createView(row % 2 == 0));
		}

		FadeTransition fadeTransition = FadeTransitionBuilder.create()
		        .duration(Duration.seconds(1))
		        .node(newsFeedEntryNodes)
		        .fromValue(0)
		        .toValue(1)
		        .build();
		fadeTransition.play();
	}
	
	private NewsFeedEntrySearchParam createSearchParam() {
		NewsFeedEntrySearchParam searchParam = new NewsFeedEntrySearchParam();
		Calendar afterDate = pubDateAfterTxt.getValue();
		Calendar beforeDate = pubDateBeforeTxt.getValue();
		if (afterDate != null) {
			searchParam.setAfterDate(afterDate.getTime());	
		}
		if (beforeDate != null) {
			searchParam.setBeforeDate(beforeDate.getTime());
		}
		
		searchParam.setShowRetired(showRetiredChk.isSelected());
		
		return searchParam;
	}

	private Node buildButtonNode() {
		TilePane tile = new TilePane();
		tile.setPadding(new Insets(5, 0, 5, 0));
		tile.setVgap(4);
		tile.setHgap(4);
		tile.setPrefColumns(2);
		tile.setAlignment(Pos.CENTER_RIGHT);

		saveBtn = new Button("Save");
		saveBtn.setCursor(Cursor.HAND);
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					for (NewsFeedEntryNode n : newsFeedEntryNodes2) {
						n.updateEntry();
					}
					
	                pageModel.saveNewsFeedEntries();
	                
	        		cancelBtn.setDisable(true);
	        		saveBtn.setDisable(true);
                } catch (Exception e) {
	                e.printStackTrace();
	                DialogFXBuilder.create()
	                	.message("Error saving news feed entries.")
	                	.build().showDialog();
                }
			}

		});

		cancelBtn = new Button("Cancel");
		cancelBtn.setCursor(Cursor.HAND);
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
                	List<NewsFeedEntry> newsFeedEntries = pageModel.getOriginalNewsFeedEntries();
                	displayNewsFeedEntries(newsFeedEntries);
                	
					saveBtn.setDisable(true);
					cancelBtn.setDisable(true);
                } catch (Exception e) {
	                e.printStackTrace();
	                DialogFXBuilder.create()
                		.message("Error canceling news feed entry changes.")
                		.build().showDialog();
                }
			}

		});
		
		cancelBtn.setDisable(true);
		saveBtn.setDisable(true);

		tile.getChildren().addAll(cancelBtn, saveBtn);

		return tile;
	}

}
