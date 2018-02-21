/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeopt;

import gradientdescent.DrStrangeRealMin;
import gradientdescent.GradientDescentMinimizer;
import gradientdescent.RealMin;
import gradientdescent.ReflectedRealMin;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoe.GamePlay;
import tictactoe.Parameters;
import tictactoe.State;

/**
 *
 * @author wmacevoy
 */
public class OptMinMaxAgent extends ReflectedRealMin {

    private int trials = 100;
    private int threads = 4;
    private double eps = 0.05;
    private double clippedHeuristicMyTurn;
    private double clippedHeuristicOtherTurn;

    public double heuristicMyTurn;
    public double heuristicOtherTurn;

    class TrialWorker extends Thread {
        int id;
        int subtrials;
        int xwins;
        int owins;
        int draws;

        TrialWorker(int _id, int _subtrials) {
            id = _id;
            subtrials = _subtrials;
        }

        public void run() {
            xwins = 0;
            owins = 0;
            draws = 0;
            progress("started");
            for (int trial = 0; trial<subtrials; ++trial) {
                play();
                if (trial % 10 == 0) {
                    progress("" + trial + "/" + subtrials + "(" + (100.0*trial/subtrials) + "%)" 
                            + " xwins: " + xwins + " owins: " + owins + " draws: " + draws + "");
                }
            }
            progress("done");
        }

        void progress(String msg) {
            System.out.println("worker # " + id + ": " + msg);
        }
        void play() {
            int maxDepth = 3;
            int cacheCapacity = 10_000_000;

            Parameters o = Parameters.make()
                    .set("class", "tictactoe.MinMaxAgent")
                    .set("heuristicMyTurn", clippedHeuristicMyTurn)
                    .set("heuristicOtherTurn", clippedHeuristicOtherTurn)
                    .set("maxDepth", maxDepth)
                    .set("cacheCapacity", cacheCapacity)
                    .parameters();

            Parameters x = Parameters.make()
                    .set("class", "tictactoe.MinMaxAgent")
                    .set("heuristicMyTurn", clippedHeuristicMyTurn)
                    .set("heuristicOtherTurn", clippedHeuristicOtherTurn)
                    .set("maxDepth", maxDepth + 1)
                    .set("cacheCapacity", cacheCapacity)
                    .parameters();

            Parameters ox = Parameters.make().set("O", o).set("X", x).parameters();

            GamePlay gamePlay = new GamePlay(ox);
            gamePlay.play();

            tictactoe.State state = gamePlay.getState();
            switch (state) {
                case WIN_O:
                    ++owins;
                    break;
                case WIN_X:
                    ++xwins;
                    break;
                case DRAW:
                    ++draws;
                    break;
            }
        }
    }

    public double value() {
        double penalty = 0;
        clippedHeuristicMyTurn=heuristicMyTurn;
        clippedHeuristicOtherTurn=heuristicOtherTurn;
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
        TrialWorker[] workers = new TrialWorker[threads];
        for (int id = 0; id < threads; ++id) {
            workers[id] = new TrialWorker(id, trials / threads);
        }
        for (int i = 0; i < threads; ++i) {
            workers[i].start();
        }
        for (int i = 0; i < threads; ++i) {
            try {
                workers[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(OptMinMaxAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int xwins = 0;
        int owins = 0;
        int draws = 0;
        for (int i = 0; i < threads; ++i) {
            xwins += workers[i].xwins;
            owins += workers[i].owins;
            draws += workers[i].draws;
        }

        double value = (-1.0 * owins + 0.0 * draws + 1.0 * xwins) / trials;
        return value + penalty;
    }
    
    public static void main(String[] args) {
        new OptMinMaxAgent().go();
    }
    
    void go() {
        
        // choose problem to minimize
        RealMin problem = new OptMinMaxAgent();
        int dim = problem.getRealParameterSize();

        // choose minimizer
        GradientDescentMinimizer minimizer = new GradientDescentMinimizer();
        minimizer.setProblem(problem);
        minimizer.setEps(eps);

        minimizer.min();

        for (int i = 0; i < problem.getRealParameterSize(); ++i) {
            String name = problem.getRealParameterName(i);
            double value = problem.getRealParameterValue(i);
            System.out.println(name + "=" + value);
        }

    }

}
