package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class SlotMachineBlock extends github.d3d9_dll.minecraft.fabric.erek.block.Block {

    public static final Identifier IDENTIFIER = SlotMachineBlockItem.IDENTIFIER;

    private static final VoxelShape SHAPE_NORTH = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 16.0D),
            // Right plate
            Block.createCuboidShape(0.0D, 0.0D, 6.0D, 1.0D, 14.0D, 7.0D),
            Block.createCuboidShape(0.0D, 0.0D, 5.0D, 1.0D, 12.0D, 6.0D),
            Block.createCuboidShape(0.0D, 0.0D, 4.0D, 1.0D, 10.0D, 5.0D),
            Block.createCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 8.0D, 4.0D),
            Block.createCuboidShape(0.0D, 0.0D, 2.0D, 1.0D, 6.0D, 3.0D),
            Block.createCuboidShape(0.0D, 0.0D, 1.0D, 1.0D, 4.0D, 2.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D),
            // Left plate
            Block.createCuboidShape(15.0D, 0.0D, 6.0D, 16.0D, 14.0D, 7.0D),
            Block.createCuboidShape(15.0D, 0.0D, 5.0D, 16.0D, 12.0D, 6.0D),
            Block.createCuboidShape(15.0D, 0.0D, 4.0D, 16.0D, 10.0D, 5.0D),
            Block.createCuboidShape(15.0D, 0.0D, 3.0D, 16.0D, 8.0D, 4.0D),
            Block.createCuboidShape(15.0D, 0.0D, 2.0D, 16.0D, 6.0D, 3.0D),
            Block.createCuboidShape(15.0D, 0.0D, 1.0D, 16.0D, 4.0D, 2.0D),
            Block.createCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 2.0D, 1.0D),
            // Display header (bottom)
            Block.createCuboidShape(1.0D, 13.0D, 6.0D, 15.0D, 14.0D, 7.0D),
            // Display backplate
            Block.createCuboidShape(1.0D, 0.0D, 9.0D, 15.0D, 11.0D, 10.0D),
            Block.createCuboidShape(1.0D, 0.0D, 10.0D, 15.0D, 9.0D, 11.0D),
            Block.createCuboidShape(1.0D, 0.0D, 11.0D, 15.0D, 6.0D, 12.0D),
            Block.createCuboidShape(1.0D, 0.0D, 12.0D, 15.0D, 4.0D, 13.0D),
            Block.createCuboidShape(1.0D, 0.0D, 13.0D, 15.0D, 1.0D, 14.0D)
    );
    private static final VoxelShape SHAPE_EAST = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 9.0D, 16.0D, 16.0D),
            // Right plate
            Block.createCuboidShape(9.0D, 0.0D, 0.0D, 10.0D, 14.0D, 1.0D),
            Block.createCuboidShape(10.0D, 0.0D, 0.0D, 11.0D, 12.0D, 1.0D),
            Block.createCuboidShape(11.0D, 0.0D, 0.0D, 12.0D, 10.0D, 1.0D),
            Block.createCuboidShape(12.0D, 0.0D, 0.0D, 13.0D, 8.0D, 1.0D),
            Block.createCuboidShape(13.0D, 0.0D, 0.0D, 14.0D, 6.0D, 1.0D),
            Block.createCuboidShape(14.0D, 0.0D, 0.0D, 15.0D, 4.0D, 1.0D),
            Block.createCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 2.0D, 1.0D),
            // Left plate
            Block.createCuboidShape(9.0D, 0.0D, 15.0D, 10.0D, 14.0D, 16.0D),
            Block.createCuboidShape(10.0D, 0.0D, 15.0D, 11.0D, 12.0D, 16.0D),
            Block.createCuboidShape(11.0D, 0.0D, 15.0D, 12.0D, 10.0D, 16.0D),
            Block.createCuboidShape(12.0D, 0.0D, 15.0D, 13.0D, 8.0D, 16.0D),
            Block.createCuboidShape(13.0D, 0.0D, 15.0D, 14.0D, 6.0D, 16.0D),
            Block.createCuboidShape(14.0D, 0.0D, 15.0D, 15.0D, 4.0D, 16.0D),
            Block.createCuboidShape(15.0D, 0.0D, 15.0D, 16.0D, 2.0D, 16.0D),
            // Display header (bottom)
            Block.createCuboidShape(9.0D, 13.0D, 1.0D, 10.0D, 14.0D, 15.0D),
            // Display backplate
            Block.createCuboidShape(9.0D, 0.0D, 1.0D, 10.0D, 11.0D, 15.0D),
            Block.createCuboidShape(10.0D, 0.0D, 1.0D, 11.0D, 9.0D, 15.0D),
            Block.createCuboidShape(11.0D, 0.0D, 1.0D, 12.0D, 6.0D, 15.0D),
            Block.createCuboidShape(12.0D, 0.0D, 1.0D, 13.0D, 4.0D, 15.0D),
            Block.createCuboidShape(13.0D, 0.0D, 1.0D, 14.0D, 1.0D, 15.0D)
    );
    private static final VoxelShape SHAPE_SOUTH = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 9.0D),
            // Right plate
            Block.createCuboidShape(15.0D, 0.0D, 9.0D, 16.0D, 14.0D, 10.0D),
            Block.createCuboidShape(15.0D, 0.0D, 10.0D, 16.0D, 12.0D, 11.0D),
            Block.createCuboidShape(15.0D, 0.0D, 11.0D, 16.0D, 10.0D, 12.0D),
            Block.createCuboidShape(15.0D, 0.0D, 12.0D, 16.0D, 8.0D, 13.0D),
            Block.createCuboidShape(15.0D, 0.0D, 13.0D, 16.0D, 6.0D, 14.0D),
            Block.createCuboidShape(15.0D, 0.0D, 14.0D, 16.0D, 4.0D, 15.0D),
            Block.createCuboidShape(15.0D, 0.0D, 15.0D, 16.0D, 2.0D, 16.0D),
            // Left plate
            Block.createCuboidShape(0.0D, 0.0D, 9.0D, 1.0D, 14.0D, 10.0D),
            Block.createCuboidShape(0.0D, 0.0D, 10.0D, 1.0D, 12.0D, 11.0D),
            Block.createCuboidShape(0.0D, 0.0D, 11.0D, 1.0D, 10.0D, 12.0D),
            Block.createCuboidShape(0.0D, 0.0D, 12.0D, 1.0D, 8.0D, 13.0D),
            Block.createCuboidShape(0.0D, 0.0D, 13.0D, 1.0D, 6.0D, 14.0D),
            Block.createCuboidShape(0.0D, 0.0D, 14.0D, 1.0D, 4.0D, 15.0D),
            Block.createCuboidShape(0.0D, 0.0D, 15.0D, 1.0D, 2.0D, 16.0D),
            // Display header (bottom)
            Block.createCuboidShape(1.0D, 13.0D, 9.0D, 15.0D, 14.0D, 10.0D),
            // Display backplate
            Block.createCuboidShape(1.0D, 0.0D, 9.0D, 15.0D, 11.0D, 10.0D),
            Block.createCuboidShape(1.0D, 0.0D, 10.0D, 15.0D, 9.0D, 11.0D),
            Block.createCuboidShape(1.0D, 0.0D, 11.0D, 15.0D, 6.0D, 12.0D),
            Block.createCuboidShape(1.0D, 0.0D, 12.0D, 15.0D, 4.0D, 13.0D),
            Block.createCuboidShape(1.0D, 0.0D, 13.0D, 15.0D, 1.0D, 14.0D)
    );
    private static final VoxelShape SHAPE_WEST = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(7.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            // Right plate
            Block.createCuboidShape(6.0D, 0.0D, 15.0D, 7.0D, 14.0D, 16.0D),
            Block.createCuboidShape(5.0D, 0.0D, 15.0D, 6.0D, 12.0D, 16.0D),
            Block.createCuboidShape(4.0D, 0.0D, 15.0D, 5.0D, 10.0D, 16.0D),
            Block.createCuboidShape(3.0D, 0.0D, 15.0D, 4.0D, 8.0D, 16.0D),
            Block.createCuboidShape(2.0D, 0.0D, 15.0D, 3.0D, 6.0D, 16.0D),
            Block.createCuboidShape(1.0D, 0.0D, 15.0D, 2.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 15.0D, 1.0D, 2.0D, 16.0D),
            // Left plate
            Block.createCuboidShape(6.0D, 0.0D, 0.0D, 7.0D, 14.0D, 1.0D),
            Block.createCuboidShape(5.0D, 0.0D, 0.0D, 6.0D, 12.0D, 1.0D),
            Block.createCuboidShape(4.0D, 0.0D, 0.0D, 5.0D, 10.0D, 1.0D),
            Block.createCuboidShape(3.0D, 0.0D, 0.0D, 4.0D, 8.0D, 1.0D),
            Block.createCuboidShape(2.0D, 0.0D, 0.0D, 3.0D, 6.0D, 1.0D),
            Block.createCuboidShape(1.0D, 0.0D, 0.0D, 2.0D, 4.0D, 1.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D),
            // Display header (bottom)
            Block.createCuboidShape(6.0D, 13.0D, 1.0D, 7.0D, 14.0D, 15.0D),
            // Display backplate
            Block.createCuboidShape(6.0D, 0.0D, 1.0D, 7.0D, 11.0D, 15.0D),
            Block.createCuboidShape(5.0D, 0.0D, 1.0D, 6.0D, 9.0D, 15.0D),
            Block.createCuboidShape(4.0D, 0.0D, 1.0D, 5.0D, 6.0D, 15.0D),
            Block.createCuboidShape(3.0D, 0.0D, 1.0D, 4.0D, 4.0D, 15.0D),
            Block.createCuboidShape(2.0D, 0.0D, 1.0D, 3.0D, 1.0D, 15.0D)
    );

    public SlotMachineBlock(Block.Settings settings) {
        super(settings, SHAPE_NORTH, SHAPE_EAST, SHAPE_SOUTH, SHAPE_WEST);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkConstruct(BlockPos blockPos, World world) {
        if (world == null || blockPos == null) return false;

        BlockState slotmachine = world.getBlockState(blockPos);
        BlockState slotmachine_info_panel = world.getBlockState(blockPos.add(0, 1, 0));
        BlockState slotmachine_bottom_case = world.getBlockState(blockPos.add(0, -1, 0));

        return (slotmachine.getBlock() instanceof SlotMachineBlock &&
                slotmachine_info_panel.getBlock() instanceof SlotMachineInfoPanelBlock &&
                slotmachine_bottom_case.getBlock() instanceof SlotMachineBottomCaseBlock) &&
                (slotmachine.get(FACING).equals(slotmachine_info_panel.get(FACING)) &&
                        slotmachine.get(FACING).equals(slotmachine_bottom_case.get(FACING)));
    }

}
