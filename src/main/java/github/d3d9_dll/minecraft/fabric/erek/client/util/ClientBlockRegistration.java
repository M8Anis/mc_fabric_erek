package github.d3d9_dll.minecraft.fabric.erek.client.util;

import github.d3d9_dll.minecraft.fabric.erek.block.AtmBottomCaseBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.ExchangeMachineStandBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBottomCaseBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineInfoPanelBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.client.block.ClientAtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.block.ClientExchangeMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.block.ClientSlotMachineBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class ClientBlockRegistration {

    public static final FabricBlockSettings DEFAULT_BLOCK_SETTINGS = FabricBlockSettings.of(Material.METAL)
            .strength(6f)
            .breakByTool(FabricToolTags.PICKAXES, 2);

    public final SlotMachineBottomCaseBlock slotMachineBottomCaseBlock;
    public final ClientSlotMachineBlock slotMachineBlock;
    public final SlotMachineInfoPanelBlock slotMachineInfoPanelBlock;

    public final ClientExchangeMachineBlock exchangeMachineBlock;
    public final ExchangeMachineStandBlock exchangeMachineStandBlock;

    public final ClientAtmBlock atmBlock;
    public final AtmBottomCaseBlock atmBottomCaseBlock;

    private boolean registered = false;

    public ClientBlockRegistration() {
        slotMachineBottomCaseBlock = new SlotMachineBottomCaseBlock(DEFAULT_BLOCK_SETTINGS);
        slotMachineBlock = new ClientSlotMachineBlock(DEFAULT_BLOCK_SETTINGS);
        slotMachineInfoPanelBlock = new SlotMachineInfoPanelBlock(DEFAULT_BLOCK_SETTINGS);

        exchangeMachineBlock = new ClientExchangeMachineBlock(DEFAULT_BLOCK_SETTINGS);
        exchangeMachineStandBlock = new ExchangeMachineStandBlock(DEFAULT_BLOCK_SETTINGS);

        atmBlock = new ClientAtmBlock(DEFAULT_BLOCK_SETTINGS);
        atmBottomCaseBlock = new AtmBottomCaseBlock(DEFAULT_BLOCK_SETTINGS);
    }

    public ClientBlockRegistration registerAll() {
        if (registered) return this;

        ClientEntrypoint.LOGGER.debug("Block registering");

        Registry.register(Registry.BLOCK, SlotMachineBottomCaseBlock.IDENTIFIER, slotMachineBottomCaseBlock);
        ClientEntrypoint.LOGGER.debug("Block \"slotmachine_bottom_case\" registered");

        Registry.register(Registry.BLOCK, ClientSlotMachineBlock.IDENTIFIER, slotMachineBlock);
        ClientEntrypoint.LOGGER.debug("Block \"slotmachine_block\" registered");

        Registry.register(Registry.BLOCK, SlotMachineInfoPanelBlock.IDENTIFIER, slotMachineInfoPanelBlock);
        ClientEntrypoint.LOGGER.debug("Block \"slotmachine_info_panel\" registered");

        Registry.register(Registry.BLOCK, ClientAtmBlock.IDENTIFIER, atmBlock);
        ClientEntrypoint.LOGGER.debug("Block \"atm_block\" registered");

        Registry.register(Registry.BLOCK, AtmBottomCaseBlock.IDENTIFIER, atmBottomCaseBlock);
        ClientEntrypoint.LOGGER.debug("Block \"atm_bottom_case\" registered");

        Registry.register(Registry.BLOCK, ClientExchangeMachineBlock.IDENTIFIER, exchangeMachineBlock);
        ClientEntrypoint.LOGGER.debug("Block \"exchange_machine_block\" registered");

        Registry.register(Registry.BLOCK, ExchangeMachineStandBlock.IDENTIFIER, exchangeMachineStandBlock);
        ClientEntrypoint.LOGGER.debug("Block \"exchange_machine_stand\" registered");

        registered = true;

        return this;
    }

}
