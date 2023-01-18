package github.d3d9_dll.minecraft.fabric.erek.client.gui.screen;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class AtmScreen extends Screen {

    private static float moneys = 0.0f;

    private final BlockPos atmPos;
    private final ClientWorld world;

    public AtmScreen(BlockPos atmPos, ClientWorld world) {
        super(NarratorManager.EMPTY);

        this.world = world;
        this.atmPos = atmPos;
    }

    public void init() {
        updateData();
    }

    public void render(int mouseX, int mouseY, float delta) {
        if (!checkMachine()) return;
        this.renderBackground();

        String moneys = new TranslatableText("gui.d3d9_dllerek.atm.text.moneys", AtmScreen.moneys).asString();

        int x = this.width / 2 - this.font.getStringWidth(moneys) / 2;
        int y = this.height / 2 - this.font.fontHeight / 2;

        this.font.drawWithShadow(moneys, x, y, 0xFFFFFFFF);

        super.render(mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        //noinspection ConstantConditions
        this.minecraft.openScreen(null);
    }

    public static void setMoneys(float value) {
        moneys = value;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkMachine() {
        if (!AtmBlock.checkConstruct(atmPos, world)) {
            onClose();
            return false;
        }
        return true;
    }

    private void updateData() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(atmPos);
        ClientPlayNetworking.send(Entrypoint.PACKET_BANK_MONEYS, buf);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
