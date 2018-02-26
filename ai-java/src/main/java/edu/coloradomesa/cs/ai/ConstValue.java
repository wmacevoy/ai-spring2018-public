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
public class ConstValue extends ConstFunction<Game, Double> {
    public ConstValue(Parameters parameters) {
        this(parameters.getDouble("value"));
    }
    public ConstValue(double value) {
        super(value);
    }
}

