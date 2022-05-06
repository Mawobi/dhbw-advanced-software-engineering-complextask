package applications;


import algorithms.AntColonyOptimization;

import java.io.IOException;

public class Application02 {
    public static void main(String[] args) throws IOException {
        AntColonyOptimization aco = new AntColonyOptimization();
        aco.start();
    }
}
