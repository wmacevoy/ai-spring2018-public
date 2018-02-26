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
public class LeafValue implements Function<Game, Double> {

    final int player;

    public LeafValue(Parameters parameters) {
        this(parameters.getInt("player"));
    }

    public LeafValue(int _player) {
        player = _player;
    }

    @Override
    public Double of(Game game) {
        if (!game.over()) {
            throw new IllegalStateException("game is not over");
        }
        int winner = game.won();
        if (winner == player) {
            return 1.0;
        }
        if (winner < 0) {
            return 0.0;
        }
        return -1.0;
    }

}
