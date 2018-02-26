/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author wmacevoy
 */
public class AbsDouble extends Number implements Copyable<AbsDouble>, Comparable<AbsDouble>, Equivalence<AbsDouble> {

    private double value;

    public AbsDouble(double _value) {
        value = _value;
    }

    public AbsDouble(AbsDouble copy) {
        value = copy.value;
    }

    public static AbsDouble valueOf(double value) {
        return new AbsDouble(value);
    }

    @Override
    public AbsDouble copy() {
        return new AbsDouble(this);
    }

    @Override
    public int compareTo(AbsDouble to) {
        if (value < to.value) {
            return -1;
        }
        if (value > to.value) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public Collection<AbsDouble> equivalence() {
        ArrayList<AbsDouble> ans = new ArrayList<AbsDouble>(2);
        ans.add(this);
        ans.add(new AbsDouble(-value));
        return ans;
    }

}
