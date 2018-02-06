/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author wmacevoy
 */
public class Main {
    public static void main(String[] args) {
        GamePlay play = new GamePlay() {
            public Agent createAgent(Mark side) {
                if (side == Mark.O) return new RandomAgent(Mark.O);
                else return new MinMaxAgent(Mark.X, Integer.MAX_VALUE);
            }
        };
        while (!play.isOver()) {
            Move move = play.getMove();
            System.out.println("Player " + play.getState().turn() + ": " + move);
            move.play(play.getGame());
            System.out.println(play.getGame());
        }
        System.out.println(play.getState().toString());
        
    }
    
}
