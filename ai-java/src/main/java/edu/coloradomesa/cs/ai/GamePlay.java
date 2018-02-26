/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.ArrayList;

/**
 *
 * @author wmacevoy
 */
public class GamePlay {

    public Parameters parameters;
    public Game game;
    public Agent[] agents;

    public GamePlay(Parameters _parameters) {
        parameters = _parameters;
        int players = parameters.getParameters("agents").size();
        Parameters gameParameters
                = Parameters.make(parameters.getParameters("game"))
                        .set("players", players)
                        .parameters();
        game = (Game) Parameters.create(gameParameters, Game.class);

        Parameters agentsParameters = parameters.getParameters("agents");
        agents = new Agent[players];
        for (int player = 0; player < players; ++player) {
            Parameters agentParameters
                    = Parameters.make(agentsParameters.getParameters("" + player))
                            .set("player", player)
                            .parameters();
            agents[player] = (Agent) Parameters.create(agentParameters, Agent.class);
        }
    }

    public Agent agent() {
        return agents[turn()];
    }

    public Move move() {
        return agent().move(game);
    }

    public boolean over() {
        return game.over();
    }

    public int turn() {
        return game.turn();
    }

    public void play(Move move) {
        move.play(game);
    }

    public void play() {
        while (!over()) {
            play(move());
        }
    }
}
