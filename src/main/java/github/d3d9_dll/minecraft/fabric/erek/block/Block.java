package github.d3d9_dll.minecraft.fabric.erek.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class Block extends HorizontalFacingBlock {

    private VoxelShape northShape = VoxelShapes.union(
            net.minecraft.block.Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    );
    private VoxelShape eastShape = VoxelShapes.union(
            net.minecraft.block.Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    );
    private VoxelShape southShape = VoxelShapes.union(
            net.minecraft.block.Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    );
    private VoxelShape westShape = VoxelShapes.union(
            net.minecraft.block.Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    );

    protected Block(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    public Block(Settings settings, VoxelShape northShape, VoxelShape eastShape, VoxelShape southShape,
                 VoxelShape westShape) {
        this(settings);
        this.northShape = northShape;
        this.eastShape = eastShape;
        this.southShape = southShape;
        this.westShape = westShape;
    }

    @SuppressWarnings("deprecation")
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                            BlockHitResult hit) {
        return true;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return getShape(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return getShape(state);
    }

    private VoxelShape getShape(BlockState state) {
        Direction direction = state.get(FACING);
        switch (direction) {
            default:
            case NORTH:
                return northShape;
            case SOUTH:
                return southShape;
            case EAST:
                return eastShape;
            case WEST:
                return westShape;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
