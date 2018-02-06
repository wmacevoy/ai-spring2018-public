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
public class RandomAgent implements Agent {
    public RandomAgent(Mark side) {}
    Random rng = new Random();
    public Move getMove(Game game) {
        ArrayList<Move> moves = game.getMoves();
        int n = rng.nextInt(moves.size());
        return moves.get(n);
    }
    
}
