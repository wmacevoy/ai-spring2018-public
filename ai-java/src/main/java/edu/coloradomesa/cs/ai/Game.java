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
public interface Game extends Comparable<Game>, Equivalence<Game>, Copyable<Game> {
    int getPlayers();
    void setPlayers(int count);
    int turn();
    int won(); //  player or -1 for draw
    boolean over();
    void reset();
    Collection<Move> getMoves();
}
