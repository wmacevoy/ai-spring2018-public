/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

/**
 *
 * @author wmacevoy -- An agent represents a mechanism for choosing a move
 based on the current state of the game.  MaxValueAgent chooses a move based
 on the highest value (some valuation of game states).
 */
public interface Agent {
    Move move(Game game);
}
