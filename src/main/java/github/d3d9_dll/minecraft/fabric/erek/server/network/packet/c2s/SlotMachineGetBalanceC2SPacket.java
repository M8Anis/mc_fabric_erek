package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.models.Balances;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.SERVER)
public class SlotMachineGetBalanceC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        float balance = Balances.get(player.getUuidAsString());
        PacketByteBuf buff = PacketByteBufs.create();
        buff.writeFloat(balance);

        responseSender.sendPacket(Entrypoint.PACKET_SLOTMACHINE_BALANCE, buff);

        ServerEntrypoint.LOGGER.debug("Slotmachine balance (" + balance +
                ") sended to player \"" + player.getName().asString() + "\"");
    }

}
