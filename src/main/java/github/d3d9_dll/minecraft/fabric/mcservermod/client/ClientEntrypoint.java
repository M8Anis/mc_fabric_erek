package github.d3d9_dll.minecraft.fabric.mcservermod.client;

import github.d3d9_dll.minecraft.fabric.mcservermod.Entrypoint;
import github.d3d9_dll.minecraft.fabric.mcservermod.client.network.packet.s2c.SlotMachineSpinResultS2CPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientEntrypoint implements ClientModInitializer {

    public final static Logger LOGGER = LogManager.getLogger("d3d9_dll MCServerMod | Client-side");

    private static void registerClientPackets() {
        ClientPlayNetworking.registerReceiver(Entrypoint.PACKET_SLOTMACHINE_SPIN_RESULT, new SlotMachineSpinResultS2CPacket());
        LOGGER.info("Packet \"SlotMachineSpinResultS2CPacket\" registered");
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Client-side initialization");

        ClientPlayConnectionEvents.INIT.register((handler, minecraftClient) -> {
            LOGGER.info("Client play connection event - INIT, handled");
            registerClientPackets();
        });
        LOGGER.info("Subscribed to \"ClientPlayConnectionEvents.INIT\"");

        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.d3d9_dllmcservermod.debug_handle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_CONTROL,
                "category.key.d3d9_dllmcservermod.debug_category"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                ClientPlayNetworking.send(Entrypoint.PACKET_SLOTMACHINE_SPIN, PacketByteBufs.empty());
            }
        });
    }

}
