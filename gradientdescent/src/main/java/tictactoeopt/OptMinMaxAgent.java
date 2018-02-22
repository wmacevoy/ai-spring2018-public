/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeopt;

import gradientdescent.ReflectedRealMin;
import tictactoe.GamePlay;
import tictactoe.Parameters;
import tictactoe.State;

/**
 *
 * @author wmacevoy
 */

public class OptMinMaxAgent extends ReflectedRealMin {
    Parameters parameters;
    Integer trials = null;
    int trials() {
	return (trials != null) ? trials :
	    trials = (int) parameters.getLong("trials");
    }
    Integer threads = null;
    int threads() {
	return (threads != null) ? threads :
	    threads = (int) parameters.getLong("threads");
    }
    
    public double heuristicMyTurn;
    public double heuristicOtherTurn;
    
    public double value() {
        double penalty = 0;
        double clippedHeuristicMyTurn=heuristicMyTurn;
        double clippedHeuristicOtherTurn=heuristicOtherTurn;
        if (clippedHeuristicMyTurn < -1) {
            penalty += Math.pow(heuristicMyTurn+1,2);
            clippedHeuristicMyTurn = -1;
        }
        if (clippedHeuristicMyTurn > 1) {
            penalty += Math.pow(heuristicMyTurn-1,2);
            clippedHeuristicMyTurn = 1;
        }
        if (clippedHeuristicOtherTurn < -1) {
            penalty += Math.pow(heuristicOtherTurn+1,2);
            clippedHeuristicOtherTurn = -1;
        }
        if (clippedHeuristicOtherTurn > 1) {
            penalty += Math.pow(heuristicOtherTurn-1,2);
            clippedHeuristicOtherTurn = 1;
        }
        TrialWorker[] workers = new TrialWorker[threads()];
        for (int id = 0; id < threads(); ++id) {
	    Parameters p = Parameters.make(parameters)
		.set("id",id)
		.set("heuristicMyTurn",clippedHeuristicMyTurn)
		.set("heuristicOtherTurn",clippedHeuristicOtherTurn)
		.parameters();
            workers[id] = new TrialWorker(p);
        }
        for (int i = 0; i < threads; ++i) {
            workers[i].start();
        }
	
        int xwins = 0;
        int owins = 0;
        int draws = 0;
        for (int i = 0; i < threads; ++i) {
            try {
                workers[i].join();
		Parameters results = workers[i].results;
		workers[i]=null;
		xwins += results.getLong("xwins");
		owins += results.getLong("owins");
		draws += results.getLong("draws");
            } catch (InterruptedException ex) {
            }
        }

	// small values are good (we are minimizing)
        double value = (-1.0 * owins + 0.0 * draws + 1.0 * xwins) / trials;
        return value + penalty;
    }
}

