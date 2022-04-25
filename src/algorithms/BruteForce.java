package algorithms;

import applications.Application01;
import util.Configuration;
import util.FileSystemLogger;
import util.TSPFileReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BruteForce {
    private final FileSystemLogger logger;
    private final ArrayList<Integer> nodesPool;

    public BruteForce() throws IOException {
        this.logger = new FileSystemLogger(Application01.class.getName());
        this.nodesPool = new ArrayList<>();
    }

    public void start() throws FileNotFoundException {
        TSPFileReader tspFileReader = new TSPFileReader();
        double[][] distanceMatrix = tspFileReader.readTSPData();

        Route bestRoute = generateRandomRoute(distanceMatrix);
        double bestTravelingCost = Integer.MAX_VALUE;

        this.logger.info("=== Bruteforce TSP ===");
        this.logger.info("Starting " + Configuration.INSTANCE.bruteForceIterationCount + " iterations");
        this.logger.info("Best costs:");

        for (int i = 0; i < Configuration.INSTANCE.bruteForceIterationCount; i++) {
            refillNodesPool(distanceMatrix.length);

            Route route = generateRandomRoute(distanceMatrix);

            if (route.totalCost < bestTravelingCost) {
                bestTravelingCost = route.totalCost;
                bestRoute = route;
                logger.info("Iteration " + i + " | " + bestTravelingCost);
            }
        }

        this.logger.info("Best cost " + bestTravelingCost + " with order: " + bestRoute);
        this.logger.info("=== Bruteforce TSP End ===");
    }

    private Route generateRandomRoute(double[][] distanceMatrix) {
        int[] nodeOrder = new int[distanceMatrix.length];

        for (int i = 0; ; i++) {
            int node = getRandomNodeFromPool();
            if (node == -1) break;
            nodeOrder[i] = node;
        }

        return new Route(distanceMatrix, nodeOrder);
    }

    private double calculateTravelingCost(ArrayList<Integer> order, double[][] distanceMatrix) {
        if (order.size() <= 1) return 0;
        double cost = 0;

        for (int i = 0; i < order.size() - 1; i++) {
            int from = order.get(i);
            int to = order.get(i + 1);
            cost += distanceMatrix[from][to];
        }

        cost += distanceMatrix[order.get(order.size() - 1)][order.get(0)];
        return cost;
    }

    private void refillNodesPool(int dimension) {
        this.nodesPool.clear();
        for (int i = 0; i < dimension; i++) {
            this.nodesPool.add(i);
        }
    }

    private int getRandomNodeFromPool() {
        if (this.nodesPool.size() == 0) return -1;
        return this.nodesPool.remove(Configuration.INSTANCE.randomGenerator.nextInt(0, nodesPool.size() - 1));
    }
}
