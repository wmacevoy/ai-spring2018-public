/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author wmacevoy
 */
public class Game implements Comparable<Game> {

    public static final int ROWS = 5;
    public static final int COLS = 5;
    public static final int WIN_LENGTH = 4;

    private Mark[][] board = new Mark[ROWS][COLS];
    private State state = State.TURN_O;
    private int unplayed = ROWS * COLS;

    public State getState() {
        return state;
    }

    public static void rowOk(int row) {
        if (row < 0 || row >= ROWS) {
            throw new IndexOutOfBoundsException("row (" + (row) + ") must be between 0 and " + (ROWS - 1));
        }
    }

    public static void colOk(int col) {
        if (col < 0 || col >= COLS) {
            throw new IndexOutOfBoundsException("col (" + (col) + ") must be between 0 and " + (COLS - 1));
        }

    }

    public void moveOk(int row, int col, Mark mark) {
        rowOk(row);
        colOk(col);
        if (board[row][col] == Mark.UNMARKED && state.turn().equals(mark)) {
            return;
        }
        throw new IllegalStateException("move (" + (new Move(mark, row, col)) + ") invalid in current state");
    }

    public void move(int row, int col, Mark mark) {
        moveOk(row, col, mark);
        board[row][col] = mark;
        --unplayed;
        if (win(row, col)) {
            state = mark.win();
        } else if (draw()) {
            state = State.DRAW;
        } else {
            state = mark.other().turn();
        }
    }

    public void unmove(int row, int col) {
        rowOk(row);
        colOk(col);
        switch (board[row][col]) {
            case X:
                ++unplayed;
                board[row][col] = Mark.UNMARKED;
                state = State.TURN_X;
                return;
            case O:
                ++unplayed;
                board[row][col] = Mark.UNMARKED;
                state = State.TURN_O;
                return;
        }
        throw new IllegalStateException();
    }

    public void reset() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Mark.UNMARKED;
            }
        }
        state = State.TURN_O;
        unplayed = ROWS * COLS;
    }

    public ArrayList<Move> getMoves() {
        ArrayList<Move> ans = new ArrayList<Move>();

        if (state.over()) {
            return ans;
        }
        Mark mark = state.turn();
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Mark.UNMARKED) {
                    ans.add(new Move(mark, row, col));
                }
            }
        }
        return ans;
    }

    int repeats(int row, int col, int rowDir, int colDir) {
        Mark mark = board[row][col];
        int count = 0;
        while ((row >= 0 && row < ROWS && col >= 0)
                && (col < COLS && board[row][col] == mark)) {
            row += rowDir;
            col += colDir;
            ++count;
        }
        return count;
    }

    int length(int row, int col, int rowDir, int colDir) {
        return repeats(row, col, rowDir, colDir) + repeats(row, col, -rowDir, -colDir) - 1;
    }

    boolean winRow(int row, int col) {
        return length(row, col, 1, 0) >= WIN_LENGTH;
    }

    boolean winCol(int row, int col) {
        return length(row, col, 0, 1) >= WIN_LENGTH;
    }

    boolean winMainDiag(int row, int col) {
        return length(row, col, 1, 1) >= WIN_LENGTH;
    }

    boolean winOffDiag(int row, int col) {
        return length(row, col, 1, -1) >= WIN_LENGTH;
    }

    public boolean win(int row, int col) {
        return winRow(row, col) || winCol(row, col)
                || winMainDiag(row, col) || winOffDiag(row, col);
    }

    public boolean draw() {
        return unplayed == 0;
    }

    public Mark[][] getBoard() {
        Mark[][] ans = new Mark[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                ans[row][col] = board[row][col];
            }
        }
        return ans;
    }

    public Game() {
        reset();
    }

    public void play(String moves) {
        for (String move : moves.split(" ")) {
            Move.parse(move).play(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                ans.append(board[row][col].toString());
            }
            ans.append("\n");
        }
        return ans.toString();
    }

    public void copyTo(Game target) {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                target.board[row][col] = board[row][col];
            }
        }
        target.state = state;
        target.unplayed = unplayed;
    }

    public Game clone() {
        Game ans = new Game();
        copyTo(ans);
        return ans;
    }

    @Override
    public int compareTo(Game to) {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                int a = board[row][col].toInt();
                int b = to.board[row][col].toInt();
                if (a != b) {
                    return a < b ? -1 : 1;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object to) {
        return compareTo((Game) to) == 0;
    }

    Game flipRows() {
        Game ans = clone();
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                ans.board[ROWS - row - 1][col] = board[row][col];
            }
        }
        return ans;
    }

    Game flipCols() {
        Game ans = clone();
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                ans.board[row][COLS - col - 1] = board[row][col];
            }
        }
        return ans;
    }

    Game flipRowsAndCols() {
        Game ans = clone();
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                ans.board[ROWS - row - 1][COLS - col - 1] = board[row][col];
            }
        }
        return ans;
    }

    Game transpose() {
        if (ROWS != COLS) {
            return null;
        }
        Game ans = clone();
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                ans.board[col][row] = board[row][col];
            }
        }
        return ans;
    }

    Game offDiagonalTranspose() {
        if (ROWS != COLS) {
            return null;
        }
        Game ans = clone();
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                ans.board[col][row] = board[ROWS - row - 1][COLS - col - 1];
            }
        }
        return ans;
    }

    public Collection<Game> getEquivClass() {
        ArrayList<Game> ans = new ArrayList<Game>();
        ans.add(clone());
        ans.add(flipRows());
        ans.add(flipCols());
        ans.add(flipRowsAndCols());
        if (ROWS == COLS) {
            ans.add(transpose());
            ans.add(offDiagonalTranspose());
        }
        return ans;
    }

}
