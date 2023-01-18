package github.d3d9_dll.minecraft.fabric.erek.client.block;

import github.d3d9_dll.minecraft.fabric.erek.block.ExchangeMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.ExchangeMachineScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ClientExchangeMachineBlock extends ExchangeMachineBlock {

    public ClientExchangeMachineBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                            BlockHitResult hit) {
        if (!world.isClient || hand != Hand.OFF_HAND) return false;

        if (!checkConstruct(pos, world)) {
            player.sendMessage(new TranslatableText("chat.d3d9_dllerek.exchange_machine.construct_not_full"));
            return false;
        } else {
            MinecraftClient.getInstance().openScreen(new ExchangeMachineScreen(pos, (ClientWorld) world));
            return true;
        }
    }

}
