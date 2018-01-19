/*
 * Copyright GroovyFly.com OrderTableFactory.java
 */
package com.groovyfly.controlcentre.client.orders;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import com.groovyfly.controlcentre.application.NavigationManager.HistoryAction;
import com.groovyfly.controlcentre.structure.common.orders.OrderStatus;
import com.groovyfly.controlcentre.structure.common.orders.OrderSummary;

/**
 * @author Chris Hatton
 */
public class OrderTableFactory {
	
	private static DateFormat standDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

	public static TableView<OrderSummary> createOrderTable(
			final MultiOrderPage multiOrderPage, final MultiOrderPageModel ordersPageModel, 
			final List<OrderStatus> orderStatuses, final OrderStatus orderStatus) {
		
		final TableView<OrderSummary> ordersTable = new TableView<OrderSummary>();
		ordersTable.setTableMenuButtonVisible(true);
		ordersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		ImageView emptyTableImg = new ImageView("META-INF/images/empty-table.png");
		ordersTable.setPlaceholder(emptyTableImg);

		TableColumn<OrderSummary, String> orderIdCol = new TableColumn<OrderSummary, String>("Order Id");
		orderIdCol.setMinWidth(180);
		orderIdCol.setCellValueFactory(new PropertyValueFactory<OrderSummary, String>("orderId"));

		TableColumn<OrderSummary, String> countryCol = new TableColumn<OrderSummary, String>("Country");
		countryCol.setMinWidth(130);
		countryCol.setCellValueFactory(new PropertyValueFactory<OrderSummary, String>("country"));

		TableColumn<OrderSummary, BigDecimal> subTotalCol = new TableColumn<OrderSummary, BigDecimal>("Sub-total");
		subTotalCol.setMinWidth(80);
		subTotalCol.setMaxWidth(80);
		subTotalCol.setResizable(false);
		subTotalCol.setCellValueFactory(new PropertyValueFactory<OrderSummary, BigDecimal>("subTotalAmount"));
		subTotalCol.setCellFactory(new Callback<TableColumn<OrderSummary, BigDecimal>, TableCell<OrderSummary, BigDecimal>>() {

			public TableCell<OrderSummary, BigDecimal> call(TableColumn<OrderSummary, BigDecimal> arg0) {
				
				return new TableCell<OrderSummary, BigDecimal>() {
					public void updateItem(BigDecimal item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							setText(item.toString());
							setAlignment(Pos.CENTER_RIGHT);
						}
					}
				};
			}
		});
		
		


		TableColumn<OrderSummary, BigDecimal> postageCol = new TableColumn<OrderSummary, BigDecimal>("Postage");
		postageCol.setMinWidth(80);
		postageCol.setMaxWidth(80);
		postageCol.setResizable(false);
		postageCol.setCellValueFactory(new PropertyValueFactory<OrderSummary, BigDecimal>("postageAndPackingAmount"));
		postageCol.setCellFactory(new Callback<TableColumn<OrderSummary, BigDecimal>, TableCell<OrderSummary, BigDecimal>>() {

			public TableCell<OrderSummary, BigDecimal> call(TableColumn<OrderSummary, BigDecimal> arg0) {
				
				return new TableCell<OrderSummary, BigDecimal>() {
					public void updateItem(BigDecimal item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							setText(item.toString());
							setAlignment(Pos.CENTER_RIGHT);
						}
					}
				};
			}
		});

		TableColumn<OrderSummary, BigDecimal> totalCol = new TableColumn<OrderSummary, BigDecimal>("Total");
		totalCol.setMinWidth(80);
		totalCol.setMaxWidth(80);
		totalCol.setResizable(false);
		totalCol.setCellValueFactory(new PropertyValueFactory<OrderSummary, BigDecimal>("totalAmount"));
		totalCol.setCellFactory(new Callback<TableColumn<OrderSummary, BigDecimal>, TableCell<OrderSummary, BigDecimal>>() {

			public TableCell<OrderSummary, BigDecimal> call(TableColumn<OrderSummary, BigDecimal> arg0) {
				
				return new TableCell<OrderSummary, BigDecimal>() {
					public void updateItem(BigDecimal item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							setText(item.toString());
							setAlignment(Pos.CENTER_RIGHT);
						}
					}
				};
			}
		});

		TableColumn<OrderSummary, Date> dateCol = new TableColumn<OrderSummary, Date>("Date");
		dateCol.setMinWidth(80);
		dateCol.setMaxWidth(80);
		dateCol.setResizable(false);
		dateCol.setCellValueFactory(new PropertyValueFactory<OrderSummary, Date>("dateCreated"));
		dateCol.setCellFactory(new Callback<TableColumn<OrderSummary, Date>, TableCell<OrderSummary, Date>>() {

			public TableCell<OrderSummary, Date> call(TableColumn<OrderSummary, Date> arg0) {
				return new TableCell<OrderSummary, Date>() {
					public void updateItem(Date item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							setText(standDateFormat.format(item));
							setAlignment(Pos.CENTER);
						}
					}
				};
			}
		});

		
		TableColumn<OrderSummary, OrderStatus> orderStatusCol = new TableColumn<OrderSummary, OrderStatus>("Status");
		orderStatusCol.setCellValueFactory(new PropertyValueFactory<OrderSummary, OrderStatus>("orderStatus"));
		orderStatusCol.setCellFactory(new Callback<TableColumn<OrderSummary, OrderStatus>, TableCell<OrderSummary, OrderStatus>>() {

			@Override
			public TableCell<OrderSummary, OrderStatus> call(final TableColumn<OrderSummary, OrderStatus> param) {

				TableCell<OrderSummary, OrderStatus> cell = new TableCell<OrderSummary, OrderStatus>() {

					@Override
					public void updateItem(OrderStatus item, boolean empty) {
						super.updateItem(item, empty);
						
						if (item != null) {
							
							
							
							ObservableList<OrderStatus> s = FXCollections.observableArrayList(orderStatuses);

							ChoiceBox<OrderStatus> choice = new ChoiceBox<>(s);
							choice.getSelectionModel().select(s.indexOf(item));
							
							choice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderStatus>() {

								@Override
                                public void changed(ObservableValue<? extends OrderStatus> arg0, OrderStatus oldStatus, OrderStatus newStatus) {
									
									param.getTableView().getSelectionModel().select(getIndex()); // otherwise we will get null pointer if row not selected
									
									OrderSummary selectedRow = ordersTable.getSelectionModel().getSelectedItem();
									
									selectedRow.setOrderStatus(newStatus);
									
									try {
	                                    ordersPageModel.doOrderSummaryStatusChange(selectedRow, oldStatus, newStatus);
                                    } catch (Exception e) {
	                                    e.printStackTrace();
                                    }
								}
								
							});

							// SETTING ALL THE GRAPHICS COMPONENT FOR CELL
							this.setAlignment(Pos.CENTER_RIGHT);
							setGraphic(choice);
							setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						}
					}
				};

				return cell;
			}
		});
		orderStatusCol.setEditable(true);
		

		TableColumn<OrderSummary, String> drillCol = new TableColumn<OrderSummary, String>("Drill");
		drillCol.setCellValueFactory(new PropertyValueFactory<OrderSummary, String>("orderId"));
		drillCol.setEditable(true);

		Callback<TableColumn<OrderSummary, String>, TableCell<OrderSummary, String>> printColumnCellFactory =
		        new Callback<TableColumn<OrderSummary, String>, TableCell<OrderSummary, String>>() {

			        public TableCell<OrderSummary, String> call(final TableColumn<OrderSummary, String> param) {

				        final TableCell<OrderSummary, String> cell = new TableCell<OrderSummary, String>() {

					        public void updateItem(String item, boolean empty) {
						        super.updateItem(item, empty);

						        if (empty) {
							        setText(null);
							        setGraphic(null);
						        } else {

						        	ImageView drillImg = new ImageView("META-INF/images/magnifier--arrow.png");
							        final Button btnPrint = new Button("", drillImg);
							        btnPrint.setCursor(Cursor.HAND);
							        btnPrint.setAlignment(Pos.CENTER);
							        btnPrint.setOnAction(new EventHandler<ActionEvent>() {

								        public void handle(ActionEvent event) {

									        param.getTableView().getSelectionModel().select(getIndex());

									        OrderSummary item = ordersTable.getSelectionModel().getSelectedItem();

									        if (item != null) {
										        SingleOrderPage singleOrderPage = (SingleOrderPage) multiOrderPage.getNavigationManager().requestPageChange("SingleOrderPage", HistoryAction.CLEAR_FORWARD_HISTORY, true);
										        try {
											        singleOrderPage.setOrderSummary(item);
										        } catch (Exception e) {
											        e.printStackTrace();
										        }
									        }

								        }
							        });

							        this.setAlignment(Pos.CENTER);
							        setGraphic(btnPrint);
							        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						        }
					        }
				        };
				        return cell;
			        }
		        };
		drillCol.setCellFactory(printColumnCellFactory);

		ordersTable.getColumns().add(orderIdCol);
		ordersTable.getColumns().add(countryCol);
		ordersTable.getColumns().add(subTotalCol);
		ordersTable.getColumns().add(postageCol);
		ordersTable.getColumns().add(totalCol);
		ordersTable.getColumns().add(dateCol);
		ordersTable.getColumns().add(orderStatusCol);
		ordersTable.getColumns().add(drillCol);


		return ordersTable;
	}
	
}
