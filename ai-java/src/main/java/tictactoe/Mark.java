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
public enum Mark {
    UNMARKED, X, O;

    public Mark other() {
        switch (this) {
            case X:
                return O;
            case O:
                return X;
        }
        throw new IllegalStateException();
    }

    public State turn() {
        switch (this) {
            case X:
                return State.TURN_X;
            case O:
                return State.TURN_O;
        }
        throw new IllegalStateException();
    }
    
    public State win() {
        switch (this) {
            case X:
                return State.WIN_X;
            case O:
                return State.WIN_O;
        }
        throw new IllegalStateException();
    }
    
    public int toInt() {
        switch (this) {
            case X:
                return -1;
            case O:
                return  1;
        }
        return 0;
    }
            

    public String toString() {
        switch (this) {
            case X:
                return "x";
            case O:
                return "o";
            case UNMARKED:
                return ".";
        }
        throw new IllegalStateException();
    }
}
