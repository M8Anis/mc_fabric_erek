package github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.casino;

import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.ExchangeMachineScreen;
import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.SlotmachineScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class CasinoPiecesS2CPacket implements ClientPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                        PacketSender responseSender) {
        float newPiecesCount = buf.readFloat();
        SlotmachineScreen.setPiecesCounter(newPiecesCount);
        ExchangeMachineScreen.setPieces(newPiecesCount);

        ClientEntrypoint.LOGGER.debug("Casino pieces changed to " + newPiecesCount);
    }

}
