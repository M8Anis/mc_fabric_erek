package github.d3d9_dll.minecraft.fabric.erek.server.network.packet.c2s;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.server.games.slotmachine.Reals;
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
public class SlotMachineSpineC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        PacketByteBuf buff = PacketByteBufs.create();

        String[][] resultOfSpin = Reals.generateResult();
        /*
         *  Writing in reverse
         **/
        for (int reals = resultOfSpin.length - 1; 0 <= reals; reals--) {
            for (int symbol = resultOfSpin[reals].length - 1; 0 <= symbol; symbol--) {
                buff.writeString(resultOfSpin[reals][symbol]);
            }
        }

        ServerPlayNetworking.send(player, Entrypoint.PACKET_SLOTMACHINE_SPIN_RESULT, buff);
    }

}
