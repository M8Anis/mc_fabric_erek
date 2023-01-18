package github.d3d9_dll.minecraft.fabric.erek.client;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.AtmBottomCaseBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBottomCaseBlock;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineInfoPanelBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.block.ClientAtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.block.ClientSlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.item.ClientAtmBlockItem;
import github.d3d9_dll.minecraft.fabric.erek.client.item.ClientSlotMachineBlockItem;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.ServerVersionSyncS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.SlotMachineBalanceS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.SlotMachineSpinResultS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.item.AtmBottomCaseItem;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineBottomCaseItem;
import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineInfoPanelItem;
import github.d3d9_dll.minecraft.fabric.erek.util.Logs;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientEntrypoint implements ClientModInitializer {

    public final static Logs LOGGER = new Logs(LogManager.getLogger(Logs.LOG_PREFIX + " | Client-side"));

    public static final ItemGroup CASINO_ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("d3d9_dllerek", "casino"))
            .icon(() -> new ItemStack(ClientSlotMachineBlockItem.ITEM))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(SlotMachineBottomCaseItem.ITEM));
                stacks.add(new ItemStack(ClientSlotMachineBlockItem.ITEM));
                stacks.add(new ItemStack(SlotMachineInfoPanelItem.ITEM));
            })
            .build();

    public static final ItemGroup BANK_ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("d3d9_dllerek", "bank"))
            .icon(() -> new ItemStack(ClientAtmBlockItem.ITEM))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(ClientAtmBlockItem.ITEM));
                stacks.add(new ItemStack(AtmBottomCaseItem.ITEM));
            })
            .build();

    @Override
    public void onInitializeClient() {
        LOGGER.debug("Client-side initialization");

        ClientPlayConnectionEvents.INIT.register((handler, minecraftClient) -> registerClientPackets());
        LOGGER.debug("Packet registration registered to \"ClientPlayConnectionEvents.INIT\" event");

        registerKeyBinds();

        registerClientItemsAndBlocks();
    }

    private static void registerKeyBinds() {
        LOGGER.debug("Key binds registration");

        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.d3d9_dllerek.debug_handle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_CONTROL,
                "category.d3d9_dllerek.debug_category"
        ));
        LOGGER.debug("KeyBind \"key.d3d9_dllerek.debug_handle\" registered");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                client.player.sendMessage(new LiteralText("debug"));
            }
        });
        LOGGER.debug("KeyBind \"key.d3d9_dllerek.debug_handle\" callback registered");
    }

    private static void registerClientPackets() {
        LOGGER.debug("Packets registration");

        ClientPlayNetworking.registerReceiver(
                Entrypoint.PACKET_SLOTMACHINE_SPIN, new SlotMachineSpinResultS2CPacket()
        );
        LOGGER.debug("Packet \"PACKET_SLOTMACHINE_SPIN\" registered");

        ClientPlayNetworking.registerReceiver(
                Entrypoint.PACKET_SLOTMACHINE_BALANCE, new SlotMachineBalanceS2CPacket()
        );
        LOGGER.debug("Packet \"PACKET_SLOTMACHINE_BALANCE\" registered");

        ClientPlayNetworking.registerReceiver(
                Entrypoint.PACKET_VERSION_SYNC, new ServerVersionSyncS2CPacket()
        );
        LOGGER.debug("Packet \"PACKET_VERSION_SYNC\" registered");
    }

    private static void registerClientItemsAndBlocks() {
        LOGGER.debug("Registering casino category");
        SlotMachineBottomCaseItem.register();
        LOGGER.debug("BlockItem \"slotmachine_bottom_case\" registered");
        SlotMachineBottomCaseBlock.register();
        LOGGER.debug("Block \"slotmachine_bottom_case\" registered");

        ClientSlotMachineBlockItem.register();
        LOGGER.debug("BlockItem \"slotmachine_block\" registered");
        ClientSlotMachineBlock.register();
        LOGGER.debug("Block \"slotmachine_block\" registered");

        SlotMachineInfoPanelItem.register();
        LOGGER.debug("BlockItem \"slotmachine_info_panel\" registered");
        SlotMachineInfoPanelBlock.register();
        LOGGER.debug("Block \"slotmachine_info_panel\" registered");


        LOGGER.debug("Registering bank category");
        ClientAtmBlockItem.register();
        LOGGER.debug("BlockItem \"atm_block\" registered");
        ClientAtmBlock.register();
        LOGGER.debug("Block \"atm_block\" registered");
        AtmBottomCaseItem.register();
        LOGGER.debug("BlockItem \"atm_bottom_case\" registered");
        AtmBottomCaseBlock.register();
        LOGGER.debug("Block \"atm_bottom_case\" registered");
    }

}
