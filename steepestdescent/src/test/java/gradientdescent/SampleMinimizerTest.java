/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author wmacevoy
 */
public class SampleMinimizerTest {

    @Test
    public void testRosenbrock() {
        Rosenbrock f = new Rosenbrock();
        SampleMinimizer instance = new SampleMinimizer();
        instance.setProblem(f);
        double eps = 0.01;
        instance.setBox(-10, eps, 10);
        double ans = instance.min();
        double[] x = new double[]{1, 1};
        assertArrayEquals(x, f.getRealParameters(null), Math.sqrt(eps));
    }

    /**
     * Test of setProblem method, of class SteepestDescentMinimizer.
     */
    @Test
    public void testStrange() {
        DrStrangeRealMin f = new DrStrangeRealMin();

        SampleMinimizer instance = new SampleMinimizer();
        instance.setProblem(f);
        double eps = 0.01;
        int ijokes = f.getRealParameterIndex("jokes");
        int ifights = f.getRealParameterIndex("fights");
        double[] xmins = new double[2];
        double[] xmaxs = new double[2];
        double[] dxs = new double[2];

        xmins[ijokes] = 0;
        xmaxs[ijokes] = 10;
        dxs[ijokes] = eps;
        xmins[ifights] = 0;
        xmaxs[ifights] = 2;
        dxs[ifights] = eps;

        instance.setBox(xmins, dxs, xmaxs);
        instance.min();

        double[] expect = new double[2];
        double[] result = f.getRealParameters(null);
        expect[ifights] = 2.0;
        expect[ijokes] = 4.0;
        System.out.println("expect=" + Arrays.toString(expect));

        System.out.println("result=" + Arrays.toString(result));
        assertArrayEquals(expect, result, Math.sqrt(eps));
    }

}
