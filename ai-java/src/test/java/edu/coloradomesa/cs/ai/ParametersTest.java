/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class ParametersTest {

    public ParametersTest() {
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
     * Test of make method, of class Parameters.
     */
    @Test
    public void testMake() {
        Parameters p1 = Parameters.make()
                .set("name", "bob")
                .set("value", new BigDecimal("33.0")).parameters();
        Parameters p2 = Parameters.make()
                .set("name", "bob")
                .set("value", (byte) 33).parameters();
        Parameters p3 = Parameters.make(p2)
                .set("not", true)
                .parameters();
        assertEquals(p1, p2);
        assertTrue(p1.compareTo(p3) < 0);
    }

    public static class Simple {

        public String name;
        private BigDecimal value;

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal _value) {
            value = _value;
        }
        private boolean _not;

        public boolean not() {
            return _not;
        }

        public void not(boolean value) {
            _not = value;
        }
        boolean initialized = false;

        void init() {
            initialized = true;
        }
    }

    /**
     * Test of make method, of class Parameters.
     */
    @Test
    public void testCreate() {
        Parameters p = Parameters.make()
                .set("name", "bob")
                .set("value", (byte) 33)
                .set("not", true)
                .parameters();

        Simple simple = (Simple) Parameters.create(p, Simple.class);

        assertEquals(p.getString("name"), simple.name);
        assertEquals(BigDecimal.valueOf(p.getLong("value")), simple.getValue());
        assertEquals(p.getBoolean("not"), simple.not());
        assertEquals(true, simple.initialized);
    }

    /**
     * Test of defined method, of class Parameters.
     */
    @Test
    public void testDefined() {
        Parameters p = Parameters.make().set("a", 1).set("b", 2).parameters();
        assertTrue(p.defined("a"));
        assertTrue(p.defined("b"));
        assertFalse(p.defined("c"));
    }

    @Test
    public void testCopy() {
        Parameters p = Parameters.make()
                .set("a", 1)
                .set("b", Parameters.make()
                        .set("x", "test")
                        .set("y", "this")
                        .parameters()
                )
                .parameters();
        Parameters q = p.copy();
        assertEquals(p, q);
    }

    @Test
    public void testGetBoolean() {
        Parameters p = Parameters.make().set("x", false).parameters();
        assertFalse(p.getBoolean("x"));
        assertTrue(p.getBoolean("y", true));
        assertTrue(p.getBoolean("z", () -> true));
        boolean ok = false;
        try {
            p.getBoolean("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);
    }

    @Test
    public void testGetByte() {
        byte value = 23;
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getByte("x"));
        assertEquals(value, p.getByte("y", value));
        assertEquals(value, p.getByte("z", () -> value));
        boolean ok = false;
        try {
            p.getByte("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    @Test
    public void testShort() {
        short value = 23_000;
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getShort("x"));
        assertEquals(value, p.getShort("y", value));
        assertEquals(value, p.getShort("z", () -> value));
        boolean ok = false;
        try {
            p.getShort("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    @Test
    public void testInt() {
        int value = 1_700_000_000;
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getInt("x"));
        assertEquals(value, p.getInt("y", value));
        assertEquals(value, p.getInt("z", () -> value));
        boolean ok = false;
        try {
            p.getInt("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    @Test
    public void testLong() {
        long value = 1_700_000_000_000_000L;
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getLong("x"));
        assertEquals(value, p.getLong("y", value));
        assertEquals(value, p.getLong("z", () -> value));
        boolean ok = false;
        try {
            p.getLong("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    @Test
    public void testDouble() {
        double value = Math.PI;
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getDouble("x"), 0.0);
        assertEquals(value, p.getDouble("y", value), 0.0);
        assertEquals(value, p.getDouble("z", () -> value), 0.0);
        boolean ok = false;
        try {
            p.getDouble("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    @Test
    public void testBigInteger() {
        BigInteger value = new BigInteger("123456789123456789123456789123456789");
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getBigInteger("x"));
        assertEquals(value, p.getBigInteger("y", value));
        assertEquals(value, p.getBigInteger("z", () -> value));
        boolean ok = false;
        try {
            p.getBigInteger("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    @Test
    public void testBigDecimal() {
        BigDecimal value = new BigDecimal("123456789123456789123456789.123456789");
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getBigDecimal("x"));
        assertEquals(value, p.getBigDecimal("y", value));
        assertEquals(value, p.getBigDecimal("z", () -> value));
        boolean ok = false;
        try {
            p.getBigDecimal("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    @Test
    public void testString() {
        String value = "lucy";
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getString("x"));
        assertEquals(value, p.getString("y", value));
        assertEquals(value, p.getString("z", () -> value));
        boolean ok = false;
        try {
            p.getString("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    @Test
    public void testParameters() {
        Parameters value = Parameters.make()
                .set("alpha", Math.PI)
                .set("beta", "boop")
                .parameters();
        Parameters p = Parameters.make().set("x", value).parameters();
        assertEquals(value, p.getParameters("x"));
        assertEquals(value, p.getParameters("y", value));
        assertEquals(value, p.getParameters("z", () -> value));
        boolean ok = false;
        try {
            p.getParameters("z");
        } catch (NullPointerException ex) {
            ok = true;
        }
        assertTrue(ok);

    }

    /**
     * Test of toString method, of class Parameters.
     */
    @Test
    public void testToString() {
        Parameters value = Parameters.make()
                .set("alpha", Math.PI)
                .set("beta", "boop")
                .parameters();
        Parameters p = Parameters.make().set("x", value).parameters();
        String expect = "{x={alpha=" + Math.PI + ",beta='boop'}}";
        assertEquals(expect, p.toString());
    }

    static class Fields {

        public Parameters parameters;
        public boolean booleanValue;
        public byte byteValue;
        public short shortValue;
        public int intValue;
        public long longValue;
        public double doubleValue;
        public BigInteger bigIntegerValue;
        public BigDecimal bigDecimalValue;
        public String stringValue;
        public Parameters parametersValue;
    }

    /**
     * Test of modify method, of class Parameters.
     */
    @Test
    public void testModifyFields() {
        Parameters p = Parameters.make()
                .set("booleanValue", true)
                .set("byteValue", 23)
                .set("shortValue", 23_000)
                .set("intValue", 23_000_000)
                .set("longValue", 23_000_000_000_000L)
                .set("doubleValue", Math.sqrt(2.0))
                .set("bigIntegerValue", new BigInteger("123456789123456789123456789"))
                .set("bigDecimalValue", new BigDecimal("123456789123456789123456.789"))
                .set("stringValue", "alice")
                .set("parametersValue", Parameters.make().set("x", 1).set("y", 2).parameters())
                .parameters();

        Fields f = new Fields();
        p.modify(f);
        assertEquals(p,f.parameters);
        assertEquals(p.getBoolean("booleanValue"), f.booleanValue);
        assertEquals(p.getByte("byteValue"), f.byteValue);
        assertEquals(p.getShort("shortValue"), f.shortValue);
        assertEquals(p.getInt("intValue"), f.intValue);
        assertEquals(p.getLong("longValue"), f.longValue);
        assertEquals(p.getDouble("doubleValue"), f.doubleValue, 0.0);
        assertEquals(p.getBigInteger("bigIntegerValue"), f.bigIntegerValue);
        assertEquals(p.getBigDecimal("bigDecimalValue"), f.bigDecimalValue);
        assertEquals(p.getString("stringValue"), f.stringValue);
        assertEquals(p.getParameters("parametersValue"), f.parametersValue);
    }

    static class GetSet1 {

        private Parameters parameters;

        public Parameters getParameters() {
            return parameters;
        }

        public void setParameters(Parameters value) {
            parameters = value;
        }
        private boolean booleanValue;

        public boolean getBooleanValue() {
            return booleanValue;
        }

        public void setBooleanValue(boolean value) {
            booleanValue = value;
        }
        private byte byteValue;

        public byte getByteValue() {
            return byteValue;
        }

        public void setByteValue(byte value) {
            byteValue = value;
        }
        private short shortValue;

        public short getShortValue() {
            return shortValue;
        }

        public void setShortValue(short value) {
            shortValue = value;
        }
        private int intValue;

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int value) {
            intValue = value;
        }
        private long longValue;

        public long getLongValue() {
            return longValue;
        }

        public void setLongValue(long value) {
            longValue = value;
        }
        private double doubleValue;

        public double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(double value) {
            doubleValue = value;
        }
        private BigInteger bigIntegerValue;

        public BigInteger getBigIntegerValue() {
            return bigIntegerValue;
        }

        public void setBigIntegerValue(BigInteger value) {
            bigIntegerValue = value;
        }
        private BigDecimal bigDecimalValue;

        public BigDecimal getBigDecimalValue() {
            return bigDecimalValue;
        }

        public void setBigDecimalValue(BigDecimal value) {
            bigDecimalValue = value;
        }
        private String stringValue;

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(String value) {
            stringValue = value;
        }
        private Parameters parametersValue;

        public Parameters getParametersValue() {
            return parametersValue;
        }

        public void setParametersValue(Parameters value) {
            parametersValue = value;
        }
    }

    /**
     * Test of modify method, of class Parameters.
     */
    @Test
    public void testModifyGetSet1() {
        Parameters p = Parameters.make()
                .set("booleanValue", true)
                .set("byteValue", 23)
                .set("shortValue", 23_000)
                .set("intValue", 23_000_000)
                .set("longValue", 23_000_000_000_000L)
                .set("doubleValue", Math.sqrt(2.0))
                .set("bigIntegerValue", new BigInteger("123456789123456789123456789"))
                .set("bigDecimalValue", new BigDecimal("123456789123456789123456.789"))
                .set("stringValue", "alice")
                .set("parametersValue", Parameters.make().set("x", 1).set("y", 2).parameters())
                .parameters();

        GetSet1 f = new GetSet1();
        p.modify(f);
        assertEquals(p, f.getParameters());
        assertEquals(p.getBoolean("booleanValue"), f.getBooleanValue());
        assertEquals(p.getByte("byteValue"), f.getByteValue());
        assertEquals(p.getShort("shortValue"), f.getShortValue());
        assertEquals(p.getInt("intValue"), f.getIntValue());
        assertEquals(p.getLong("longValue"), f.getLongValue());
        assertEquals(p.getDouble("doubleValue"), f.getDoubleValue(), 0.0);
        assertEquals(p.getBigInteger("bigIntegerValue"), f.getBigIntegerValue());
        assertEquals(p.getBigDecimal("bigDecimalValue"), f.getBigDecimalValue());
        assertEquals(p.getString("stringValue"), f.getStringValue());
        assertEquals(p.getParameters("parametersValue"), f.getParametersValue());
    }
    
    static class GetSet2 {

        private Parameters parameters;

        public Parameters parameters() {
            return parameters;
        }

        public void parameters(Parameters value) {
            parameters = value;
        }
        private boolean booleanValue;

        public boolean booleanValue() {
            return booleanValue;
        }

        public void booleanValue(boolean value) {
            booleanValue = value;
        }
        private byte byteValue;

        public byte byteValue() {
            return byteValue;
        }

        public void byteValue(byte value) {
            byteValue = value;
        }
        private short shortValue;

        public short shortValue() {
            return shortValue;
        }

        public void shortValue(short value) {
            shortValue = value;
        }
        private int intValue;

        public int intValue() {
            return intValue;
        }

        public void intValue(int value) {
            intValue = value;
        }
        private long longValue;

        public long longValue() {
            return longValue;
        }

        public void longValue(long value) {
            longValue = value;
        }
        private double doubleValue;

        public double doubleValue() {
            return doubleValue;
        }

        public void doubleValue(double value) {
            doubleValue = value;
        }
        private BigInteger bigIntegerValue;

        public BigInteger bigIntegerValue() {
            return bigIntegerValue;
        }

        public void bigIntegerValue(BigInteger value) {
            bigIntegerValue = value;
        }
        private BigDecimal bigDecimalValue;

        public BigDecimal bigDecimalValue() {
            return bigDecimalValue;
        }

        public void bigDecimalValue(BigDecimal value) {
            bigDecimalValue = value;
        }
        private String stringValue;

        public String stringValue() {
            return stringValue;
        }

        public void stringValue(String value) {
            stringValue = value;
        }
        private Parameters parametersValue;

        public Parameters parametersValue() {
            return parametersValue;
        }

        public void parametersValue(Parameters value) {
            parametersValue = value;
        }
    }

    /**
     * Test of modify method, of class Parameters.
     */
    @Test
    public void testModifyGetSet2() {
        Parameters p = Parameters.make()
                .set("booleanValue", true)
                .set("byteValue", 23)
                .set("shortValue", 23_000)
                .set("intValue", 23_000_000)
                .set("longValue", 23_000_000_000_000L)
                .set("doubleValue", Math.sqrt(2.0))
                .set("bigIntegerValue", new BigInteger("123456789123456789123456789"))
                .set("bigDecimalValue", new BigDecimal("123456789123456789123456.789"))
                .set("stringValue", "alice")
                .set("parametersValue", Parameters.make().set("x", 1).set("y", 2).parameters())
                .parameters();

        GetSet2 f = new GetSet2();
        p.modify(f);
        assertEquals(p, f.parameters());
        assertEquals(p.getBoolean("booleanValue"), f.booleanValue());
        assertEquals(p.getByte("byteValue"), f.byteValue());
        assertEquals(p.getShort("shortValue"), f.shortValue());
        assertEquals(p.getInt("intValue"), f.intValue());
        assertEquals(p.getLong("longValue"), f.longValue());
        assertEquals(p.getDouble("doubleValue"), f.doubleValue(), 0.0);
        assertEquals(p.getBigInteger("bigIntegerValue"), f.bigIntegerValue());
        assertEquals(p.getBigDecimal("bigDecimalValue"), f.bigDecimalValue());
        assertEquals(p.getString("stringValue"), f.stringValue());
        assertEquals(p.getParameters("parametersValue"), f.parametersValue());
    }
}
