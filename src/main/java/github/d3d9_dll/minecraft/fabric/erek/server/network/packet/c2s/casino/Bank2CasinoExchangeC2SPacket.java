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
public class Bank2CasinoExchangeC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    private static final float EXCHANGE_COURSE = 1.0f;

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos exchangeMachine = buf.readBlockPos();
        if (!ExchangeMachineBlock.checkConstruct(exchangeMachine, player.getServerWorld()))
            throw new IllegalArgumentException("exchange machine is not found on the given coordinates");

        String UUID = player.getUuidAsString();

        float exchange = buf.readFloat();
        float currentBalance = ServerEntrypoint.MONEYS.get(UUID);
        if (exchange > currentBalance || exchange <= 0)
            throw new IllegalArgumentException("illegal exchange value");
        float pieces = exchange * EXCHANGE_COURSE;

        ServerEntrypoint.MONEYS.subtract(UUID, exchange);
        ServerEntrypoint.PIECES.increment(UUID, pieces);

        ServerEntrypoint.LOGGER.debug(
                String.format("ATM Exchanged by course %.2f pieces %.2f", EXCHANGE_COURSE, pieces)
        );
    }

}
