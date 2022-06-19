package algorithms;

public class Ant {
    public Route trail;

    public Ant(double[][] distanceMatrix) {
        this.trail = new Route(distanceMatrix);
    }
}