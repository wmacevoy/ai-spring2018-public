/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

/**
 *
 * @author wmacevoy
 */
public class GoldenSectionMinimizer implements Minimizer {

    double a;
    double b;
    double eps;

    RealMin problem;

    @Override
    public void setProblem(RealMin _problem) {
        problem = _problem;
        if (problem.getRealParameterSize() != 1) {
            throw new IllegalArgumentException("golden section 1d only");
        }
    }
    public static final double r1 = (Math.sqrt(5.0) - 1) / 2.0;
    public static final double r2 = r1 * r1;

    double f(double x) {
        problem.setRealParameterValue(0, x);
        double y = problem.getValue();
        return y;
    }

    //http://mathfaculty.fullerton.edu/mathews/n2003/goldenratiosearchmod.html
    // optimized to remove unused function evaluations.
    @Override
    public double min() {
        double a = Math.min(this.a,this.b);
        double b = Math.max(this.a,this.b);
        double h = b - a;
        double c = a + r2 * h;
        double d = a + r1 * h;
        double yc = f(c);
        double yd = f(d);
        double ymin = yd;
        for (;;) {
            if (yc < yd) {
                b = d;
                d = c;
                yd = yc;
                h = b - a;
                c = a + r2 * h;
                if (h <= eps) {
                    break;
                }
                yc = f(c);
                ymin = yc;
            } else {
                a = c;
                c = d;
                yc = yd;
                h = b - a;
                d = a + r1 * h;
                if (h <= eps) {
                    break;
                }
                yd = f(d);
                ymin = yd;
            }
        }
        return ymin;
    }
}
