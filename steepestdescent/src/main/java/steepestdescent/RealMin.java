/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

/**
 *
 * @author Warren MacEvoy
 * Represent a real-valued minimization problem
 */
public interface RealMin extends RealParameters {
    RealMin copy();
    double getValue();
}
