package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBlockItem;
import github.d3d9_dll.minecraft.fabric.erek.server.games.slotmachine.Reals;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SlotMachineBlock extends Block implements BlockEntityProvider {

    public static final Identifier IDENTIFIER = SlotMachineBlockItem.IDENTIFIER;
    public static final Block BLOCK = new SlotMachineBlock(
            FabricBlockSettings.of(Material.METAL)
                    .strength(6f)
                    .breakByTool(FabricToolTags.PICKAXES, 2)
    );
    private final VoxelShape SHAPE = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 16.0D),

            // Right plate
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D),
            Block.createCuboidShape(0.0D, 0.0D, 1.0D, 1.0D, 4.0D, 2.0D),
            Block.createCuboidShape(0.0D, 0.0D, 2.0D, 1.0D, 6.0D, 3.0D),
            Block.createCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 8.0D, 4.0D),
            Block.createCuboidShape(0.0D, 0.0D, 4.0D, 1.0D, 10.0D, 5.0D),
            Block.createCuboidShape(0.0D, 0.0D, 5.0D, 1.0D, 12.0D, 6.0D),
            Block.createCuboidShape(0.0D, 0.0D, 6.0D, 1.0D, 14.0D, 7.0D),
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 1.0D, 16.0D, 8.0D),

            // Left plate
            Block.createCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 2.0D, 1.0D),
            Block.createCuboidShape(15.0D, 0.0D, 1.0D, 16.0D, 4.0D, 2.0D),
            Block.createCuboidShape(15.0D, 0.0D, 2.0D, 16.0D, 6.0D, 3.0D),
            Block.createCuboidShape(15.0D, 0.0D, 3.0D, 16.0D, 8.0D, 4.0D),
            Block.createCuboidShape(15.0D, 0.0D, 4.0D, 16.0D, 10.0D, 5.0D),
            Block.createCuboidShape(15.0D, 0.0D, 5.0D, 16.0D, 12.0D, 6.0D),
            Block.createCuboidShape(15.0D, 0.0D, 6.0D, 16.0D, 14.0D, 7.0D),
            Block.createCuboidShape(15.0D, 0.0D, 7.0D, 16.0D, 16.0D, 8.0D),

            // Display header (bottom)
            Block.createCuboidShape(1.0D, 13.0D, 6.0D, 15.0D, 14.0D, 7.0D),

            // Display
            Block.createCuboidShape(1.0D, 12.0D, 7.0D, 15.0D, 13.0D, 7.0D),

            // Display backplate
            Block.createCuboidShape(1.0D, 0.0D, 2.0D, 15.0D, 2.0D, 3.0D),
            Block.createCuboidShape(1.0D, 0.0D, 3.0D, 15.0D, 4.0D, 4.0D),
            Block.createCuboidShape(1.0D, 0.0D, 4.0D, 15.0D, 6.0D, 5.0D),
            Block.createCuboidShape(1.0D, 0.0D, 5.0D, 15.0D, 8.0D, 6.0D),
            Block.createCuboidShape(1.0D, 0.0D, 6.0D, 15.0D, 10.0D, 7.0D),
            Block.createCuboidShape(1.0D, 0.0D, 7.0D, 15.0D, 12.0D, 8.0D)
    );

    public SlotMachineBlock(Block.Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if ( hand != Hand.MAIN_HAND ) return false;

        boolean client = world.isClient;
        boolean constructValid = checkConstruct(pos, world);

        if (!constructValid) {
            if (!client)
                player.sendMessage(new TranslatableText("chat.d3d9_dllerek.slotmachine.construct_not_full"));

            return false;
        } else if (!client) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, Entrypoint.PACKET_SLOTMACHINE_SPIN_RESULT, Reals.generateForPacket());
        }

        return true;
    }

    private boolean checkConstruct(BlockPos blockPos, World world) {
        if (world == null || blockPos == null) return false;

        Block slotmachine = world.getBlockState(blockPos).getBlock();
        Block slotmachine_info_panel = world.getBlockState(blockPos.add(0, 1, 0)).getBlock();
        Block slotmachine_bottom_case = world.getBlockState(blockPos.add(0, -1, 0)).getBlock();

        return slotmachine instanceof SlotMachineBlock &&
                slotmachine_info_panel instanceof SlotMachineInfoPanelBlock &&
                slotmachine_bottom_case instanceof SlotMachineBottomCaseBlock;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new SlotMachineBlockEntity();
    }

    public static void register() {
        Registry.register(Registry.BLOCK, IDENTIFIER, BLOCK);
    }

}
