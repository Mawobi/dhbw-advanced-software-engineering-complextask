package util;

public enum Configuration {
    INSTANCE;

    // Random generator
    public final MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    // File paths
    public final String logDir = "logs";
    public final String tspPath = "src/resources/a280.tsp";

    // Algorithm parameters
    // Brute force:
    public final long bruteForceIterationCount = 100000000;

    // ACO:
    public final double initialPheromoneValue = 1.0;
    public final double alpha = 2;              // pheromone importance
    public final double beta = 2;               // distance priority
    public final double evaporation = 0.05;
    public final double q = 500;                // pheromone left on trail per ant
    public final double antFactor = 0.8;        // no ants per node
    public final double randomFactor = 0.01;    // introducing randomness
    public final int maximumIterations = 50;
}