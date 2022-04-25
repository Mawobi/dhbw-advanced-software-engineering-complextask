package algorithms;

public class Route {
    public final double totalCost;

    public Route(double[][] distanceMatrix, int[] nodes) {
        this.totalCost = getTotalCost(distanceMatrix, nodes);
    }

    private double getTotalCost(double[][] distanceMatrix, int[] nodes) {
        double totalCost = 0;

        for (int i = 0; i < nodes.length - 1; i++) {
            totalCost += distanceMatrix[nodes[i]][nodes[i + 1]];
        }

        totalCost += distanceMatrix[nodes[nodes.length - 1]][nodes[0]];

        return totalCost;
    }

    @Override
    public String toString() {
        // TODO implement
        return super.toString();
    }
}
