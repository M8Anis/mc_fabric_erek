package github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.casino;

import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.SlotmachineScreen;
import github.d3d9_dll.minecraft.fabric.erek.models.slotmachine.Lines;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class SlotMachineSpinResultS2CPacket implements ClientPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                        PacketSender responseSender) {
        String[][] resultOfSpin = new String[5][3];
        for (int real = 0; real < 5; real++) {
            for (int symbol = 0; symbol < 3; symbol++) {
                resultOfSpin[real][symbol] = buf.readString(1);
            }
        }

        SlotmachineScreen.setResult(resultOfSpin, new Lines.Matched(new Lines(resultOfSpin)));

        float newPiecesCounter = buf.readFloat();
        float newCoefficient = buf.readFloat();

        SlotmachineScreen.setPiecesCounter(newPiecesCounter);
        SlotmachineScreen.setCoefficient(newCoefficient);
        SlotmachineScreen.bonusGame = buf.readBoolean();

        ClientEntrypoint.LOGGER.debug("Slotmachine pieces changed to " + newPiecesCounter +
                ", coefficient to " + newCoefficient + " and spin result showed");
    }

}