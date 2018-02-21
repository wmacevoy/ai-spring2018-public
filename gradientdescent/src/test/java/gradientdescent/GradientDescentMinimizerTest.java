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
public class GradientDescentMinimizerTest {

    @Test
    public void testRosenbrock() {
        Rosenbrock f = new Rosenbrock();
        GradientDescentMinimizer instance = new GradientDescentMinimizer();
        instance.setProblem(f);
        instance.setEps(1e-6);
        instance.min();
        double[] x = f.getRealParameters(null);
        assertArrayEquals(new double[]{1, 1}, x, Math.sqrt(instance.getEps()));
    }

    @Test
    public void testStrange() {
        DrStrangeRealMin f = new DrStrangeRealMin();

        GradientDescentMinimizer instance = new GradientDescentMinimizer();

        instance.setEps(1e-6);
        double[] x = new double[]{0, 0};
        f.setRealParameters(x);
        instance.setProblem(f);
        instance.min();
        x = f.getRealParameters(null);
        System.out.println("x=" + Arrays.toString(x));
        assertArrayEquals(new double[]{4, 2}, x, 1e-4);
    }
}
