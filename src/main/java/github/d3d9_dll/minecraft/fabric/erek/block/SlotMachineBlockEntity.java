package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBlockItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SlotMachineBlockEntity extends BlockEntity {

    public static final Identifier IDENTIFIER = SlotMachineBlockItem.IDENTIFIER;

    public static final BlockEntityType<SlotMachineBlockEntity> BLOCK_ENTITY =
            BlockEntityType.Builder.create(
                    SlotMachineBlockEntity::new,
                    SlotMachineBlock.BLOCK
            ).build(null);

    public SlotMachineBlockEntity() {
        super(BLOCK_ENTITY);
    }

    public static void register() {
        Registry.register(Registry.BLOCK_ENTITY, IDENTIFIER, BLOCK_ENTITY);
    }

}
