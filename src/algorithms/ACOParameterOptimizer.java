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
        ACOParameters bestParameters = null;
        Route bestRoute = null;

        for (double initialPheromoneValue = 0.1; initialPheromoneValue <= 1; initialPheromoneValue += 0.1) {
            for (double randomFactor = 0.005; randomFactor <= 0.1; randomFactor += 0.005) {
                for (double evaporation = 0.05; evaporation <= 1; evaporation += 0.05) {
                    for (int q = 100; q <= 1000; q += 100) {
                        for (double alpha = 0.1; alpha <= 2; alpha += 0.1) {
                            for (double beta = 0.1; beta <= 2; beta += 0.1) {
                                for (double antFactor = 0.1; antFactor <= 1; antFactor += 0.1) {
                                    for (int maximumIterations = 1; maximumIterations <= 12; maximumIterations++) {
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
                    }
                }
            }
        }

        this.logger.info("Final optimized ACO parameters | " + bestParameters);
        this.logger.info("runtime | " + (System.currentTimeMillis() - runtimeStart) + " ms");
    }
}
