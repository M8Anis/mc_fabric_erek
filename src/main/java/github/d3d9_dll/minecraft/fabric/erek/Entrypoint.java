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
    public static final Identifier PACKET_CASINO_PIECES =
            new Identifier("d3d9_dllerek", "packet_casino_pieces");
    public static final Identifier PACKET_CASINO_TO_BANK_EXCHANGE =
            new Identifier("d3d9_dllerek", "packet_casino_to_bank_exchange");
    // Bank
    public static final Identifier PACKET_BANK_TO_CASINO_EXCHANGE =
            new Identifier("d3d9_dllerek", "packet_bank_to_casino_exchange");
    public static final Identifier PACKET_BANK_MONEYS =
            new Identifier("d3d9_dllerek", "packet_bank_moneys");

    public static final File MOD_DATA_DIRECTORY =
            new File(FabricLoader.getInstance().getConfigDir().toFile(), "d3d9_dllerek");

    @Override
    public void onInitialize() {
        LOGGER.debug("Common-side initialization");

        if (!MOD_DATA_DIRECTORY.exists()) //noinspection ResultOfMethodCallIgnored
            MOD_DATA_DIRECTORY.mkdirs();
    }

}
