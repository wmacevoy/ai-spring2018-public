/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

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
public class SampleMinimizerTest {
    
    public SampleMinimizerTest() {
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

    class Rosenbrock extends ReflectedRealMin {

        public double x, y;

        public double of() {
            return Math.pow(1 - x, 2) + 100 * Math.pow((y - Math.pow(x, 2)), 2);
        }
    }

    class Strange extends ReflectedRealMin {

        public double jokes, fights;

        public double of() {
            double d2 = 0;
            if (jokes < 0) {
                d2 += jokes * jokes;
                jokes = 0;
            }

            if (fights < 0) {
                d2 += fights * fights;
                fights = 0;
            }
            if (fights > 2) {
                d2 += (fights - 2) * (fights - 2);
                fights = 2;
            }
            return Math.pow(jokes + fights - 6, 2) + Math.pow(fights - 4, 2) + d2;

        }
    }

    @Test
    public void testRosenbrock() {
        Rosenbrock f = new Rosenbrock();
        SampleMinimizer instance = new SampleMinimizer();
        instance.setProblem(f);
        double eps = 0.01;
        instance.setBox(-10,eps,10);
        double ans=instance.min();
        assertEquals(1.0, f.x, Math.sqrt(eps));
        assertEquals(1.0, f.y, Math.sqrt(eps));
    }

    /**
     * Test of setProblem method, of class SteepestDescentMinimizer.
     */
    @Test
    public void testStrange() {
        Strange f = new Strange();
        
        SampleMinimizer instance = new SampleMinimizer();
        instance.setProblem(f);
        double eps = 0.01;
        int ijokes = f.getRealParameterIndex("jokes");
        int ifights = f.getRealParameterIndex("fights");
        double [] xmins = new double[2];
        double [] xmaxs = new double[2];
        double [] dxs = new double[2];
        
        xmins[ijokes]=0;
        xmaxs[ijokes]=10;
        dxs[ijokes]=eps;
        xmins[ifights]=0;
        xmaxs[ifights]=2;
        dxs[ifights]=eps;      
        
        instance.setBox(xmins,dxs,xmaxs);
        instance.min();
        assertEquals(4.0, f.jokes, Math.sqrt(eps));
        assertEquals(2.0, f.fights, Math.sqrt(eps));
    }
    
}
