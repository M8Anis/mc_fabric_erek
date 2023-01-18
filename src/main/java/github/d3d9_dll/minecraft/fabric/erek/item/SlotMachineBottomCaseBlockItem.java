package github.d3d9_dll.minecraft.fabric.erek.item;

import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBottomCaseBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class SlotMachineBottomCaseBlockItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_bottom_case");

    public SlotMachineBottomCaseBlockItem(SlotMachineBottomCaseBlock block, Settings settings) {
        super(block, settings);
    }

}
