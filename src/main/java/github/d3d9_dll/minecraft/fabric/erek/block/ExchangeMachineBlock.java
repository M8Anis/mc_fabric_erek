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
            // Bottom plate
            Block.createCuboidShape(4.0D, 0.0D, 3.0D, 12.0D, 2.0D, 8.0D),
            // Right plate
            Block.createCuboidShape(4.0D, 2.0D, 3.0D, 5.0D, 3.0D, 4.0D),
            Block.createCuboidShape(4.0D, 2.0D, 4.0D, 5.0D, 4.0D, 5.0D),
            Block.createCuboidShape(4.0D, 2.0D, 5.0D, 5.0D, 5.0D, 6.0D),
            Block.createCuboidShape(4.0D, 2.0D, 6.0D, 5.0D, 6.0D, 7.0D),
            Block.createCuboidShape(4.0D, 2.0D, 7.0D, 5.0D, 7.0D, 8.0D),
            // Left plate
            Block.createCuboidShape(11.0D, 2.0D, 3.0D, 12.0D, 3.0D, 4.0D),
            Block.createCuboidShape(11.0D, 2.0D, 4.0D, 12.0D, 4.0D, 5.0D),
            Block.createCuboidShape(11.0D, 2.0D, 5.0D, 12.0D, 5.0D, 6.0D),
            Block.createCuboidShape(11.0D, 2.0D, 6.0D, 12.0D, 6.0D, 7.0D),
            Block.createCuboidShape(11.0D, 2.0D, 7.0D, 12.0D, 7.0D, 8.0D),
            // Upper plate
            Block.createCuboidShape(4.0D, 6.0D, 8.0D, 12.0D, 8.0D, 11.0D),
            // Backplate
            Block.createCuboidShape(5.0D, 5.0D, 9.0D, 11.0D, 6.0D, 11.0D),
            Block.createCuboidShape(5.0D, 4.0D, 8.0D, 11.0D, 5.0D, 10.0D),
            Block.createCuboidShape(5.0D, 3.0D, 7.0D, 11.0D, 4.0D, 9.0D),
            Block.createCuboidShape(5.0D, 2.0D, 6.0D, 11.0D, 3.0D, 8.0D),
            // Right plate 2
            Block.createCuboidShape(4.0D, 5.0D, 8.0D, 5.0D, 6.0D, 11.0D),
            Block.createCuboidShape(4.0D, 4.0D, 8.0D, 5.0D, 5.0D, 10.0D),
            Block.createCuboidShape(4.0D, 3.0D, 8.0D, 5.0D, 4.0D, 9.0D),
            // Left plate 2
            Block.createCuboidShape(11.0D, 5.0D, 8.0D, 12.0D, 6.0D, 11.0D),
            Block.createCuboidShape(11.0D, 4.0D, 8.0D, 12.0D, 5.0D, 10.0D),
            Block.createCuboidShape(11.0D, 3.0D, 8.0D, 12.0D, 4.0D, 9.0D)
    ).simplify();

    public ExchangeMachineBlock(Settings settings) {
        super(settings, SHAPE_NORTH);
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
