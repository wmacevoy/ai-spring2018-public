/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai.tictactoe;

import edu.coloradomesa.cs.ai.GamePlay;
import edu.coloradomesa.cs.ai.Move;
import edu.coloradomesa.cs.ai.Parameters;

/**
 *
 * @author wmacevoy
 */
public class Main {

    public static void main(String[] args) {
        int maxDepth = 10;
        int cacheCapacity = 10_000_000;

        Parameters parameters = Parameters.make()
                .set("game", Parameters.make()
                        .set("class", "edu.coloradomesa.cs.ai.tictactoe.TicTacToeGame")
                        .set("rows",3)
                        .set("cols",3)
                        .set("winLength",3)
                        .parameters()
                )
                .set("agents", Parameters.make()
                        .set("0", Parameters.make()
                                .set("class", "edu.coloradomesa.cs.ai.MaxValueAgent")
                                .set("value", Parameters.make()
                                        .set("class", "edu.coloradomesa.cs.ai.MinMaxValue")
                                        .set("maxDepth", maxDepth)
                                        .set("cacheCapacity", cacheCapacity)
                                        .parameters()
                                )
                                .parameters()
                        )
                        .set("1", Parameters.make()
                                .set("class", "edu.coloradomesa.cs.ai.MaxValueAgent")
                                .set("value", Parameters.make()
                                        .set("class", "edu.coloradomesa.cs.ai.MinMaxValue")
                                        .set("maxDepth", maxDepth)
                                        .set("cacheCapacity", cacheCapacity)
                                        .parameters()
                                )
                                .parameters()
                        )
                        .parameters()
                )
                .parameters();

        System.out.println(parameters);

        GamePlay play = new GamePlay(parameters);

        while (!play.over()) {
            Move move = play.move();
            System.out.println("Player " + play.game.turn() + ": " + move);
            move.play(play.game);
            System.out.println(play.game);
        }
        System.out.println(((TicTacToeGame)(play.game)).getState());

    }

}
