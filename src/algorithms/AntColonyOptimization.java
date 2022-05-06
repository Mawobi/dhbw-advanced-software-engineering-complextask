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
    public StringBuilder stringBuilder = new StringBuilder();
    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;

    public AntColonyOptimization() throws IOException {
        this.logger = new FileSystemLogger(AntColonyOptimization.class.getName());

        TSPFileReader tspFileReader = new TSPFileReader();
        this.distanceMatrix = tspFileReader.readTSPData();

        this.trails = new double[distanceMatrix.length][distanceMatrix.length];
        this.probabilities = new double[distanceMatrix.length];
        this.numberOfAnts = (int) (distanceMatrix.length * Configuration.INSTANCE.antFactor);

        for (int i = 0; i < this.numberOfAnts; i++) {
            this.ants.add(new Ant(distanceMatrix.length));
        }
    }

    public void start() {
        long runtimeStart = System.currentTimeMillis();

        setupAnts();
        clearTrails();

        for (int i = 0; i < Configuration.INSTANCE.maximumIterations; i++) {
            moveAnts();
            updateTrails();
            updateBest();
        }

        stringBuilder.append("\nbest tour length | ").append((bestTourLength - distanceMatrix.length));
        stringBuilder.append("\nbest tour order  | ").append(Arrays.toString(bestTourOrder));
        stringBuilder.append("\nruntime          | ").append(System.currentTimeMillis() - runtimeStart).append(" ms");

        System.out.println(stringBuilder);
    }

    private void setupAnts() {
        for (int i = 0; i < this.numberOfAnts; i++) {
            for (Ant ant : ants) {
                ant.clear();
                ant.visitCity(-1, Configuration.INSTANCE.randomGenerator.nextInt(0, distanceMatrix.length - 1));
            }
        }
        currentIndex = 0;
    }

    private void moveAnts() {
        for (int i = currentIndex; i < distanceMatrix.length - 1; i++) {
            for (Ant ant : ants) {
                ant.visitCity(currentIndex, selectNextCity(ant));
            }
            currentIndex++;
        }
    }

    private int selectNextCity(Ant ant) {
        int t = Configuration.INSTANCE.randomGenerator.nextInt(0, distanceMatrix.length - currentIndex);
        if (Configuration.INSTANCE.randomGenerator.nextDouble() < Configuration.INSTANCE.randomFactor) {
            int cityIndex = -999;

            for (int i = 0; i < distanceMatrix.length; i++) {
                if (i == t && !ant.visited(i)) {
                    cityIndex = i;
                    break;
                }
            }

            if (cityIndex != -999) {
                return cityIndex;
            }
        }

        calculateProbabilities(ant);

        double randomNumber = Configuration.INSTANCE.randomGenerator.nextDouble();
        double total = 0;

        for (int i = 0; i < distanceMatrix.length; i++) {
            total += probabilities[i];
            if (total >= randomNumber) {
                return i;
            }
        }

        throw new RuntimeException("runtime exception | other cities");
    }

    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;

        for (int l = 0; l < distanceMatrix.length; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], Configuration.INSTANCE.alpha) * Math.pow(1.0 / distanceMatrix[i][l], Configuration.INSTANCE.beta);
            }
        }

        for (int j = 0; j < distanceMatrix.length; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], Configuration.INSTANCE.alpha) * Math.pow(1.0 / distanceMatrix[i][j], Configuration.INSTANCE.beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    private void updateTrails() {
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix.length; j++) {
                trails[i][j] *= Configuration.INSTANCE.evaporation;
            }
        }

        for (Ant ant : ants) {
            double contribution = Configuration.INSTANCE.q / ant.trailLength(distanceMatrix);
            for (int i = 0; i < distanceMatrix.length - 1; i++) {
                trails[ant.trail[i]][ant.trail[i + 1]] += contribution;
            }
            trails[ant.trail[distanceMatrix.length - 1]][ant.trail[0]] += contribution;
        }
    }

    private void updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0).trailLength(distanceMatrix);
        }

        for (Ant ant : ants) {
            if (ant.trailLength(distanceMatrix) < bestTourLength) {
                bestTourLength = ant.trailLength(distanceMatrix);
                bestTourOrder = ant.trail.clone();
            }
        }
    }

    private void clearTrails() {
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix.length; j++) {
                trails[i][j] = Configuration.INSTANCE.initialPheromoneValue;
            }
        }
    }
}