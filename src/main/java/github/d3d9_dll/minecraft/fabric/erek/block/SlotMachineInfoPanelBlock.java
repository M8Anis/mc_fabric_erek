package github.d3d9_dll.minecraft.fabric.erek.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SlotMachineInfoPanelBlock extends HorizontalFacingBlock {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_info_panel");
    public static final Block BLOCK = new SlotMachineInfoPanelBlock(
            FabricBlockSettings.of(Material.METAL)
                    .strength(6f)
                    .breakByTool(FabricToolTags.PICKAXES, 2)
    );

    private final VoxelShape SHAPE_NORTH =
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 12.0D, 16.0D);
    private final VoxelShape SHAPE_EAST =
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 9.0D, 12.0D, 16.0D);
    private final VoxelShape SHAPE_SOUTH =
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 9.0D);
    private final VoxelShape SHAPE_WEST =
            Block.createCuboidShape(16.0D, 0.0D, 0.0D, 7.0D, 12.0D, 16.0D);

    public SlotMachineInfoPanelBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
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

    private VoxelShape getShape(BlockState state) {
        Direction dir = state.get(FACING);
        switch (dir) {
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
