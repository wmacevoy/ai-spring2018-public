/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author wmacevoy
 */
public class MinMaxAgent implements Agent {

    Mark side;
    int maxDepth = Integer.MAX_VALUE;

    public MinMaxAgent(Mark _side, int _maxDepth) {
        side = _side;
        maxDepth = _maxDepth;
    }

    double getValue(Game game, int depth) {
        State state = game.getState();
        if (state.over()) {
            if (state == State.DRAW) {
                return 0;
            }
            return (state.turn() == side) ? 1 : -1;
        }
        if (depth >= maxDepth) {
            return -1;
        }
        boolean maximize = (state.turn() == side);
        double value = maximize ? -1 : 1;
        for (Move move : game.getMoves()) {

            try {
                move.play(game);
                double childValue = getValue(game, depth + 1);
                if (maximize) {
                    if (childValue > value) {
                        value = childValue;
                        if (value == 1.0) {
                            break;
                        }
                    }
                } else {
                    if (childValue < value) {
                        value = childValue;
                        if (value == -1.0) {
                            break;
                        }
                    }
                }
            } finally {
                move.unplay(game);
            }
        }

        return value;
    }

    Random rng = new Random();

    @Override
    public Move getMove(Game game) {
        double maxValue = -1.0;
        ArrayList<Move> maxMoves = new ArrayList<Move>();
        for (Move move : game.getMoves()) {
            move.play(game);
            double childValue = getValue(game, 0);
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
