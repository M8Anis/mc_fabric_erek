package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s;

import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.server.models.bank.Balances;
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
public class CheatSetBalanceC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos atmPos = buf.readBlockPos();
        if (!AtmBlock.checkConstruct(atmPos, player.getServerWorld()))
            throw new IllegalArgumentException();

        float newBalance = buf.readFloat();
        if (newBalance < 0.0f || newBalance > 10000.0f)
            throw new IllegalArgumentException();

        String UUID = player.getUuidAsString();
        float currentBalance = Balances.get(UUID);
        float delta = currentBalance - newBalance;
        if (delta < 0.0f) Balances.increment(UUID, -delta);
        else Balances.subtract(UUID, delta);
    }

}
