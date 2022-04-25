package algorithms;

public class Particle {
    private Route pBest;
    private Route location;
    private Velocity velocity;

    public Particle(Route initialLocation) {
        this.pBest = initialLocation;
        this.location = initialLocation;
    }

    public Route getLocation() {
        return this.location;
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    public Route getPBest() {
        return this.pBest;
    }

    public void calculatePBest() {
        if(this.location.totalCost < this.pBest.totalCost) {
            this.pBest = this.location;
        }
    }

    public void move(Route newLocation) {
        this.location = newLocation;
    }
}
