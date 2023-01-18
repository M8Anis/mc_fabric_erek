package github.d3d9_dll.minecraft.fabric.erek.server;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.*;
import github.d3d9_dll.minecraft.fabric.erek.item.*;
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

    public final static Logs LOGGER = new Logs(LogManager.getLogger(Logs.LOG_PREFIX + " | Server-side"));

    private static final File BALANCES_FILE = new File(Entrypoint.MOD_DATA_DIRECTORY, "balances.json");
    private static final File CASINO_DIRECTORY = new File(Entrypoint.MOD_DATA_DIRECTORY, "casino");
    private static final File FREE_SPINS_FILE = new File(CASINO_DIRECTORY, "freespins.json");

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

        registerServerItemsAndBlocks();

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
                BufferedWriter writer = new BufferedWriter(new FileWriter(BALANCES_FILE));
                String data = Balances.exportData();
                writer.write(data);
                writer.close();
                LOGGER.debug("File \"balances.json\" saved");
            } catch (FileNotFoundException e) {
                LOGGER.error("File \"balances.json\" not found for save");
            } catch (IOException e) {
                LOGGER.error("Cannot write \"balances.json\" for save");
            }
        }

        if (!FREE_SPINS_FILE.canWrite()) {
            LOGGER.error("File \"freespins.json\" is not writeable");
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(FREE_SPINS_FILE));
                String data = FreeSpin.exportData();
                writer.write(data);
                writer.close();
                LOGGER.debug("File \"freespins.json\" saved");
            } catch (FileNotFoundException e) {
                LOGGER.error("File \"freespins.json\" not found for save");
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
                BufferedReader reader = new BufferedReader(new FileReader(BALANCES_FILE));
                StringBuilder data = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    data.append(line);
                    line = reader.readLine();
                }
                Balances.importData(data.toString());
                reader.close();
                LOGGER.debug("File \"balances.json\" loaded");
            } catch (FileNotFoundException e) {
                LOGGER.error("File \"balances.json\" not found for load");
            } catch (IOException e) {
                LOGGER.error("Cannot read \"balances.json\" for load");
            }
        }

        if (!FREE_SPINS_FILE.canRead()) {
            LOGGER.error("File \"freespins.json\" is not readable");
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
                LOGGER.debug("File \"freespins.json\" loaded");
            } catch (FileNotFoundException e) {
                LOGGER.error("File \"freespins.json\" not found for load");
            } catch (IOException e) {
                LOGGER.error("Cannot read \"freespins.json\" for load");
            }
        }
    }

    private static void registerServerItemsAndBlocks() {
        LOGGER.debug("Registering casino category");
        SlotMachineBottomCaseItem.register();
        LOGGER.debug("BlockItem \"slotmachine_bottom_case\" registered");
        SlotMachineBottomCaseBlock.register();
        LOGGER.debug("Block \"slotmachine_bottom_case\" registered");

        SlotMachineBlockItem.register();
        LOGGER.debug("BlockItem \"slotmachine_block\" registered");
        SlotMachineBlock.register();
        LOGGER.debug("Block \"slotmachine_block\" registered");

        SlotMachineInfoPanelItem.register();
        LOGGER.debug("BlockItem \"slotmachine_info_panel\" registered");
        SlotMachineInfoPanelBlock.register();
        LOGGER.debug("Block \"slotmachine_info_panel\" registered");


        LOGGER.debug("Registering bank category");
        AtmBlockItem.register();
        LOGGER.debug("BlockItem \"atm_block\" registered");
        AtmBlock.register();
        LOGGER.debug("Block \"atm_block\" registered");
        AtmBottomCaseItem.register();
        LOGGER.debug("BlockItem \"atm_bottom_case\" registered");
        AtmBottomCaseBlock.register();
        LOGGER.debug("Block \"atm_bottom_case\" registered");
    }

}
