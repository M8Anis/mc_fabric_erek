package github.d3d9_dll.minecraft.fabric.erek.client.gui.screen;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.AtmBlock;
import github.d3d9_dll.minecraft.fabric.erek.client.ClientEntrypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class ATMScreen extends Screen {

    @SuppressWarnings({"FieldCanBeLocal", "unused", "RedundantSuppression"})
    private TextFieldWidget newBalanceTextField;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget setBalanceButton;

    private final BlockPos atmPos;
    private final ClientWorld world;

    public ATMScreen(BlockPos atmPos, ClientWorld world) {
        super(NarratorManager.EMPTY);

        this.world = world;
        this.atmPos = atmPos;
    }

    public void init() {
        this.newBalanceTextField =
                new TextFieldWidget(this.font, 8, 8, 64, 20, "");
        this.newBalanceTextField.setMaxLength(5);
        this.newBalanceTextField.setHasBorder(true);
        this.newBalanceTextField.setText("1000");
        this.children.add(this.newBalanceTextField);

        this.setBalanceButton = this.addButton(
                new ButtonWidget(8 + 64 + 4, 8, 64, 20, "Set", this::sendNewBalance)
        );
    }

    public void render(int mouseX, int mouseY, float delta) {
        if (!checkMachine()) return;
        this.renderBackground();

        this.font.drawWithShadow("0 < x < 10000", 8, 20 + 12 + 4, 0xFFFFFFFF);

        this.newBalanceTextField.render(mouseX, mouseY, delta);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        //noinspection ConstantConditions
        this.minecraft.openScreen(null);
    }

    @SuppressWarnings("unused")
    private void sendNewBalance(ButtonWidget button) {
        if (!checkMachine()) return;
        try {
            float balance = Float.parseFloat(this.newBalanceTextField.getText());

            if (balance < 0.0f || balance > 10000.0f) return;

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(atmPos);
            buf.writeFloat(balance);

            ClientPlayNetworking.send(Entrypoint.PACKET_CHEAT_SET_BALANCE, buf);
        } catch (NumberFormatException e) {
            ClientEntrypoint.LOGGER.error("Incorrect new balance");
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkMachine() {
        if (!AtmBlock.checkConstruct(atmPos, world)) {
            onClose();
            return false;
        }
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
