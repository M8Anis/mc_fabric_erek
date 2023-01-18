package github.d3d9_dll.minecraft.fabric.erek.item;

import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineInfoPanelBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class SlotMachineInfoPanelBlockItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_info_panel");

    public SlotMachineInfoPanelBlockItem(SlotMachineInfoPanelBlock block, Settings settings) {
        super(block, settings);
    }

}
