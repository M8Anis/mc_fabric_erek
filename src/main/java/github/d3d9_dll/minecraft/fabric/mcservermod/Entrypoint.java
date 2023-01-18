package github.d3d9_dll.minecraft.fabric.mcservermod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Entrypoint implements ModInitializer {

    public final static Logger LOGGER = LogManager.getLogger("d3d9_dll MCServerMod");

    public static final Identifier PACKET_SLOTMACHINE_SPIN =
            new Identifier("d3d9_dllmcservermod", "slotmachine_spin");
    public static final Identifier PACKET_SLOTMACHINE_SPIN_RESULT =
            new Identifier("d3d9_dllmcservermod", "slotmachine_result");

    @Override
    public void onInitialize() {
        LOGGER.info("Common-side initialization");
    }

}
