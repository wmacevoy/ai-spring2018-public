/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

/**
 *
 * @author Warren MacEvoy Used by ReflectedRealParameters to create manipulators
 * for fields in an object to express them as RealParameters.
 */
public interface RealParameterFly {

    String name();

    double get(Object fly);

    void set(Object fly, double value);
}
