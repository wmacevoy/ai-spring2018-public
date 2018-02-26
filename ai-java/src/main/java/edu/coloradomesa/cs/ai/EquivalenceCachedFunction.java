/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

/**
 *
 * @author wmacevoy
 */
public class EquivalenceCachedFunction<X extends Comparable<X> & Copyable<X> & Equivalence<X>,Y> 
        implements Function<X,Y> {
    private Function<X,Y> uncached;
    public Function<X,Y> getUncached() { return uncached; }
    private RandomEquivalenceCache<X,Y> cache;
    
    public EquivalenceCachedFunction(Function<X,Y> _uncached, int _capacity) {
        uncached=_uncached;
        cache = new RandomEquivalenceCache<X,Y>(_capacity);
    }

    public void clear() {
        cache.clear();
    }
    
    public void setCapacity(int _capacity) {
        cache.setCapacity(_capacity);
    }
    
    @Override
    public Y of(X x) {
        Y y = cache.get(x);
        if (y == null) {
            y = uncached.of(x);
            cache.add(x.copy(), y);
        }
        return y;
    }
    
}
