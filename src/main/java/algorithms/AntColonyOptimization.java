/**
 * Matrikelnummern: 3110300 und 2858031
 */

package algorithms;

import util.Configuration;
import util.FileSystemLogger;
import util.TSPFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AntColonyOptimization {
    // Variable declarations
    private final double[][] distanceMatrix;
    private final double[][] trails;
    private final List<Ant> ants = new ArrayList<>();
    private final ExecutorService executorService;
    private final ACOParameters parameters;
    private Route bestTour;
    private FileSystemLogger logger;

    /**
     * Initialisation of the Variables like executor service, distance matrix, trails and pheromone values.
     *
     * @param parameters ACO parameters from configuration or json file
     * @param silentLogs if true, no logs are printed
     * @throws IOException if tsp-data json file could not be read to initialize the distance matrix
     */
    public AntColonyOptimization(ACOParameters parameters, boolean silentLogs) throws IOException {
        this.parameters = parameters;
        if (!silentLogs) this.logger = new FileSystemLogger(AntColonyOptimization.class.getName());
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TSPFileReader tspFileReader = new TSPFileReader();
        this.distanceMatrix = tspFileReader.readTSPData();

        this.trails = new double[this.distanceMatrix.length][this.distanceMatrix.length];

        // initialize pheromones
        for (double[] row : this.trails) {
            Arrays.fill(row, this.parameters.initialPheromoneValue);
        }

        // initialize ants
        int numberOfAnts = (int) (this.distanceMatrix.length * this.parameters.antFactor);
        for (int i = 0; i < numberOfAnts; i++) {
            this.ants.add(new Ant(this.distanceMatrix));
        }
    }

    /**
     * Starts the ACO algorithm.
     *
     * @return the best route and the related best cost
     * @throws InterruptedException if the thread is interrupted
     */
    public Route start() throws InterruptedException {
        long runtimeStart = System.currentTimeMillis();
        log("Starting Ant Colony Optimization with " + ((ThreadPoolExecutor) this.executorService).getMaximumPoolSize() + " processors");
        log("Parameters | " + this.parameters);

        for (int i = 0; i < this.parameters.maximumIterations; i++) {
            Route currentBestTour = this.bestTour;

            moveAnts();

            updateTrails();
            updateBest();

            // Check and print if new best tour is found
            if (currentBestTour != null && this.bestTour.getTotalCost() < currentBestTour.getTotalCost()) {
                log("Found new best | Iteration " + i + " | " + this.bestTour);
            }
        }

        // shutdown executor service, print best tour and total runtime
        this.executorService.shutdown();
        log("Best tour | " + this.bestTour);
        log("runtime | " + (System.currentTimeMillis() - runtimeStart) + " ms");
        return this.bestTour;
    }

    /**
     * Moves all ants parallel in separate threads.
     *
     * @throws InterruptedException if the thread is interrupted
     */
    private void moveAnts() throws InterruptedException {
        List<Callable<String>> callableTasks = new ArrayList<>();

        for (Ant ant : ants) {
            callableTasks.add(() -> {
                ant.trail.clear();

                // It does not care which city is visited first, but to speed up calculation
                // we will use 0 instead of a random generated value
                ant.trail.visitCity(0);

                for (int i = 0; i < this.distanceMatrix.length - 1; i++) {
                    ant.trail.visitCity(selectNextCity(ant));
                }
                return null;
            });
        }

        this.executorService.invokeAll(callableTasks);
    }

    /**
     * Selects the next city to visit for a specific ant.
     *
     * @param ant the ant to select the next city for
     * @return the index of the next city
     */
    private int selectNextCity(Ant ant) {
        if (Configuration.INSTANCE.randomGenerator.nextDouble() < this.parameters.randomFactor) {
            int randomCity = Configuration.INSTANCE.randomGenerator.nextInt(0, this.distanceMatrix.length - 1);

            if (!ant.trail.visited(randomCity)) {
                return randomCity;
            }
        }

        double[] probabilities = calculateProbabilities(ant);

        double randomNumber = Configuration.INSTANCE.randomGenerator.nextDouble();
        double total = 0;

        for (int i = 0; i < this.distanceMatrix.length; i++) {
            total += probabilities[i];
            if (total >= randomNumber) {
                return i;
            }
        }

        throw new RuntimeException("runtime exception | unable to select next city");
    }

    /**
     * Calculates the probabilities for each city to be visited by a specific ant.
     *
     * @param ant the ant to calculate the probabilities for
     * @return the probabilities for each city
     */
    public double[] calculateProbabilities(Ant ant) {
        double[] probabilities = new double[this.distanceMatrix.length];

        if (ant.trail.getSize() == 0) {
            throw new RuntimeException("Unable to calculate probabilities for empty ant trail");
        }

        int i = ant.trail.get(ant.trail.getSize() - 1);
        double pheromone = 0.0;

        for (int l = 0; l < this.distanceMatrix.length; l++) {
            if (!ant.trail.visited(l)) {
                double distance = this.distanceMatrix[i][l];
                if (distance == 0) distance = 0.01;
                pheromone += Math.pow(this.trails[i][l], this.parameters.alpha) * Math.pow(1.0 / distance, this.parameters.beta);
            }
        }

        for (int j = 0; j < probabilities.length; j++) {
            if (!ant.trail.visited(j)) {
                if (pheromone == 0) {
                    throw new RuntimeException("Error while calculation probabilities. Division with zero.");
                }
                if (Double.isInfinite(pheromone)) {
                    throw new RuntimeException("Error while calculation probabilities. Division with infinity.");
                }

                double numerator = Math.pow(trails[i][j], this.parameters.alpha) * Math.pow(1.0 / this.distanceMatrix[i][j], this.parameters.beta);
                probabilities[j] = numerator / pheromone;
            }
        }

        return probabilities;
    }

    /**
     * Updates pheromone concentration for each edge through evaporation and new applied pheromones.
     */
    private void updateTrails() {
        // evaporate trails
        for (int i = 0; i < this.distanceMatrix.length; i++) {
            for (int j = 0; j < this.distanceMatrix[0].length; j++) {
                this.trails[i][j] *= this.parameters.evaporation;
            }
        }

        for (Ant ant : this.ants) {
            double contribution = this.parameters.q / ant.trail.getTotalCost();
            for (int i = 0; i < this.distanceMatrix.length - 1; i++) {
                this.trails[ant.trail.get(i)][ant.trail.get(i + 1)] += contribution;
            }
            this.trails[ant.trail.get(this.distanceMatrix.length - 1)][ant.trail.get(0)] += contribution;
        }
    }

    /**
     * Checks if new best tour costs are found and updates the best tour if necessary.
     */
    private void updateBest() {
        double bestTourCost = this.bestTour != null ? this.bestTour.getTotalCost() : Integer.MAX_VALUE;

        for (Ant ant : this.ants) {
            double antTrailCost = ant.trail.getTotalCost();

            if (antTrailCost < bestTourCost) {
                this.bestTour = new Route(ant.trail);
                bestTourCost = antTrailCost;
            }
        }
    }

    private void log(String msg) {
        if (this.logger == null) return;
        this.logger.info(msg);
    }
}