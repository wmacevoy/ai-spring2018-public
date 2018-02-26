/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wmacevoy
 */
public class CachedFunctionTest {

    @Test
    public void testRegression1() {
        CachedSquare cSquare = new CachedSquare(100);
        EquivCachedSquare ecSquare = new EquivCachedSquare(100);
        ecSquare.of(AbsDouble.valueOf(10));
        ecSquare.of(AbsDouble.valueOf(-10));
        ecSquare.of(AbsDouble.valueOf(10));
        ecSquare.of(AbsDouble.valueOf(-10));
        assertEquals(1, ecSquare.square.evals);
    }

    @Test
    public void testCachedFunction() {

        Square square = new Square();
        CachedSquare cSquare = new CachedSquare(100);
        EquivCachedSquare ecSquare = new EquivCachedSquare(100);

        for (int j = 0; j < 10; ++j) {
            for (int i = -4; i <= 4; ++i) {
                AbsDouble x = AbsDouble.valueOf(i);
                double s = square.of(x);
                double cs = cSquare.of(x);
                double ecs = ecSquare.of(x);
                assertEquals(s, cs, 0.0);
                assertEquals(s, ecs, 0.0);

            }
        }

        assertTrue(square.evals > cSquare.square.evals);
        assertTrue(cSquare.square.evals > ecSquare.square.evals);
    }

    @Test
    public void testClearedCachedFunction() {
        Square square = new Square();
        CachedSquare cSquare = new CachedSquare(100);
        EquivCachedSquare ecSquare = new EquivCachedSquare(100);

        for (int j = 0; j < 10; ++j) {
            for (int i = -4; i <= 4; ++i) {
                AbsDouble x = AbsDouble.valueOf(i);
                double s = square.of(x);
                cSquare.clear();
                double cs = cSquare.of(x);
                ecSquare.clear();
                double ecs = ecSquare.of(x);
                assertEquals(s, cs, 0.0);
                assertEquals(s, ecs, 0.0);
            }
        }

        assertEquals(cSquare.evals, ecSquare.square.evals);
        assertEquals(square.evals, cSquare.square.evals);

    }

}
