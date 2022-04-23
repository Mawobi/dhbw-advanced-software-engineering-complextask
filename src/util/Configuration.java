package util;

public enum Configuration {
    INSTANCE;

    // Random generator
    public final MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    // File paths
    public final String logDir = "logs";
    public final String tspPath = "src/resources/a280.tsp";

    // Algorithm parameters
    public final long bruteForceIterationCount = 100000000;
}