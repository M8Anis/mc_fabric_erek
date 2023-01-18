package github.d3d9_dll.minecraft.fabric.erek.client.block;

import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.ATMScreen;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientAtmBlock extends AtmBlock {

    public ClientAtmBlock(Settings settings) {
        super(settings);
    }

    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                            BlockHitResult hit) {
        if (!world.isClient || hand != Hand.OFF_HAND) return false;

        if (!checkConstruct(pos, world)) {
            player.sendMessage(new TranslatableText("chat.d3d9_dllerek.atm.construct_not_full"));
            return false;
        } else {
            MinecraftClient.getInstance().openScreen(new ATMScreen(pos, (ClientWorld) world));
            return true;
        }
    }

}
