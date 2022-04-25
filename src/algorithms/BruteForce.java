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

        ArrayList<Integer> bestOrder = new ArrayList<>();
        int bestTravelingCost = Integer.MAX_VALUE;

        this.logger.info("=== Bruteforce TSP ===");
        this.logger.info("Starting " + Configuration.INSTANCE.bruteForceIterationCount + " iterations");
        this.logger.info("Best costs:");

        for (int i = 0; i < Configuration.INSTANCE.bruteForceIterationCount; i++) {
            refillNodesPool(distanceMatrix.length);

            ArrayList<Integer> order = generateRandomNodeOrder();
            int travelingCost = calculateTravelingCost(order, distanceMatrix);

            if (travelingCost < bestTravelingCost) {
                bestTravelingCost = travelingCost;
                bestOrder = order;
                logger.info("Iteration " + i + " | " + bestTravelingCost);
            }
        }

        StringBuilder orderString = new StringBuilder();
        for (int node : bestOrder) orderString.append(node + 1).append(" » ");
        orderString.append(bestOrder.get(0) + 1);
        this.logger.info("Best cost " + bestTravelingCost + " with order: " + orderString);
        this.logger.info("=== Bruteforce TSP End ===");
    }

    private ArrayList<Integer> generateRandomNodeOrder() {
        ArrayList<Integer> order = new ArrayList<>();

        while (true) {
            int node = getRandomNodeFromPool();
            if (node == -1) break;
            order.add(node);
        }

        return order;
    }

    private int calculateTravelingCost(ArrayList<Integer> order, double[][] distanceMatrix) {
        if (order.size() <= 1) return 0;
        int cost = 0;

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
