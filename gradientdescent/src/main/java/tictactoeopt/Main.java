/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeopt;

import gradientdescent.DrStrangeRealMin;
import gradientdescent.GradientDescentMinimizer;
import gradientdescent.Minimizer;
import gradientdescent.RealMin;
import gradientdescent.ReflectedRealMin;
import gradientdescent.SampleMinimizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoe.Game;
import tictactoe.GamePlay;
import tictactoe.Parameters;
import tictactoe.State;

/**
 *
 * @author wmacevoy
 */

public class Main {
    private Parameters parameters;
    public Parameters parameters() {
	if (parameters == null) {
	    int maxDepth = 3;
	    int cacheCapacity = 10_000_000;
	    
	    Parameters o = Parameters.make()
		.set("class", "tictactoe.MinMaxAgent")
		.set("maxDepth", maxDepth)
		.set("cacheCapacity", cacheCapacity)
		.parameters();
	    
	    Parameters x = Parameters.make()
		.set("class", "tictactoe.MinMaxAgent")
		.set("maxDepth", maxDepth + 1)
		.set("cacheCapacity", Game.ROWS*Game.COLS*cacheCapacity)
		.parameters();
	    
	    Parameters game =
		Parameters.make().set("O", o).set("X", x).parameters();

	    Parameters opt = Parameters.make()
		.set("game",game)
		.set("quiet",false)
		.set("threads",8)
		.set("trials",96)
		.parameters();

	    Parameters min = Parameters.make()
//		.set("class","gradientdescent.GradientDescentMinimizer")
		.set("class","gradientdescent.SampleMinimizer")
                    
		.set("eps",0.05)
		.parameters();
	    
	    parameters = Parameters.make()
		.set("opt",opt)
		.set("min",min)
		.parameters();		
	}
	return parameters;
    }

    private RealMin problem = null;
    public RealMin problem() {
	if (problem == null) {
	    problem = new OptMinMaxAgent(parameters().getParameters("opt"));
	}
	return problem;
    }

    private Minimizer minimizer = null;
    public Minimizer minimizer() {
	if (minimizer == null) {
	    Parameters min = parameters().getParameters("min");
	    if (min.getString("class").equals("gradientdescent.GradientDescentMinimizer")) {
		GradientDescentMinimizer gdm = new GradientDescentMinimizer();
		gdm.setEps(min.getDouble("eps",gdm.getEps()));
		minimizer=gdm;
	    } else if (min.getString("class").equals("gradientdescent.SampleMinimizer")) {
		SampleMinimizer sm = new SampleMinimizer();
		RealMin prob = problem();
		
		int iMy = prob.getRealParameterIndex("heuristicMyTurn");
		int iOther = prob.getRealParameterIndex("heuristicOtherTurn");
		double [] xmins  = new double[2];
		double [] xmaxs  = new double[2];
		double [] dxs = new double[2];

		xmins[iMy] = -1;
		xmaxs[iMy] =  1;
		dxs[iMy] = min.getDouble("eps");

		xmins[iOther] = -1;
		xmaxs[iOther] =  1;
		dxs[iOther] = min.getDouble("eps");

		sm.setBox(xmins,dxs,xmaxs);
		minimizer = sm;
	    } else {
		throw new IllegalArgumentException("unknown minimizer " + min.getString("class"));
	    }
	}
	return minimizer;
    }

    public static void main(String[] args) {
        new Main().go();
    }

    void go() {
	minimizer().setProblem(problem());
	minimizer().min();

        for (int i = 0; i < problem().getRealParameterSize(); ++i) {
            String name = problem().getRealParameterName(i);
            double value = problem().getRealParameterValue(i);
            System.out.println(name + "=" + value);
        }
    }
}
