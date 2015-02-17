/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svds.resttest.model;

import java.util.HashMap;
import java.util.Map;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author kevin
 */
public class MetaDataNGTest {
    
    public MetaDataNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of getColumnName method, of class MetaData.
     */
    @Test
    public void testGetColumnName() {
        System.out.println("setColumnName");
        String columnName = "myCol";
        MetaData instance = new MetaData();
        instance.setColumnName(columnName);
        assertEquals(instance.getColumnName(), columnName);
    }

    /**
     * Test of setColumnName method, of class MetaData.
     */
    @Test
    public void testSetColumnName() {
        System.out.println("setColumnName");
        String columnName = "myCol";
        MetaData instance = new MetaData();
        instance.setColumnName(columnName);
        assertEquals(instance.getColumnName(), columnName);
    }

    /**
     * Test of getColumnAttributes method, of class MetaData.
     */
    @Test
    public void testGetColumnAttributes() {
        System.out.println("setColumnAttributes");
        Map<String, Object> columnAttributes = new HashMap<>();
        MetaData instance = new MetaData();
        instance.setColumnAttributes(columnAttributes);
        assertEquals(instance.getColumnAttributes(),columnAttributes);
    }

    /**
     * Test of setColumnAttributes method, of class MetaData.
     */
    @Test
    public void testSetColumnAttributes() {
        System.out.println("setColumnAttributes");
        Map<String, Object> columnAttributes = new HashMap<>();
        MetaData instance = new MetaData();
        instance.setColumnAttributes(columnAttributes);
        assertEquals(instance.getColumnAttributes(),columnAttributes);
    }
    
}
