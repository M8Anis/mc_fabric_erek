package github.d3d9_dll.minecraft.fabric.erek.server;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.Balances;
import github.d3d9_dll.minecraft.fabric.erek.server.models.VersionSynchronizeQueue;
import github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine.FreeSpin;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.ServerVersionSyncC2SPacket;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.SlotMachineGetBalanceC2SPacket;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.SlotMachineSpinC2SPacket;
import github.d3d9_dll.minecraft.fabric.erek.server.util.ServerBlockRegistration;
import github.d3d9_dll.minecraft.fabric.erek.server.util.ServerItemRegistration;
import github.d3d9_dll.minecraft.fabric.erek.util.File;
import github.d3d9_dll.minecraft.fabric.erek.util.Logs;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;

import java.io.FileNotFoundException;
import java.io.IOException;

@Environment(EnvType.SERVER)
public class ServerEntrypoint implements DedicatedServerModInitializer {

    public final static Logs LOGGER = new Logs(LogManager.getLogger(Logs.LOG_PREFIX + " | Server-side"));

    private static final java.io.File BALANCES_FILE = new java.io.File(Entrypoint.MOD_DATA_DIRECTORY, "balances.json");
    private static final java.io.File CASINO_DIRECTORY = new java.io.File(Entrypoint.MOD_DATA_DIRECTORY, "casino");
    private static final java.io.File FREE_SPINS_FILE = new java.io.File(CASINO_DIRECTORY, "freespins.json");

    @Override
    public void onInitializeServer() {
        LOGGER.debug("Server-side initialization");

        if (!BALANCES_FILE.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                BALANCES_FILE.createNewFile();
                LOGGER.debug("File \"balances.json\" created");
            } catch (IOException e) {
                LOGGER.error("Cannot create \"balances.json\"");
            }
        }
        if (!FREE_SPINS_FILE.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                CASINO_DIRECTORY.mkdirs();
                //noinspection ResultOfMethodCallIgnored
                FREE_SPINS_FILE.createNewFile();
                LOGGER.debug("File \"freespins.json\" created");
            } catch (IOException e) {
                LOGGER.error("Cannot create \"freespins.json\"");
            }
        }

        new ServerItemRegistration(new ServerBlockRegistration());

        registerServerPackets();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> addToVersionSyncQueue(handler));
        LOGGER.debug("Players adding to version sync registered to \"ServerPlayConnectionEvents.JOIN\" event");

        ServerLifecycleEvents.SERVER_STOPPING.register((listener) -> saveData());
        LOGGER.debug("Data saving registered to \"ServerLifecycleEvents.SERVER_STOPPING\" event");

        ServerLifecycleEvents.SERVER_STARTING.register((listener) -> loadData());
        LOGGER.debug("Data loading registered to \"ServerLifecycleEvents.SERVER_STARTING\" event");
    }

    private static void registerServerPackets() {
        LOGGER.debug("Packets registration");
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_SLOTMACHINE_SPIN, new SlotMachineSpinC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_SLOTMACHINE_SPIN\" registered");
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_SLOTMACHINE_BALANCE, new SlotMachineGetBalanceC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_SLOTMACHINE_BALANCE\" registered");
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_VERSION_SYNC, new ServerVersionSyncC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_VERSION_SYNC\" registered");
    }

    private static void addToVersionSyncQueue(ServerPlayNetworkHandler handler) {
        ServerPlayerEntity player = handler.player;
        VersionSynchronizeQueue.add(handler);
        ServerPlayNetworking.send(player, Entrypoint.PACKET_VERSION_SYNC, PacketByteBufs.empty());
        LOGGER.debug("Player \"" + player.getName().asString() + "\" added to version synchronize queue");
    }

    private static void saveData() {
        LOGGER.debug("Saving data");
        if (!BALANCES_FILE.canWrite()) {
            LOGGER.error("File \"balances.json\" is not writeable");
        } else {
            try {
                File.write(BALANCES_FILE, Balances.exportData());
                LOGGER.debug("File \"balances.json\" saved");
            } catch (IOException e) {
                LOGGER.error("Cannot write \"balances.json\" for save");
            }
        }

        if (!FREE_SPINS_FILE.canWrite()) {
            LOGGER.error("File \"freespins.json\" is not writeable");
        } else {
            try {
                File.write(FREE_SPINS_FILE, FreeSpin.exportData());
                LOGGER.debug("File \"freespins.json\" saved");
            } catch (IOException e) {
                LOGGER.error("Cannot write \"freespins.json\" for save");
            }
        }
    }

    private static void loadData() {
        LOGGER.debug("Loading data");
        if (!BALANCES_FILE.canRead()) {
            LOGGER.error("File \"balances.json\" is not readable");
        } else {
            try {
                String data = File.read(BALANCES_FILE);
                if (data == null) {
                    LOGGER.error("Cannot read \"balances.json\" for load");
                } else {
                    Balances.importData(data);
                    LOGGER.debug("File \"balances.json\" loaded");
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("File \"balances.json\" not found for load");
            }
        }

        if (!FREE_SPINS_FILE.canRead()) {
            LOGGER.error("File \"freespins.json\" is not readable");
        } else {
            try {
                String data = File.read(FREE_SPINS_FILE);
                if (data == null) {
                    LOGGER.error("Cannot read \"freespins.json\" for load");
                } else {
                    FreeSpin.importData(data);
                    LOGGER.debug("File \"freespins.json\" loaded");
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("File \"freespins.json\" not found for load");
            }
        }
    }

}
