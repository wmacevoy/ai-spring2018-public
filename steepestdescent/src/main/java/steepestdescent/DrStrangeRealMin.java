/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Warren MacEvoy
 * 
 * Silly minimization example based on Dr. Strange movie.
 * Assuming you must have a positive number of jokes and
 * between 0 and 2 fights, maximize the appeal of the movie.
 */
public class DrStrangeRealMin implements RealMin {

    double[] values = new double[2];
    private static final String[] names = new String[]{"joke", "fight"};
    private static final HashMap< String, Integer> indexes = new HashMap< String, Integer>();

    static {
        for (int i = 0; i < names.length; ++i) {
            indexes.put(names[i], i);
        }
    }
    public static final int IJOKE = indexes.get("joke");
    public static final int IFIGHT = indexes.get("fight");

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
        double joke = values[IJOKE];
        double fight = values[IFIGHT];
        double d2 = 0;
        boolean constrained = true;
        if (constrained) {

            if (joke < 0) {
                d2 += joke * joke;
                joke = 0;
            }

            if (fight < 0) {
                d2 += fight * fight;
                fight = 0;
            }
            if (fight > 2) {
                d2 += (fight - 2) * (fight - 2);
                fight = 2;
            }
        }
        return Math.pow(joke + fight - 6, 2) + Math.pow(fight - 4, 2) + d2;
    }
}
