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

package io.github.m8anis.mc_fabric_erek.client.network.packet.s2c.casino;

import io.github.m8anis.mc_fabric_erek.client.ClientEntrypoint;
import io.github.m8anis.mc_fabric_erek.client.gui.screen.SlotmachineScreen;
import io.github.m8anis.mc_fabric_erek.models.slotmachine.Lines;
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
