package util;

import algorithms.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class PSOCalculator {
    /**
     * Calculates randomFactor * learningRate * (best - location).
     */
    public static Transposition[] getRatio(Route best, Route location, double learningRate) {
        double randomFactor = Configuration.INSTANCE.randomGenerator.nextDouble();
        Transposition[] difference = PSOCalculator.getDifference(best, location);
        return PSOCalculator.multiply(randomFactor * learningRate, difference);
    }

    /**
     * Calculates route1 - route2.
     */
    public static Transposition[] getDifference(Route route1, Route route2) {
        // TODO: implement
        // route1 - route2 = shortest sequence of transpositions such that route2 (with transpositions applied)
        // is equal to route1
        return (Transposition[]) new ArrayList<>().toArray();
    }

    /**
     * Calculates factor * difference.
     */
    public static Transposition[] multiply(double factor, Transposition[] difference) {
        if (factor < 0 || factor > 1) {
            throw new RuntimeException("Cannot multiply route difference with factor " + factor);
        }
        if (difference.length == 0) {
            throw new RuntimeException("Cannot multiply empty route difference");
        }

        return Arrays.copyOfRange(difference, 0, (int) Math.round(difference.length * factor));
    }

    /**
     * Applies the transpositions to the location. Does not modify the given location.
     */
    public static Route applyTranspositions(Route location, Transposition[] difference) {
        // TODO: apply the transpositions to the position
        return new Route(new int[]{1, 2}, new double[][]{});
    }

    public static Transposition[] add(Transposition[] diff1, Transposition[] diff2) {
        return Stream.concat(Arrays.stream(diff1), Arrays.stream(diff2))
                .toArray(Transposition[]::new);
    }
}
