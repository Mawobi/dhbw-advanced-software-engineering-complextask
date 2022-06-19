package applications;


import algorithms.ACOParameters;
import algorithms.AntColonyOptimization;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.Configuration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class Application02 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ACOParameters parameters = new ACOParameters(Configuration.INSTANCE.initialPheromoneValue, Configuration.INSTANCE.alpha, Configuration.INSTANCE.beta, Configuration.INSTANCE.evaporation, Configuration.INSTANCE.q, Configuration.INSTANCE.antFactor, Configuration.INSTANCE.randomFactor, Configuration.INSTANCE.maximumIterations);

        // read cli arguments
        HashMap<String, String> arguments = new HashMap<>();

        if (args.length % 2 != 0) {
            throw new RuntimeException("CLI Argumente sind unvollständig");
        }
        for (int i = 0; i < args.length; i += 2) {
            if (!args[i].startsWith("-")) continue;
            arguments.put(args[i].substring(1), args[i + 1]);
        }

        if (arguments.containsKey("best")) {
            ObjectMapper mapper = new ObjectMapper();
            parameters = mapper.readValue(Paths.get(arguments.get("best")).toFile(), ACOParameters.class);
        }

        AntColonyOptimization aco = new AntColonyOptimization(parameters, false);
        aco.start();
    }
}
