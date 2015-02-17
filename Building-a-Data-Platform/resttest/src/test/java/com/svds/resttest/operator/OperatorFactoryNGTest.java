/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svds.resttest.operator;

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
public class OperatorFactoryNGTest {
    
    public OperatorFactoryNGTest() {
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
     * Test of getInfo method, of class OperatorFactory.
     */
    @Test
    public void testGetInfo() {
        System.out.println("getInfo");
        
        String value = "STRINGEQUALS";
        String expResult = "com.svds.resttest.operator.StringEqualsOperatorImpl";
        String result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "STRINGNOTEQUALS";
        expResult = "com.svds.resttest.operator.StringNotEqualsOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "STRINGIN";
        expResult = "com.svds.resttest.operator.StringInOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "STRINGNOTIN";
        expResult = "com.svds.resttest.operator.StringNotInOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "STRINGLIKE";
        expResult = "com.svds.resttest.operator.StringLikeOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "STRINGNOTLIKE";
        expResult = "com.svds.resttest.operator.StringNotLikeOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        
        value = "INTEQUALS";
        expResult = "com.svds.resttest.operator.IntEqualsOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "INTNOTEQUALS";
        expResult = "com.svds.resttest.operator.IntNotEqualsOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "INTIN";
        expResult = "com.svds.resttest.operator.IntInOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "INTNOTIN";
        expResult = "com.svds.resttest.operator.IntNotInOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "INTLESSTHAN";
        expResult = "com.svds.resttest.operator.IntLessThanOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
        
        value = "INTGREATERTHAN";
        expResult = "com.svds.resttest.operator.IntGreaterThanOperatorImpl";
        result = OperatorFactory.getInfo(value);
        assertEquals(result, expResult);
    }

    /**
     * Test of getOperatorClass method, of class OperatorFactory.
     */
    @Test
    public void testGetOperatorClass() throws Exception {
        System.out.println("getOperatorClass");
        
        String operatorType = "STRINGEQUALS";
        Class expClass = StringEqualsOperatorImpl.class;
        Operator result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "STRINGNOTEQUALS";
        expClass = StringNotEqualsOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "STRINGIN";
        expClass = StringInOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "STRINGNOTIN";
        expClass = StringNotInOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "STRINGLIKE";
        expClass = StringLikeOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "STRINGNOTLIKE";
        expClass = StringNotLikeOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "INTEQUALS";
        expClass = IntEqualsOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "INTNOTEQUALS";
        expClass = IntNotEqualsOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "INTIN";
        expClass = IntInOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "INTNOTIN";
        expClass = IntNotInOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "INTLESSTHAN";
        expClass = IntLessThanOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
        
        operatorType = "INTGREATERTHAN";
        expClass = IntGreaterThanOperatorImpl.class;
        result = OperatorFactory.getOperatorClass(operatorType);
        assertEquals(result.getClass(), expClass);
    }
    
}
