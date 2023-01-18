package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.bank;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.bank.Moneys;
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
        BlockPos atmPos = buf.readBlockPos();
        if (!AtmBlock.checkConstruct(atmPos, player.getServerWorld()))
            throw new IllegalArgumentException();

        float money = Moneys.get(player.getUuidAsString());
        PacketByteBuf buff = PacketByteBufs.create();
        buff.writeFloat(money);

        responseSender.sendPacket(Entrypoint.PACKET_BANK_MONEYS, buff);

        ServerEntrypoint.LOGGER.debug("Bank moneys (" + money +
                ") sended to player \"" + player.getName().asString() + "\"");
    }

}
