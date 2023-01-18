package github.d3d9_dll.minecraft.fabric.erek.util;

import github.d3d9_dll.minecraft.fabric.erek.Version;
import org.apache.logging.log4j.Logger;

public class Logs {

    public final static String LOG_PREFIX = "d3d9_dll EREK";

    private final Logger logger;

    public Logs(Logger logger) {
        this.logger = logger;
    }

    public void debug(String message) {
        if (Version.IS_DEBUG != 1) return;
        logger.info("[" + LOG_PREFIX + "/DEBUG] " + message);
    }

    public void error(String message) {
        logger.info("[" + LOG_PREFIX + "/ERROR] " + message);
    }

}
