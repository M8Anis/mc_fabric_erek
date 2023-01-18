package github.d3d9_dll.minecraft.fabric.erek.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SlotMachineInfoPanelBlock extends Block {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_info_panel");
    public static final Block BLOCK = new SlotMachineInfoPanelBlock(
            FabricBlockSettings.of(Material.METAL)
                    .strength(6f)
                    .breakByTool(FabricToolTags.PICKAXES, 2)
    );
    private final VoxelShape SHAPE =
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 12.0D, 16.0D);

    public SlotMachineInfoPanelBlock(Block.Settings settings) {
        super(settings);
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

    public static void register() {
        Registry.register(Registry.BLOCK, IDENTIFIER, BLOCK);
    }

}
