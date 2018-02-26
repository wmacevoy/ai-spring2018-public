/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

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
public class ConstValueTest {
    @Test
    public void testSomeMethod() {
        ConstValue f = new ConstValue(Math.PI);
        assertEquals(Math.PI,f.of(null),0.0);
    }
    
}
