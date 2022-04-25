package algorithms;

public class Particle {
    public Particle(Route initialLocation) {
    }

    public Route getLocation() {
        return new Route(new int[]{1, 2}, new double[][]{});
    }

    public Velocity getVelocity() {
        return new Velocity(new Route(new int[]{1, 2}, new double[][]{}), new Route(new int[]{1, 2}, new double[][]{}));
    }

    public Route getPBest() {
        return new Route(new int[]{1, 2}, new double[][]{});
    }

    public void calculatePBest() {

    }

    public void move(Route newLocation) {

    }
}
