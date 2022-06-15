package algorithms;

import util.Configuration;
import util.FileSystemLogger;
import util.TSPFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AntColonyOptimization {

    private final FileSystemLogger logger;
    private final ExecutorService executorService;
    private CountDownLatch countDownLatch;
    private final double[][] distanceMatrix;
    private final double[][] trails;
    private final List<Ant> ants = new ArrayList<>();

    private Route bestTour;

    public AntColonyOptimization() throws IOException {
        this.logger = new FileSystemLogger(AntColonyOptimization.class.getName());
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

        TSPFileReader tspFileReader = new TSPFileReader();
        this.distanceMatrix = tspFileReader.readTSPData();

        this.trails = new double[this.distanceMatrix.length][this.distanceMatrix.length];

        // initialize pheromones
        for (double[] row : this.trails) {
            Arrays.fill(row, Configuration.INSTANCE.initialPheromoneValue);
        }

        // initialize ants
        int numberOfAnts = (int) (this.distanceMatrix.length * Configuration.INSTANCE.antFactor);
        for (int i = 0; i < numberOfAnts; i++) {
            this.ants.add(new Ant(this.distanceMatrix));
        }
    }

    public void start() {
        long runtimeStart = System.currentTimeMillis();

        for (int i = 0; i < Configuration.INSTANCE.maximumIterations; i++) {
            Route currentBestTour = this.bestTour;

            moveAnts();

            // this.executorService.submit(() -> {
                updateTrails();
            // });

            // this.executorService.submit(() -> {
                updateBest();
            // });

            if (currentBestTour != null && this.bestTour.getTotalCost() < currentBestTour.getTotalCost()) {
                this.logger.info("Found new best | Iteration " + i + " | " + this.bestTour);
            }
        }

        this.logger.info("Best tour | " + this.bestTour);
        this.logger.info("runtime | " + (System.currentTimeMillis() - runtimeStart) + " ms");
    }

    private void moveAnts() {
        this.countDownLatch = new CountDownLatch(ants.size());
        for (Ant ant : ants) {
            executorService.submit(() -> {
                moveAnt(ant);
            });
        }
        waitForCountDownLatch();
    }

    private void moveAnt(Ant ant) {
        ant.trail.clear();

        // it does not care which city is visited first, but to speed up calculation
        // we will use 0 instead of a random generated value
        ant.trail.visitCity(0);

        for (int i = 0; i < this.distanceMatrix.length - 1; i++) {
            ant.trail.visitCity(selectNextCity(ant));
        }
        countDownLatch();
    }

    private synchronized int selectNextCity(Ant ant) {
        if (Configuration.INSTANCE.randomGenerator.nextDouble() < Configuration.INSTANCE.randomFactor) {
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

    public double[] calculateProbabilities(Ant ant) {
        double[] probabilities = new double[this.distanceMatrix.length];

        if (ant.trail.getSize() == 0) {
            throw new RuntimeException("Unable to calculate probabilities for empty ant trail");
        }

        int i = ant.trail.get(ant.trail.getSize() - 1);
        double pheromone = 0.0;

        for (int l = 0; l < this.distanceMatrix.length; l++) {
            if (!ant.trail.visited(l)) {
                // TODO: Mit Herrn Müller besprechen, wie wir damit umgehen sollen
                if (this.distanceMatrix[i][l] == 0) continue;
                pheromone += Math.pow(this.trails[i][l], Configuration.INSTANCE.alpha) * Math.pow(1.0 / this.distanceMatrix[i][l], Configuration.INSTANCE.beta);
            }
        }

        for (int j = 0; j < probabilities.length; j++) {
            if (!ant.trail.visited(j)) {
                if (pheromone == 0 || Double.isInfinite(pheromone)) {
                    throw new RuntimeException("Error while calculation probabilities. Division with zero or infinity.");
                }

                double numerator = Math.pow(trails[i][j], Configuration.INSTANCE.alpha) * Math.pow(1.0 / this.distanceMatrix[i][j], Configuration.INSTANCE.beta);
                probabilities[j] = numerator / pheromone;
            }
        }

        return probabilities;
    }

    private void updateTrails() {
        // evaporate trails
        for (int i = 0; i < this.distanceMatrix.length; i++) {
            for (int j = 0; j < this.distanceMatrix[0].length; j++) {
                synchronized (this.trails) {
                    this.trails[i][j] *= Configuration.INSTANCE.evaporation;
                }
            }
        }

        for (Ant ant : this.ants) {
            double contribution = Configuration.INSTANCE.q / ant.trail.getTotalCost();
            for (int i = 0; i < this.distanceMatrix.length - 1; i++) {
                synchronized (this.trails) {
                    this.trails[ant.trail.get(i)][ant.trail.get(i + 1)] += contribution;
                }
            }
            synchronized (this.trails) {
                this.trails[ant.trail.get(this.distanceMatrix.length - 1)][ant.trail.get(0)] += contribution;
            }
        }
    }

    private synchronized void updateBest() {
        double bestTourCost = this.bestTour != null ? this.bestTour.getTotalCost() : Integer.MAX_VALUE;
        for (Ant ant : this.ants) {
            if (ant.trail.getTotalCost() < bestTourCost) {
                this.bestTour = new Route(ant.trail);
                bestTourCost = this.bestTour.getTotalCost();
            }
        }
    }

    private void countDownLatch() {
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    private void waitForCountDownLatch() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            this.logger.error("CountDownLatch | Fehler beim Wartevorgang!");
            throw new RuntimeException(e);
        }
    }
}