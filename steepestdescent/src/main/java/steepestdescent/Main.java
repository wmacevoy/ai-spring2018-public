/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

/**
 *
 * @author wmacevoy
 */
public class Main {

    public static void main(String[] args) {
        
        // choose problem to minimize
        RealMin problem = new DrStrangeRealMin();
        int dim = problem.getRealParameterSize();

        // choose minimizer
        SteepestDescentMinimizer minimizer = new SteepestDescentMinimizer();
        minimizer.setProblem(problem);
        
        minimizer.min();

        for (int i = 0; i < problem.getRealParameterSize(); ++i) {
            String name = problem.getRealParameterName(i);
            double value = problem.getRealParameterValue(i);
            System.out.println(name + "=" + value);
        }
    }

}
