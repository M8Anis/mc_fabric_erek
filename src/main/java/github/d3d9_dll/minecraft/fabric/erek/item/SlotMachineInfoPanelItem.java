package github.d3d9_dll.minecraft.fabric.erek.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class SlotMachineInfoPanelItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_info_panel");

    public SlotMachineInfoPanelItem(Block block, Settings settings) {
        super(block, settings);
    }

}
