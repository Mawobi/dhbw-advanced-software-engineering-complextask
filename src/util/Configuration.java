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

    // PSO:
    public final int particleCount = 16;
    public final int psoIterationCount = 256;
    public final double inertiaWeight = 1; // w
    public final double cognitiveRatioLearningRate = 1; // c1
    public final double socialRatioLearningRate = 1; // c2
}