package github.d3d9_dll.minecraft.fabric.erek.client.item;

import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.client.block.ClientAtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.item.AtmBlockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;

public class ClientAtmBlockItem extends AtmBlockItem {

    public static final BlockItem ITEM = new ClientAtmBlockItem(
            ClientAtmBlock.BLOCK,
            new FabricItemSettings().group(ClientEntrypoint.BANK_ITEM_GROUP)
    );

    public ClientAtmBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    public static void register() {
        Registry.register(Registry.ITEM, IDENTIFIER, ITEM);
    }

}
