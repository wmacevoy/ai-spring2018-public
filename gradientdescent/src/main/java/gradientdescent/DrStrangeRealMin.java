/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Warren MacEvoy
 *
 * Silly minimization example based on Dr. Strange movie. Assuming you must have
 * a positive number of jokes and between 0 and 2 fights, maximize the appeal of
 * the movie.
 */
public class DrStrangeRealMin implements RealMin {

    double[] values = new double[2];
    private static final String[] names = new String[]{"jokes", "fights"};
    private static final HashMap< String, Integer> indexes = new HashMap< String, Integer>();

    static {
        for (int i = 0; i < names.length; ++i) {
            indexes.put(names[i], i);
        }
    }
    public static final int IJOKES = indexes.get("jokes");
    public static final int IFIGHTS = indexes.get("fights");

    @Override
    public int getRealParameterSize() {
        return names.length;
    }

    @Override
    public String getRealParameterName(int i) {
        return names[i];
    }

    @Override
    public int getRealParameterIndex(String name) {
        return indexes.get(name);
    }

    @Override
    public double getRealParameterValue(int index) {
        return values[index];
    }

    @Override
    public void setRealParameterValue(int index, double value) {
        values[index] = value;
    }

    public DrStrangeRealMin() {
    }

    public DrStrangeRealMin(DrStrangeRealMin copy) {
        System.arraycopy(copy.values, 0, copy.values, 0, values.length);
    }

    @Override
    public RealMin copy() {
        return new DrStrangeRealMin(this);
    }

    @Override
    public double getValue() {
        double jokes = values[IJOKES];
        double fights = values[IFIGHTS];
        double d2 = 0;
        boolean constrained = true;
        if (constrained) {

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
        }
        return Math.pow(jokes + fights - 6, 2) + Math.pow(fights - 4, 2) + d2;
    }
}
