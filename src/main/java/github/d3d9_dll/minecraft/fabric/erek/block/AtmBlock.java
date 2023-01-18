package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.item.AtmBlockItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class AtmBlock extends HorizontalFacingBlock {

    public static final Identifier IDENTIFIER = AtmBlockItem.IDENTIFIER;
    public static final Block BLOCK = new AtmBlock(
            FabricBlockSettings.of(Material.METAL)
                    .strength(6f)
                    .breakByTool(FabricToolTags.PICKAXES, 2)
    );

    private final VoxelShape SHAPE_NORTH = VoxelShapes.union(
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
    private final VoxelShape SHAPE_EAST = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(0.0D, 0.0D, 2.0D, 7.0D, 14.0D, 14.0D),
            // Left plate
            Block.createCuboidShape(13.0D, 0.0D, 2.0D, 14.0D, 4.0D, 3.0D),
            Block.createCuboidShape(12.0D, 0.0D, 2.0D, 13.0D, 7.0D, 3.0D),
            Block.createCuboidShape(11.0D, 0.0D, 2.0D, 12.0D, 9.0D, 3.0D),
            Block.createCuboidShape(9.0D, 0.0D, 2.0D, 11.0D, 10.0D, 3.0D),
            Block.createCuboidShape(7.0D, 0.0D, 2.0D, 9.0D, 14.0D, 3.0D),
            // Right plate
            Block.createCuboidShape(13.0D, 0.0D, 13.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(12.0D, 0.0D, 13.0D, 13.0D, 7.0D, 14.0D),
            Block.createCuboidShape(11.0D, 0.0D, 13.0D, 12.0D, 9.0D, 14.0D),
            Block.createCuboidShape(9.0D, 0.0D, 13.0D, 11.0D, 10.0D, 14.0D),
            Block.createCuboidShape(7.0D, 0.0D, 13.0D, 9.0D, 14.0D, 14.0D),
            // Upper plate
            Block.createCuboidShape(7.0D, 12.0D, 3.0D, 9.0D, 14.0D, 13.0D),
            // Keypad
            Block.createCuboidShape(9.0D, 0.0D, 6.0D, 12.0D, 1.0D, 10.0D)
    );
    private final VoxelShape SHAPE_SOUTH = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 14.0D, 7.0D),
            // Left plate
            Block.createCuboidShape(2.0D, 0.0D, 13.0D, 3.0D, 4.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 12.0D, 3.0D, 7.0D, 13.0D),
            Block.createCuboidShape(2.0D, 0.0D, 11.0D, 3.0D, 9.0D, 12.0D),
            Block.createCuboidShape(2.0D, 0.0D, 9.0D, 3.0D, 10.0D, 11.0D),
            Block.createCuboidShape(2.0D, 0.0D, 7.0D, 3.0D, 14.0D, 9.0D),
            // Right plate
            Block.createCuboidShape(13.0D, 0.0D, 13.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(13.0D, 0.0D, 12.0D, 14.0D, 7.0D, 13.0D),
            Block.createCuboidShape(13.0D, 0.0D, 11.0D, 14.0D, 9.0D, 12.0D),
            Block.createCuboidShape(13.0D, 0.0D, 9.0D, 14.0D, 10.0D, 11.0D),
            Block.createCuboidShape(13.0D, 0.0D, 7.0D, 14.0D, 14.0D, 9.0D),
            // Upper plate
            Block.createCuboidShape(3.0D, 12.0D, 7.0D, 13.0D, 14.0D, 9.0D),
            // Keypad
            Block.createCuboidShape(6.0D, 0.0D, 9.0D, 10.0D, 1.0D, 12.0D)
    );
    private final VoxelShape SHAPE_WEST = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(9.0D, 0.0D, 2.0D, 16.0D, 14.0D, 14.0D),
            // Left plate
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 3.0D, 4.0D, 3.0D),
            Block.createCuboidShape(3.0D, 0.0D, 2.0D, 4.0D, 7.0D, 3.0D),
            Block.createCuboidShape(4.0D, 0.0D, 2.0D, 5.0D, 9.0D, 3.0D),
            Block.createCuboidShape(5.0D, 0.0D, 2.0D, 7.0D, 10.0D, 3.0D),
            Block.createCuboidShape(7.0D, 0.0D, 2.0D, 9.0D, 14.0D, 3.0D),
            // Right plate
            Block.createCuboidShape(2.0D, 0.0D, 13.0D, 3.0D, 4.0D, 14.0D),
            Block.createCuboidShape(3.0D, 0.0D, 13.0D, 4.0D, 7.0D, 14.0D),
            Block.createCuboidShape(4.0D, 0.0D, 13.0D, 5.0D, 9.0D, 14.0D),
            Block.createCuboidShape(5.0D, 0.0D, 13.0D, 7.0D, 10.0D, 14.0D),
            Block.createCuboidShape(7.0D, 0.0D, 13.0D, 9.0D, 14.0D, 14.0D),
            // Upper plate
            Block.createCuboidShape(7.0D, 12.0D, 3.0D, 9.0D, 14.0D, 13.0D),
            // Keypad
            Block.createCuboidShape(4.0D, 0.0D, 6.0D, 7.0D, 1.0D, 10.0D)
    );

    public AtmBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    @SuppressWarnings("deprecation")
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                            BlockHitResult hit) {
        if (!world.isClient) return false;

        player.sendMessage(new LiteralText("Click!"));
        return true;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
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

    private VoxelShape getShape(BlockState state){
        Direction dir = state.get(FACING);
        switch(dir) {
            default:
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public static void register() {
        Registry.register(Registry.BLOCK, IDENTIFIER, BLOCK);
    }

}
