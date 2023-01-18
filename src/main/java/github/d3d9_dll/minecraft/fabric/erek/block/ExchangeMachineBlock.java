package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.item.ExchangeMachineBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class ExchangeMachineBlock extends github.d3d9_dll.minecraft.fabric.erek.block.Block {

    public static final Identifier IDENTIFIER = ExchangeMachineBlockItem.IDENTIFIER;

    private static final VoxelShape SHAPE_NORTH = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    );
    private static final VoxelShape SHAPE_EAST = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    );
    private static final VoxelShape SHAPE_SOUTH = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    );
    private static final VoxelShape SHAPE_WEST = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    );

    public ExchangeMachineBlock(Settings settings) {
        super(settings, SHAPE_NORTH, SHAPE_EAST, SHAPE_SOUTH, SHAPE_WEST);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkConstruct(BlockPos blockPos, World world) {
        if (world == null || blockPos == null) return false;

        BlockState exchange_machine = world.getBlockState(blockPos);
        BlockState exchange_machine_stand = world.getBlockState(blockPos.add(0, -1, 0));

        return exchange_machine.getBlock() instanceof ExchangeMachineBlock &&
                exchange_machine_stand.getBlock() instanceof ExchangeMachineStandBlock &&
                exchange_machine.get(FACING).equals(exchange_machine_stand.get(FACING));
    }

}
