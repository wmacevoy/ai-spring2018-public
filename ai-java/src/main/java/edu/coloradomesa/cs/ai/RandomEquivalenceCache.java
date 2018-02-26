/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author wmacevoy
 */
public class RandomEquivalenceCache<X extends Comparable<X> & Equivalence<X>, Y>
        extends RandomCache<X, Y> {

    public RandomEquivalenceCache(int _capacity) {
        super(_capacity);
    }

    public Y get(X x) {
        if (capacity > 0) {
            for (X e : x.equivalence()) {
                Y y = data.get(e);
                if (y != null) {
                    return y;
                }
            }

        }
        return null;
    }

    public void clear() {
        data.clear();
    }
}
