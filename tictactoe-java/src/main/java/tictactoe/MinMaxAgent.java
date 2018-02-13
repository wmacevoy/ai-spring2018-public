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
    int cacheCapacity;
    GameCache<Double> cache;

    public MinMaxAgent(Parameters parameters) {
        this(Mark.valueOf(parameters.getString("side")),
                (int) parameters.getLong("maxDepth", Integer.MAX_VALUE),
                (int) parameters.getLong("cacheCapacity", 0));
    }

    public MinMaxAgent(Mark _side, int _maxDepth, int _cacheCapacity) {
        side = _side;
        maxDepth = _maxDepth;
        cacheCapacity = _cacheCapacity;
        cache = new GameCache<Double>(cacheCapacity);
    }

    double getLeafValue(Game game) {
        switch (game.getState()) {
            case DRAW:
                return 0;
            case WIN_O:
                return (side == Mark.O) ? 1 : -1;
            case WIN_X:
                return (side == Mark.X) ? 1 : -1;
        }
        throw new IllegalStateException();
    }

    double getHeuristicValue(Game game) {
        return (side == game.turn()) ? -0.25 : -0.50;
    }

    double getMinValue(Game game, int depth) {
        double value = 1;
        for (Move move : game.getMoves()) {
            try {
                move.play(game);
                double childValue = getValue(game, depth + 1);
                if (childValue < value) {
                    value = childValue;
                    if (value == -1) {
                        break;
                    }
                }

            } finally {
                move.unplay(game);
            }
        }

        return value;

    }

    double getMaxValue(Game game, int depth) {
        double value = -1;
        for (Move move : game.getMoves()) {
            try {
                move.play(game);
                double childValue = getValue(game, depth + 1);

                if (childValue > value) {
                    value = childValue;
                    if (value == 1.0) {
                        break;
                    }
                }

            } finally {
                move.unplay(game);
            }
        }

        return value;

    }

    double getUncachedValue(Game game, int depth) {
        if (game.over()) {
            return getLeafValue(game);
        } else if (depth >= maxDepth) {
            return getHeuristicValue(game);
        } else {
            if (game.turn() == side) {
                return getMaxValue(game, depth);
            } else {
                return getMinValue(game, depth);
            }
        }
    }

    double getValue(Game game, int depth) {
        Double value = cache.get(game);
        if (value != null) {
            return value;
        }
        value = getUncachedValue(game, depth);
        cache.add(game.copy(), value);
        return value;
    }

    Random rng = new Random();

    @Override
    public Move getMove(Game game) {
        cache.clear();
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
