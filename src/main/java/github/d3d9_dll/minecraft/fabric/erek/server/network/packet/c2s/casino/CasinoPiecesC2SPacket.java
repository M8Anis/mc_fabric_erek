package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.casino;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.ExchangeMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
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
public class CasinoPiecesC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos blockPos = buf.readBlockPos();
        if (!SlotMachineBlock.checkConstruct(blockPos, player.getServerWorld()) &&
                !ExchangeMachineBlock.checkConstruct(blockPos, player.getServerWorld()))
            throw new IllegalArgumentException("slot or exchange machine is not found on the given coordinates");

        float pieces = ServerEntrypoint.PIECES.get(player.getUuidAsString());
        PacketByteBuf buff = PacketByteBufs.create();
        buff.writeFloat(pieces);

        responseSender.sendPacket(Entrypoint.PACKET_CASINO_PIECES, buff);

        ServerEntrypoint.LOGGER.debug("Casino pieces (" + pieces +
                ") sended to player \"" + player.getName().asString() + "\"");
    }

}
