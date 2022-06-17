package util;

public enum Configuration {
    INSTANCE;

    // Random generator
    public final MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    // File paths
    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String logDir = userDirectory + fileSeparator + "logs";
    public final String tspPath = userDirectory + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "a280.tsp";
    public final String jsonDir = userDirectory + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources";

    // Algorithm parameters
    // Brute force:
    public final long bruteForceIterationCount = 100000000;

    // ACO:
    public final double initialPheromoneValue = 1;
    public final double alpha = 3;              // pheromone importance
    public final double beta = 4;               // distance priority
    public final double evaporation = 1.0;
    public final double q = 500;                // pheromone left on trail per ant
    public final double antFactor = 0.9;        // no ants per node
    public final double randomFactor = 0.01;    // introducing randomness
    public final int maximumIterations = 50;
}