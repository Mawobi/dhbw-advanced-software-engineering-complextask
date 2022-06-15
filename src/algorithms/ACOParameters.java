package algorithms;

public class ACOParameters {
    public final double initialPheromoneValue;
    public final double alpha;              // pheromone importance
    public final double beta;               // distance priority
    public final double evaporation;
    public final double q;                // pheromone left on trail per ant
    public final double antFactor;        // no ants per node
    public final double randomFactor;    // introducing randomness
    public final int maximumIterations;

    public ACOParameters(double initialPheromoneValue, double alpha, double beta, double evaporation, double q, double antFactor, double randomFactor, int maximumIterations) {
        this.initialPheromoneValue = initialPheromoneValue;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporation = evaporation;
        this.q = q;
        this.antFactor = antFactor;
        this.randomFactor = randomFactor;
        this.maximumIterations = maximumIterations;
    }

    @Override
    public String toString() {
        return "ACOParameters{" +
                "initialPheromoneValue=" + initialPheromoneValue +
                ", alpha=" + alpha +
                ", beta=" + beta +
                ", evaporation=" + evaporation +
                ", q=" + q +
                ", antFactor=" + antFactor +
                ", randomFactor=" + randomFactor +
                ", maximumIterations=" + maximumIterations +
                '}';
    }
}
