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
        int maxDepth = 4;
        int cacheCapacity = 10_000_000;
        
        Parameters o = Parameters.make()
                .set("class","tictactoe.MinMaxAgent")
                .set("maxDepth",maxDepth)
                .set("cacheCapacity",cacheCapacity)
                .parameters();
        
        Parameters x = Parameters.make()
                .set("class","tictactoe.MinMaxAgent")
                .set("maxDepth",maxDepth)
                .set("cacheCapacity",cacheCapacity)
                .parameters();
        
        Parameters ox = Parameters.make().set("O",o).set("X",x).parameters();
        
        System.out.println(ox);
    
        GamePlay play = new GamePlay(ox); 
//        {
//            public Agent createAgent(Mark side) {
//                if (side == Mark.O) return new MinMaxAgent(Mark.O, maxDepth,cacheSize);
//                else return new MinMaxAgent(Mark.X , maxDepth,cacheSize);
//            }
//        };
        while (!play.over()) {
            Move move = play.getMove();
            System.out.println("Player " + play.getState().turn() + ": " + move);
            move.play(play.getGame());
            System.out.println(play.getGame());
        }
        System.out.println(play.getState().toString());
        
    }
    
}
