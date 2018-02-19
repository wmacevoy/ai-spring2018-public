/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author wmacevoy
 */
public class MethodParameterFly implements RealParameterFly {

    private String name;
    private Method get;
    private Method set;

    public MethodParameterFly(String _name, Method _get, Method _set) {
        name = _name;
        get = _get;
        set = _set;
    }

    public String name() {
        return name;
    }

    public double get(Object fly) {
        try {
            return (double) get.invoke(fly);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void set(Object fly, double value) {
        try {
            set.invoke(fly, value);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
