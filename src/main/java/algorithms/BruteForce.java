package algorithms;

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
        this.logger = new FileSystemLogger(BruteForce.class.getName());
        this.nodesPool = new ArrayList<>();
    }

    /**
     * Starts the Brute Force algorithm.
     * @throws FileNotFoundException if the tsp-data json file could not be read to initialize the distance matrix
     */
    public void start() throws FileNotFoundException {
        TSPFileReader tspFileReader = new TSPFileReader();
        double[][] distanceMatrix = tspFileReader.readTSPData();

        Route bestRoute = getNewRandomRoute(distanceMatrix);

        this.logger.info("=== Bruteforce TSP ===");
        this.logger.info("Starting " + Configuration.INSTANCE.bruteForceIterationCount + " iterations");
        this.logger.info("Best costs:");

        // Brute Force iterations - update new best route if necessary
        for (int i = 0; i < Configuration.INSTANCE.bruteForceIterationCount; i++) {
            refillNodesPool(distanceMatrix.length);

            Route route = getNewRandomRoute(distanceMatrix);

            if (route.getTotalCost() < bestRoute.getTotalCost()) {
                bestRoute = route;
                logger.info("Iteration " + i + " | " + bestRoute.getTotalCost());
            }
        }

        this.logger.info(bestRoute.toString());
        this.logger.info("=== Bruteforce TSP End ===");
    }

    /**
     * Refills the nodes pool with all nodes.
     * @param distanceMatrix distance matrix of the tsp nodes
     * @return a new Route with all nodes
     */
    private Route getNewRandomRoute(double[][] distanceMatrix) {
        int[] nodeOrder = new int[distanceMatrix.length];

        for (int i = 0; ; i++) {
            int node = getRandomNodeFromPool();
            if (node == -1) break;
            nodeOrder[i] = node;
        }

        return new Route(nodeOrder, distanceMatrix);
    }

    /**
     * Refills the nodes pool with all nodes.
     * @param dimension dimension of the distance matrix
     */
    private void refillNodesPool(int dimension) {
        this.nodesPool.clear();
        for (int i = 0; i < dimension; i++) {
            this.nodesPool.add(i);
        }
    }

    /**
     * Returns a random node from the nodes pool.
     * @return a random node from the nodes pool
     */
    private int getRandomNodeFromPool() {
        if (this.nodesPool.size() == 0) return -1;
        return this.nodesPool.remove(Configuration.INSTANCE.randomGenerator.nextInt(0, nodesPool.size() - 1));
    }
}
