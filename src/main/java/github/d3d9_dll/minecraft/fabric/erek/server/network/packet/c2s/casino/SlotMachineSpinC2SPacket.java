package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.casino;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.models.slotmachine.Lines;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine.Coefficients;
import github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine.FreeSpin;
import github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine.Pieces;
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
            throw new IllegalArgumentException();

        String UUID = player.getUuidAsString();
        float balance = Pieces.get(UUID);
        float bet = buf.readFloat();

        if (bet > MAXIMAL_BET || bet < MINIMAL_BET || bet > balance)
            throw new IllegalArgumentException();

        String[][] resultOfSpin = Reals.generateResult();
        Lines lines = new Lines(resultOfSpin);
        Lines.Matched matchedLines = new Lines.Matched(lines);
        boolean bonusGame = lines.isBonusGame;
        float coeff = (new Coefficients(matchedLines)).getCoefficient();

        int freeSpins = FreeSpin.get(UUID);
        if (freeSpins > 0) FreeSpin.subtract(UUID);
        if (bonusGame) FreeSpin.add(UUID, 10);

        if (coeff < 0f) {
            if (!(freeSpins > 0)) Pieces.subtract(UUID, bet * -coeff);
        } else {
            Pieces.increment(UUID, bet * coeff);
        }

        PacketByteBuf buff = Reals.generateForPacket(resultOfSpin);
        buff.writeFloat(Pieces.get(UUID));
        buff.writeFloat(coeff);
        buff.writeBoolean(freeSpins > 0 || bonusGame);

        responseSender.sendPacket(Entrypoint.PACKET_SLOTMACHINE_SPIN, buff);

        ServerEntrypoint.LOGGER.debug("Slotmachine spin result sended to player \""
                + player.getName().asString() + "\"");
    }

}
