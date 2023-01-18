package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineInfoPanelBlockItem;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;

public class SlotMachineInfoPanelBlock extends github.d3d9_dll.minecraft.fabric.erek.block.Block {

    public static final Identifier IDENTIFIER = SlotMachineInfoPanelBlockItem.IDENTIFIER;

    private static final VoxelShape SHAPE_NORTH =
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 12.0D, 16.0D);

    public SlotMachineInfoPanelBlock(Block.Settings settings) {
        super(settings, SHAPE_NORTH);
    }

}
