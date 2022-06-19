package algorithms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Data Class for the ACO Parameters
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ACOParameters {
    public double initialPheromoneValue;
    public double alpha;              // pheromone importance
    public double beta;               // distance priority
    public double evaporation;
    public double q;                // pheromone left on trail per ant
    public double antFactor;        // no ants per node
    public double randomFactor;    // introducing randomness
    public int maximumIterations;

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
