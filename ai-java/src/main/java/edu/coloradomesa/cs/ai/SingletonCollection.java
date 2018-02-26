/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author wmacevoy
 */
public class SingletonCollection<T> implements Collection<T> {

    public SingletonCollection(T _singleton) {
        singleton = _singleton;
    }
    private final T singleton;

    public class SingletonIterator implements Iterator<T> {

        private boolean next = true;

        @Override
        public boolean hasNext() {
            return next;
        }

        @Override
        public T next() {
            if (next) {
                next = false;
                return singleton;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (o == singleton) {
            return true;
        }
        if (o == null || singleton == null) {
            return false;
        }
        return singleton.equals(o);
    }

    @Override
    public SingletonIterator iterator() {
        return new SingletonIterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[]{singleton};
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < 1) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), 1);
        } else if (a.length > 1) {
            a[1] = null;
        }
        a[0] = (T) singleton;
        return a;
    }

    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

}
