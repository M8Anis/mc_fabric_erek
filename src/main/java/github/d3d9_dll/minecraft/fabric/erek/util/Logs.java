package github.d3d9_dll.minecraft.fabric.erek.util;

import github.d3d9_dll.minecraft.fabric.erek.Version;
import org.apache.logging.log4j.Logger;

public class Logs {

    private final Logger logger;

    public Logs(Logger logger) {
        this.logger = logger;
    }

    public void log(String message) {
        if (Version.IS_DEBUG != 1) return;
        logger.info(message);
    }

}
