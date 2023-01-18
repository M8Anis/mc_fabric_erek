package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.bank;

import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
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
public class Bank2CasinoExchangeC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    private static final float EXCHANGE_COURSE = 1.0f;

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos atmPos = buf.readBlockPos();
        if (!AtmBlock.checkConstruct(atmPos, player.getServerWorld()))
            throw new IllegalArgumentException();

        String UUID = player.getUuidAsString();

        float exchange = buf.readFloat();
        float currentBalance = ServerEntrypoint.MONEYS.get(UUID);
        if (exchange > currentBalance || exchange <= 0)
            throw new IllegalArgumentException();
        float pieces = exchange * EXCHANGE_COURSE;

        ServerEntrypoint.MONEYS.subtract(UUID, exchange);
        ServerEntrypoint.PIECES.increment(UUID, pieces);

        ServerEntrypoint.LOGGER.debug(
                String.format("ATM Exchanged by course %.2f pieces %.2f", EXCHANGE_COURSE, pieces)
        );
    }

}
