package github.d3d9_dll.minecraft.fabric.erek.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.SERVER)
public class ServerEntrypoint implements DedicatedServerModInitializer {

    public final static Logger LOGGER = LogManager.getLogger("d3d9_dll MCServerMod | Server-side");

    @Override
    public void onInitializeServer() {
        LOGGER.info("Server-side initialization");
    }

}
