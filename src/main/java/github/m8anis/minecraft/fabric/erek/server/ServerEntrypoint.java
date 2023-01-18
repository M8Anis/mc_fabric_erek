/*
Copyright © 2022-2023 https://github.com/M8Anis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package github.m8anis.minecraft.fabric.erek.server;

import github.m8anis.minecraft.fabric.erek.Entrypoint;
import github.m8anis.minecraft.fabric.erek.server.models.AutoSave;
import github.m8anis.minecraft.fabric.erek.server.models.ServerBlockRegistration;
import github.m8anis.minecraft.fabric.erek.server.models.ServerItemRegistration;
import github.m8anis.minecraft.fabric.erek.server.models.VersionSynchronizeQueue;
import github.m8anis.minecraft.fabric.erek.server.models.bank.Moneys;
import github.m8anis.minecraft.fabric.erek.server.models.slotmachine.FreeSpin;
import github.m8anis.minecraft.fabric.erek.server.models.slotmachine.Pieces;
import github.m8anis.minecraft.fabric.erek.server.network.packet.c2s.ServerVersionSyncC2SPacket;
import github.m8anis.minecraft.fabric.erek.server.network.packet.c2s.bank.BankMoneysC2SPacket;
import github.m8anis.minecraft.fabric.erek.server.network.packet.c2s.casino.Bank2CasinoExchangeC2SPacket;
import github.m8anis.minecraft.fabric.erek.server.network.packet.c2s.casino.Casino2BankExchangeC2SPacket;
import github.m8anis.minecraft.fabric.erek.server.network.packet.c2s.casino.CasinoPiecesC2SPacket;
import github.m8anis.minecraft.fabric.erek.server.network.packet.c2s.casino.SlotMachineSpinC2SPacket;
import github.m8anis.minecraft.fabric.erek.util.File;
import github.m8anis.minecraft.fabric.erek.util.Logs;
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

    private static final java.io.File BANK_DIRECTORY = new java.io.File(Entrypoint.MOD_DATA_DIRECTORY, "bank");
    private static final java.io.File BALANCES_FILE = new java.io.File(BANK_DIRECTORY, "balances.json");
    private static final java.io.File CASINO_DIRECTORY = new java.io.File(Entrypoint.MOD_DATA_DIRECTORY, "casino");
    private static final java.io.File FREE_SPINS_FILE = new java.io.File(CASINO_DIRECTORY, "freespins.json");
    private static final java.io.File PIECES_FILE = new java.io.File(CASINO_DIRECTORY, "pieces.json");

    private static final AutoSave AUTO_SAVE_THREAD = new AutoSave(300000L);
    public static final Moneys MONEYS = new Moneys();
    public static final Pieces PIECES = new Pieces();
    public static final FreeSpin FREE_SPINS = new FreeSpin();
    public static final ServerBlockRegistration BLOCK_REGISTER = new ServerBlockRegistration();
    public static final ServerItemRegistration ITEM_REGISTER = new ServerItemRegistration(BLOCK_REGISTER);

    @Override
    public void onInitializeServer() {
        LOGGER.debug("Server-side initialization");

        if (!BALANCES_FILE.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                BANK_DIRECTORY.mkdirs();
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
        if (!PIECES_FILE.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                PIECES_FILE.createNewFile();
                LOGGER.debug("File \"pieces.json\" created");
            } catch (IOException e) {
                LOGGER.error("Cannot create \"pieces.json\"");
            }
        }

        registerServerPackets();

        ServerLifecycleEvents.SERVER_STARTING.register((listener) -> loadData());
        LOGGER.debug("Data loading registered to \"ServerLifecycleEvents.SERVER_STARTING\" event");

        ServerLifecycleEvents.SERVER_STOPPING.register((listener) -> saveData());
        LOGGER.debug("Data saving registered to \"ServerLifecycleEvents.SERVER_STOPPING\" event");

        ServerLifecycleEvents.SERVER_STARTING.register((listener) -> AUTO_SAVE_THREAD.start());
        LOGGER.debug("Data auto-saving enabler registered to \"ServerLifecycleEvents.SERVER_STARTING\" event");

        ServerLifecycleEvents.SERVER_STOPPING.register((listener) -> AUTO_SAVE_THREAD.interrupt());
        LOGGER.debug("Data auto-saver interrupter registered to \"ServerLifecycleEvents.SERVER_STOPPING\" event");

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> addToVersionSyncQueue(handler));
        LOGGER.debug("Players adding to version sync registered to \"ServerPlayConnectionEvents.JOIN\" event");

        BLOCK_REGISTER.registerAll();
        ITEM_REGISTER.registerAll();
    }

    private static void registerServerPackets() {
        LOGGER.debug("Packets registration");

        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_SLOTMACHINE_SPIN, new SlotMachineSpinC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_SLOTMACHINE_SPIN\" registered");

        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_CASINO_PIECES, new CasinoPiecesC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_CASINO_PIECES\" registered");

        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_VERSION_SYNC, new ServerVersionSyncC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_VERSION_SYNC\" registered");

        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_BANK_TO_CASINO_EXCHANGE, new Bank2CasinoExchangeC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_BANK_TO_CASINO_EXCHANGE\" registered");

        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_CASINO_TO_BANK_EXCHANGE, new Casino2BankExchangeC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_CASINO_TO_BANK_EXCHANGE\" registered");

        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_BANK_MONEYS, new BankMoneysC2SPacket()
        );
        LOGGER.debug("Packet \"PACKET_BANK_MONEYS\" registered");
    }

    private static void addToVersionSyncQueue(ServerPlayNetworkHandler handler) {
        ServerPlayerEntity player = handler.player;
        VersionSynchronizeQueue.add(handler);
        ServerPlayNetworking.send(player, Entrypoint.PACKET_VERSION_SYNC, PacketByteBufs.empty());
        LOGGER.debug("Player \"" + player.getName().asString() + "\" added to version synchronize queue");
    }

    public static void saveData() {
        LOGGER.debug("Saving data");

        if (!BALANCES_FILE.canWrite()) {
            LOGGER.error("File \"balances.json\" is not writeable");
        } else {
            try {
                File.write(BALANCES_FILE, MONEYS.exportData());
                LOGGER.debug("File \"balances.json\" saved");
            } catch (IOException e) {
                LOGGER.error("Cannot write \"balances.json\" for save");
            }
        }

        if (!FREE_SPINS_FILE.canWrite()) {
            LOGGER.error("File \"freespins.json\" is not writeable");
        } else {
            try {
                File.write(FREE_SPINS_FILE, FREE_SPINS.exportData());
                LOGGER.debug("File \"freespins.json\" saved");
            } catch (IOException e) {
                LOGGER.error("Cannot write \"freespins.json\" for save");
            }
        }

        if (!PIECES_FILE.canWrite()) {
            LOGGER.error("File \"pieces.json\" is not writeable");
        } else {
            try {
                File.write(PIECES_FILE, PIECES.exportData());
                LOGGER.debug("File \"pieces.json\" saved");
            } catch (IOException e) {
                LOGGER.error("Cannot write \"pieces.json\" for save");
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
                    MONEYS.importData(data);
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
                    FREE_SPINS.importData(data);
                    LOGGER.debug("File \"freespins.json\" loaded");
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("File \"freespins.json\" not found for load");
            }
        }

        if (!PIECES_FILE.canRead()) {
            LOGGER.error("File \"pieces.json\" is not readable");
        } else {
            try {
                String data = File.read(PIECES_FILE);
                if (data == null) {
                    LOGGER.error("Cannot read \"pieces.json\" for load");
                } else {
                    PIECES.importData(data);
                    LOGGER.debug("File \"pieces.json\" loaded");
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("File \"pieces.json\" not found for load");
            }
        }
    }

}
