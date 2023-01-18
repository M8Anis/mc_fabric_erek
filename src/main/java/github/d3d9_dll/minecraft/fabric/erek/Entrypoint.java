package github.d3d9_dll.minecraft.fabric.erek;

import github.d3d9_dll.minecraft.fabric.erek.block.*;
import github.d3d9_dll.minecraft.fabric.erek.item.*;
import github.d3d9_dll.minecraft.fabric.erek.util.Logs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;

import java.io.File;

public class Entrypoint implements ModInitializer {

    public final static Logs LOGGER = new Logs(LogManager.getLogger(Logs.LOG_PREFIX));

    public static final Identifier PACKET_SLOTMACHINE_SPIN =
            new Identifier("d3d9_dllerek", "packet_d3d9_dllerek_slotmachine_spin");
    public static final Identifier PACKET_SLOTMACHINE_BALANCE =
            new Identifier("d3d9_dllerek", "packet_d3d9_dllerek_slotmachine_balance");
    public static final Identifier PACKET_VERSION_SYNC =
            new Identifier("d3d9_dllerek", "packet_d3d9_dllerek_version_synchronize");

    public static final ItemGroup CASINO_ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("d3d9_dllerek", "casino"))
            .icon(() -> new ItemStack(SlotMachineBlockItem.ITEM))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(SlotMachineBottomCaseItem.ITEM));
                stacks.add(new ItemStack(SlotMachineBlockItem.ITEM));
                stacks.add(new ItemStack(SlotMachineInfoPanelItem.ITEM));
            })
            .build();

    public static final ItemGroup BANK_ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("d3d9_dllerek", "bank"))
            .icon(() -> new ItemStack(AtmBlockItem.ITEM))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(AtmBlockItem.ITEM));
                stacks.add(new ItemStack(AtmBottomCaseItem.ITEM));
            })
            .build();

    public static final File MOD_DATA_DIRECTORY =
            new File(FabricLoader.getInstance().getConfigDir().toFile(), "d3d9_dllerek");

    @Override
    public void onInitialize() {
        LOGGER.debug("Common-side initialization");

        if (!MOD_DATA_DIRECTORY.exists()) //noinspection ResultOfMethodCallIgnored
            MOD_DATA_DIRECTORY.mkdirs();

        registerItemsAndBlocks();
    }

    private static void registerItemsAndBlocks() {
        LOGGER.debug("Registering casino category");
        SlotMachineBottomCaseItem.register();
        LOGGER.debug("BlockItem \"slotmachine_bottom_case\" registered");
        SlotMachineBottomCaseBlock.register();
        LOGGER.debug("Block \"slotmachine_bottom_case\" registered");

        SlotMachineBlockItem.register();
        LOGGER.debug("BlockItem \"slotmachine_block\" registered");
        SlotMachineBlock.register();
        LOGGER.debug("Block \"slotmachine_block\" registered");

        SlotMachineInfoPanelItem.register();
        LOGGER.debug("BlockItem \"slotmachine_info_panel\" registered");
        SlotMachineInfoPanelBlock.register();
        LOGGER.debug("Block \"slotmachine_info_panel\" registered");


        LOGGER.debug("Registering bank category");
        AtmBlockItem.register();
        LOGGER.debug("BlockItem \"atm_block\" registered");
        AtmBlock.register();
        LOGGER.debug("Block \"atm_block\" registered");
        AtmBottomCaseItem.register();
        LOGGER.debug("BlockItem \"atm_bottom_case\" registered");
        AtmBottomCaseBlock.register();
        LOGGER.debug("Block \"atm_bottom_case\" registered");
    }

}
