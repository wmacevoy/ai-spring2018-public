/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

/**
 *
 * @author Warren MacEvoy Represent a real-valued minimization problem
 */
public interface RealMin extends RealParameters {

    RealMin copy();

    double getValue();

    default double[] getGradient(double[] dy, double eps) {
        int dim = getRealParameterSize();
        if (dy == null) {
            dy = new double[dim];
        }

        for (int i = 0; i < dim; ++i) {
            double xi = getRealParameterValue(i);
            setRealParameterValue(i, xi + eps);
            double yp = getValue();
            setRealParameterValue(i, xi - eps);
            double ym = getValue();
            dy[i] = (yp - ym) / (2 * eps);
            setRealParameterValue(i, xi);
        }
        return dy;

    }
}
