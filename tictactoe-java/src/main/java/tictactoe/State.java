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
public enum State {
    TURN_O,
    TURN_X,
    WIN_O,
    WIN_X,
    DRAW;

    public boolean over() {
        return this == WIN_O || this == WIN_X || this == DRAW;
    }

    public State other() {
        switch (this) {
            case TURN_X:
                return TURN_O;
            case TURN_O:
                return TURN_X;
            case WIN_X:
                return WIN_O;
            case WIN_O:
                return WIN_X;
            case DRAW:
                return DRAW;
        }
        throw new IllegalStateException();
    }

    public Mark turn() {
        switch (this) {
            case TURN_O:
                return Mark.O;
            case TURN_X:
                return Mark.X;
            case WIN_O:
                return Mark.O;
            case WIN_X:
                return Mark.X;
        }
        throw new IllegalStateException();
    }

    @Override
    public String toString() {
        switch (this) {
            case TURN_O:
                return "o's turn";
            case TURN_X:
                return "x's turn";
            case WIN_O:
                return "o wins";
            case WIN_X:
                return "x wins";
            case DRAW:
                return "draw";
        }
        throw new IllegalStateException();
    }
}
