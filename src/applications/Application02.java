package applications;


import algorithms.ParticleSwarmOptimization;

import java.io.IOException;

public class Application02 {
    public static void main(String[] args) throws IOException {
        ParticleSwarmOptimization pso = new ParticleSwarmOptimization();
        pso.start();
    }
}
