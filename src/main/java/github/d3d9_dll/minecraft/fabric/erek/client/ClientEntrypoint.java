package github.d3d9_dll.minecraft.fabric.erek.client;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.ServerVersionSyncS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.SlotMachineBalanceS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.SlotMachineSpinResultS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.util.Logs;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientEntrypoint implements ClientModInitializer {

    public final static Logs LOGGER = new Logs(LogManager.getLogger("d3d9_dll EREK | Client-side"));

    @Override
    public void onInitializeClient() {
        LOGGER.log("Client-side initialization");

        ClientPlayConnectionEvents.INIT.register((handler, minecraftClient) -> {
            LOGGER.log("Packets registration handled");
            registerClientPackets();
        });
        LOGGER.log("Subscribed to \"ClientPlayConnectionEvents.INIT\"");

        registerKeyBinds();
    }

    private static void registerKeyBinds() {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.d3d9_dllerek.debug_handle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_CONTROL,
                "category.d3d9_dllerek.debug_category"
        ));
        LOGGER.log("KeyBind \"key.d3d9_dllerek.debug_handle\" registered");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                client.player.sendMessage(new LiteralText("debug"));
            }
        });
        LOGGER.log("KeyBind \"key.d3d9_dllerek.debug_handle\" callback registered");
    }

    private static void registerClientPackets() {
        ClientPlayNetworking.registerReceiver(
                Entrypoint.PACKET_SLOTMACHINE_SPIN, new SlotMachineSpinResultS2CPacket()
        );
        LOGGER.log("Packet \"PACKET_SLOTMACHINE_SPIN\" registered");
        ClientPlayNetworking.registerReceiver(
                Entrypoint.PACKET_SLOTMACHINE_BALANCE, new SlotMachineBalanceS2CPacket()
        );
        LOGGER.log("Packet \"PACKET_SLOTMACHINE_BALANCE\" registered");
        ClientPlayNetworking.registerReceiver(
                Entrypoint.PACKET_VERSION_SYNC, new ServerVersionSyncS2CPacket()
        );
        LOGGER.log("Packet \"PACKET_VERSION_SYNC\" registered");
    }

}
