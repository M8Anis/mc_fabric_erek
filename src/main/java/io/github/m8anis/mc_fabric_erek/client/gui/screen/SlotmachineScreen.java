/*
Copyright © 2022-2023 https://github.com/M8Anis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.github.m8anis.mc_fabric_erek.client.gui.screen;

import io.github.m8anis.mc_fabric_erek.Entrypoint;
import io.github.m8anis.mc_fabric_erek.block.SlotMachineBlock;
import io.github.m8anis.mc_fabric_erek.client.models.slotmachine.LinesDraw;
import io.github.m8anis.mc_fabric_erek.models.slotmachine.Lines;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class SlotmachineScreen extends Screen {

    public static final int REAL_WIDTH_PIXEL = 66;
    public static final int SYMBOL_HEIGHT_PIXEL = 65;
    private static final float MINIMAL_BET = 4.0f;
    private static final float MAXIMAL_BET = 12.0f;

    private final static Identifier SYMBOLS_ATLAS =
            new Identifier("m8anis_erek:textures/gui/slotmachine/symbols.png");
    @SuppressWarnings("FieldCanBeLocal")
    private final int SYMBOLS_ATLAS_WIDTH = 768;
    @SuppressWarnings("FieldCanBeLocal")
    private final int SYMBOLS_ATLAS_HEIGHT = 768;
    @SuppressWarnings("FieldCanBeLocal")
    private final int TEXTURE_WIDTH = 256;
    @SuppressWarnings("FieldCanBeLocal")
    private final int TEXTURE_HEIGHT = 256;
    private final HashMap<String, int[]> SYMBOLS_OFFSETS = createOffsets();

    private static String[][] lastSpin;
    private static Lines.Matched matchedLines;
    private static float pieces = 0.0f;
    private static float coefficient = 0.0f;
    private static float bet = MINIMAL_BET;
    public static boolean bonusGame = false;
    private static boolean initialized = false;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget spinButton;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget addBetButton;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget subBetButton;

    private final BlockPos slotmachinePos;
    private final ClientWorld world;

    public SlotmachineScreen(BlockPos slotmachinePos, ClientWorld world) {
        super(NarratorManager.EMPTY);

        this.world = world;
        this.slotmachinePos = slotmachinePos;
    }

    public void init() {
        int buttonPlaceY = (this.height / 10) + (65 * 3) + 16 + this.textRenderer.fontHeight;
        if ((buttonPlaceY + 20) > this.height) {
            float difference = 1 - (
                    (24 + (this.height - ((this.height / 10.0f) + (65 * 3) + 16 + this.textRenderer.fontHeight + 20))) / 24.0f
            );
            buttonPlaceY -= (16 + this.textRenderer.fontHeight) * difference;
        }

        this.subBetButton = this.addButton(
                new ButtonWidget(
                        (this.width / 2) - 50,
                        buttonPlaceY, 16, 20,
                        Text.of("-"), this::subBetButton
                )
        );
        this.spinButton = this.addButton(
                new ButtonWidget(
                        (this.width / 2) - 32,
                        buttonPlaceY,
                        64, 20,
                        new TranslatableText("gui.m8anis_erek.slotmachine.button.spin"),
                        this::spinButton
                )
        );
        this.addBetButton = this.addButton(
                new ButtonWidget(
                        (this.width / 2) + 34,
                        buttonPlaceY,
                        16, 20, Text.of("+"), this::addBetButton
                )
        );

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(slotmachinePos);

        ClientPlayNetworking.send(Entrypoint.PACKET_CASINO_PIECES, buf);
    }

    @SuppressWarnings("DataFlowIssue")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!checkMachine()) return;
        this.renderBackground(matrices);

        int x = (this.width / 2) - ((66 * 5) / 2);
        int y = (this.height / 10);

        if (lastSpin != null) {
            this.client.getTextureManager().bindTexture(SYMBOLS_ATLAS);
            for (int real = 0; real < lastSpin.length; real++) {
                for (int symbol = 0; symbol < lastSpin[real].length; symbol++) {
                    int symbolX = x + (REAL_WIDTH_PIXEL * real);
                    int symbolY = y + (SYMBOL_HEIGHT_PIXEL * symbol);
                    String resultSymbol = lastSpin[real][symbol];
                    int[] symbolTextureOffset = SYMBOLS_OFFSETS.get(resultSymbol);
                    DrawableHelper.drawTexture(matrices, symbolX, symbolY, 64, 64, symbolTextureOffset[0],
                            symbolTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT, SYMBOLS_ATLAS_WIDTH, SYMBOLS_ATLAS_HEIGHT);
                }
            }
            if (matchedLines != null && matchedLines.getLines().size() > 0) {
                HashMap<Integer, Function<Integer, Void>> lines = new LinesDraw(matrices, this.client, x, y).DRAWS;
                HashMap<Integer, Lines.Matched.Line> linesEntries = matchedLines.getLines();
                for (int line = 0; line < 10; line++) {
                    int lineNumber = line + 1;
                    if (linesEntries.containsKey(lineNumber)) {
                        lines.get(lineNumber)
                                .apply(
                                        linesEntries.get(lineNumber).length
                                );
                    }
                }
            }
        } else {
            DrawableHelper.fill(matrices, x, y,
                    x + (REAL_WIDTH_PIXEL * 5), y + (SYMBOL_HEIGHT_PIXEL * 3), 0x80000000);
        }

        y = y + (65 * 3) + 8;

        String balanceString =
                new TranslatableText("gui.m8anis_erek.slotmachine.text.balance", pieces).getString();
        String betString =
                new TranslatableText("gui.m8anis_erek.slotmachine.text.bet", bet).getString();

        int betWidth = this.textRenderer.getWidth(betString);

        this.textRenderer.drawWithShadow(matrices, balanceString, x + 4, y, 0xFFFFFFFF);
        this.textRenderer.drawWithShadow(matrices, betString, x + REAL_WIDTH_PIXEL * 5 - betWidth, y,
                0xFFFFFFFF);

        if (coefficient != 0.0f) {
            String coefficientString = coefficient < 0 ? "-x" + -coefficient : "x" + coefficient;
            float coefficientWidth = this.textRenderer.getWidth(coefficientString) / 2.0f;
            this.textRenderer.drawWithShadow(matrices, coefficientString,
                    x + REAL_WIDTH_PIXEL * 2.5f - coefficientWidth, y, 0xFFFFFFFF);
        }

        if (bonusGame) {
            this.textRenderer.drawWithShadow(matrices, "BONUS", 8, 8, 0xFFFFFFFF);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onClose() {
        lastSpin = null;
        pieces = 0.0f;
        bet = MINIMAL_BET;
        initialized = false;
        matchedLines = null;
        coefficient = 0.0f;
        this.client.openScreen(null);
    }

    public static void setResult(String[][] spinResult, Lines.Matched matchedLines) {
        SlotmachineScreen.lastSpin = spinResult;
        SlotmachineScreen.matchedLines = matchedLines;
    }

    public static void setCoefficient(float value) {
        coefficient = value;
    }

    public static void setPiecesCounter(float value) {
        if (!initialized) initialized = true;
        pieces = value;
    }

    private void changedBet(float newBet) {
        if (!checkMachine()) return;

        if (bet == newBet) return;
        SlotmachineScreen.bet = newBet;
    }

    @SuppressWarnings("unused")
    private void subBetButton(ButtonWidget button) {
        if (!checkMachine()) return;

        float subtract = 1.0f;

        if (bet - subtract > MINIMAL_BET - 1) this.changedBet(bet - subtract);
    }

    @SuppressWarnings("unused")
    private void addBetButton(ButtonWidget button) {
        if (!checkMachine()) return;

        float add = 1.0f;

        if (bet + add < MAXIMAL_BET + 1) this.changedBet(bet + add);
    }

    @SuppressWarnings("unused")
    private void spinButton(ButtonWidget button) {
        if (!checkMachine()) return;

        if (bet > pieces || !initialized) return;

        lastSpin = new String[0][0];

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(slotmachinePos);
        buf.writeFloat(bet);

        ClientPlayNetworking.send(Entrypoint.PACKET_SLOTMACHINE_SPIN, buf);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkMachine() {
        if (!SlotMachineBlock.checkConstruct(slotmachinePos, world)) {
            onClose();
            return false;
        }
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static HashMap<String, int[]> createOffsets() {
        HashMap<String, int[]> SYMBOLS_OFFSETS = new HashMap<>(9);
        // symbol = {x, y}
        SYMBOLS_OFFSETS.put("B", new int[]{0, 0});
        SYMBOLS_OFFSETS.put("W", new int[]{256, 0});
        SYMBOLS_OFFSETS.put("1", new int[]{512, 0});
        SYMBOLS_OFFSETS.put("2", new int[]{0, 256});
        SYMBOLS_OFFSETS.put("3", new int[]{256, 256});
        SYMBOLS_OFFSETS.put("A", new int[]{512, 256});
        SYMBOLS_OFFSETS.put("K", new int[]{0, 512});
        SYMBOLS_OFFSETS.put("Q", new int[]{256, 512});
        SYMBOLS_OFFSETS.put("J", new int[]{512, 512});
        return SYMBOLS_OFFSETS;
    }

}
