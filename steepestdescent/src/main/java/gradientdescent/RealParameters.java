/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

/**
 *
 * @author wmacevoy
 */
public interface RealParameters {

    int getRealParameterSize();

    String getRealParameterName(int index);

    int getRealParameterIndex(String name);

    double getRealParameterValue(int index);

    void setRealParameterValue(int index, double value);

    default double[] getRealParameters(double[] x) {
        int dim = getRealParameterSize();
        if (x == null) {
            x = new double[dim];
        }
        for (int i = 0; i < dim; ++i) {
            x[i] = getRealParameterValue(i);
        }
        return x;
    }

    default void setRealParameters(double[] x) {
        int dim = getRealParameterSize();
        for (int i = 0; i < dim; ++i) {
            setRealParameterValue(i, x[i]);
        }
    }
}
