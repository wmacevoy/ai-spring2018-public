/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wmacevoy
 */
public class ReflectedRealParametersTest {

    static class BusyBox {

        public double apples;
        public double bananas;
        public int i;
        public double[] x = new double[3];

        private double cents;

        public double getCents() {
            return cents;
        }

        public void setCents(double value) {
            cents = value;
        }

        public double bucks() {
            return cents / 100.0;
        }

        public void bucks(double value) {
            cents = value * 100.0;
        }

        private void something() {
        }
    }

    public ReflectedRealParametersTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isPublic method, of class ReflectedRealParameters.
     */
    @Test
    public void testIsPublic_Field() throws Exception {
        assertTrue(ReflectedRealParameters.isPublic(BusyBox.class.getDeclaredField("apples")));
        assertTrue(ReflectedRealParameters.isPublic(BusyBox.class.getDeclaredField("i")));
        assertFalse(ReflectedRealParameters.isPublic(BusyBox.class.getDeclaredField("cents")));
    }

    /**
     * Test of isPublic method, of class ReflectedRealParameters.
     */
    @Test
    public void testIsPublic_Method() throws Exception {
        assertTrue(ReflectedRealParameters.isPublic(BusyBox.class.getDeclaredMethod("getCents")));
        assertTrue(ReflectedRealParameters.isPublic(BusyBox.class.getDeclaredMethod("setCents",double.class)));
        assertFalse(ReflectedRealParameters.isPublic(BusyBox.class.getDeclaredMethod("something")));
    }

    /**
     * Test of isDouble method, of class ReflectedRealParameters.
     */
    @Test
    public void testIsDouble() {
        assertTrue(ReflectedRealParameters.isDouble(double.class));
        assertFalse(ReflectedRealParameters.isDouble(Double.class));
        assertFalse(ReflectedRealParameters.isDouble(int.class));
    }

    /**
     * Test of getRealParameterSize method, of class ReflectedRealParameters.
     */
    @Test
    public void testGetRealParameterSize() {
        BusyBox bb = new BusyBox();
        ReflectedRealParameters instance = new ReflectedRealParameters(bb);
        int expResult = 7;
        int result = instance.getRealParameterSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRealParameterName method, of class ReflectedRealParameters.
     */
    @Test
    public void testGetRealParameterName() {
        BusyBox bb = new BusyBox();
        ReflectedRealParameters instance = new ReflectedRealParameters(bb);
        assertEquals("apples", instance.getRealParameterName(0));
        assertEquals("bananas", instance.getRealParameterName(1));
        assertEquals("x[0]", instance.getRealParameterName(2));
        assertEquals("x[1]", instance.getRealParameterName(3));
        assertEquals("x[2]", instance.getRealParameterName(4));
        assertEquals("bucks", instance.getRealParameterName(5));
        assertEquals("cents", instance.getRealParameterName(6));
    }

    /**
     * Test of getRealParameterIndex method, of class ReflectedRealParameters.
     */
    @Test
    public void testGetRealParameterIndex() {
        BusyBox bb = new BusyBox();
        ReflectedRealParameters instance = new ReflectedRealParameters(bb);
        assertEquals(0, instance.getRealParameterIndex("apples"));
        assertEquals(1, instance.getRealParameterIndex("bananas"));
        assertEquals(2, instance.getRealParameterIndex("x[0]"));
        assertEquals(3, instance.getRealParameterIndex("x[1]"));
        assertEquals(4, instance.getRealParameterIndex("x[2]"));
        assertEquals(5, instance.getRealParameterIndex("bucks"));
        assertEquals(6, instance.getRealParameterIndex("cents"));
    }

    /**
     * Test of getRealParameterValue method, of class ReflectedRealParameters.
     */
    @Test
    public void testRealParameterValues() {
        BusyBox bb = new BusyBox();
        ReflectedRealParameters instance = new ReflectedRealParameters(bb);
        for (int i = 0; i < instance.getRealParameterSize(); ++i) {
            for (double a : new double[]{-2, 3.14, 10}) {
                instance.setRealParameterValue(i, a);
                assertEquals("i=" + i, a, instance.getRealParameterValue(i), 0);
            }
        }
    }
}
