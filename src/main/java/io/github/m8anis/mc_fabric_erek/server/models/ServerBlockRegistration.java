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

package io.github.m8anis.mc_fabric_erek.server.models;

import io.github.m8anis.mc_fabric_erek.block.*;
import io.github.m8anis.mc_fabric_erek.server.ServerEntrypoint;
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
