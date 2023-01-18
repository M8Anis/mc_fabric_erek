package github.d3d9_dll.minecraft.fabric.erek.client.item;

import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBlockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;

public class ClientSlotMachineBlockItem extends SlotMachineBlockItem {

    public static final BlockItem ITEM = new ClientSlotMachineBlockItem(
            SlotMachineBlock.BLOCK,
            new FabricItemSettings().group(ClientEntrypoint.CASINO_ITEM_GROUP)
    );

    public ClientSlotMachineBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    public static void register() {
        Registry.register(Registry.ITEM, IDENTIFIER, ITEM);
    }

}
