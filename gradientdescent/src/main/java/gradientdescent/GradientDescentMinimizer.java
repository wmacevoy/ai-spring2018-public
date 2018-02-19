/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import java.util.Arrays;

/**
 *
 * @author wmacevoy
 */
public class GradientDescentMinimizer implements Minimizer {

    double eps = 1e-6;

    RealMin problem;

    @Override
    public void setProblem(RealMin _problem) {
        problem = _problem;
    }

    @Override
    public double min() {
        double minimum;
        int dim = problem.getRealParameterSize();
        double[] x0 = new double[dim];
        double[] x1 = new double[dim];
        problem.getRealParameters(x0);
        LineRealMin g = new LineRealMin(problem, eps);
        GoldenSectionMinimizer min1d = new GoldenSectionMinimizer();
        for (;;) {
            g.setup(); // calc grad f at x now to define g(t) = f(x+t*grad f)
            min1d.setProblem(g);
            min1d.eps = eps;
            g.config(min1d); // use g',g'' to estimate [a,b] for min problem

            minimum = min1d.min(); // find min for g (t star)
            problem.getRealParameters(x1);

            double err = 0;
            for (int i = 0; i < x0.length; ++i) {
                err = Math.max(err, Math.abs(x0[i] - x1[i]));
            }
            if (err <= eps) {
                break;
            }
            double[] tmp = x1;
            x1 = x0;
            x0 = tmp;
        }
        return minimum;
    }
}
