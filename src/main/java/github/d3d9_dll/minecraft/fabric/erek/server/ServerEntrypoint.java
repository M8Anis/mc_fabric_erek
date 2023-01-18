package github.d3d9_dll.minecraft.fabric.erek.server;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.Balances;
import github.d3d9_dll.minecraft.fabric.erek.server.models.VersionSynchronizeQueue;
import github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine.FreeSpin;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.ServerVersionSyncC2SPacket;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.SlotMachineGetBalanceC2SPacket;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.SlotMachineSpinC2SPacket;
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

import java.io.*;

@Environment(EnvType.SERVER)
public class ServerEntrypoint implements DedicatedServerModInitializer {

    public final static Logs LOGGER = new Logs(LogManager.getLogger("d3d9_dll EREK | Server-side"));

    private static final File BALANCES_FILE = new File(Entrypoint.MOD_DATA_DIRECTORY, "balances.json");
    private static final File CASINO_DIRECTORY = new File(Entrypoint.MOD_DATA_DIRECTORY, "casino");
    private static final File FREE_SPINS_FILE = new File(CASINO_DIRECTORY, "freespins.json");

    @Override
    public void onInitializeServer() {
        LOGGER.log("Server-side initialization");

        if (!BALANCES_FILE.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                BALANCES_FILE.createNewFile();
                LOGGER.log("File \"balances.json\" created");
            } catch (IOException e) {
                LOGGER.log("Cannot create \"balances.json\"");
            }
        }
        if (!FREE_SPINS_FILE.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                CASINO_DIRECTORY.mkdirs();
                //noinspection ResultOfMethodCallIgnored
                FREE_SPINS_FILE.createNewFile();
                LOGGER.log("File \"freespins.json\" created");
            } catch (IOException e) {
                LOGGER.log("Cannot create \"freespins.json\"");
            }
        }

        registerServerPackets();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> addToVersionSyncQueue(handler));
        LOGGER.log("Players adding to version sync registered to \"ServerPlayConnectionEvents.JOIN\" event");

        ServerLifecycleEvents.SERVER_STOPPING.register((listener) -> saveData());
        LOGGER.log("Data saving registered to \"ServerLifecycleEvents.SERVER_STOPPING\" event");

        ServerLifecycleEvents.SERVER_STARTING.register((listener) -> loadData());
        LOGGER.log("Data loading registered to \"ServerLifecycleEvents.SERVER_STARTING\" event");
    }

    private static void registerServerPackets() {
        LOGGER.log("Packets registration");
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_SLOTMACHINE_SPIN, new SlotMachineSpinC2SPacket()
        );
        LOGGER.log("Packet \"PACKET_SLOTMACHINE_SPIN\" registered");
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_SLOTMACHINE_BALANCE, new SlotMachineGetBalanceC2SPacket()
        );
        LOGGER.log("Packet \"PACKET_SLOTMACHINE_BALANCE\" registered");
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_VERSION_SYNC, new ServerVersionSyncC2SPacket()
        );
        LOGGER.log("Packet \"PACKET_VERSION_SYNC\" registered");
    }

    private static void addToVersionSyncQueue(ServerPlayNetworkHandler handler) {
        ServerPlayerEntity player = handler.player;
        VersionSynchronizeQueue.add(handler);
        ServerPlayNetworking.send(player, Entrypoint.PACKET_VERSION_SYNC, PacketByteBufs.empty());
        LOGGER.log("Player \"" + player.getName().asString() + "\" added to version synchronize queue");
    }

    private static void saveData() {
        LOGGER.log("Saving data");
        if (!BALANCES_FILE.canWrite()) {
            LOGGER.log("File \"balances.json\" is not writeable");
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(BALANCES_FILE));
                String data = Balances.exportData();
                writer.write(data);
                writer.close();
            } catch (FileNotFoundException e) {
                LOGGER.log("File \"balances.json\" not found for save");
            } catch (IOException e) {
                LOGGER.log("Cannot write \"balances.json\" for save");
            }
        }

        if (!FREE_SPINS_FILE.canWrite()) {
            LOGGER.log("File \"freespins.json\" is not writeable");
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(FREE_SPINS_FILE));
                String data = FreeSpin.exportData();
                writer.write(data);
                writer.close();
            } catch (FileNotFoundException e) {
                LOGGER.log("File \"freespins.json\" not found for save");
            } catch (IOException e) {
                LOGGER.log("Cannot write \"freespins.json\" for save");
            }
        }
    }

    private static void loadData() {
        LOGGER.log("Loading data");
        if (!BALANCES_FILE.canRead()) {
            LOGGER.log("File \"balances.json\" is not readable");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(BALANCES_FILE));
                StringBuilder data = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    data.append(line);
                    line = reader.readLine();
                }
                Balances.importData(data.toString());
                reader.close();
            } catch (FileNotFoundException e) {
                LOGGER.log("File \"balances.json\" not found for load");
            } catch (IOException e) {
                LOGGER.log("Cannot read \"balances.json\" for load");
            }
        }

        if (!FREE_SPINS_FILE.canRead()) {
            LOGGER.log("File \"freespins.json\" is not readable");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(FREE_SPINS_FILE));
                StringBuilder data = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    data.append(line);
                    line = reader.readLine();
                }
                FreeSpin.importData(data.toString());
                reader.close();
            } catch (FileNotFoundException e) {
                LOGGER.log("File \"freespins.json\" not found for load");
            } catch (IOException e) {
                LOGGER.log("Cannot read \"freespins.json\" for load");
            }
        }
    }

}
