package github.d3d9_dll.minecraft.fabric.erek.item;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.AtmBottomCaseBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AtmBottomCaseItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "atm_bottom_case");
    public static final BlockItem ITEM = new AtmBottomCaseItem(
            AtmBottomCaseBlock.BLOCK,
            new FabricItemSettings().group(Entrypoint.BANK_ITEM_GROUP)
    );

    public AtmBottomCaseItem(Block block, Settings settings) {
        super(block, settings);
    }

    public static void register() {
        Registry.register(Registry.ITEM, IDENTIFIER, ITEM);
    }
}
