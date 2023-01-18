package github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.bank;

import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.AtmScreen;
import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.ExchangeMachineScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class BankMoneysS2CPacket implements ClientPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                        PacketSender responseSender) {
        float newMoneyCount = buf.readFloat();
        AtmScreen.setMoneys(newMoneyCount);
        ExchangeMachineScreen.setMoneys(newMoneyCount);

        ClientEntrypoint.LOGGER.debug("Bank money changed to " + newMoneyCount);
    }

}
