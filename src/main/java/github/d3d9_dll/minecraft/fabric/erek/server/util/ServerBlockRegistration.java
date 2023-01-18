package github.d3d9_dll.minecraft.fabric.erek.server.util;

import github.d3d9_dll.minecraft.fabric.erek.block.*;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.SERVER)
public class ServerBlockRegistration {

    public static final FabricBlockSettings DEFAULT_BLOCK_SETTINGS = FabricBlockSettings.of(Material.METAL)
            .strength(6f)
            .breakByTool(FabricToolTags.PICKAXES, 2);

    public final SlotMachineBottomCaseBlock slotMachineBottomCaseBlock;
    public final SlotMachineBlock slotMachineBlock;
    public final SlotMachineInfoPanelBlock slotMachineInfoPanelBlock;

    public final ExchangeMachineBlock exchangeMachineBlock;
    public final ExchangeMachineStandBlock exchangeMachineStandBlock;

    public final AtmBlock atmBlock;
    public final AtmBottomCaseBlock atmBottomCaseBlock;

    private boolean registered = false;

    public ServerBlockRegistration() {
        slotMachineBottomCaseBlock = new SlotMachineBottomCaseBlock(DEFAULT_BLOCK_SETTINGS);
        slotMachineBlock = new SlotMachineBlock(DEFAULT_BLOCK_SETTINGS);
        slotMachineInfoPanelBlock = new SlotMachineInfoPanelBlock(DEFAULT_BLOCK_SETTINGS);

        exchangeMachineBlock = new ExchangeMachineBlock(DEFAULT_BLOCK_SETTINGS);
        exchangeMachineStandBlock = new ExchangeMachineStandBlock(DEFAULT_BLOCK_SETTINGS);

        atmBlock = new AtmBlock(DEFAULT_BLOCK_SETTINGS);
        atmBottomCaseBlock = new AtmBottomCaseBlock(DEFAULT_BLOCK_SETTINGS);
    }

    public void registerAll() throws IllegalStateException {
        if (registered) throw new IllegalStateException("blocks already registered");

        ServerEntrypoint.LOGGER.debug("Block registering");

        Registry.register(Registry.BLOCK, SlotMachineBottomCaseBlock.IDENTIFIER, slotMachineBottomCaseBlock);
        ServerEntrypoint.LOGGER.debug("Block \"slotmachine_bottom_case\" registered");

        Registry.register(Registry.BLOCK, SlotMachineBlock.IDENTIFIER, slotMachineBlock);
        ServerEntrypoint.LOGGER.debug("Block \"slotmachine_block\" registered");

        Registry.register(Registry.BLOCK, SlotMachineInfoPanelBlock.IDENTIFIER, slotMachineInfoPanelBlock);
        ServerEntrypoint.LOGGER.debug("Block \"slotmachine_info_panel\" registered");

        Registry.register(Registry.BLOCK, AtmBlock.IDENTIFIER, atmBlock);
        ServerEntrypoint.LOGGER.debug("Block \"atm_block\" registered");

        Registry.register(Registry.BLOCK, AtmBottomCaseBlock.IDENTIFIER, atmBottomCaseBlock);
        ServerEntrypoint.LOGGER.debug("Block \"atm_bottom_case\" registered");

        Registry.register(Registry.BLOCK, ExchangeMachineBlock.IDENTIFIER, exchangeMachineBlock);
        ServerEntrypoint.LOGGER.debug("Block \"exchange_machine_block\" registered");

        Registry.register(Registry.BLOCK, ExchangeMachineStandBlock.IDENTIFIER, exchangeMachineStandBlock);
        ServerEntrypoint.LOGGER.debug("Block \"exchange_machine_stand\" registered");

        registered = true;
    }

}
