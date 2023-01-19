/*
Copyright © 2022-2023 https://github.com/M8Anis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.github.m8anis.mc_fabric_erek.client.models;

import io.github.m8anis.mc_fabric_erek.block.AtmBottomCaseBlock;
import io.github.m8anis.mc_fabric_erek.block.ExchangeMachineStandBlock;
import io.github.m8anis.mc_fabric_erek.block.SlotMachineBottomCaseBlock;
import io.github.m8anis.mc_fabric_erek.block.SlotMachineInfoPanelBlock;
import io.github.m8anis.mc_fabric_erek.client.ClientEntrypoint;
import io.github.m8anis.mc_fabric_erek.client.block.ClientAtmBlock;
import io.github.m8anis.mc_fabric_erek.client.block.ClientExchangeMachineBlock;
import io.github.m8anis.mc_fabric_erek.client.block.ClientSlotMachineBlock;
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

    public void registerAll() throws IllegalStateException {
        if (registered) throw new IllegalStateException("blocks already registered");

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
    }

}
