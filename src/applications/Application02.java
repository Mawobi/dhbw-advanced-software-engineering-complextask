package applications;


import algorithms.ACOParameters;
import algorithms.AntColonyOptimization;
import util.Configuration;

import java.io.IOException;

public class Application02 {
    public static void main(String[] args) throws IOException,InterruptedException {
        ACOParameters parameters = new ACOParameters(Configuration.INSTANCE.initialPheromoneValue, Configuration.INSTANCE.alpha, Configuration.INSTANCE.beta, Configuration.INSTANCE.evaporation, Configuration.INSTANCE.q, Configuration.INSTANCE.antFactor, Configuration.INSTANCE.randomFactor, Configuration.INSTANCE.maximumIterations);
        AntColonyOptimization aco = new AntColonyOptimization(parameters, false);
        aco.start();
    }
}
