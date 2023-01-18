package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.item.AtmBottomCaseItem;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class AtmBottomCaseBlock extends github.d3d9_dll.minecraft.fabric.erek.block.Block {

    public static final Identifier IDENTIFIER = AtmBottomCaseItem.IDENTIFIER;

    private static final VoxelShape SHAPE_NORTH = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 16.0D),

            Block.createCuboidShape(4.0D, 12.0D, 1.0D, 12.0D, 14.0D, 2.0D)
    );
    private static final VoxelShape SHAPE_EAST = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),

            Block.createCuboidShape(14.0D, 12.0D, 4.0D, 15.0D, 14.0D, 12.0D)
    );
    private static final VoxelShape SHAPE_SOUTH = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 14.0D),

            Block.createCuboidShape(4.0D, 12.0D, 14.0D, 12.0D, 14.0D, 15.0D)
    );
    private static final VoxelShape SHAPE_WEST = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D),

            Block.createCuboidShape(1.0D, 12.0D, 4.0D, 2.0D, 14.0D, 12.0D)
    );

    public AtmBottomCaseBlock(Settings settings) {
        super(settings, SHAPE_NORTH, SHAPE_EAST, SHAPE_SOUTH, SHAPE_WEST);
    }

}
