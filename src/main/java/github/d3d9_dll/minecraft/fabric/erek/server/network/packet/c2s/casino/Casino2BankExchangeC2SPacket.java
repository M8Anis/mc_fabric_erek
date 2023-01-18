/*
Copyright (c) 2022-2023 https://github.com/d3d9-dll

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

package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.casino;

import github.d3d9_dll.minecraft.fabric.erek.block.ExchangeMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.SERVER)
public class Casino2BankExchangeC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    private static final float EXCHANGE_COURSE = 0.95f;

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos exchangeMachine = buf.readBlockPos();
        if (!ExchangeMachineBlock.checkConstruct(exchangeMachine, player.getServerWorld()))
            throw new IllegalArgumentException("exchange machine is not found on the given coordinates");

        String UUID = player.getUuidAsString();

        float exchange = buf.readFloat();
        float currentPieces = ServerEntrypoint.PIECES.get(UUID);
        if (exchange > currentPieces || exchange <= 0)
            throw new IllegalArgumentException("illegal exchange value");
        float money = exchange * EXCHANGE_COURSE;

        ServerEntrypoint.PIECES.subtract(UUID, exchange);
        ServerEntrypoint.MONEYS.increment(UUID, money);

        ServerEntrypoint.LOGGER.debug(
                String.format("ATM Exchanged by course %.2f money %.2f", EXCHANGE_COURSE, money)
        );
    }

}
