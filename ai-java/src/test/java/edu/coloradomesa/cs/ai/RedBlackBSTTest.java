/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import edu.coloradomesa.cs.ai.RedBlackBST;
import java.util.TreeSet;
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
public class RedBlackBSTTest {
    
    public RedBlackBSTTest() {
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

    
    @Test
    public void testBusyBox() {
        System.out.println("size");
        int n = 100;
        RedBlackBST<Integer,Integer> instance = new RedBlackBST<Integer,Integer>();
        assertTrue(instance.isEmpty());
        for (int i=0; i<n; ++i) {
            assertEquals(i,instance.size());
            instance.put(i,-(i+1));
        }
        for (int i=0; i<n; ++i) {
            assertEquals(Integer.valueOf(-(i+1)),instance.get(i));
        }
        TreeSet<Integer> keys = new TreeSet<Integer>();
        for (int i=0; i<n; ++i) {
            keys.add(i);
        }
        
        for (int i=0; i<n; ++i) {
            Integer key = instance.key(i);
            assertTrue(keys.contains(key));
            keys.remove(key);
        }
        for (int i=0; i<n; ++i) {
            if (i % 5 == 0) { 
                instance.delete(i);
            }
        }
        
        int size = 0;
        for (int i=0; i<n; ++i) {
            if (i % 5 == 0) { 
                assertEquals(null,instance.get(i));
            } else {
                ++size;
                assertEquals(Integer.valueOf(-(i+1)),instance.get(i));
            }
        }
        assertEquals(size,instance.size());
    }

    /**
     * Test of isEmpty method, of class RedBlackBST.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        RedBlackBST instance = new RedBlackBST();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }
    
}
