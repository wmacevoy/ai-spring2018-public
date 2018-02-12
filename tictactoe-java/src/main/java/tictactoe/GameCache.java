/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Collection;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author wmacevoy
 */
public class GameCache<Value> {

    private int capacity;
    private Random rng = new Random();
    private RedBlackBST<Game, Value> data = new RedBlackBST<Game, Value>();

    public GameCache(int _capacity) {
        capacity = _capacity;
    }

    int getCapacity() {
        return capacity;
    }

    private void fit(int limit) {
        if (limit == 0) {
            data.clear();
        } else {
            while (data.size() > limit) {
                int n = rng.nextInt();
                Game del = data.key(n);
                data.delete(del);
            }
        }
    }

    void setCapacity(int _capacity) {
        capacity = _capacity;
        fit(capacity);
    }

    void add(Game game, Value value) {
        fit(capacity - 1);
        data.put(game, value);
    }

    Value get(Game game) {
        Collection<Game> games = game.getEquivClass();
        for (Game equivGame : games) {
            Value value = data.get(equivGame);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
