/*
 * @(#)ComparisionButton.java			1 Apr 2013
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
package com.groovyfly.controlcentre.customcontrols;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * @author Chris Hatton
 */
public class ComparisionButton extends Button {
	
	public static enum Operator {
		LESS_THAN,
		EQUAL_TO,
		GREATER_THAN
	}
	
	private Operator operator = Operator.GREATER_THAN;
	
	/**
     * Constructor
     */
    public ComparisionButton() {
    	this.setText(">");
    	
    	this.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				if (operator == Operator.LESS_THAN) {
					ComparisionButton.this.setText(">");
					ComparisionButton.this.operator = Operator.GREATER_THAN;
				} else if (operator == Operator.GREATER_THAN) {
					ComparisionButton.this.setText("=");
					ComparisionButton.this.operator = Operator.EQUAL_TO;
				} else {
					ComparisionButton.this.setText("<");
					ComparisionButton.this.operator = Operator.LESS_THAN;
				}
			}
		});
    }

	public Operator getOperator() {
		return operator;
	}
    
}
