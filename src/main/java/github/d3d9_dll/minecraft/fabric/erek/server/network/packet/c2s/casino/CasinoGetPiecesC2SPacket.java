package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s.casino;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine.Pieces;
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
public class CasinoGetPiecesC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos blockPos = buf.readBlockPos();
        if (!SlotMachineBlock.checkConstruct(blockPos, player.getServerWorld()) &&
                !AtmBlock.checkConstruct(blockPos, player.getServerWorld()))
            throw new IllegalArgumentException();

        float pieces = Pieces.get(player.getUuidAsString());
        PacketByteBuf buff = PacketByteBufs.create();
        buff.writeFloat(pieces);

        responseSender.sendPacket(Entrypoint.PACKET_CASINO_PIECES, buff);

        ServerEntrypoint.LOGGER.debug("Casino pieces (" + pieces +
                ") sended to player \"" + player.getName().asString() + "\"");
    }

}
