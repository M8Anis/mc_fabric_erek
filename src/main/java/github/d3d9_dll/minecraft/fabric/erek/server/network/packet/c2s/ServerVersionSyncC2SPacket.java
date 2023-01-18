package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s;

import github.d3d9_dll.minecraft.fabric.erek.Version;
import github.d3d9_dll.minecraft.fabric.erek.server.models.VersionSynchronizeQueue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.SERVER)
public class ServerVersionSyncC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        long clientVersionFingerprint = buf.readLong();
        if (Version.VERSION_FINGERPRINT != clientVersionFingerprint) {
            TranslatableText reason = new TranslatableText(
                    clientVersionFingerprint > Version.VERSION_FINGERPRINT ?
                            "text.d3d9_dllerek.version_sync.newer" : "text.d3d9_dllerek.version_sync.older",
                    new TranslatableText("text.d3d9_dllerek.erek_prefix")
            );
            handler.disconnect(reason);
        }
        VersionSynchronizeQueue.remove(handler);
    }

}
