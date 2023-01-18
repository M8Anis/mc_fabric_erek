package github.d3d9_dll.minecraft.fabric.erek.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class SlotMachineInfoPanelBlockItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_info_panel_block");

    public SlotMachineInfoPanelBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

}
