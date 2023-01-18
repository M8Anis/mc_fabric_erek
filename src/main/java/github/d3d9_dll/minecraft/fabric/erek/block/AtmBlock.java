package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.item.AtmBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class AtmBlock extends github.d3d9_dll.minecraft.fabric.erek.block.Block {

    public static final Identifier IDENTIFIER = AtmBlockItem.IDENTIFIER;

    private static final VoxelShape SHAPE_NORTH = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(2.0D, 0.0D, 9.0D, 14.0D, 14.0D, 16.0D),
            // Left plate
            Block.createCuboidShape(13.0D, 0.0D, 2.0D, 14.0D, 4.0D, 3.0D),
            Block.createCuboidShape(13.0D, 0.0D, 3.0D, 14.0D, 7.0D, 4.0D),
            Block.createCuboidShape(13.0D, 0.0D, 4.0D, 14.0D, 9.0D, 5.0D),
            Block.createCuboidShape(13.0D, 0.0D, 5.0D, 14.0D, 10.0D, 7.0D),
            Block.createCuboidShape(13.0D, 0.0D, 7.0D, 14.0D, 14.0D, 9.0D),
            // Right plate
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 3.0D, 4.0D, 3.0D),
            Block.createCuboidShape(2.0D, 0.0D, 3.0D, 3.0D, 7.0D, 4.0D),
            Block.createCuboidShape(2.0D, 0.0D, 4.0D, 3.0D, 9.0D, 5.0D),
            Block.createCuboidShape(2.0D, 0.0D, 5.0D, 3.0D, 10.0D, 7.0D),
            Block.createCuboidShape(2.0D, 0.0D, 7.0D, 3.0D, 14.0D, 9.0D),
            // Upper plate
            Block.createCuboidShape(3.0D, 12.0D, 7.0D, 13.0D, 14.0D, 9.0D),
            // Keypad
            Block.createCuboidShape(6.0D, 0.0D, 4.0D, 10.0D, 1.0D, 6.0D)
    );
    public AtmBlock(Settings settings) {
        super(settings, SHAPE_NORTH);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkConstruct(BlockPos blockPos, World world) {
        if (world == null || blockPos == null) return false;

        BlockState atm_block = world.getBlockState(blockPos);
        BlockState atm_bottom_case = world.getBlockState(blockPos.add(0, -1, 0));

        return atm_block.getBlock() instanceof AtmBlock &&
                atm_bottom_case.getBlock() instanceof AtmBottomCaseBlock &&
                atm_block.get(FACING).equals(atm_bottom_case.get(FACING));
    }

}
