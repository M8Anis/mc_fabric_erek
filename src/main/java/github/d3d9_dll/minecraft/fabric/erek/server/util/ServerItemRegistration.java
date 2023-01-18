package github.d3d9_dll.minecraft.fabric.erek.server.util;

import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.item.*;
import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
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
                        new Identifier("d3d9_dllerek", "casino"))
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
                        new Identifier("d3d9_dllerek", "bank"))
                .icon(() -> new ItemStack(atmBlockItem))
                .appendItems(stacks -> {
                    stacks.add(new ItemStack(atmBottomCaseBlockItem));
                    stacks.add(new ItemStack(atmBlockItem));
                })
                .build();
    }

    public void registerAll() throws IllegalStateException {
        if (registered) throw new IllegalStateException("blocks already registered");

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
        ClientEntrypoint.LOGGER.debug("Item \"exchange_machine_block\" registered");

        Registry.register(Registry.ITEM, ExchangeMachineStandBlockItem.IDENTIFIER, exchangeMachineStandBlockItem);
        ClientEntrypoint.LOGGER.debug("Item \"exchange_machine_stand\" registered");

        registered = true;
    }

}
