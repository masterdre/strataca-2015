/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svds.resttest.operator;

import java.util.Arrays;
import java.util.List;
import static org.testng.Assert.*;

/**
 *
 * @author kevin
 */
public class StringNotLikeOperatorImplNGTest {
    
    public StringNotLikeOperatorImplNGTest() {
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of process method, of class StringNotLikeOperatorImpl.
     */
    @org.testng.annotations.Test
    public void testProcess() throws Exception {
        System.out.println("StringNotLikeOperatorImpl.process");
        String columnName = "testCol";
        List<String> values = Arrays.asList("value");
        StringNotLikeOperatorImpl instance = new StringNotLikeOperatorImpl();
        
        String expResult = "testCol NOT LIKE 'value' ";
        String result = instance.process(columnName, values);
        assertEquals(result, expResult);
    }
    
}
