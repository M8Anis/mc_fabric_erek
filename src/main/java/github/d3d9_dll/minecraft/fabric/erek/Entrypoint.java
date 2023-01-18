package github.d3d9_dll.minecraft.fabric.erek;

import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBottomCaseBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineInfoPanelBlock;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBlockItem;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBottomCaseItem;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineInfoPanelItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Entrypoint implements ModInitializer {

    public final static Logger LOGGER = LogManager.getLogger("d3d9_dll EREK");

    public static final Identifier PACKET_SLOTMACHINE_SPIN =
            new Identifier("d3d9_dllerek", "packet_d3d9_dllerek_slotmachine_spin");
    public static final Identifier PACKET_SLOTMACHINE_BALANCE =
            new Identifier("d3d9_dllerek", "packet_d3d9_dllerek_slotmachine_balance");
    public static final Identifier PACKET_VERSION_SYNC =
            new Identifier("d3d9_dllerek", "packet_d3d9_dllerek_version_synchronize");

    public static final ItemGroup GENERAL_ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("d3d9_dllerek", "casino"))
            .icon(() -> new ItemStack(SlotMachineBlockItem.ITEM))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(SlotMachineBottomCaseItem.ITEM));
                stacks.add(new ItemStack(SlotMachineBlockItem.ITEM));
                stacks.add(new ItemStack(SlotMachineInfoPanelItem.ITEM));
            })
            .build();

    @Override
    public void onInitialize() {
        LOGGER.info("Common-side initialization");

        registerItemsAndBlocks();
    }

    private static void registerItemsAndBlocks() {
        SlotMachineBottomCaseItem.register();
        LOGGER.info("BlockItem \"slotmachine_bottom_case\" registered");
        SlotMachineBottomCaseBlock.register();
        LOGGER.info("Block \"slotmachine_bottom_case\" registered");

        SlotMachineBlockItem.register();
        LOGGER.info("BlockItem \"slotmachine_block\" registered");
        SlotMachineBlock.register();
        LOGGER.info("Block \"slotmachine_block\" registered");

        SlotMachineInfoPanelItem.register();
        LOGGER.info("BlockItem \"slotmachine_info_panel\" registered");
        SlotMachineInfoPanelBlock.register();
        LOGGER.info("Block \"slotmachine_info_panel\" registered");
    }

}
