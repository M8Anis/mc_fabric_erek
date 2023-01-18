package github.d3d9_dll.minecraft.fabric.erek.block;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;

public class SlotMachineInfoPanelBlock extends github.d3d9_dll.minecraft.fabric.erek.block.Block {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_info_panel");

    private static final VoxelShape SHAPE_NORTH =
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 12.0D, 16.0D);
    private static final VoxelShape SHAPE_EAST =
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 9.0D, 12.0D, 16.0D);
    private static final VoxelShape SHAPE_SOUTH =
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 9.0D);
    private static final VoxelShape SHAPE_WEST =
            Block.createCuboidShape(16.0D, 0.0D, 0.0D, 7.0D, 12.0D, 16.0D);

    public SlotMachineInfoPanelBlock(Block.Settings settings) {
        super(settings, SHAPE_NORTH, SHAPE_EAST, SHAPE_SOUTH, SHAPE_WEST);
    }

}
