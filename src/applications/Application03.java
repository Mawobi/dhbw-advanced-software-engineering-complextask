package applications;

import util.FileSystemLogger;

import java.io.IOException;

public class Application03 {
    private final FileSystemLogger logger;

    public Application03() throws IOException {
        logger = new FileSystemLogger(Application03.class.getName());
        logger.info("Application03 initialized");
    }

    public static void main(String[] args) {
        Application03 app;
        try {
            app = new Application03();
        } catch (IOException e) {
            System.out.println("Error while initializing Application03");
            e.printStackTrace();
        }
    }
}
