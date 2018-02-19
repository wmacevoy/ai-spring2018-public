/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author wmacevoy
 */
public class ReflectedRealMinTest {

    static class F extends ReflectedRealMin {

        public double x, y;

        public double of() {
            return x + y;
        }
    }

    static class G extends ReflectedRealMin {

        public double a, b;

        public G() {
        }

        public G(G copy) {
            a = copy.a;
            b = copy.b;
        }

        public double getValue() {
            return a + b;
        }

        public double of() {
            return a - b;
        }
    }

    @Test
    public void testGetValueF() {
        F f = new F();

        f.setRealParameterValue(f.getRealParameterIndex("x"), 10);
        f.setRealParameterValue(f.getRealParameterIndex("y"), 20);
        double z = f.getValue();

        assertEquals(30, z, 0);
    }

    @Test
    public void testGetValueG() {
        G g = new G();

        g.setRealParameterValue(g.getRealParameterIndex("a"), 10);
        g.setRealParameterValue(g.getRealParameterIndex("b"), 20);
        double z = g.getValue();

        assertEquals(30, z, 0);
    }

    @Test
    public void testCopyF() {
        F f = new F();

        f.setRealParameterValue(f.getRealParameterIndex("x"), 10);
        f.setRealParameterValue(f.getRealParameterIndex("y"), 20);
        F cp = (F) f.copy();

        assertEquals(f.getRealParameterValue(0), cp.getRealParameterValue(0), 0);
        assertEquals(f.getRealParameterValue(1), cp.getRealParameterValue(1), 0);

    }

    @Test
    public void testCopyG() {
        G g = new G();

        g.setRealParameterValue(g.getRealParameterIndex("a"), 10);
        g.setRealParameterValue(g.getRealParameterIndex("b"), 20);
        G cp = (G) g.copy();

        assertEquals(g.getRealParameterValue(0), cp.getRealParameterValue(0), 0);
        assertEquals(g.getRealParameterValue(1), cp.getRealParameterValue(1), 0);
    }
}
