/**
 * Matrikelnummern: 3110300 und 2858031
 */

package applications;

import algorithms.ACOParameterOptimizer;

import java.io.IOException;

public class Application03 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ACOParameterOptimizer optimizer = new ACOParameterOptimizer();
        optimizer.start();
    }
}
