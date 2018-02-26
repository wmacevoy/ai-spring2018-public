/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author wmacevoy
 */
public class MaxValueAgent implements Agent {

    int player;
    Function<Game, Double> value;
    Random rng = new Random();

    MaxValueAgent(Parameters parameters) {
        player = parameters.getInt("player");
        value = (Function<Game, Double>) Parameters.create(Parameters.make(parameters.getParameters("value"))
                .set("player", player).parameters(), Function.class);
    }

    @Override
    public Move move(Game game) {
        if (game.over()) {
            throw new IllegalStateException("game is over");
        }
        double maxValue = -Double.MAX_VALUE;
        ArrayList<Move> maxMoves = new ArrayList<Move>();
        Collection<Move> allMoves = game.getMoves();
        if (allMoves.isEmpty()) {
            throw new IllegalStateException("game is not over but no moves?");
        }
        for (Move move : allMoves) {
            move.play(game);
            double childValue = value.of(game);
            if (!Double.isFinite(childValue) || childValue < -1 || childValue > 1) {
                throw new IllegalStateException("game value (" + childValue + ") is out of range.");
            }
            if (childValue == maxValue) {
                maxMoves.add(move);
            } else if (childValue > maxValue) {
                maxMoves.clear();
                maxMoves.add(move);
                maxValue = childValue;
            }
            move.unplay(game);
        }
        int n = rng.nextInt(maxMoves.size());
        return maxMoves.get(n);
    }
}
