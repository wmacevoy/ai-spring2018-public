/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai.tictactoe;

import edu.coloradomesa.cs.ai.Game;
import edu.coloradomesa.cs.ai.Move;

/**
 *
 * @author wmacevoy
 */
public class TicTacToeMove implements Move {

    public TicTacToeMove(Mark _mark, int _row, int _col) {
        row = _row;
        col = _col;
        mark = _mark;
    }

    private int row;

    public int getRow() {
        return row;
    }
    private int col;

    public int getCol() {
        return col;
    }
    private Mark mark;

    public Mark getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return mark.toString() + ((char) ('a' + row)) + ((char) ('1' + col));
    }

    public static Move parse(String str) {
        Mark mark;
        int row;
        int col;
        mark = Mark.UNMARKED;
        if (str.charAt(0) == 'x' || str.charAt(0) == 'X') {
            mark = Mark.X;
        }
        if (str.charAt(0) == 'o' || str.charAt(0) == 'O') {
            mark = Mark.O;
        }
        int i = 0;
        if (mark != Mark.UNMARKED) {
            i = 1;
        }
        row = str.charAt(i) - 'a';
        col = str.charAt(i + 1) - '1';
        return new TicTacToeMove(mark, row, col);
    }

    @Override
    public void play(Game game) {
        ((TicTacToeGame) game).move(row,col,mark);
    }

    @Override
    public void unplay(Game game) {
        ((TicTacToeGame) game).unmove(row,col);
    }

}
