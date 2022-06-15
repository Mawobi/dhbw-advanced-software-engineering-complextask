package applications;

import algorithms.ACOParameterOptimizer;

import java.io.IOException;

public class Application03 {
    public static void main(String[] args) throws IOException {
        ACOParameterOptimizer optimizer = new ACOParameterOptimizer();
        optimizer.start();
    }
}
