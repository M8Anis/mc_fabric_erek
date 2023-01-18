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

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.models.slotmachine.Lines;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine.Coefficients;
import github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine.Reals;
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
public class SlotMachineSpinC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    private static final float MINIMAL_BET = 4.0f;
    private static final float MAXIMAL_BET = 12.0f;

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos slotmachinePos = buf.readBlockPos();
        if (!SlotMachineBlock.checkConstruct(slotmachinePos, player.getServerWorld()))
            throw new IllegalArgumentException("slot exchange machine is not found on the given coordinates");

        String UUID = player.getUuidAsString();
        float balance = ServerEntrypoint.PIECES.get(UUID);
        float bet = buf.readFloat();

        if (bet > MAXIMAL_BET || bet < MINIMAL_BET || bet > balance)
            throw new IllegalArgumentException("bet out of bounds");

        String[][] resultOfSpin = Reals.generateResult();
        Lines lines = new Lines(resultOfSpin);
        Lines.Matched matchedLines = new Lines.Matched(lines);
        boolean bonusGame = lines.isBonusGame;
        float coeff = (new Coefficients(matchedLines)).getCoefficient();

        int freeSpins = ServerEntrypoint.FREE_SPINS.get(UUID);
        if (freeSpins > 0) ServerEntrypoint.FREE_SPINS.subtract(UUID);
        if (bonusGame) ServerEntrypoint.FREE_SPINS.add(UUID, 10);

        if (coeff < 0f) {
            if (!(freeSpins > 0)) ServerEntrypoint.PIECES.subtract(UUID, bet * -coeff);
        } else {
            ServerEntrypoint.PIECES.increment(UUID, bet * coeff);
        }

        PacketByteBuf buff = Reals.generateForPacket(resultOfSpin);
        buff.writeFloat(ServerEntrypoint.PIECES.get(UUID));
        buff.writeFloat(coeff);
        buff.writeBoolean(freeSpins > 0 || bonusGame);

        responseSender.sendPacket(Entrypoint.PACKET_SLOTMACHINE_SPIN, buff);

        ServerEntrypoint.LOGGER.debug("Slotmachine spin result sended to player \""
                + player.getName().asString() + "\"");
    }

}
