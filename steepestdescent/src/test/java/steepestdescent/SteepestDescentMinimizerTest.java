/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

import java.util.Arrays;
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
public class SteepestDescentMinimizerTest {
    
    double[] grad(RealMin p) {
        int dim = p.getRealParameterSize();
        double [] ans = new double[dim];
        for (int i=0; i<dim; ++i) {
            double xi = p.getRealParameterValue(i);
            double eps=1e-7;
            p.setRealParameterValue(i, xi+eps);
            double yp=p.getValue();
            p.setRealParameterValue(i, xi-eps);
            double ym=p.getValue();
            ans[i]=(yp-ym)/(2*eps);
            p.setRealParameterValue(i, xi);
        }
        return ans;
        
    }

    class Rosenbrock extends ReflectedRealMin {

        public double x, y;

        public double of() {
            return Math.pow(1 - x, 2) + 100 * Math.pow((y - Math.pow(x, 2)), 2);
        }
    }

    class Strange extends ReflectedRealMin {

        public double jokes, fights;
        int ijokes, ifights;
        Strange() {
            ijokes = getRealParameterIndex("jokes");
            ifights = getRealParameterIndex("fights");
        }
        public double of() {
            double d2 = 0;
            if (jokes < 0) {
                double d = Math.abs(jokes);
                d2 += Math.pow(d,2);
                jokes = 0;
            }
            if (fights < 0) {
                double d = Math.abs(fights);       
                d2 += Math.pow(d,2);
                fights = 0;
            }
            if (fights > 2) {
                double d = Math.abs(fights-2);
                d2 += Math.pow(d,2);
                fights = 2;
            }
            return Math.pow(jokes + fights - 6, 2) + Math.pow(fights - 4, 2) + d2;

        }
        
        public double[] grad() {
            double [] dx = new double[2];
            dx[ijokes] = 2*(jokes+fights-6);
            dx[ifights] = 2*(jokes+fights-6)+2*(fights-4);
            return dx;
        }
    }

    public SteepestDescentMinimizerTest() {
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
    public void testRosenbrock() {
        Rosenbrock f = new Rosenbrock();
        SteepestDescentMinimizer instance = new SteepestDescentMinimizer();
        instance.setProblem(f);
        instance.eps = 1e-6;
        instance.min();
        assertEquals(1.0, f.x, Math.sqrt(instance.eps));
        assertEquals(1.0, f.y, Math.sqrt(instance.eps));
    }

    /**
     * Test of setProblem method, of class SteepestDescentMinimizer.
     */
    @Test
    public void testStrange() {
        Strange f = new Strange();

        SteepestDescentMinimizer instance = new SteepestDescentMinimizer();
        instance.setProblem(f);
        instance.eps = 1e-6;
        instance.min();
        
        assertEquals(4.0, f.jokes, Math.sqrt(instance.eps));
        assertEquals(2.0, f.fights, Math.sqrt(instance.eps));
    }

    @Test
    public void testStrangeRegression1() {
        Strange f = new Strange();
        int dim=2;
        double [] x = new double [2];
        x[f.ijokes]=3;
        x[f.ifights]=2;
        for (int i=0; i<2; ++i) {
            f.setRealParameterValue(i,x[i]);
        }
        
                
        double [] dx = grad(f);
        double [] dx2 = f.grad();
        
        System.out.println("grad(" + Arrays.toString(x) + ")=" 
                + Arrays.toString(dx)
                + ", " + Arrays.toString(dx2)
        );

        SteepestDescentMinimizer instance = new SteepestDescentMinimizer();
        instance.setProblem(f);
        instance.eps = 1e-6;
        instance.min();
        
        assertEquals(2.0, f.jokes, Math.sqrt(instance.eps));
        assertEquals(2.0, f.fights, Math.sqrt(instance.eps));
    }
}
