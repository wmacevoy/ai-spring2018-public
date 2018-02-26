/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.ArrayList;
import java.util.Collection;
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
public class EmptyCollectionTest {
    /**
     * Test of size method, of class EmptyCollection.
     */
    @Test
    public void testCollection() {
        EmptyCollection<Integer> instance = new EmptyCollection<Integer>();
        assertEquals(0,instance.size());
        ArrayList<Integer> a = new ArrayList<Integer>(instance);
        assertEquals(0,a.size());
        assertEquals(true,instance.isEmpty());
        assertEquals(false,instance.iterator().hasNext());
    }    
}
