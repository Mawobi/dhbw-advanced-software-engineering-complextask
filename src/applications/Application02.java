package applications;


import algorithms.AntColonyOptimization;
import util.ACOParameters;
import util.Configuration;

import java.io.IOException;

public class Application02 {
    public static void main(String[] args) throws IOException {
        ACOParameters parameters = new ACOParameters(Configuration.INSTANCE.initialPheromoneValue, Configuration.INSTANCE.alpha, Configuration.INSTANCE.beta, Configuration.INSTANCE.evaporation, Configuration.INSTANCE.q, Configuration.INSTANCE.antFactor, Configuration.INSTANCE.randomFactor, Configuration.INSTANCE.maximumIterations);
        AntColonyOptimization aco = new AntColonyOptimization(parameters);
        aco.start();
    }
}
