/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author wmacevoy
 */
public class EmptyCollection<T> implements Collection<T> {
    public class EmptyIterator implements Iterator<T> {
        @Override
        public boolean hasNext() { return false; }

        @Override
        public T next() {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() { return 0; }
    @Override
    public boolean isEmpty() { return true; }
    @Override
    public boolean contains(Object o) { return false; }

    @Override
    public EmptyIterator iterator() { 
        return new EmptyIterator(); 
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length > 0) { a[0] = null; }
        return a;
    }

    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.isEmpty();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (!c.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
    }
  
}
