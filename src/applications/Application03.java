package applications;

import util.FileSystemLogger;

import java.io.IOException;

public class Application03 {
    private final FileSystemLogger logger;

    public Application03() throws IOException {
        logger = new FileSystemLogger(Application03.class.getName());
        logger.info("Application03 initialized");
    }

    public static void main(String[] args) throws IOException {
        Application03 app = new Application03();
    }
}
