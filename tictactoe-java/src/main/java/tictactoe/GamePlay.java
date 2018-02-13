/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.ArrayList;

/**
 *
 * @author wmacevoy
 */
public class GamePlay {

    private Parameters parameters;

    public GamePlay(Parameters _parameters) {
        parameters = _parameters;
    }
    private Game game = null;

    protected Game createGame() {
        return new Game();
    }

    public Game getGame() {
        if (game == null) {
            game = createGame();
        }
        return game;
    }

    private Agent agentO = null;
    private Agent agentX = null;

    protected Agent createAgent(Mark side) {
        // get side's parameters and set "side" parameter
        Parameters my = Parameters.make(parameters.getParameters(side.name()))
                .set("side", side.name()).parameters();
        Agent agent = Agent.make(my).agent();
        System.out.println("agent " + side.name() + "=" + agent.getClass());
        return agent;
    }

    public Agent getAgentO() {
        if (agentO == null) {
            agentO = createAgent(Mark.O);
        }
        return agentO;
    }

    public Agent getAgentX() {
        if (agentX == null) {
            agentX = createAgent(Mark.X);
        }
        return agentX;
    }

    public Agent getAgent(Mark side) {
        switch (side) {
            case X:
                return getAgentX();
            case O:
                return getAgentO();
        }
        throw new IllegalArgumentException();
    }

    public State getState() {
        return getGame().getState();
    }

    public Agent getAgent() {
        return getAgent(getState().turn());
    }

    public Move getMove() {
        return getAgent().getMove(getGame());
    }

    public boolean over() {
        return getGame().getState().over();
    }

    public Move turn() {
        Move move = getMove();
        move.play(getGame());
        return move;
    }

    public void play() {
        while (!over()) {
            turn();
        }
    }

}
