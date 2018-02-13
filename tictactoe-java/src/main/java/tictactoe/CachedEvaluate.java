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
public class CachedEvaluate implements Evaluate {
    private Evaluate uncached;
    private GameCache<Double> cache;

    public CachedEvaluate(Evaluate _uncached, int _capacity) {
        uncached=_uncached;
        cache = new GameCache<Double>(_capacity);
    }
    
    public void clear() {
        cache.clear();
    }
    
    public void setCapacity(int _capacity) {
        cache.setCapacity(_capacity);
    }
    
    @Override
    public double getValue(Game game) {
        Double value = cache.get(game);
        if (value == null) {
            value = uncached.getValue(game);
            cache.add(game.copy(), value);
        }
        return value;
    }
    
}
