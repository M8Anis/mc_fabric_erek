package github.d3d9_dll.minecraft.fabric.mcservermod.server;

import github.d3d9_dll.minecraft.fabric.mcservermod.Entrypoint;
import github.d3d9_dll.minecraft.fabric.mcservermod.server.network.packet.c2s.SlotMachineSpineC2SPacket;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.SERVER)
public class ServerEntrypoint implements DedicatedServerModInitializer {

    public final static Logger LOGGER = LogManager.getLogger("d3d9_dll MCServerMod | Server-side");

    private static void registerServerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(Entrypoint.PACKET_SLOTMACHINE_SPIN, new SlotMachineSpineC2SPacket());
        LOGGER.info("Packet \"SlotMachineSpineC2SPacket\" registered");
    }

    @Override
    public void onInitializeServer() {
        LOGGER.info("Server-side initialization");

        registerServerPackets();
    }

}
