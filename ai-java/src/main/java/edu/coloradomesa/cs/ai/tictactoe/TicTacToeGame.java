/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai.tictactoe;

import edu.coloradomesa.cs.ai.Game;
import edu.coloradomesa.cs.ai.Move;
import edu.coloradomesa.cs.ai.Parameters;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author wmacevoy
 */
public class TicTacToeGame implements Game {
    private final int rows;
    private final int cols;
    private final int winLength;

    private final Mark[][] board;
    private State state = State.TURN_O;
    private int unplayed;


    
    public TicTacToeGame(Parameters parameters) {
        this(parameters.getInt("rows",3),parameters.getInt("cols",3),parameters.getInt("winLength",3));
    }
    
    public TicTacToeGame(int _rows, int _cols, int _winLength) {
        rows=_rows;
        cols=_cols;
        winLength=_winLength;
        board = new Mark[rows][cols];
        reset();
    }
    public State getState() {
        return state;
    }

    public boolean over() {
        return state.over();
    }

    public int turn() {
        return state.turn() == Mark.O ? 0 : 1;
    }

    public void rowOk(int row) {
        if (row < 0 || row >= rows) {
            throw new IndexOutOfBoundsException("row (" + (row) + ") must be between 0 and " + (rows - 1));
        }
    }

    public void colOk(int col) {
        if (col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("col (" + (col) + ") must be between 0 and " + (cols - 1));
        }

    }

    public void moveOk(int row, int col, Mark mark) {
        rowOk(row);
        colOk(col);
        if (board[row][col] == Mark.UNMARKED && state.turn().equals(mark)) {
            return;
        }
        throw new IllegalStateException("move (" + (new TicTacToeMove(mark, row, col)) + ") invalid in current state");
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

    @Override
    public void reset() {
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                board[row][col] = Mark.UNMARKED;
            }
        }
        state = State.TURN_O;
        unplayed = rows * cols;
    }

    @Override
    public Collection<Move> getMoves() {
        ArrayList<Move> ans = new ArrayList<Move>();

        if (state.over()) {
            return ans;
        }
        Mark mark = state.turn();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                if (board[row][col] == Mark.UNMARKED) {
                    ans.add(new TicTacToeMove(mark, row, col));
                }
            }
        }
        return ans;
    }

    int repeats(int row, int col, int rowDir, int colDir) {
        Mark mark = board[row][col];
        int count = 0;
        while ((row >= 0 && row < rows && col >= 0)
                && (col < cols && board[row][col] == mark)) {
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
        return length(row, col, 1, 0) >= winLength;
    }

    boolean winCol(int row, int col) {
        return length(row, col, 0, 1) >= winLength;
    }

    boolean winMainDiag(int row, int col) {
        return length(row, col, 1, 1) >= winLength;
    }

    boolean winOffDiag(int row, int col) {
        return length(row, col, 1, -1) >= winLength;
    }

    public boolean win(int row, int col) {
        return winRow(row, col) || winCol(row, col)
                || winMainDiag(row, col) || winOffDiag(row, col);
    }

    public boolean draw() {
        return unplayed == 0;
    }

    public Mark[][] getBoard() {
        Mark[][] ans = new Mark[rows][cols];
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                ans[row][col] = board[row][col];
            }
        }
        return ans;
    }

    public TicTacToeGame(TicTacToeGame copy) {
        rows=copy.rows;
        cols=copy.cols;
        winLength=copy.winLength;
        board = new Mark[rows][cols];
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                board[row][col] = copy.board[row][col];
            }
        }
        state = copy.state;
        unplayed = copy.unplayed;
    }

    public void play(String moves) {
        for (String move : moves.split(" ")) {
            TicTacToeMove.parse(move).play(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();

        ans.append(" ");
        for (int col = 0; col < cols; ++col) {
            ans.append(((char) (col + '1')));
        }
        ans.append("\n");
        for (int row = 0; row < rows; ++row) {
            ans.append(((char) (row + 'a')));
            for (int col = 0; col < cols; ++col) {
                ans.append(board[row][col].toString());
            }
            ans.append("\n");
        }
        return ans.toString();
    }

    public void copyTo(TicTacToeGame target) {
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                target.board[row][col] = board[row][col];
            }
        }
        target.state = state;
        target.unplayed = unplayed;
    }

    public TicTacToeGame copy() {
        return new TicTacToeGame(this);
    }

    @Override
    public int compareTo(Game _to) {
        TicTacToeGame to = (TicTacToeGame) _to;
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
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
        return compareTo((TicTacToeGame) to) == 0;
    }

    TicTacToeGame flipRows() {
        TicTacToeGame ans = copy();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                ans.board[rows - row - 1][col] = board[row][col];
            }
        }
        return ans;
    }

    TicTacToeGame flipCols() {
        TicTacToeGame ans = copy();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                ans.board[row][cols - col - 1] = board[row][col];
            }
        }
        return ans;
    }

    TicTacToeGame flipRowsAndCols() {
        TicTacToeGame ans = copy();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                ans.board[rows - row - 1][cols - col - 1] = board[row][col];
            }
        }
        return ans;
    }

    TicTacToeGame transpose() {
        if (rows != cols) {
            return null;
        }
        TicTacToeGame ans = copy();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                ans.board[col][row] = board[row][col];
            }
        }
        return ans;
    }

    TicTacToeGame offDiagonalTranspose() {
        if (rows != cols) {
            return null;
        }
        TicTacToeGame ans = copy();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                ans.board[col][row] = board[rows - row - 1][cols - col - 1];
            }
        }
        return ans;
    }

    @Override
    public Collection<Game> equivalence() {
        ArrayList<Game> ans = new ArrayList<Game>();
        ans.add(copy());
        ans.add(flipRows());
        ans.add(flipCols());
        ans.add(flipRowsAndCols());
        if (rows == cols) {
            ans.add(transpose());
            ans.add(offDiagonalTranspose());
        }
        return ans;
    }

    @Override
    public int getPlayers() {
        return 2;
    }

    @Override
    public void setPlayers(int count) {
        if (count != 2) {
            throw new IllegalStateException("2 player game");
        }
    }

    @Override
    public int won() {
        switch(state) {
            case WIN_O: return 0;
            case WIN_X: return 1;
            case DRAW: return -1;
            default: throw new IllegalStateException("unfinished game");
        }
    }


}
