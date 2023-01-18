package github.d3d9_dll.minecraft.fabric.erek.client.util;

import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import github.d3d9_dll.minecraft.fabric.erek.client.item.ClientAtmBlockItem;
import github.d3d9_dll.minecraft.fabric.erek.client.item.ClientExchangeMachineBlockItem;
import github.d3d9_dll.minecraft.fabric.erek.client.item.ClientSlotMachineBlockItem;
import github.d3d9_dll.minecraft.fabric.erek.item.AtmBottomCaseItem;
import github.d3d9_dll.minecraft.fabric.erek.item.ExchangeMachineStandItem;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBottomCaseItem;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineInfoPanelItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class ClientItemRegistration {

    @SuppressWarnings("unused")
    public final ItemGroup casinoItemGroup;

    @SuppressWarnings("unused")
    public final ItemGroup bankItemGroup;

    public final SlotMachineBottomCaseItem slotMachineBottomCaseBlockItem;
    public final ClientSlotMachineBlockItem slotMachineBlockItem;
    public final SlotMachineInfoPanelItem slotMachineInfoPanelBlockItem;

    public final ClientExchangeMachineBlockItem exchangeMachineBlockItem;
    public final ExchangeMachineStandItem exchangeMachineStandItem;

    public final ClientAtmBlockItem atmBlockItem;
    public final AtmBottomCaseItem atmBottomCaseBlockItem;

    public ClientItemRegistration(ClientBlockRegistration blockRegistration) {
        slotMachineBottomCaseBlockItem = new SlotMachineBottomCaseItem(
                blockRegistration.slotMachineBottomCaseBlock,
                new FabricItemSettings()
        );
        slotMachineBlockItem = new ClientSlotMachineBlockItem(
                blockRegistration.slotMachineBlock,
                new FabricItemSettings()
        );
        slotMachineInfoPanelBlockItem = new SlotMachineInfoPanelItem(
                blockRegistration.slotMachineInfoPanelBlock,
                new FabricItemSettings()
        );

        exchangeMachineBlockItem = new ClientExchangeMachineBlockItem(
                blockRegistration.exchangeMachineBlock,
                new FabricItemSettings()
        );
        exchangeMachineStandItem = new ExchangeMachineStandItem(
                blockRegistration.exchangeMachineStand,
                new FabricItemSettings()
        );

        atmBlockItem = new ClientAtmBlockItem(
                blockRegistration.atmBlock,
                new FabricItemSettings()
        );
        atmBottomCaseBlockItem = new AtmBottomCaseItem(
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
                    stacks.add(new ItemStack(exchangeMachineStandItem));
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

        registerAll();
    }

    private void registerAll() {
        ClientEntrypoint.LOGGER.debug("Item registering");

        Registry.register(Registry.ITEM, SlotMachineBottomCaseItem.IDENTIFIER, slotMachineBottomCaseBlockItem);
        ClientEntrypoint.LOGGER.debug("Item \"slotmachine_bottom_case\" registered");

        Registry.register(Registry.ITEM, ClientSlotMachineBlockItem.IDENTIFIER, slotMachineBlockItem);
        ClientEntrypoint.LOGGER.debug("Item \"slotmachine_block\" registered");

        Registry.register(Registry.ITEM, SlotMachineInfoPanelItem.IDENTIFIER, slotMachineInfoPanelBlockItem);
        ClientEntrypoint.LOGGER.debug("Item \"slotmachine_info_panel\" registered");

        Registry.register(Registry.ITEM, ClientAtmBlockItem.IDENTIFIER, atmBlockItem);
        ClientEntrypoint.LOGGER.debug("Item \"atm_block\" registered");

        Registry.register(Registry.ITEM, AtmBottomCaseItem.IDENTIFIER, atmBottomCaseBlockItem);
        ClientEntrypoint.LOGGER.debug("Item \"atm_bottom_case\" registered");

        Registry.register(Registry.ITEM, ClientExchangeMachineBlockItem.IDENTIFIER, exchangeMachineBlockItem);
        ClientEntrypoint.LOGGER.debug("Item \"exchange_machine_block\" registered");

        Registry.register(Registry.ITEM, ExchangeMachineStandItem.IDENTIFIER, exchangeMachineStandItem);
        ClientEntrypoint.LOGGER.debug("Item \"exchange_machine_stand\" registered");
    }

}
