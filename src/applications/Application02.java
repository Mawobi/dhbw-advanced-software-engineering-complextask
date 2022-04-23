package applications;

import util.FileSystemLogger;

import java.io.IOException;

public class Application02 {
    private final FileSystemLogger logger;

    public Application02() throws IOException {
        logger = new FileSystemLogger(Application02.class.getName());
        logger.info("Application02 initialized");
    }

    public static void main(String[] args) throws IOException {
        Application02 app = new Application02();
    }
}
