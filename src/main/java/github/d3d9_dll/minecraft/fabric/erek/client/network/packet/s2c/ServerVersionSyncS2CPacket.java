package github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.Version;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class ServerVersionSyncS2CPacket implements ClientPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                        PacketSender responseSender) {
        PacketByteBuf buff = PacketByteBufs.create();
        buff.writeLong(Version.VERSION_FINGERPRINT);

        responseSender.sendPacket(Entrypoint.PACKET_VERSION_SYNC, buff);
    }

}
