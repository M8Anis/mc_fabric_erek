package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.bank;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.ExchangeMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.SERVER)
public class BankMoneysC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos blockPos = buf.readBlockPos();
        if (!AtmBlock.checkConstruct(blockPos, player.getServerWorld()) &&
                !ExchangeMachineBlock.checkConstruct(blockPos, player.getServerWorld()))
            throw new IllegalArgumentException("atm is not found on the given coordinates");

        float money = ServerEntrypoint.MONEYS.get(player.getUuidAsString());
        PacketByteBuf buff = PacketByteBufs.create();
        buff.writeFloat(money);

        responseSender.sendPacket(Entrypoint.PACKET_BANK_MONEYS, buff);

        ServerEntrypoint.LOGGER.debug("Bank moneys (" + money +
                ") sended to player \"" + player.getName().asString() + "\"");
    }

}
