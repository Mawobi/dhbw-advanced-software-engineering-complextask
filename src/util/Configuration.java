package util;

import java.text.DecimalFormat;

public enum Configuration {
    INSTANCE;

    // common
//    public final DecimalFormat integerFormat = new DecimalFormat("0000");
//    public final DecimalFormat decimalFormat = new DecimalFormat("#0.000000000000000");

    // random generator
    public final MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    // objective function
//    public final FunctionType function = FunctionType.ACKLEY;
//    public final int beginRange = -100;
//    public final int endRange = 101;

    // algorithm
//    public final int maximumNumberOfIterations = 1000;
//    public final int numberOfParticles = 20;
//    public final double inertia = 0.729844;
//    public final double cognitiveRatio = 1.496180; // cognitive component
//    public final double socialRatio = 1.496180;    // social component
}