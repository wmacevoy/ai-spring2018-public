/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author wmacevoy
 */
public class GoldenSectionMinimizerTest {

    class F extends ReflectedRealMin {

        public double x;

        public double of() {
            return Math.pow(x - 0.25, 2);
        }
    }

    class G extends ReflectedRealMin {

        public double x;

        public double of() {
            return Math.exp(x);
        }
    }

    class H extends ReflectedRealMin {

        public double x;

        public double of() {
            return 1.0 / (1 + Math.pow(x, 2));
        }
    }

    /**
     * Test of setProblem method, of class GoldenSectionMinimizer.
     */
    @Test
    public void testF() {
        F f = new F();
        GoldenSectionMinimizer instance = new GoldenSectionMinimizer();
        instance.setProblem(f);
        instance.a = 0;
        instance.b = 1;
        instance.eps = 1e-6;
        instance.min();
        assertEquals(0.25, f.getRealParameterValue(0), instance.eps);
    }

    @Test
    public void testG() {
        RealMin g = new G();
        GoldenSectionMinimizer instance = new GoldenSectionMinimizer();
        instance.setProblem(g);
        instance.a = 0;
        instance.b = 1;
        instance.eps = 1e-6;
        instance.min();
        assertEquals(0, g.getRealParameterValue(0), instance.eps);
    }

    @Test
    public void testH() {
        RealMin h = new H();
        GoldenSectionMinimizer instance = new GoldenSectionMinimizer();
        instance.setProblem(h);
        instance.a = 0;
        instance.b = 1;
        instance.eps = 1e-6;
        instance.min();
        assertEquals(1, h.getRealParameterValue(0), instance.eps);
    }
}
