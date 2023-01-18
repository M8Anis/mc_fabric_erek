package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.SlotmachineScreen;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBlockItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.text.TranslatableText;
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

public class SlotMachineBlock extends HorizontalFacingBlock {

    public static final Identifier IDENTIFIER = SlotMachineBlockItem.IDENTIFIER;
    public static final Block BLOCK = new SlotMachineBlock(
            FabricBlockSettings.of(Material.METAL)
                    .strength(6f)
                    .breakByTool(FabricToolTags.PICKAXES, 2)
    );

    private final VoxelShape SHAPE_NORTH = VoxelShapes.union(
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
    private final VoxelShape SHAPE_EAST = VoxelShapes.union(
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
    private final VoxelShape SHAPE_SOUTH = VoxelShapes.union(
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
    private final VoxelShape SHAPE_WEST = VoxelShapes.union(
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
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
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

    @SuppressWarnings("deprecation")
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                            BlockHitResult hit) {
        if (!world.isClient || hand != Hand.OFF_HAND) return false;

        if (!checkConstruct(pos, world)) {
            player.sendMessage(new TranslatableText("chat.d3d9_dllerek.slotmachine.construct_not_full"));
            return false;
        } else {
            MinecraftClient.getInstance().openScreen(new SlotmachineScreen(pos, (ClientWorld) world));
            return true;
        }
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
