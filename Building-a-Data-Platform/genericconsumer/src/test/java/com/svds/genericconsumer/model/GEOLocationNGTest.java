package com.svds.genericconsumer.model;

import com.svds.genericconsumer.model.GEOLocation;
import java.math.BigDecimal;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author steveo
 */
public class GEOLocationNGTest {
    
    private String stringValue = "String";
    private BigDecimal bigDecimalValue = new BigDecimal(10.00);
    private long longValue = 10000000;
    
    public GEOLocationNGTest() {
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
     * Test of getId method, of class GEOLocation.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        GEOLocation instance = new GEOLocation();
        String expResult = stringValue;
        instance.setId(stringValue);
        String result = instance.getId();
        assertEquals(result, expResult);
        
    }

    /**
     * Test of setId method, of class GEOLocation.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = stringValue;
        GEOLocation instance = new GEOLocation();
        instance.setId(id);
        assertEquals(instance.getId(), stringValue);
    }

    /**
     * Test of getLatitude method, of class GEOLocation.
     */
    @Test
    public void testGetLatitude() {
        System.out.println("getLatitude");
        GEOLocation instance = new GEOLocation();
        BigDecimal expResult = bigDecimalValue;
        instance.setLatitude(expResult);
        BigDecimal result = instance.getLatitude();
        assertEquals(result, expResult);
    }

    /**
     * Test of setLatitude method, of class GEOLocation.
     */
    @Test
    public void testSetLatitude() {
        System.out.println("setLatitude");
        BigDecimal latitude = bigDecimalValue;
        GEOLocation instance = new GEOLocation();
        instance.setLatitude(latitude);
        assertEquals(instance.getLatitude(), bigDecimalValue);
    }

    /**
     * Test of getLongitude method, of class GEOLocation.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("getLongitude");
        GEOLocation instance = new GEOLocation();
        BigDecimal expResult = bigDecimalValue;
        instance.setLongitude(bigDecimalValue);
        BigDecimal result = instance.getLongitude();
        assertEquals(result, expResult);
    }

    /**
     * Test of setLongitude method, of class GEOLocation.
     */
    @Test
    public void testSetLongitude() {
        System.out.println("setLongitude");
        BigDecimal longitude = bigDecimalValue;
        GEOLocation instance = new GEOLocation();
        instance.setLongitude(longitude);
        assertEquals(instance.getLongitude(), bigDecimalValue);
    }

    /**
     * Test of getEpoch method, of class GEOLocation.
     */
    @Test
    public void testGetEpoch() {
        System.out.println("getEpoch");
        GEOLocation instance = new GEOLocation();
        long expResult = longValue;
        instance.setEpoch(longValue);
        long result = instance.getEpoch();
        assertEquals(result, expResult);
    }

    /**
     * Test of setEpoch method, of class GEOLocation.
     */
    @Test
    public void testSetEpoch() {
        System.out.println("setEpoch");
        long epoch = longValue;
        GEOLocation instance = new GEOLocation();
        instance.setEpoch(epoch);
        assertEquals(instance.getEpoch(), longValue);
    }

    /**
     * Test of getAccuracy method, of class GEOLocation.
     */
    @Test
    public void testGetAccuracy() {
        System.out.println("getAccuracy");
        GEOLocation instance = new GEOLocation();
        BigDecimal expResult = bigDecimalValue;
        instance.setAccuracy(bigDecimalValue);
        BigDecimal result = instance.getAccuracy();
        assertEquals(result, expResult);
    }

    /**
     * Test of setAccuracy method, of class GEOLocation.
     */
    @Test
    public void testSetAccuracy() {
        System.out.println("setAccuracy");
        BigDecimal accuracy = bigDecimalValue;
        GEOLocation instance = new GEOLocation();
        instance.setAccuracy(accuracy);
        assertEquals(instance.getAccuracy(), bigDecimalValue);
    }

    /**
     * Test of toString method, of class GEOLocation.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        GEOLocation instance = new GEOLocation();
        instance.setId(stringValue);
        instance.setLatitude(bigDecimalValue);
        instance.setLongitude(bigDecimalValue);
        instance.setEpoch(longValue);
        instance.setAccuracy(bigDecimalValue);
        String expResult = stringValue + "/" + bigDecimalValue + "/" + bigDecimalValue + "/" + longValue + "/" + bigDecimalValue;
        String result = instance.toString();
        assertEquals(result, expResult);
    }
    
}
