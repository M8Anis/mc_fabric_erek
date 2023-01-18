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

package github.m8anis.minecraft.fabric.erek.server.models;

import github.m8anis.minecraft.fabric.erek.item.*;
import github.m8anis.minecraft.fabric.erek.server.ServerEntrypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.SERVER)
public class ServerItemRegistration {

    @SuppressWarnings("unused")
    public final ItemGroup casinoItemGroup;

    @SuppressWarnings("unused")
    public final ItemGroup bankItemGroup;

    public final SlotMachineBottomCaseBlockItem slotMachineBottomCaseBlockItem;
    public final SlotMachineBlockItem slotMachineBlockItem;
    public final SlotMachineInfoPanelBlockItem slotMachineInfoPanelBlockItem;

    public final ExchangeMachineBlockItem exchangeMachineBlockItem;
    public final ExchangeMachineStandBlockItem exchangeMachineStandBlockItem;

    public final AtmBlockItem atmBlockItem;
    public final AtmBottomCaseBlockItem atmBottomCaseBlockItem;

    private boolean registered = false;

    public ServerItemRegistration(ServerBlockRegistration blockRegistration) {
        slotMachineBottomCaseBlockItem = new SlotMachineBottomCaseBlockItem(
                blockRegistration.slotMachineBottomCaseBlock,
                new FabricItemSettings()
        );
        slotMachineBlockItem = new SlotMachineBlockItem(
                blockRegistration.slotMachineBlock,
                new FabricItemSettings()
        );
        slotMachineInfoPanelBlockItem = new SlotMachineInfoPanelBlockItem(
                blockRegistration.slotMachineInfoPanelBlock,
                new FabricItemSettings()
        );

        exchangeMachineBlockItem = new ExchangeMachineBlockItem(
                blockRegistration.exchangeMachineBlock,
                new FabricItemSettings()
        );
        exchangeMachineStandBlockItem = new ExchangeMachineStandBlockItem(
                blockRegistration.exchangeMachineStandBlock,
                new FabricItemSettings()
        );

        atmBlockItem = new AtmBlockItem(
                blockRegistration.atmBlock,
                new FabricItemSettings()
        );
        atmBottomCaseBlockItem = new AtmBottomCaseBlockItem(
                blockRegistration.atmBottomCaseBlock,
                new FabricItemSettings()
        );

        casinoItemGroup = FabricItemGroupBuilder.create(
                        new Identifier("m8anis_erek", "casino"))
                .icon(() -> new ItemStack(slotMachineBlockItem))
                .appendItems(stacks -> {
                    stacks.add(new ItemStack(slotMachineBottomCaseBlockItem));
                    stacks.add(new ItemStack(slotMachineBlockItem));
                    stacks.add(new ItemStack(slotMachineInfoPanelBlockItem));
                    stacks.add(new ItemStack(exchangeMachineStandBlockItem));
                    stacks.add(new ItemStack(exchangeMachineBlockItem));
                })
                .build();

        bankItemGroup = FabricItemGroupBuilder.create(
                        new Identifier("m8anis_erek", "bank"))
                .icon(() -> new ItemStack(atmBlockItem))
                .appendItems(stacks -> {
                    stacks.add(new ItemStack(atmBottomCaseBlockItem));
                    stacks.add(new ItemStack(atmBlockItem));
                })
                .build();
    }

    public void registerAll() throws IllegalStateException {
        if (registered) throw new IllegalStateException("items already registered");

        ServerEntrypoint.LOGGER.debug("Item registering");

        Registry.register(Registry.ITEM, SlotMachineBottomCaseBlockItem.IDENTIFIER, slotMachineBottomCaseBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"slotmachine_bottom_case\" registered");

        Registry.register(Registry.ITEM, SlotMachineBlockItem.IDENTIFIER, slotMachineBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"slotmachine_block\" registered");

        Registry.register(Registry.ITEM, SlotMachineInfoPanelBlockItem.IDENTIFIER, slotMachineInfoPanelBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"slotmachine_info_panel\" registered");

        Registry.register(Registry.ITEM, AtmBlockItem.IDENTIFIER, atmBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"atm_block\" registered");

        Registry.register(Registry.ITEM, AtmBottomCaseBlockItem.IDENTIFIER, atmBottomCaseBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"atm_bottom_case\" registered");

        Registry.register(Registry.ITEM, ExchangeMachineBlockItem.IDENTIFIER, exchangeMachineBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"exchange_machine_block\" registered");

        Registry.register(Registry.ITEM, ExchangeMachineStandBlockItem.IDENTIFIER, exchangeMachineStandBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"exchange_machine_stand\" registered");

        registered = true;
    }

}
