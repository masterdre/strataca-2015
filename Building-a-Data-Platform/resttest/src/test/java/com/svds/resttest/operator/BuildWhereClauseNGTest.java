/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svds.resttest.operator;

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
public class BuildWhereClauseNGTest {
    
    public BuildWhereClauseNGTest() {
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
     * Test of buildWhereClause method, of class BuildWhereClause.
     */
    @Test
    public void testBuildWhereClause() throws Exception {
        System.out.println("buildWhereClause");
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("operator__episode_title", "stringlike");
        requestParams.add("column__episode_title", "%Cybermen%");
        String expResult = "episode_title LIKE '%Cybermen%' ";
        String result = BuildWhereClause.buildWhereClause(requestParams);
        assertEquals(result, expResult);
    }
    
}
