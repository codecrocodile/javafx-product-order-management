/*
 * Copyright GroovyFly.com, OrdersDAO.java
 */
package com.groovyfly.controlcentre.dao.mysql;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.groovyfly.controlcentre.structure.common.Customer;
import com.groovyfly.controlcentre.structure.common.PaypalTransactionValues;
import com.groovyfly.controlcentre.structure.common.ShippingAddress;
import com.groovyfly.controlcentre.structure.common.orders.Order;
import com.groovyfly.controlcentre.structure.common.orders.OrderItem;
import com.groovyfly.controlcentre.structure.common.orders.OrderSearchParam;
import com.groovyfly.controlcentre.structure.common.orders.OrderStatus;
import com.groovyfly.controlcentre.structure.common.orders.OrderSummary;
import com.groovyfly.controlcentre.util.DbUtil;

/**
 * @author Chris Hatton
 */
@Repository("OrderDAO")
public class OrderDAO {
	
	public static final Logger log = Logger.getLogger(OrderDAO.class);

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
	}

	public List<OrderSummary> searchOrderSummary(OrderSearchParam orderSearchParam) throws Exception {

		String whereClause = constructWhereClause(orderSearchParam);

		String sql = 
            "SELECT o.orderId, o.orderStatusCode, os_lu.description, o.shippingCountry, o.subTotalAmount, " +
            "	o.postageAndPackingAmount, o.total, o.dateCreated " +
            "FROM `order` o " +
            "JOIN orderstatus_lu os_lu ON o.orderStatusCode = os_lu.orderStatusCode " + whereClause;
		
		log.info(sql);
        
        return jdbcTemplate.query(sql, new OrderSummaryMapper());
	}
	
	private String constructWhereClause(OrderSearchParam orderSearchParam) {
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE 1 = 1 ");
		
		if (orderSearchParam.getOrderId() != null && !orderSearchParam.getOrderId().trim().equals("")) {
			sb.append("AND ");
			sb.append("o.orderId LIKE " + DbUtil.quoteStringOrNull(orderSearchParam.getOrderId().trim() + "%") + " ");
		}
		
		if (orderSearchParam.getEmailAddress() != null && !orderSearchParam.getEmailAddress().trim().equals("")) {
			sb.append("AND ");
			sb.append("o.email LIKE " + DbUtil.quoteStringOrNull(orderSearchParam.getEmailAddress().trim() + "%") + " ");
		}
		
		if (orderSearchParam.getCreatedAfter() != null) {
			sb.append("AND ");
			sb.append("o.dateCreated > " + DbUtil.dateToSqlStringOrNull(orderSearchParam.getCreatedAfter()) + " ");
		}
		
		if (orderSearchParam.getCreatedBefore() != null) {
			sb.append("AND ");
			sb.append("o.dateCreated < " + DbUtil.dateToSqlStringOrNull(orderSearchParam.getCreatedBefore()) + " ");
		}
		
		List<OrderStatus> orderStatuses = orderSearchParam.getOrderStatuses();
		if (orderStatuses != null && orderStatuses.size() > 0) {
			sb.append("AND o.orderStatusCode IN ( ");
			
			StringBuilder inClause = new StringBuilder();
			for (OrderStatus os : orderStatuses) {
				inClause.append("'" + os.getStatusCode() + "', ");
			}
			sb.append(inClause.substring(0, inClause.length() - 2));
			sb.append(" )");
		}
		
		return sb.toString();
	}
	
	private static class OrderSummaryMapper implements RowMapper<OrderSummary>{

		/* 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
	    @Override
	    public OrderSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	OrderSummary orderSummary = new OrderSummary();
	    	orderSummary.setOrderId(rs.getString("orderId"));
	    	
	    	OrderStatus orderStatus = new OrderStatus();
	    	orderStatus.setStatusCode(rs.getString("orderStatusCode"));
	    	orderStatus.setStatusDescription(rs.getString("description"));
	    	orderSummary.setOrderStatus(orderStatus);
	    	
	    	orderSummary.setCountry(rs.getString("shippingCountry"));
	    	orderSummary.setSubTotalAmount(rs.getBigDecimal("subTotalAmount"));
	    	orderSummary.setPostageAndPackingAmount(rs.getBigDecimal("postageAndPackingAmount"));
	    	orderSummary.setTotalAmount(rs.getBigDecimal("total"));
	    	orderSummary.setDateCreated(rs.getDate("dateCreated"));
	    	
	    	return orderSummary;
	    }

	}

	public int getOpenOrderCount() throws Exception {
		String sql =
	        "SELECT COUNT(1) AS cnt " +
		    "FROM `order` o " +
		    "WHERE o.orderStatusCode = ?";
		
		return jdbcTemplate.queryForInt(sql, new Object[]{"01"});
	}
	
	public int getPackagedOrderCount() throws Exception {
		String sql =
	        "SELECT COUNT(1) AS cnt " +
		    "FROM `order` o " +
		    "WHERE o.orderStatusCode = :orderStatusCode";
		
        SqlParameterSource namedParameters = new MapSqlParameterSource("orderStatusCode", "02");
        
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class); 
	}
	
	public Order getOrder(OrderSummary orderSummary) throws Exception {
		String sql = 
	        "SELECT o.orderId, o.orderStatusCode, os_lu.description, " +
	        "	o.shippingName, o.shippingAddressLine1, o.shippingAddressLine2, o.shippingAddressLine3, o.shippingAddressLine4, o.shippingCountry, o.shippingPostcode, o.shippingAddressStatus, " +
	        "	o.title, o.forename, o.surname, o.email, o.emailStatus, o.phoneNumber, " +
	        "	o.paymentprocessor, o.paypalPayerId, o.paypalTransationId, o.paypalPaymentDate, o.paypalPaymentStatus, o.payaplPendingReason, " +
	        "	o.subTotalAmount, o.postageAndPackingAmount, o.discountCode, o.discountAmount, o.total, o.dateCreated, " +
	        "	oi.orderItemsId, oi.productId, oi.name, oi.sku, oi.quantity, oi.unitPrice, oi.totalPrice, oi.currencyCode," +
	        "	p.storageLocation " +
	        "FROM `order` o " +
	        "JOIN orderstatus_lu os_lu ON o.orderStatusCode = os_lu.orderStatusCode " +
	        "JOIN orderitems oi ON oi.orderId = o.orderId " +
	        "JOIN product p on oi.productId = p.productId " +
	        "WHERE o.orderId = '" + orderSummary.getOrderId() + "'";
		
		List<Order> orders = jdbcTemplate.query(sql, new ResultSetExtractor<List<Order>>() {

			Map<String, Order> orderMap = new LinkedHashMap<>();
			Order order = null;
			
			@Override
            public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {

				while (rs.next()) {
					String orderId = rs.getString("orderId");
					order = orderMap.get(orderId);
					if (order == null) {
						order = new Order();
						System.out.println("new order created");
						
						// set the OrderSummary data first
						order.setOrderId(rs.getString("orderId"));
				    	
				    	OrderStatus orderStatus = new OrderStatus();
				    	orderStatus.setStatusCode(rs.getString("orderStatusCode"));
				    	orderStatus.setStatusDescription(rs.getString("description"));
				    	order.setOrderStatus(orderStatus);
				    	
				    	order.setCountry(rs.getString("shippingCountry"));
				    	order.setSubTotalAmount(rs.getBigDecimal("subTotalAmount"));
//				    	order.setVatAmount(rs.getBigDecimal(""));
				    	order.setDiscountCode(rs.getString("discountCode"));
				    	order.setDiscountAmount(rs.getBigDecimal("discountAmount") != null ? rs.getBigDecimal("discountAmount") : new BigDecimal(0.00));
				    	order.setPostageAndPackingAmount(rs.getBigDecimal("postageAndPackingAmount"));
				    	order.setTotalAmount(rs.getBigDecimal("total"));
				    	order.setDateCreated(rs.getDate("dateCreated"));
				    	
				    	order.setDateCreated(rs.getTimestamp("dateCreated"));
				    	
				    	System.out.println("xxx" + order.getDateCreated());
				    	
				    	// set the rest of the data
				    	Customer customer = new Customer();
				    	customer.setTitle(rs.getString("title"));
				    	customer.setForename(rs.getString("forename"));
				    	customer.setSurname(rs.getString("surname"));
				    	customer.setEmail(rs.getString("email"));
				    	customer.setEmailStatus(rs.getString("emailStatus"));
				    	customer.setPhoneNumber(rs.getString("phoneNumber"));
				    	
				    	order.setCustomer(customer);
				    	
				    	ShippingAddress shippingAddress = new ShippingAddress();
				    	shippingAddress.setShippingName(rs.getString("shippingName"));
				    	shippingAddress.setLine1(rs.getString("shippingAddressLine1"));
				    	shippingAddress.setLine2(rs.getString("shippingAddressLine2"));
				    	shippingAddress.setLine3(rs.getString("shippingAddressLine3"));
				    	shippingAddress.setLine4(rs.getString("shippingAddressLine4"));
				    	shippingAddress.setCountry(rs.getString("shippingCountry"));
				    	shippingAddress.setPostcode(rs.getString("shippingPostcode"));
				    	shippingAddress.setAddressStatus(rs.getString("shippingAddressStatus"));
				    	
				    	order.setShippingAddress(shippingAddress);
				    	
				    	PaypalTransactionValues paypalTransactionValues = new PaypalTransactionValues();
				    	paypalTransactionValues.setPaymentProcessor(rs.getString("paymentprocessor"));
				    	paypalTransactionValues.setPaypalPayerId(rs.getString("paypalPayerId"));
				    	paypalTransactionValues.setPaypalTransationId(rs.getString("paypalTransationId"));
				    	paypalTransactionValues.setPaypalPaymentDate(rs.getDate("paypalPaymentDate"));
				    	paypalTransactionValues.setPaypalPaymentStatus(rs.getString("paypalPaymentStatus"));
				    	paypalTransactionValues.setPayaplPendingReason(rs.getString("payaplPendingReason"));
				    	
				    	order.setPaypalTransactionValues(paypalTransactionValues);
						
						orderMap.put(orderId, order);
					}
					
					int orderItemsId = rs.getInt("orderItemsId");
					if (orderItemsId > 0) {
						OrderItem orderItem = new OrderItem();
						orderItem.setOrderItemsId(orderItemsId);
						orderItem.setProductId(rs.getInt("productId"));
						orderItem.setProductName(rs.getString("name"));
						orderItem.setProductSku(rs.getString("sku"));
						orderItem.setQuantity(rs.getInt("quantity"));
						orderItem.setStorageLocation(rs.getString("storageLocation"));
						orderItem.setUnitPrice(rs.getBigDecimal("unitPrice"));
						orderItem.setTotalPrice(rs.getBigDecimal("totalPrice"));
						orderItem.setCurrencyCode(rs.getString("currencyCode"));
						
						System.out.println(order);
						System.out.println(order.getOrderItems());
						order.getOrderItems().add(orderItem);
					}
                }
				
				return new ArrayList<Order>(orderMap.values());
            }
		});
		
		// in this case we are only expecting 1 order so just return this if we find it
		if (orders.size() > 0) {
			return orders.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Changes the status of an order.
	 * 
	 * @param orderId
	 * @param newStatus
	 * @throws Exception
	 */
	public void changeOrderStatus(String orderId, OrderStatus newStatus) throws Exception {
        String sql = 
            "UPDATE `order` " +
            "SET orderStatusCode = ? " +
            "WHERE orderId = ?";
        
        Object[] updateValues = new Object[]{ newStatus.getStatusCode(),  orderId };
        int[] updateValueTypes = new int[] { Types.VARCHAR, Types.VARCHAR };
        
        jdbcTemplate.update(sql, updateValues, updateValueTypes);
	}
	
	
}
