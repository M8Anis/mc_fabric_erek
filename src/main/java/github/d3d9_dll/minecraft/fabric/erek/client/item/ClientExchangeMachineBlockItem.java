package github.d3d9_dll.minecraft.fabric.erek.client.item;

import github.d3d9_dll.minecraft.fabric.erek.item.ExchangeMachineBlockItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;

@Environment(EnvType.CLIENT)
public class ClientExchangeMachineBlockItem extends ExchangeMachineBlockItem {

    public ClientExchangeMachineBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

}
