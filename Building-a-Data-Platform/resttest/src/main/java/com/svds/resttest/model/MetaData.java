/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.svds.resttest.model;

import java.util.HashMap;
import java.util.Map;

/**
 * POJO for metadata with a column name and associated metadata map
 * 
 * @author steveo
 */
public class MetaData {
    
    private String columnName;
    private Map<String, Object> columnAttributes;
    
    /**
     *
     */
    public MetaData(){
        this.columnAttributes = new HashMap<String, Object>();
    }

    /**
     *
     * @return Name of the column this metadata pertains to
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     *
     * @param columnName Name of the column this metadata pertains to
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     *
     * @return Metadata attributes associated with the column
     */
    public Map<String, Object> getColumnAttributes() {
        return columnAttributes;
    }

    /**
     *
     * @param columnAttributes Metadata attributes associated with the column
     */
    public void setColumnAttributes(Map<String, Object> columnAttributes) {
        this.columnAttributes = columnAttributes;
    }
    
    
}