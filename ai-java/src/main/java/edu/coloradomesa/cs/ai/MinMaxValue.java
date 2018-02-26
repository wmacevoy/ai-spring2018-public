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
    RandomEquivalenceCache<Game, Pair<Double, Integer>> cache;

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
        cache = new RandomEquivalenceCache<Game, Pair<Double, Integer>>(parameters.getInt("capacity", 0));
    }

    Function<Game, Double> leafValue;
    Function<Game, Double> heuristicValue;

    Double MIN_AGENT_VALUE = Double.valueOf(-1.0);
    Double MAX_AGENT_VALUE = Double.valueOf(1.0);

    Pair<Double, Integer> getMinValue(Game game, int depth) {
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
        int certainty = 0;
        for (Pair<Move, Double> moveValue : valuedMoves) {
            Move move = moveValue.first;
            try {
                move.play(game);
                Pair<Double, Integer> childValueCertainty = getValue(game, depth + 1);
                int cmp = childValueCertainty.first.compareTo(value);
                if (cmp < 0) {
                    value = childValueCertainty.first;
                    certainty = childValueCertainty.second + 1;
                    if (value.compareTo(MIN_AGENT_VALUE) <= 0) {
                        value = MIN_AGENT_VALUE;
                        break;
                    }

                } else if (cmp == 0) {
                    if (childValueCertainty.second >= certainty) {
                        certainty = childValueCertainty.second + 1;
                    }
                }

            } finally {
                move.unplay(game);
            }
        }

        return new Pair<Double, Integer>(value, certainty);

    }

    Pair<Double, Integer> getMaxValue(Game game, int depth) {
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
        int certainty = 0;
        for (Pair<Move, Double> moveValue : valuedMoves) {
            Move move = moveValue.first;
            try {
                move.play(game);
                Pair<Double, Integer> childValueCertainty = getValue(game, depth + 1);
                int cmp = childValueCertainty.first.compareTo(value);
                if (cmp > 0) {
                    value = childValueCertainty.first;
                    certainty = childValueCertainty.second + 1;
                    if (value.compareTo(MAX_AGENT_VALUE) >= 0) {
                        value = MAX_AGENT_VALUE;
                        break;
                    }
                } else if (cmp == 0) {
                    if (childValueCertainty.second >= certainty) {
                        certainty = childValueCertainty.second + 1;
                    }
                }
            } finally {
                move.unplay(game);
            }
        }

        return new Pair<Double, Integer>(value, certainty);

    }

    Pair<Double, Integer> getUncachedValue(Game game, int depth) {
        if (game.over()) {
            return new Pair<Double, Integer>(leafValue.of(game), Integer.MAX_VALUE / 2);
        } else if (depth >= maxDepth) {
            return new Pair<Double, Integer>(heuristicValue.of(game), 0);
        } else {
            if (game.turn() == player) {
                return getMaxValue(game, depth);
            } else {
                return getMinValue(game, depth);
            }
        }
    }

    Pair<Double, Integer> getValue(Game game, int depth) {
        Pair<Double, Integer> valueCertainty = cache.get(game);
        if (valueCertainty != null
                && (maxDepth == Integer.MAX_VALUE
                || valueCertainty.second >= maxDepth - depth)) {
//            System.out.println("cached value of " + game + " = " + value);
            return valueCertainty;
        }
        valueCertainty = getUncachedValue(game, depth);

        cache.add(game.copy(), valueCertainty);
//            System.out.println("uncached value of " + game + " = " + value);
        return valueCertainty;
    }

    @Override
    public Double of(Game game) {
        return getValue(game, 0).first;
    }
}
