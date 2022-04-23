package applications;

import util.FileSystemLogger;
import util.MersenneTwisterFast;
import util.TSPFileReader;

import java.io.IOException;

public class Application01 {
    private final FileSystemLogger logger;
    // random generator
    public final MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    public Application01() throws IOException {
        TSPFileReader tspFileReader = new TSPFileReader();
        double[][] distanceMatix = tspFileReader.readTSPData();

        logger = new FileSystemLogger(Application01.class.getName());
        logger.info("Application01 initialized");

    }

    public static void main(String[] args) throws IOException {
        Application01 app = new Application01();
    }
}
