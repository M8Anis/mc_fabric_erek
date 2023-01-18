package github.d3d9_dll.minecraft.fabric.erek.client;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.ServerVersionSyncS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.bank.BankMoneysS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.casino.CasinoPiecesS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.client.network.packet.s2c.casino.SlotMachineSpinResultS2CPacket;
import github.d3d9_dll.minecraft.fabric.erek.client.util.ClientBlockRegistration;
import github.d3d9_dll.minecraft.fabric.erek.client.util.ClientItemRegistration;
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

    public final static Logs LOGGER = new Logs(LogManager.getLogger(Logs.LOG_PREFIX + " | Client-side"));

    public static final ClientBlockRegistration BLOCK_REGISTER = new ClientBlockRegistration().registerAll();
    @SuppressWarnings("unused")
    public static final ClientItemRegistration ITEM_REGISTER = new ClientItemRegistration(BLOCK_REGISTER).registerAll();

    @Override
    public void onInitializeClient() {
        LOGGER.debug("Client-side initialization");

        ClientPlayConnectionEvents.INIT.register((handler, minecraftClient) -> registerClientPackets());
        LOGGER.debug("Packet registration registered to \"ClientPlayConnectionEvents.INIT\" event");

        registerKeyBinds();
    }

    private static void registerKeyBinds() {
        LOGGER.debug("Key-binds registration");

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
                Entrypoint.PACKET_CASINO_PIECES, new CasinoPiecesS2CPacket()
        );
        LOGGER.debug("Packet \"PACKET_CASINO_PIECES\" registered");

        ClientPlayNetworking.registerReceiver(
                Entrypoint.PACKET_VERSION_SYNC, new ServerVersionSyncS2CPacket()
        );
        LOGGER.debug("Packet \"PACKET_VERSION_SYNC\" registered");

        ClientPlayNetworking.registerReceiver(
                Entrypoint.PACKET_BANK_MONEYS, new BankMoneysS2CPacket()
        );
        LOGGER.debug("Packet \"PACKET_BANK_MONEYS\" registered");
    }

}
