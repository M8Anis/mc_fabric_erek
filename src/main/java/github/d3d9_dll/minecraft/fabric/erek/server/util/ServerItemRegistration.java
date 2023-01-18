package github.d3d9_dll.minecraft.fabric.erek.server.util;

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

    public final ItemGroup casinoItemGroup;

    public final ItemGroup bankItemGroup;

    public final SlotMachineBottomCaseItem slotMachineBottomCaseBlockItem;
    public final SlotMachineBlockItem slotMachineBlockItem;
    public final SlotMachineInfoPanelItem slotMachineInfoPanelBlockItem;

    public final AtmBlockItem atmBlockItem;
    public final AtmBottomCaseItem atmBottomCaseBlockItem;

    public ServerItemRegistration(ServerBlockRegistration blockRegistration) {
        slotMachineBottomCaseBlockItem = new SlotMachineBottomCaseItem(
                blockRegistration.slotMachineBottomCaseBlock,
                new FabricItemSettings()
        );
        slotMachineBlockItem = new SlotMachineBlockItem(
                blockRegistration.slotMachineBlock,
                new FabricItemSettings()
        );
        slotMachineInfoPanelBlockItem = new SlotMachineInfoPanelItem(
                blockRegistration.slotMachineInfoPanelBlock,
                new FabricItemSettings()
        );

        atmBlockItem = new AtmBlockItem(
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
        ServerEntrypoint.LOGGER.debug("Item registering");

        Registry.register(Registry.ITEM, SlotMachineBottomCaseItem.IDENTIFIER, slotMachineBottomCaseBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"slotmachine_bottom_case\" registered");

        Registry.register(Registry.ITEM, SlotMachineBlockItem.IDENTIFIER, slotMachineBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"slotmachine_block\" registered");

        Registry.register(Registry.ITEM, SlotMachineInfoPanelItem.IDENTIFIER, slotMachineInfoPanelBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"slotmachine_info_panel\" registered");

        Registry.register(Registry.ITEM, AtmBlockItem.IDENTIFIER, atmBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"atm_block\" registered");

        Registry.register(Registry.ITEM, AtmBottomCaseItem.IDENTIFIER, atmBottomCaseBlockItem);
        ServerEntrypoint.LOGGER.debug("Item \"atm_bottom_case\" registered");
    }

}
