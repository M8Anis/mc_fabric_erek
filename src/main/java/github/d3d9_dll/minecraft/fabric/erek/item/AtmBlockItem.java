package github.d3d9_dll.minecraft.fabric.erek.item;

import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class AtmBlockItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "atm_block");

    public AtmBlockItem(AtmBlock block, Settings settings) {
        super(block, settings);
    }

}
