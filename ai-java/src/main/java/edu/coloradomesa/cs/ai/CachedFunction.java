/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.Collection;

/**
 *
 * @author wmacevoy
 * 
 * Uses a random cache (values are deleted at random once the cache limit
 * is reached).  Domain elements must be comparable and copyable.
 */
public class CachedFunction<X extends Comparable<X> & Copyable<X>,Y> 
        implements Function<X,Y> {
    private Function<X,Y> uncached;
    private RandomCache<X,Y> cache;
    
    public Function<X,Y> getUncached() { return uncached; }
    
    public CachedFunction(Function<X,Y> _uncached, int _capacity) {
        uncached=_uncached;
        cache = new RandomCache<X,Y>(_capacity);
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
