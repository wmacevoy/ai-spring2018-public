/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import java.lang.reflect.Field;

/**
 *
 * @author Warren MacEvoy
 *
 * Flyweight pattern access for double array in object, Used by
 * ReflectedRealParameters to manipulate an object as RealParameters object.
 */
public class ArrayParameterFly implements RealParameterFly {

    private Field field;
    private int index;

    public ArrayParameterFly(Field _field, int _index) {
        field = _field;
        index = _index;
    }

    public String name() {
        return field.getName() + "[" + index + "]";
    }

    public double get(Object fly) {
        try {
            return ((double[]) field.get(fly))[index];
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void set(Object fly, double value) {
        try {
            ((double[]) field.get(fly))[index] = value;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
        }
    }
}
