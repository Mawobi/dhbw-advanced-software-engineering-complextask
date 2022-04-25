package algorithms;

import applications.Application02;
import util.Configuration;
import util.FileSystemLogger;
import util.TSPFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ParticleSwarmOptimization {

    private final FileSystemLogger logger;
    private final ArrayList<Particle> particles;

    public ParticleSwarmOptimization() throws IOException {
        this.logger = new FileSystemLogger(Application02.class.getName());

        TSPFileReader tspFileReader = new TSPFileReader();
        double[][] distanceMatrix = tspFileReader.readTSPData();

        this.particles = new ArrayList<>();
        ArrayList<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < distanceMatrix.length; i++) {
            nodes.add(i);
        }

        for (int i = 0; i < Configuration.INSTANCE.particleCount; i++) {
            Collections.shuffle(nodes);
            Route initialRoute = new Route(nodes.stream().mapToInt(Integer::intValue).toArray(), distanceMatrix);
            this.particles.add(new Particle(initialRoute));
        }

        if (this.particles.size() == 0) {
            this.logger.error("Unable to initialize PSO with 0 particleCount");
            throw new RuntimeException("Particle count must be greater than 0");
        }

        // TODO: initialize velocities
    }

    public void start() {
        this.logger.info("=== Particle Swarm Optimization TSP ===");
        this.logger.info("Starting " + Configuration.INSTANCE.psoIterationCount + " iterations with " + this.particles.size() + " particles");

        // execute optimization
        for (int i = 0; i < Configuration.INSTANCE.psoIterationCount; i++) {
            for (Particle particle : this.particles) {
                particle.calculatePBest();
            }

            Route gBest = getGBest();
            this.logger.info("Iteration " + i + " gBest | " + gBest.totalCost);

            for (Particle particle : this.particles) {
                // TODO: update velocity and location
            }
        }

        Route gBest = getGBest();
        this.logger.info("gBest | " + gBest);
        this.logger.info("=== Particle Swarm Optimization TSP END ===");
    }

    private Route getGBest() {
        Route best = null;

        for (Particle particle : this.particles) {
            Route pBest = particle.getPBest();
            if (best == null || best.totalCost > pBest.totalCost) {
                best = pBest;
            }
        }

        return best;
    }
}
