package github.d3d9_dll.minecraft.fabric.erek.item;

import github.d3d9_dll.minecraft.fabric.erek.block.ExchangeMachineStandBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class ExchangeMachineStandBlockItem extends BlockItem {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "exchange_machine_stand");

    public ExchangeMachineStandBlockItem(ExchangeMachineStandBlock block, Settings settings) {
        super(block, settings);
    }

}
