/*
 * Copyright GroovyFly.com OrderService.java
 */
package com.groovyfly.controlcentre.service.orders;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groovyfly.controlcentre.structure.common.orders.OrderStatus;

/**
 * @author Chris Hatton
 */
@Service("OrderService")
@Repository
@Transactional
public class OrderService {
	
    @PersistenceContext
    private EntityManager em;

	/* 
	 * @see com.groovyfly.controlcentre.service.OrderServiceIF#getOrderStatuses()
	 */
    @Transactional(readOnly=true)
    public List<OrderStatus> getOrderStatuses() {
    	
    	
//    	TypedQuery<OrderStatus> createQuery = em.createQuery("from OrderStatus", OrderStatus.class);
//    	List<OrderStatus> resultList = createQuery.getResultList();
    	
    	TypedQuery<OrderStatus> namedQuery = em.createNamedQuery("OrderStatus.findAll", OrderStatus.class);
    	List<OrderStatus> resultList = namedQuery.getResultList();
    	
    	return resultList;
    }

}
