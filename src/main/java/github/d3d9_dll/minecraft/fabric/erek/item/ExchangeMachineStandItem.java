package github.d3d9_dll.minecraft.fabric.erek.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class ExchangeMachineStandItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "exchange_machine_stand");

    public ExchangeMachineStandItem(Block block, Settings settings) {
        super(block, settings);
    }

}
