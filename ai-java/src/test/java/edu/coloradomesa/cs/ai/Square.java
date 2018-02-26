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
public class Square implements Function<AbsDouble, Double> {

    int evals = 0;

    @Override
    public Double of(AbsDouble x) {
        ++evals;
        return Double.valueOf(Math.pow(x.doubleValue(), 2));
    }
}
