/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.lang.reflect.Constructor;

/**
 *
 * @author wmacevoy
 */
public interface Agent {

    Move getMove(Game game);
    
    static Builder make() { return new Builder(); }
    static Builder make(Parameters parameters) { return new Builder(parameters); }
    
    static class Builder extends Parameters.Builder {
        Builder() {}
        Builder(Parameters parameters) { super(parameters); }
        Agent agent() {
            return new Factory<Agent>().create(parameters());
        }
    }
}
