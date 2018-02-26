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
public class HeuristicValue implements Function<Game, Double> {

    final int player;
    final double myTurn;
    final double otherTurn;

    public HeuristicValue(Parameters parameters) {
        this(parameters.getInt("player"), parameters.getDouble("myTurn"), parameters.getDouble("otherTurn"));
    }

    public HeuristicValue(int _player, double _myTurn, double _otherTurn) {
        player = _player;
        myTurn = _myTurn;
        otherTurn = _otherTurn;
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
            return (game.turn() == player ? myTurn : otherTurn);
        }
    }
}

