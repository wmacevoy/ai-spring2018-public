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
public class CachedSquare extends CachedFunction<AbsDouble, Double> {

    Square square;

    CachedSquare(int _capacity) {
        super(new Square(), _capacity);
        square = (Square) getUncached();
    }
    int evals = 0;

    @Override
    public Double of(AbsDouble x) {
        ++evals;
        return super.of(x);
    }

}
