/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.Random;

/**
 *
 * @author wmacevoy
 */
public class RandomValue implements Function<Game, Double> {
    private int player;
    private Random rng = new Random();
    public RandomValue(Parameters parameters) {
        this(parameters.getInt("player"));
    }
    RandomValue(int _player) {
        player=_player;
    }
    @Override
    public Double of(Game game) {
        if (game.over()) {
            int winner = game.won();
            if (winner == player) {
                return 1.0;
            }
            if (winner < 0) {
                return 0.0;
            }
            return -1.0;
        } else {
            return rng.nextDouble()-0.5;
        }
    }
}

