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
 * Manipulate a double field of an object as flyweight.
 */
public class FieldParameterFly implements RealParameterFly {

    private Field field;

    public FieldParameterFly(Field _field) {
        field = _field;
    }

    public String name() {
        return field.getName();
    }

    public double get(Object fly) {
        try {
            return field.getDouble(fly);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void set(Object fly, double value) {
        try {
            field.setDouble(fly, value);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
