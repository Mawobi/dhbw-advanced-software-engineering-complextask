package algorithms;

import util.FileSystemLogger;

import java.io.IOException;

public class ACOParameterOptimizer {
    private final FileSystemLogger logger;

    public ACOParameterOptimizer() throws IOException {
        this.logger = new FileSystemLogger(ACOParameterOptimizer.class.getName());
    }

    public void start() throws IOException, InterruptedException {
        long runtimeStart = System.currentTimeMillis();
        this.logger.info("Start parameter optimizer for ACO");
        ACOParameters bestParameters = null;
        Route bestRoute = null;

        double initialPheromoneValue = 1.0;
        int maximumIterations = 50;
        int q = 500;
        double randomFactor = 0.01;

        for (double evaporation = 0.1; evaporation <= 1; evaporation += 0.1) {
            for (double alpha = 1; alpha <= 6; alpha++) {
                for (double beta = 1; beta <= 6; beta++) {
                    for (double antFactor = 1; antFactor >= 0.4; antFactor -= 0.1) {
                        ACOParameters parameters = new ACOParameters(initialPheromoneValue, alpha, beta, evaporation, q, antFactor, randomFactor, maximumIterations);
                        AntColonyOptimization aco = new AntColonyOptimization(parameters, true);
                        Route route = aco.start();

                        double routeCost = route.getTotalCost();
                        if (bestRoute == null || bestRoute.getTotalCost() > routeCost) {
                            bestParameters = parameters;
                            bestRoute = route;
                            this.logger.info("New best parameters | costs: " + routeCost + " | " + parameters);
                        }
                    }
                }
            }
        }

        this.logger.info("Final optimized ACO parameters | " + bestParameters);
        this.logger.info("runtime | " + (System.currentTimeMillis() - runtimeStart) + " ms");
    }
}
