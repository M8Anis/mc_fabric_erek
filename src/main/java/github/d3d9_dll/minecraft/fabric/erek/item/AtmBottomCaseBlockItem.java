package github.d3d9_dll.minecraft.fabric.erek.item;

import github.d3d9_dll.minecraft.fabric.erek.block.AtmBottomCaseBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class AtmBottomCaseBlockItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "atm_bottom_case");

    public AtmBottomCaseBlockItem(AtmBottomCaseBlock block, Settings settings) {
        super(block, settings);
    }

}
