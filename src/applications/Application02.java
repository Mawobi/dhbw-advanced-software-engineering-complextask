package applications;

import util.FileSystemLogger;

import java.io.IOException;

public class Application02 {
    private final FileSystemLogger logger;

    public Application02() throws IOException {
        logger = new FileSystemLogger(Application02.class.getName());
        logger.info("Application02 initialized");
    }

    public static void main(String[] args) {
        Application02 app;
        try {
            app = new Application02();
        } catch (IOException e) {
            System.out.println("Error while initializing Application02");
            e.printStackTrace();
        }
    }
}
