package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.games.slotmachine.Lines;
import github.d3d9_dll.minecraft.fabric.erek.server.games.slotmachine.Balances;
import github.d3d9_dll.minecraft.fabric.erek.server.games.slotmachine.Coefficients;
import github.d3d9_dll.minecraft.fabric.erek.server.games.slotmachine.Reals;
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

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos slotmachinePos = buf.readBlockPos();
        if (!SlotMachineBlock.checkConstruct(slotmachinePos, player.getServerWorld()))
            return;

        float balance = Balances.get(player.getUuidAsString());
        float bet = buf.readFloat();

        if (bet > balance) return;

        String[][] resultOfSpin = Reals.generateResult();
        Lines lines = new Lines(resultOfSpin);
        Lines.Matched matchedLines = new Lines.Matched(lines);
        boolean bonusGame = lines.isBonusGame;
        float coeff = (new Coefficients(matchedLines)).getCoefficient();
        if (coeff == -1.0f) Balances.subtract(player.getUuidAsString(), bet * 2);
        else Balances.increment(player.getUuidAsString(), bet * coeff);

        PacketByteBuf buff = Reals.generateForPacket(resultOfSpin);
        buff.writeFloat(Balances.get(player.getUuidAsString()));
        buff.writeBoolean(bonusGame);

        ServerPlayNetworking.send(player, Entrypoint.PACKET_SLOTMACHINE_SPIN, buff);
    }

}
