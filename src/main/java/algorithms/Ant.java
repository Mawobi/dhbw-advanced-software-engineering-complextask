package algorithms;

public class Ant {
    public Route trail;

    public Ant(double[][] distanceMatrix) {
        // Initialisation of the ants trail
        this.trail = new Route(distanceMatrix);
    }
}