package github.d3d9_dll.minecraft.fabric.erek.item;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SlotMachineBlockItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_block");
    public static final BlockItem ITEM = new SlotMachineBlockItem(
            SlotMachineBlock.BLOCK,
            new FabricItemSettings().group(Entrypoint.GENERAL_ITEM_GROUP)
    );

    public SlotMachineBlockItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

    public static void register() {
        Registry.register(Registry.ITEM, IDENTIFIER, ITEM);
    }
}
