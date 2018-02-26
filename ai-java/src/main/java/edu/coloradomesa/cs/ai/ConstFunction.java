/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

/**
 *
 * @author wmacevoy
 */
public class ConstFunction<X, Y> implements Function<X, Y> {

    final Y c;

    ConstFunction(Y _c) {
        c = _c;
    }

    @Override
    public Y of(X x) {
        return c;
    }
}
