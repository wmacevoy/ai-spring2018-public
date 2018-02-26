/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author wmacevoy
 */
public class MinMaxValue implements Function<Game, Double> {

    Parameters parameters;
    int player;
    int maxDepth;
    RandomEquivalenceCache<Game, Double> cache;

    public MinMaxValue(Parameters _parameters) {
        parameters = _parameters;
        player = parameters.getInt("player");
        leafValue = (Function<Game, Double>) Parameters.create(Parameters.make(parameters.getParameters("leafValue", Parameters.NONE))
                .def("class", LeafValue.class.getName())
                .set("player", player).parameters(), Function.class);
        maxDepth = parameters.getInt("maxDepth", Integer.MAX_VALUE);
        heuristicValue = (Function<Game, Double>) Parameters.create(Parameters.make(parameters.getParameters("heuristicValue", Parameters.NONE))
                .def("class", HeuristicValue.class.getName())
                .def("myTurn", 0.0)
                .def("otherTurn", 0.0)
                .set("player", player).parameters(), Function.class);
        cache = new RandomEquivalenceCache<Game, Double>(parameters.getInt("capacity", 0));
    }

    Function<Game, Double> leafValue;
    Function<Game, Double> heuristicValue;

    Double MIN_AGENT_VALUE = Double.valueOf(-1.0);
    Double MAX_AGENT_VALUE = Double.valueOf(1.0);

    Double getMinValue(Game game, int depth) {
        Collection<Move> moves = game.getMoves();

        ArrayList<Pair<Move, Double>> valuedMoves = new ArrayList<Pair<Move, Double>>(moves.size());
        for (Move move : moves) {
            move.play(game);
            Double value = heuristicValue.of(game);
            valuedMoves.add(new Pair<Move, Double>(move, value));
            move.unplay(game);
        }
        valuedMoves.sort((a, b) -> -a.second.compareTo(b.second));
        Double value = Double.valueOf(Double.MAX_VALUE);
        for (Pair<Move, Double> moveValue : valuedMoves) {
            Move move = moveValue.first;
            try {
                move.play(game);
                Double childValue = getValue(game, depth + 1);
                if (childValue.compareTo(value) < 0) {
                    value = childValue;
                    if (value.compareTo(MIN_AGENT_VALUE) <= 0) {
                        value = MIN_AGENT_VALUE;
                        break;
                    }
                }

            } finally {
                move.unplay(game);
            }
        }

        return value;

    }

    Double getMaxValue(Game game, int depth) {
        Collection<Move> moves = game.getMoves();

        ArrayList<Pair<Move, Double>> valuedMoves = new ArrayList<Pair<Move, Double>>(moves.size());
        for (Move move : moves) {
            move.play(game);
            Double value = heuristicValue.of(game);
            valuedMoves.add(new Pair<Move, Double>(move, value));
            move.unplay(game);
        }
        valuedMoves.sort((a, b) -> a.second.compareTo(b.second));
        Double value = Double.valueOf(-Double.MAX_VALUE);
        for (Pair<Move, Double> moveValue : valuedMoves) {
            Move move = moveValue.first;
            try {
                move.play(game);
                Double childValue = getValue(game, depth + 1);
                if (childValue.compareTo(value) > 0) {
                    value = childValue;
                    if (value.compareTo(MAX_AGENT_VALUE) >= 0) {
                        value = MAX_AGENT_VALUE;
                        break;
                    }
                }

            } finally {
                move.unplay(game);
            }
        }

        return value;

    }

    Double getUncachedValue(Game game, int depth) {
        if (game.over()) {
            return leafValue.of(game);
        } else if (depth >= maxDepth) {
            return heuristicValue.of(game);
        } else {
            if (game.turn() == player) {
                return getMaxValue(game, depth);
            } else {
                return getMinValue(game, depth);
            }
        }
    }

    Double getValue(Game game, int depth) {
        Double value = cache.get(game);
        if (value != null) {
//            System.out.println("cached value of " + game + " = " + value);
            return value;
        }
        value = getUncachedValue(game, depth);
        cache.add(game.copy(), value);
//            System.out.println("uncached value of " + game + " = " + value);
        return value;
    }

    @Override
    public Double of(Game game) {
        return getValue(game, 0);
    }
}
