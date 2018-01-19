/*
 * Copyright GroovyFly.com DbUtil.java
 */
package com.groovyfly.controlcentre.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Chris Hatton
 */
public class DbUtil {
    
    public static String quoteStringOrNull(String s) {
        if (s == null) {
            return "NULL";
        } else {
            return "'" + addSlashes(s) + "'";
        }
    }
    
    public static String dateToSqlStringOrNull(Date date) {
        if (date == null) {
            return "NULL";
        } else {
    	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return "'" + df.format(date) + "'";
        }
    }
    
    public static String addSlashes(String s) {
        return s.replace("'", "\\'");
    }
    
    public static String getInValue(int... values) {
        StringBuilder sb = new StringBuilder();
        
        for (int j : values) {
            sb.append(j + ",");                
        }

        String toReturn = "";
        if (sb.length() > 0) {
            toReturn = sb.substring(0, sb.lastIndexOf(","));
        }
        
        return toReturn;
    }
    
    public static String getInValue(List<Integer> values) {
        StringBuilder sb = new StringBuilder();
        
        for (int j : values) {
            sb.append(j + ",");                
        }

        String toReturn = "";
        if (sb.length() > 0) {
            toReturn = sb.substring(0, sb.lastIndexOf(","));
        }
        
        return toReturn;
    }

}