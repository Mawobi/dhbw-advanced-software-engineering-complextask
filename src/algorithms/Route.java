package algorithms;

public class Route {
    public final double totalCost;
    public final int[] nodes;

    public Route(int[] nodes, double[][] distanceMatrix) {
        this.totalCost = getTotalCost(distanceMatrix);
        this.nodes = nodes;
    }

    private double getTotalCost(double[][] distanceMatrix) {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
