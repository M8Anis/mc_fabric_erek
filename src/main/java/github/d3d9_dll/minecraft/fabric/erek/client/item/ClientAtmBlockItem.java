package github.d3d9_dll.minecraft.fabric.erek.client.item;

import github.d3d9_dll.minecraft.fabric.erek.item.AtmBlockItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;

@Environment(EnvType.CLIENT)
public class ClientAtmBlockItem extends AtmBlockItem {

    public ClientAtmBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

}
