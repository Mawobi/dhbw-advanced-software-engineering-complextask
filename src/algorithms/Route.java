package algorithms;

public class Route {
    public final double totalCost;
    public final int[] nodes;

    public Route(int[] nodes, double[][] distanceMatrix) {
        this.nodes = nodes;
        this.totalCost = getTotalCost(distanceMatrix);
        this.nodes = nodes;
    }

    private double getTotalCost(double[][] distanceMatrix) {
        double totalCost = 0;

        for (int i = 0; i < nodes.length - 1; i++) {
            totalCost += distanceMatrix[nodes[i]][nodes[i + 1]];
        }

        totalCost += distanceMatrix[nodes[nodes.length - 1]][nodes[0]];

        return totalCost;
    }

    @Override
    public String toString() {
        StringBuilder routeString = new StringBuilder();
        routeString.append("Best cost ").append(this.totalCost).append(" with order: ");
        for (int node : nodes) routeString.append(node + 1).append(" » ");
        routeString.append(nodes[0] + 1);
        return routeString.toString();
    }
}
