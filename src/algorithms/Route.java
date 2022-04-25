package algorithms;

public class Route {
    public final double totalCost;

    public Route(int[] nodes, double[][] distanceMatrix) {
        this.totalCost = getTotalCost(distanceMatrix);
    }

    private double getTotalCost(double[][] distanceMatrix) {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
