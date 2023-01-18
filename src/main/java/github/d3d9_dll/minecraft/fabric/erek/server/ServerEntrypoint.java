package github.d3d9_dll.minecraft.fabric.erek.server;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.VersionSynchronizeQueue;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.ServerVersionSyncC2SPacket;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.SlotMachineGetBalanceC2SPacket;
import github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.SlotMachineSpinC2SPacket;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.SERVER)
public class ServerEntrypoint implements DedicatedServerModInitializer {

    public final static Logger LOGGER = LogManager.getLogger("d3d9_dll EREK | Server-side");

    @Override
    public void onInitializeServer() {
        LOGGER.info("Server-side initialization");

        registerServerPackets();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            VersionSynchronizeQueue.add(handler);
            ServerPlayNetworking.send(player, Entrypoint.PACKET_VERSION_SYNC, PacketByteBufs.empty());
        });
    }

    private static void registerServerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_SLOTMACHINE_SPIN, new SlotMachineSpinC2SPacket()
        );
        LOGGER.info("Packet \"PACKET_SLOTMACHINE_SPIN\" registered");
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_SLOTMACHINE_BALANCE, new SlotMachineGetBalanceC2SPacket()
        );
        LOGGER.info("Packet \"PACKET_SLOTMACHINE_BALANCE\" registered");
        ServerPlayNetworking.registerGlobalReceiver(
                Entrypoint.PACKET_VERSION_SYNC, new ServerVersionSyncC2SPacket()
        );
        LOGGER.info("Packet \"PACKET_VERSION_SYNC\" registered");
    }

}
