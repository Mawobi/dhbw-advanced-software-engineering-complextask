package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Route {
    public final double[][] distanceMatrix;
    private final List<Integer> cities = new ArrayList<>();

    public Route(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public Route(int[] cities, double[][] distanceMatrix) {
        this.cities.addAll(Arrays.stream(cities).boxed().toList());
        this.distanceMatrix = distanceMatrix;
    }

    public Route(Route route) {
        this(route.cities.stream().mapToInt(Integer::intValue).toArray(), route.distanceMatrix);
    }

    public double getTotalCost() {
        double totalCost = 0;

        for (int i = 0; i < this.cities.size() - 1; i++) {
            totalCost += this.distanceMatrix[this.cities.get(i)][this.cities.get(i + 1)];
        }

        totalCost += this.distanceMatrix[this.cities.get(this.cities.size() - 1)][this.cities.get(0)];
        return totalCost;
    }

    public void visitCity(int city) {
        if (!this.cities.contains(city)) this.cities.add(city);
    }

    public boolean visited(int city) {
        return this.cities.contains(city);
    }

    public void clear() {
        this.cities.clear();
    }

    public int getSize() {
        return this.cities.size();
    }

    public int get(int index) {
        return this.cities.get(index);
    }

    @Override
    public String toString() {
        StringBuilder routeString = new StringBuilder();
        routeString.append("Cost ").append(this.getTotalCost()).append(" with order: ");
        for (int node : this.cities) routeString.append(node + 1).append(" » ");
        routeString.append(this.cities.get(0) + 1);
        return routeString.toString();
    }
}
