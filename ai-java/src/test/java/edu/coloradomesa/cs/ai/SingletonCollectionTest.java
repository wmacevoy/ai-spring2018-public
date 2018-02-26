/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
public class SingletonCollectionTest {
    
    public SingletonCollectionTest() {
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
     * Test of size method, of class SingletonCollection.
     */
    @Test
    public void testBasic() {
        SingletonCollection<Integer> instance = new SingletonCollection<Integer>(3);
        assertEquals(1,instance.size());
        assertTrue(instance.contains(3));
        assertFalse(instance.contains(4));
        Iterator<Integer> i = instance.iterator();
        assertTrue(i.hasNext());
        assertEquals(Integer.valueOf(3),i.next());
        assertFalse(i.hasNext());
        ArrayList<Integer> a = new ArrayList<Integer>(instance);
        assertEquals(1,a.size());
        assertTrue(a.contains(3));
    }
}