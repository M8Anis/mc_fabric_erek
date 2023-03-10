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

package io.github.m8anis.mc_fabric_erek.client.models.slotmachine;

import io.github.m8anis.mc_fabric_erek.client.gui.screen.SlotmachineScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class LinesDraw extends DrawableHelper {

    private final static Identifier LINES_ATLAS =
            new Identifier("m8anis_erek:textures/gui/slotmachine/lines.png");
    public final HashMap<Integer, Function<Integer, Void>> DRAWS;
    private final int LINES_ATLAS_WIDTH = 768;
    private final int LINES_ATLAS_HEIGHT = 768;
    private final int TEXTURE_WIDTH = 256;
    private final int TEXTURE_HEIGHT = 256;

    private final HashMap<String, int[]> LINES_OFFSETS = createOffsets();
    private final MinecraftClient minecraftClient;
    private final int startX;
    private final int startY;
    private final MatrixStack matrices;

    public LinesDraw(MatrixStack matrices, MinecraftClient minecraftClient, int startX, int startY) {
        this.minecraftClient = minecraftClient;
        this.startX = startX;
        this.startY = startY;
        this.matrices = matrices;
        DRAWS = createDraws();
    }

    private static HashMap<String, int[]> createOffsets() {
        HashMap<String, int[]> LINES_OFFSETS = new HashMap<>(4);
        // line = {x, y}
        LINES_OFFSETS.put("ud", new int[]{0, 0}); // \
        LINES_OFFSETS.put("uhd", new int[]{256, 0}); // /\
        LINES_OFFSETS.put("f", new int[]{512, 0}); // --
        LINES_OFFSETS.put("du", new int[]{0, 256}); // /
        LINES_OFFSETS.put("dhu", new int[]{256, 256}); // \/
        LINES_OFFSETS.put("hu", new int[]{512, 256}); // -/
        LINES_OFFSETS.put("hd", new int[]{0, 512}); // -\
        LINES_OFFSETS.put("dh", new int[]{256, 512}); // \-
        LINES_OFFSETS.put("uh", new int[]{512, 512}); // /-
        return LINES_OFFSETS;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine1(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("f");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                DrawableHelper.drawTexture(matrices, startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine2(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("f");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                DrawableHelper.drawTexture(matrices, startX, startY, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine3(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("f");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                DrawableHelper.drawTexture(matrices, startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine4(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("f");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                lineTextureOffset = LINES_OFFSETS.get("dh");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                lineTextureOffset = LINES_OFFSETS.get("ud");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL - 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("hd");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY - 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("f");
                DrawableHelper.drawTexture(matrices, startX, startY - 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine5(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("f");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY - 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                lineTextureOffset = LINES_OFFSETS.get("uh");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY - 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                lineTextureOffset = LINES_OFFSETS.get("du");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL - 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("hu");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("f");
                DrawableHelper.drawTexture(matrices, startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine6(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("dh");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 + 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                lineTextureOffset = LINES_OFFSETS.get("hd");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                lineTextureOffset = LINES_OFFSETS.get("f");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("uh");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("hu");
                DrawableHelper.drawTexture(matrices, startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 + 3, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine7(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("uh");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY + 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                lineTextureOffset = LINES_OFFSETS.get("hu");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                lineTextureOffset = LINES_OFFSETS.get("f");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("dh");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("hd");
                DrawableHelper.drawTexture(matrices, startX, startY + 6, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine8(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("dh");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                lineTextureOffset = LINES_OFFSETS.get("ud");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL - 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                lineTextureOffset = LINES_OFFSETS.get("uhd");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY - 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("du");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL - 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("hu");
                DrawableHelper.drawTexture(matrices, startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine9(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("uh");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4, startY + 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                lineTextureOffset = LINES_OFFSETS.get("du");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                lineTextureOffset = LINES_OFFSETS.get("dhu");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 + 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("ud");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("hd");
                DrawableHelper.drawTexture(matrices, startX, startY + 9, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Void drawLine10(int length) {
        //noinspection
        this.minecraftClient.getTextureManager().bindTexture(LINES_ATLAS);

        int[] lineTextureOffset = LINES_OFFSETS.get("uh");
        switch (length) {
            case 5:
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                        startY, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 4:
                lineTextureOffset = LINES_OFFSETS.get("hu");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 12, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            case 3:
                lineTextureOffset = LINES_OFFSETS.get("f");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 12, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("uh");
                DrawableHelper.drawTexture(matrices, startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                        startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 12, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                lineTextureOffset = LINES_OFFSETS.get("hu");
                DrawableHelper.drawTexture(matrices, startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 + 12, 66, 65,
                        lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
            default:
                break;
        }
        return null;
    }

    private HashMap<Integer, Function<Integer, Void>> createDraws() {
        HashMap<Integer, Function<Integer, Void>> DRAWS = new HashMap<>(10);
        DRAWS.put(1, this::drawLine1);
        DRAWS.put(2, this::drawLine2);
        DRAWS.put(3, this::drawLine3);
        DRAWS.put(4, this::drawLine4);
        DRAWS.put(5, this::drawLine5);
        DRAWS.put(6, this::drawLine6);
        DRAWS.put(7, this::drawLine7);
        DRAWS.put(8, this::drawLine8);
        DRAWS.put(9, this::drawLine9);
        DRAWS.put(10, this::drawLine10);
        return DRAWS;
    }

}
