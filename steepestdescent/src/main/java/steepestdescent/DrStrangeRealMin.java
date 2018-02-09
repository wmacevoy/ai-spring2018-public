/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

import java.util.Arrays;

/**
 *
 * @author wmacevoy
 */
public class DrStrangeRealMin implements RealMin {
    double[] values = new double[2];
    public static final int IJOKE = 0;
    public static final int IFIGHT = 1;

    @Override
    public int getRealParameterSize() {
        return 2;
    }

    @Override
    public String getRealParameterName(int index) {
        switch (index) {
            case 0:
                return "joke";
            case 1:
                return "fight";
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getRealParameterIndex(String name) {
        switch (name) {
            case "joke":
                return IJOKE;
            case "fight":
                return IFIGHT;
        }
        throw new IndexOutOfBoundsException();
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
        return Math.pow(joke+fight - 6, 2) + Math.pow(fight - 4, 2);
    }
}
