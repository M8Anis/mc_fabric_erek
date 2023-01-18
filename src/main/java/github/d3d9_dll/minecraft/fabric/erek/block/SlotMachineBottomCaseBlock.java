package github.d3d9_dll.minecraft.fabric.erek.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SlotMachineBottomCaseBlock extends Block {

    public static final Identifier IDENTIFIER = new Identifier("d3d9_dllerek", "slotmachine_bottom_case");
    public static final Block BLOCK = new Block(
            FabricBlockSettings.of(Material.METAL)
                    .strength(6f)
                    .breakByTool(FabricToolTags.PICKAXES, 2)
    );

    public SlotMachineBottomCaseBlock(Block.Settings settings) {
        super(settings);
    }

    public static void register() {
        Registry.register(Registry.BLOCK, IDENTIFIER, BLOCK);
    }

}
