/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import edu.coloradomesa.cs.ai.tictactoe.TicTacToeGame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wmacevoy
 */
public class MinMaxValueTest {

    @Test
    public void testTTTSimple() {

        Parameters gameParameters = Parameters.make()
                .set("class", TicTacToeGame.class.getCanonicalName())
                .set("rows", 3)
                .set("cols", 3)
                .set("winLength", 3)
                .set("players", 2)
                .parameters();

        Parameters oParameters = Parameters.make()
                .set("class", MaxValueAgent.class.getCanonicalName())
                .set("player", 0)
                .set("value", Parameters.make()
                        .set("class", MinMaxValue.class.getCanonicalName())
                        .set("maxDepth", Integer.MAX_VALUE)
                        .set("capacity", 1_000_000)
                        .parameters())
                .parameters();

        Parameters xParameters = Parameters.make()
                .set("class", MaxValueAgent.class.getCanonicalName())
                .set("player", 1)
                .set("value", Parameters.make()
                        .set("class", MinMaxValue.class.getCanonicalName())
                        .set("maxDepth", Integer.MAX_VALUE)
                        .set("capacity", 1_000_000)
                        .parameters())
                .parameters();

        Parameters gamePlayParameters = Parameters.make()
                .set("game", gameParameters)
                .set("agents", Parameters.make()
                        .set("0", oParameters)
                        .set("1", xParameters)
                        .parameters()
                )
                .parameters();

        for (int i = 0; i < 100; ++i) {
            GamePlay gamePlay = new GamePlay(gamePlayParameters);
            gamePlay.play();
            assertEquals(-1, gamePlay.game.won());
        }
    }
}
