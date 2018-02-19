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
public class SampleMinimizer implements Minimizer {

    private RealMin problem;
    double[] xmins;
    double[] xmaxs;
    double[] dxs;

    @Override
    public void setProblem(RealMin _problem) {
        problem = _problem;
    }

    public void setBox(double[] _xmins, double[] _dxs, double[] _xmaxs) {
        xmins = _xmins;
        dxs = _dxs;
        xmaxs = _xmaxs;
    }

    public void setBox(double _xmins, double _dxs, double _xmaxs) {
        int dim = problem.getRealParameterSize();
        xmins = new double[dim];
        dxs = new double[dim];
        xmaxs = new double[dim];
        for (int i = 0; i < dim; ++i) {
            xmins[i] = _xmins;
            dxs[i] = _dxs;
            xmaxs[i] = _xmaxs;
        }
    }

    int dim;
    double[] x;
    double y;

    @Override
    public double min() {
        dim = problem.getRealParameterSize();
        x = new double[dim];
        y = Double.MAX_VALUE;
        min(0);
        for (int i = 0; i < dim; ++i) {
            problem.setRealParameterValue(i, x[i]);
        }
        return y;
    }

    void min(int k) {
        if (k == dim) {
            double tmp = problem.getValue();
            if (tmp < y) {
                y = tmp;
                for (int i = 0; i < dim; ++i) {
                    x[i] = problem.getRealParameterValue(i);
                }
            }
        } else {
            if (xmins[k] == xmaxs[k]) {
                problem.setRealParameterValue(k, xmins[k]);
                min(k + 1);
            } else {
                double x0 = xmins[k];
                double x1 = xmaxs[k];
                double dx = dxs[k];
                int n = (int) Math.max(1, Math.ceil(Math.abs((x1 - x0) / dx)));
                for (int i = 0; i < n; ++i) {
                    double xk = x0 * ((n - i) / ((double) n)) + x1 * (i / ((double) n));
                    problem.setRealParameterValue(k, xk);
                    min(k + 1);
                }
            }
        }
    }
}
