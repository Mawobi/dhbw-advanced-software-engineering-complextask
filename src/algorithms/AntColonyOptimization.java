package algorithms;

import util.Configuration;
import util.FileSystemLogger;
import util.TSPFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AntColonyOptimization {
    private final FileSystemLogger logger;
    private final double[][] distanceMatrix;
    private final double[][] trails;
    private final List<Ant> ants = new ArrayList<>();
    private final double[] probabilities;
    private final int numberOfAnts;
    private int currentIndex = 0;

    private Route bestTour;

    public AntColonyOptimization() throws IOException {
        this.logger = new FileSystemLogger(AntColonyOptimization.class.getName());

        TSPFileReader tspFileReader = new TSPFileReader();
        this.distanceMatrix = tspFileReader.readTSPData();

        this.trails = new double[this.distanceMatrix.length][this.distanceMatrix.length];
        this.probabilities = new double[this.distanceMatrix.length];
        this.numberOfAnts = (int) (this.distanceMatrix.length * Configuration.INSTANCE.antFactor);

        // initialize pheromones
        for (double[] row : this.distanceMatrix)
            Arrays.fill(row, Configuration.INSTANCE.initialPheromoneValue);

        // initialize ants
        for (int i = 0; i < this.numberOfAnts; i++) {
            Ant ant = new Ant(this.distanceMatrix);
            ant.trail.visitCity(Configuration.INSTANCE.randomGenerator.nextInt(0, this.distanceMatrix.length - 1));
            this.ants.add(ant);
        }
    }

    public void start() {
        long runtimeStart = System.currentTimeMillis();

        for (int i = 0; i < Configuration.INSTANCE.maximumIterations; i++) {
            moveAnts();
            updateTrails();
            updateBest();
        }

        if (this.bestTour != null) {
            this.logger.info("Best tour | " + this.bestTour);
        } else {
            this.logger.warning("No best tour found");
        }

        this.logger.info("runtime | " + (System.currentTimeMillis() - runtimeStart) + " ms");
    }

    private void moveAnts() {
        for (Ant ant : this.ants) {
            ant.trail.clear();

            for (int i = 0; i < this.distanceMatrix.length; i++) {
                ant.trail.visitCity(selectNextCity(ant));
            }
        }
    }

    private int selectNextCity(Ant ant) {
        if (Configuration.INSTANCE.randomGenerator.nextDouble() < Configuration.INSTANCE.randomFactor) {
            int t = Configuration.INSTANCE.randomGenerator.nextInt(0, this.distanceMatrix.length - 1);

            if (!ant.trail.visited(t)) {
                return t;
            }
        }

        calculateProbabilities(ant);

        double randomNumber = Configuration.INSTANCE.randomGenerator.nextDouble();
        double total = 0;

        for (int i = 0; i < this.distanceMatrix.length; i++) {
            total += this.probabilities[i];
            if (total >= randomNumber) {
                return i;
            }
        }

        throw new RuntimeException("runtime exception | other cities");
    }

    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[this.currentIndex];
        double pheromone = 0.0;

        for (int l = 0; l < this.distanceMatrix.length; l++) {
            if (!ant.trail.visited(l)) {
                pheromone += Math.pow(this.trails[i][l], Configuration.INSTANCE.alpha) * Math.pow(1.0 / this.distanceMatrix[i][l], Configuration.INSTANCE.beta);
            }
        }

        for (int j = 0; j < this.distanceMatrix.length; j++) {
            if (ant.trail.visited(j)) {
                this.probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], Configuration.INSTANCE.alpha) * Math.pow(1.0 / this.distanceMatrix[i][j], Configuration.INSTANCE.beta);
                this.probabilities[j] = numerator / pheromone;
            }
        }
    }

    private void updateTrails() {
        // evaporate trails
        for (int i = 0; i < this.distanceMatrix.length; i++) {
            for (int j = 0; j < this.distanceMatrix[0].length; j++) {
                this.trails[i][j] *= Configuration.INSTANCE.evaporation;
            }
        }

        for (Ant ant : this.ants) {
            double contribution = Configuration.INSTANCE.q / ant.trail.getTotalCost();
            for (int i = 0; i < this.distanceMatrix.length - 1; i++) {
                this.trails[ant.trail.get(i)][ant.trail.get(i + 1)] += contribution;
            }
            this.trails[ant.trail.get(this.distanceMatrix.length - 1)][ant.trail.get(0)] += contribution;
        }
    }

    private void updateBest() {
        double bestTourCost = this.bestTour != null ? this.bestTour.getTotalCost() : Integer.MAX_VALUE;

        for (Ant ant : this.ants) {
            if (ant.trail.getTotalCost() < bestTourCost) {
                this.bestTour = new Route(ant.trail);
            }
        }
    }
}