package algorithms;

import util.Configuration;

import java.text.DecimalFormat;
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

    /**
     * Calculates the total cost of the route.
     * @return the total cost of the route
     */
    public double getTotalCost() {
        double totalCost = 0;

        for (int i = 0; i < this.cities.size() - 1; i++) {
            totalCost += this.distanceMatrix[this.cities.get(i)][this.cities.get(i + 1)];
        }

        totalCost += this.distanceMatrix[this.cities.get(this.cities.size() - 1)][this.cities.get(0)];
        return totalCost;
    }

    /**
     * Adds a city to the route.
     * @param city the city to add
     */
    public void visitCity(int city) {
        if (!this.cities.contains(city)) this.cities.add(city);
    }

    /**
     * Checks if the route contains a city / was already visited.
     * @param city the city to check
     * @return true if the city was already visited, false otherwise
     */
    public boolean visited(int city) {
        return this.cities.contains(city);
    }

    /**
     * Clears the Cities of the Route.
     */
    public void clear() {
        this.cities.clear();
    }

    /**
     * Returns the size of the Route.
     * @return the size of the Route
     */
    public int getSize() {
        return this.cities.size();
    }

    /**
     * Gets the city at a specific index.
     * @param index the index of the city to return
     * @return the city at the given index
     */
    public int get(int index) {
        return this.cities.get(index);
    }

    @Override
    public String toString() {
        StringBuilder routeString = new StringBuilder();
        routeString.append("Cost ").append(new DecimalFormat(Configuration.INSTANCE.decimalFormat).format(this.getTotalCost())).append(" with order: ");
        for (int node : this.cities) routeString.append(node + 1).append(" » ");
        routeString.append(this.cities.get(0) + 1);
        return routeString.toString();
    }
}
