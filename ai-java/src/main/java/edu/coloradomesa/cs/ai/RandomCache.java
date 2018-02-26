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
public class RandomCache<X extends Comparable<X>, Y> {

    protected int capacity;
    protected Random rng = new Random();
    protected RedBlackBST<X, Y> data = new RedBlackBST<X, Y>();

    public RandomCache(int _capacity) {
        capacity = _capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    private void fit(int limit) {
        if (limit == 0) {
            data.clear();
        } else {
            while (data.size() > limit) {
                int n = rng.nextInt(data.size());
                X del = data.key(n);
                data.delete(del);
            }
        }
    }

    public void setCapacity(int _capacity) {
        capacity = _capacity;
        fit(capacity);
    }

    public void add(X x, Y y) {
        if (capacity > 0) {
            fit(capacity - 1);
            data.put(x, y);
        }
    }

    public Y get(X x) {
        if (capacity > 0) {
                Y y = data.get(x);
                if (y != null) {
                    return y;
                }
        }
        return null;
    }

    public void clear() {
        data.clear();
    }
}
