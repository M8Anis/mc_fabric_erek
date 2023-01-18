package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s;

import github.d3d9_dll.minecraft.fabric.erek.Version;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.VersionSynchronizeQueue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.SERVER)
public class ServerVersionSyncC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        long clientVersionFingerprint = buf.readLong();
        if (Version.VERSION_FINGERPRINT != clientVersionFingerprint) {
            if (clientVersionFingerprint > Version.VERSION_FINGERPRINT) {
                ServerEntrypoint.LOGGER.info("Client " + player.getName() + " has version upper");
            } else {
                ServerEntrypoint.LOGGER.info("Client " + player.getName() + " has version lower");
            }
            handler.disconnect(new LiteralText("[EREK] Bad version"));
        } else {
            ServerEntrypoint.LOGGER.info("Client " + player.getName() + " has good version");
        }
        VersionSynchronizeQueue.remove(handler);
    }

}
