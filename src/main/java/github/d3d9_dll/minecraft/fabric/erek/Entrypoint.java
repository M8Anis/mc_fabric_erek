package github.d3d9_dll.minecraft.fabric.erek;

import github.d3d9_dll.minecraft.fabric.erek.util.Logs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;

import java.io.File;

public class Entrypoint implements ModInitializer {

    public final static Logs LOGGER = new Logs(LogManager.getLogger(Logs.LOG_PREFIX));
    // Common
    public static final Identifier PACKET_VERSION_SYNC =
            new Identifier("d3d9_dllerek", "packet_version_synchronize");
    // Casino
    public static final Identifier PACKET_SLOTMACHINE_SPIN =
            new Identifier("d3d9_dllerek", "packet_slotmachine_spin");
    public static final Identifier PACKET_SLOTMACHINE_PIECES =
            new Identifier("d3d9_dllerek", "packet_slotmachine_pieces");
    // Bank
    public static final Identifier PACKET_CHEAT_SET_BALANCE =
            new Identifier("d3d9_dllerek", "packet_cheat_set_balance");

    public static final File MOD_DATA_DIRECTORY =
            new File(FabricLoader.getInstance().getConfigDir().toFile(), "d3d9_dllerek");

    @Override
    public void onInitialize() {
        LOGGER.debug("Common-side initialization");

        if (!MOD_DATA_DIRECTORY.exists()) //noinspection ResultOfMethodCallIgnored
            MOD_DATA_DIRECTORY.mkdirs();
    }

}
