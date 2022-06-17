package algorithms;

import com.fasterxml.jackson.databind.ObjectMapper;
import util.Configuration;
import util.FileSystemLogger;

import java.io.File;
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
        double bestCost = Integer.MAX_VALUE;

        double initialPheromoneValue = 1.0;
        int maximumIterations = 15;
        int q = 500;
        double randomFactor = 0.01;

        int numberOfOptimizations = 3;
        ObjectMapper mapper = new ObjectMapper();

        for (double evaporation = 1; evaporation >= 0.6; evaporation -= 0.1) {
            for (double alpha = 8; alpha >= 3; alpha--) {
                for (double beta = 8; beta >= 3; beta--) {
                    for (double antFactor = 1; antFactor >= 0.6; antFactor -= 0.1) {
                        ACOParameters parameters = new ACOParameters(initialPheromoneValue, alpha, beta, evaporation, q, antFactor, randomFactor, maximumIterations);
                        double summedCosts = 0;

                        for (int i = 0; i < numberOfOptimizations; i++) {
                            AntColonyOptimization aco = new AntColonyOptimization(parameters, true);
                            Route route = aco.start();
                            summedCosts += route.getTotalCost();
                        }

                        double averageCost = summedCosts / numberOfOptimizations;

                        if (averageCost < bestCost) {
                            bestParameters = parameters;
                            bestCost = averageCost;
                            this.logger.info("New best parameters | costs: " + averageCost + " | " + parameters);

                            mapper.writeValue(new File(Configuration.INSTANCE.jsonDir + Configuration.INSTANCE.fileSeparator + "parameters.json"), bestParameters);
                        }
                    }
                }
            }
        }

        this.logger.info("Final optimized ACO parameters | " + bestParameters);
        this.logger.info("runtime | " + (System.currentTimeMillis() - runtimeStart) + " ms");
    }
}
