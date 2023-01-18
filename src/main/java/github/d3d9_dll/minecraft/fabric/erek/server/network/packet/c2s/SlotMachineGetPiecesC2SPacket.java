package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
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
public class SlotMachineGetPiecesC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        BlockPos slotmachinePos = buf.readBlockPos();
        if (!SlotMachineBlock.checkConstruct(slotmachinePos, player.getServerWorld()))
            throw new IllegalArgumentException();

        float balance = Pieces.get(player.getUuidAsString());
        PacketByteBuf buff = PacketByteBufs.create();
        buff.writeFloat(balance);

        responseSender.sendPacket(Entrypoint.PACKET_SLOTMACHINE_PIECES, buff);

        ServerEntrypoint.LOGGER.debug("Slotmachine balance (" + balance +
                ") sended to player \"" + player.getName().asString() + "\"");
    }

}
