/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svds.resttest.model;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
public class GenericCountOutputNGTest {
    
    public GenericCountOutputNGTest() {
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
     * Test of getName method, of class GenericCountOutput.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String name = "myName";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setName(name);
        assertEquals(instance.getName(),name);
    }

    /**
     * Test of setName method, of class GenericCountOutput.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "myName";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setName(name);
        assertEquals(instance.getName(),name);
    }

    /**
     * Test of getProcessTimeMillis method, of class GenericCountOutput.
     */
    @Test
    public void testGetProcessTimeMillis() {
        System.out.println("getProcessTimeMillis");
        long processTime = 1234567890L;
        GenericCountOutput instance = new GenericCountOutput();
        instance.setProcessTimeMillis(processTime);
        assertEquals(instance.getProcessTimeMillis(),processTime);
    }

    /**
     * Test of setProcessTimeMillis method, of class GenericCountOutput.
     */
    @Test
    public void testSetProcessTimeMillis() {
        System.out.println("setProcessTimeMillis");
        long processTime = 1234567890L;
        GenericCountOutput instance = new GenericCountOutput();
        instance.setProcessTimeMillis(processTime);
        assertEquals(instance.getProcessTimeMillis(),processTime);
    }

    /**
     * Test of getStatus method, of class GenericCountOutput.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        String status = "ERROR";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setStatus(status);
        assertEquals(instance.getStatus(),status);
    }

    /**
     * Test of setStatus method, of class GenericCountOutput.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        String status = "ERROR";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setStatus(status);
        assertEquals(instance.getStatus(),status);
    }

    /**
     * Test of getMessage method, of class GenericCountOutput.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        String message = "This is a test";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setMessage(message);
        assertEquals(instance.getMessage(),message);
    }

    /**
     * Test of setMessage method, of class GenericCountOutput.
     */
    @Test
    public void testSetMessage() {
        System.out.println("setMessage");
        String message = "This is a test";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setMessage(message);
        assertEquals(instance.getMessage(),message);
    }

    /**
     * Test of getRequestParams method, of class GenericCountOutput.
     */
    @Test
    public void testGetRequestParams() {
        System.out.println("getRequestParams");
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        GenericCountOutput instance = new GenericCountOutput();
        instance.setRequestParams(requestParams);
        assertEquals(instance.getRequestParams(), requestParams);
    }

    /**
     * Test of setRequestParams method, of class GenericCountOutput.
     */
    @Test
    public void testSetRequestParams() {
        System.out.println("setRequestParams");
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        GenericCountOutput instance = new GenericCountOutput();
        instance.setRequestParams(requestParams);
        assertEquals(instance.getRequestParams(), requestParams);
    }

    /**
     * Test of getCount method, of class GenericCountOutput.
     */
    @Test
    public void testGetCount() {
        System.out.println("getCount");
        long count = 1234567890L;
        GenericCountOutput instance = new GenericCountOutput();
        instance.setCount(count);
        assertEquals(instance.getCount(),count);
    }

    /**
     * Test of setCount method, of class GenericCountOutput.
     */
    @Test
    public void testSetCount() {
        System.out.println("setCount");
        long count = 1234567890L;
        GenericCountOutput instance = new GenericCountOutput();
        instance.setCount(count);
        assertEquals(instance.getCount(),count);
    }

    /**
     * Test of getSql method, of class GenericCountOutput.
     */
    @Test
    public void testGetSql() {
        System.out.println("getSql");
        String sql = "SELECT * FROM table WHERE col=val";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setSql(sql);
        assertEquals(instance.getSql(),sql);
    }

    /**
     * Test of setSql method, of class GenericCountOutput.
     */
    @Test
    public void testSetSql() {
        System.out.println("setSql");
        String sql = "SELECT * FROM table WHERE col=val";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setSql(sql);
        assertEquals(instance.getSql(),sql);
    }

    /**
     * Test of getDatabaseEngine method, of class GenericCountOutput.
     */
    @Test
    public void testGetDatabaseEngine() {
        System.out.println("getDatabaseEngine");
        String databaseEngine = "impala";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setDatabaseEngine(databaseEngine);
        assertEquals(instance.getDatabaseEngine(),databaseEngine);
    }

    /**
     * Test of setDatabaseEngine method, of class GenericCountOutput.
     */
    @Test
    public void testSetDatabaseEngine() {
        System.out.println("setDatabaseEngine");
        String databaseEngine = "impala";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setDatabaseEngine(databaseEngine);
        assertEquals(instance.getDatabaseEngine(),databaseEngine);
    }

    /**
     * Test of getTableName method, of class GenericCountOutput.
     */
    @Test
    public void testGetTableName() {
        System.out.println("getTableName");
        String tableName = "myTable";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setTableName(tableName);
        assertEquals(instance.getTableName(),tableName);
    }

    /**
     * Test of setTableName method, of class GenericCountOutput.
     */
    @Test
    public void testSetTableName() {
        System.out.println("setTableName");
        String tableName = "myTable";
        GenericCountOutput instance = new GenericCountOutput();
        instance.setTableName(tableName);
        assertEquals(instance.getTableName(),tableName);
    }
    
}
