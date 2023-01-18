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
public class AtmScreen extends Screen {

    private static float pieces = 0.0f;
    private static float moneys = 0.0f;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget exchangePiece;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget exchangeMoney;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextFieldWidget exchangeCount;

    private boolean error = false;
    private String errorMessage = "";

    private final BlockPos atmPos;
    private final ClientWorld world;

    public AtmScreen(BlockPos atmPos, ClientWorld world) {
        super(NarratorManager.EMPTY);

        this.world = world;
        this.atmPos = atmPos;
    }

    public void init() {
        this.exchangePiece = this.addButton(
                new ButtonWidget(
                        8, 32, 96, 20, "Exchange pieces", this::sendPieceExchange
                )
        );
        this.exchangeMoney = this.addButton(
                new ButtonWidget(
                        8 + 96 + 4, 32, 96, 20, "Exchange money", this::sendMoneyExchange
                )
        );
        this.exchangeCount = new TextFieldWidget(this.font, 8, 8, 192 + 4, 20, "");
        this.children.add(this.exchangeCount);

        updateData();
    }

    public void render(int mouseX, int mouseY, float delta) {
        if (!checkMachine()) return;
        this.renderBackground();

        this.font.drawWithShadow(String.format("Moneys: %.2f", moneys), 8, 32 + 20 + 4, 0xFFFFFFFF);
        this.font.drawWithShadow(
                String.format("Pieces: %.2f", pieces), 8, 32 + 20 + 4 + font.fontHeight + 4, 0xFFFFFFFF
        );
        if (error)
            this.font.drawWithShadow(errorMessage, 8, 32 + 20 + 4 + font.fontHeight * 2 + 4 + 4, 0xFFFF0000);

        this.exchangeCount.render(mouseX, mouseY, delta);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        //noinspection ConstantConditions
        this.minecraft.openScreen(null);
    }

    public static void setPieces(float value) {
        pieces = value;
    }

    public static void setMoneys(float value) {
        moneys = value;
    }

    @SuppressWarnings("unused")
    private void sendMoneyExchange(ButtonWidget buttonWidget) {
        if (!checkMachine()) return;

        float piece;
        try {
            piece = Float.parseFloat(this.exchangeCount.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            error = true;
            errorMessage = "Exchanging value is not a float!";
            return;
        }
        if (piece > pieces) {
            error = true;
            errorMessage = "Pieces not enough!";
            return;
        }

        error = false;

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(atmPos);
        buf.writeFloat(piece);
        ClientPlayNetworking.send(Entrypoint.PACKET_CASINO_TO_BANK_EXCHANGE, buf);
        ClientEntrypoint.LOGGER.debug("" + piece);

        updateData();
    }

    @SuppressWarnings("unused")
    private void sendPieceExchange(ButtonWidget buttonWidget) {
        if (!checkMachine()) return;

        float money;
        try {
            money = Float.parseFloat(this.exchangeCount.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            error = true;
            errorMessage = "Exchanging value is not a float!";
            return;
        }
        if (money > moneys) {
            error = true;
            errorMessage = "Moneys not enough!";
            return;
        }

        error = false;

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(atmPos);
        buf.writeFloat(money);
        ClientPlayNetworking.send(Entrypoint.PACKET_BANK_TO_CASINO_EXCHANGE, buf);
        ClientEntrypoint.LOGGER.debug("" + money);

        updateData();
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
        ClientPlayNetworking.send(Entrypoint.PACKET_CASINO_PIECES, buf);

        buf = PacketByteBufs.create();
        buf.writeBlockPos(atmPos);
        ClientPlayNetworking.send(Entrypoint.PACKET_BANK_MONEYS, buf);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
