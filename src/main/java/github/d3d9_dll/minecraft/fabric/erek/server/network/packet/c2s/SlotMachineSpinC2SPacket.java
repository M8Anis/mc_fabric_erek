package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.models.slotmachine.Lines;
import github.d3d9_dll.minecraft.fabric.erek.server.models.Balances;
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
            throw new IllegalArgumentException();

        float balance = Balances.get(player.getUuidAsString());
        float bet = buf.readFloat();

        if (bet > MAXIMAL_BET || bet < MINIMAL_BET || bet > balance)
            throw new IllegalArgumentException();

        String[][] resultOfSpin = Reals.generateResult();
        Lines lines = new Lines(resultOfSpin);
        Lines.Matched matchedLines = new Lines.Matched(lines);
        boolean bonusGame = lines.isBonusGame;
        float coeff = (new Coefficients(matchedLines)).getCoefficient();

        if (coeff < 0f) Balances.subtract(player.getUuidAsString(), bet * -coeff);
        else Balances.increment(player.getUuidAsString(), bet * coeff);

        if (bonusGame)
            Balances.increment(player.getUuidAsString(), bet * 12);

        PacketByteBuf buff = Reals.generateForPacket(resultOfSpin);
        buff.writeFloat(Balances.get(player.getUuidAsString()));
        buff.writeFloat(coeff);
        buff.writeBoolean(bonusGame);

        responseSender.sendPacket(Entrypoint.PACKET_SLOTMACHINE_SPIN, buff);
    }

}
