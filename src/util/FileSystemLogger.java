package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileSystemLogger {
    private final Logger logger;

    /**
     * Creates a new logger that logs to the console and file system.
     *
     * @param name Logger and logfile name (without trailing .log). Should be a dot-separated name and should normally
     *             be based on the package name or class name.
     */
    public FileSystemLogger(String name) throws IOException {
        logger = Logger.getLogger(name != null && !name.equals("") ? name : Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);

        // configure logger to write logs to file system
        FileHandler fh;
        Path path = Paths.get("logs", logger.getName() + ".log");

        if (!Files.exists(path.getParent())) {
            Files.createDirectory(path.getParent());
        }

        fh = new FileHandler(path.toString());
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

    /**
     * Log an INFO message.
     *
     * @param msg Message to log.
     */
    public void info(String msg) {
        logger.info(msg);
    }

    /**
     * Log an WARNING message.
     *
     * @param msg Message to log.
     */
    public void warning(String msg) {
        logger.warning(msg);
    }

    /**
     * Log an ERROR message.
     *
     * @param msg Message to log.
     */
    public void error(String msg) {
        logger.severe(msg);
    }
}
