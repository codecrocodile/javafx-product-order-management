/*
 * @(#)SalesReportMonthly.java 17 Mar 2013
s * 
 * Copyright (c) 2012-2013 Groovy Fly. 3 Aillort place, East Mains, East
 * Kilbride, Scotland. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Groovy Fly.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Groovy Fly.
 */
package com.groovyfly.controlcentre.client.reports.financial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import jfxtras.labs.scene.control.CalendarTextField;

import org.springframework.beans.factory.annotation.Autowired;

import com.groovyfly.controlcentre.application.Page;
import com.groovyfly.controlcentre.service.financial.FinancialReportsServiceIF;

/**
 * @author Chris Hatton
 */
public class SalesReportMonthly extends Page {

	@Autowired
	private FinancialReportsServiceIF financialReportsServiceIF;
	
	private VBox parametersPane= null;
	
	private CalendarTextField afterDateTxt;
	
	private CalendarTextField beforeDateTxt;

	private LineChart<String, Number> salesChart;
	

	/*
	 * @see com.groovyfly.controlcentre.client.Page#createView()
	 */
	@Override
	protected Node createView() throws Exception {
		StackPane stack = new StackPane();

		Node buildParamNode = buildParamNode();
		StackPane.setAlignment(buildParamNode, Pos.TOP_CENTER);

		stack.getChildren().addAll(buildChartNode(), buildParamNode);

		return stack;
	}

	/*
	 * Builds the node for collecting the search parameters used to get the report data.
	 */
	private Node buildParamNode() {
		parametersPane = new VBox();
		parametersPane.setPadding(new Insets(10));
		parametersPane.setSpacing(5);
		parametersPane.setStyle("-fx-background-color: -fx-base");

		Label afterDateLbl = new Label("After Date");
		afterDateTxt = new CalendarTextField();
		
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.YEAR, -1);
		afterDateTxt.setValue(instance);
		
		Label beforeDateLbl = new Label("Before Date");
		beforeDateTxt = new CalendarTextField();

		Button showReportBtn = new Button("Show Report");
		showReportBtn.setCursor(Cursor.HAND);
		showReportBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				SalesReportSearchParam salesReportSearchParam = new SalesReportSearchParam();
				if (afterDateTxt.getValue() != null) {
					salesReportSearchParam.setAfterDate(afterDateTxt.getValue().getTime());	
				}
				if (beforeDateTxt.getValue() != null) {
					salesReportSearchParam.setBeforeDate(beforeDateTxt.getValue().getTime());	
				}
				
				
				try {
					
	                List<SalesMonthly> monthlySalesFigures = financialReportsServiceIF.getMonthlySalesFigures(salesReportSearchParam);
	                
	        		
	        		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
	        		series.setName("Sales by Month");
	        		
	        		Collections.sort(monthlySalesFigures);
	        		
	        		CategoryAxis xAxis2 = (CategoryAxis) salesChart.getXAxis();
	        		xAxis2.setAutoRanging(false);
	        		List<String> s = new ArrayList<>();
	        		for (SalesMonthly sm : monthlySalesFigures) {
	        			s.add(sm.toString());
	        		}
	        		ObservableList<String> observableArrayList = FXCollections.observableArrayList(s);
	        		xAxis2.setCategories(observableArrayList);   
	        		xAxis2.invalidateRange(s);
	        		
	        		for (SalesMonthly sm : monthlySalesFigures) {
	        			series.getData().add(new XYChart.Data<String, Number>(sm.toString(), sm.getSalesCount()));	
	        		}
	        		
	        		salesChart.getData().clear();
	        		salesChart.getData().add(series);
	        		
                } catch (Exception e) {
	                e.printStackTrace();
                }
				
				FadeTransition fadeTransition = FadeTransitionBuilder.create()
				        .duration(Duration.seconds(0.7))
				        .node(parametersPane)
				        .fromValue(1)
				        .toValue(0)
				        .onFinished(new EventHandler<ActionEvent>() {
							
							@Override
							public void handle(ActionEvent arg0) {
								parametersPane.setVisible(false);
							}
						})
				        .build();
				fadeTransition.play();
			}
		});

		HBox hBox = HBoxBuilder.create().alignment(Pos.CENTER_RIGHT).children(showReportBtn).build();
		VBox.setMargin(hBox, new Insets(10, 0, 0, 0));
		parametersPane.getChildren().addAll(afterDateLbl, afterDateTxt, beforeDateLbl, beforeDateTxt, hBox);

		DropShadow build = DropShadowBuilder.create().offsetY(1).build();
		parametersPane.setEffect(build);
		parametersPane.setMaxHeight(Region.USE_PREF_SIZE);
		parametersPane.setMaxWidth(200);

		return parametersPane;
	}

	/*
	 * Builds the node used to display the chart with the report data.
	 */
	private Node buildChartNode() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: rgb(140, 190, 230);");
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		salesChart = new LineChart<String, Number>(xAxis, yAxis);

		xAxis.setLabel("Month");
		yAxis.setLabel("Sales");

		Button showReportParamNodeBtn = new Button("Show Report Parameters");
		showReportParamNodeBtn.setCursor(Cursor.HAND);
		showReportParamNodeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (parametersPane.getOpacity() == 0) {
					parametersPane.setVisible(true);
					
					FadeTransition fadeTransition = FadeTransitionBuilder.create()
					        .duration(Duration.seconds(0.7))
					        .node(parametersPane)
					        .fromValue(0)
					        .toValue(1)
					        .build();
					fadeTransition.play();
				}
			}
		});

		FlowPane flowPane = FlowPaneBuilder.create().alignment(Pos.CENTER).children(showReportParamNodeBtn).build();
		pane.setTop(flowPane);

		pane.setCenter(salesChart);

		return pane;
	}

}
