package github.d3d9_dll.minecraft.fabric.erek.games.slotmachine;

import github.d3d9_dll.minecraft.fabric.erek.client.gui.screen.SlotmachineScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Function;

public class Lines {

    public final String[][] lines;
    public final boolean isBonusGame;
    private final String[][] reals;

    public Lines(String[][] reals) {
        this.reals = reals;

        lines = new String[][]{
                getLine1(),
                getLine2(),
                getLine3(),
                getLine4(),
                getLine5(),
                getLine6(),
                getLine7(),
                getLine8(),
                getLine9(),
                getLine10()
        };

        int bonusGameSymbolCounter = 0;
        for (String[] strings : reals) {
            for (String string : strings) {
                if (string.equals("B")) {
                    bonusGameSymbolCounter++;
                    break;
                }
            }
        }

        isBonusGame = bonusGameSymbolCounter >= 5;
    }

    private String[] getLine1() {
        return new String[]{
                reals[0][1],
                reals[1][1],
                reals[2][1],
                reals[3][1],
                reals[4][1]
        };
    }

    private String[] getLine2() {
        return new String[]{
                reals[0][0],
                reals[1][0],
                reals[2][0],
                reals[3][0],
                reals[4][0]
        };
    }

    private String[] getLine3() {
        return new String[]{
                reals[0][2],
                reals[1][2],
                reals[2][2],
                reals[3][2],
                reals[4][2]
        };
    }

    private String[] getLine4() {
        return new String[]{
                reals[0][0],
                reals[1][0],
                reals[2][1],
                reals[3][2],
                reals[4][2]
        };
    }

    private String[] getLine5() {
        return new String[]{
                reals[0][2],
                reals[1][2],
                reals[2][1],
                reals[3][0],
                reals[4][0]
        };
    }

    private String[] getLine6() {
        return new String[]{
                reals[0][2],
                reals[1][1],
                reals[2][1],
                reals[3][1],
                reals[4][2]
        };
    }

    private String[] getLine7() {
        return new String[]{
                reals[0][0],
                reals[1][1],
                reals[2][1],
                reals[3][1],
                reals[4][0]
        };
    }

    private String[] getLine8() {
        return new String[]{
                reals[0][2],
                reals[1][1],
                reals[2][0],
                reals[3][1],
                reals[4][2]
        };
    }

    private String[] getLine9() {
        return new String[]{
                reals[0][0],
                reals[1][1],
                reals[2][2],
                reals[3][1],
                reals[4][0]
        };
    }

    private String[] getLine10() {
        return new String[]{
                reals[0][2],
                reals[1][1],
                reals[2][1],
                reals[3][1],
                reals[4][0]
        };
    }

    public static class Matched {

        private final Lines lines;
        private final HashMap<Integer, Line> matchedLines;

        public Matched(Lines lines) {
            this.lines = lines;
            matchedLines = getMatchedLines();
        }

        public HashMap<Integer, Line> getLines() {
            return matchedLines;
        }

        private HashMap<Integer, Line> getMatchedLines() {
            HashMap<Integer, Line> lines = new HashMap<>();
            for (int line = 0; line < this.lines.lines.length; line++) {
                String lineStartsWith = "";
                String[] givenLine = this.lines.lines[line];
                int lineLength = 0;
                for (int symbol = 0; symbol < givenLine.length; symbol++) {
                    if (symbol == 0) {
                        lineStartsWith = givenLine[symbol];
                        lineLength++;
                        continue;
                    }
                    if (lineStartsWith.equals("B")) break;
                    if (givenLine[symbol].equals(lineStartsWith) || givenLine[symbol].equals("W")) {
                        lineLength++;
                    } else {
                        break;
                    }
                }
                if (lineLength >= 3) {
                    lines.put(line + 1, new Line(lineStartsWith, lineLength));
                }
            }
            return lines;
        }

        public static class Line {

            public final String symbol;
            public final int length;

            public Line(String symbol, int length) {
                this.symbol = symbol;
                this.length = length;
            }

        }

    }

    @Environment(EnvType.CLIENT)
    public static class Draw extends DrawableHelper {

        private final static Identifier LINES_ATLAS =
                new Identifier("d3d9_dllerek:textures/gui/slotmachine/lines.png");
        public final HashMap<Integer, Function<Integer, Void>> DRAWS;
        @SuppressWarnings("FieldCanBeLocal")
        private final int LINES_ATLAS_WIDTH = 768;
        @SuppressWarnings("FieldCanBeLocal")
        private final int LINES_ATLAS_HEIGHT = 768;
        @SuppressWarnings("FieldCanBeLocal")
        private final int TEXTURE_WIDTH = 256;
        @SuppressWarnings("FieldCanBeLocal")
        private final int TEXTURE_HEIGHT = 256;

        private final HashMap<String, int[]> LINES_OFFSETS = createOffsets();
        private final MinecraftClient minecraftClient;
        private final int startX;
        private final int startY;

        public Draw(MinecraftClient minecraftClient, int startX, int startY) {
            this.minecraftClient = minecraftClient;
            this.startX = startX;
            this.startY = startY;
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    blit(startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    blit(startX, startY, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    blit(startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 3, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    lineTextureOffset = LINES_OFFSETS.get("dh");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 3, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    lineTextureOffset = LINES_OFFSETS.get("ud");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL - 3, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("hd");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY - 3, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("f");
                    blit(startX, startY - 3, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY - 6, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    lineTextureOffset = LINES_OFFSETS.get("uh");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY - 6, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    lineTextureOffset = LINES_OFFSETS.get("du");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL - 6, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("hu");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 6, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("f");
                    blit(startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 6, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 + 3, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    lineTextureOffset = LINES_OFFSETS.get("hd");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 3, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    lineTextureOffset = LINES_OFFSETS.get("f");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 3, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("uh");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 3, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("hu");
                    blit(startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 + 3, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY + 6, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    lineTextureOffset = LINES_OFFSETS.get("hu");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 6, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    lineTextureOffset = LINES_OFFSETS.get("f");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 6, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("dh");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 6, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("hd");
                    blit(startX, startY + 6, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 9, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    lineTextureOffset = LINES_OFFSETS.get("ud");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL - 9, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    lineTextureOffset = LINES_OFFSETS.get("uhd");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY - 9, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("du");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL - 9, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("hu");
                    blit(startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 - 9, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4, startY + 9, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    lineTextureOffset = LINES_OFFSETS.get("du");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 9, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    lineTextureOffset = LINES_OFFSETS.get("dhu");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 + 9, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("ud");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 9, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("hd");
                    blit(startX, startY + 9, 66, 65,
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
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 4,
                            startY, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 4:
                    lineTextureOffset = LINES_OFFSETS.get("hu");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 3,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 12, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                case 3:
                    lineTextureOffset = LINES_OFFSETS.get("f");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL * 2,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 12, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("uh");
                    blit(startX + SlotmachineScreen.REAL_WIDTH_PIXEL,
                            startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL + 12, 66, 65,
                            lineTextureOffset[0], lineTextureOffset[1], TEXTURE_WIDTH, TEXTURE_HEIGHT,
                            LINES_ATLAS_WIDTH, LINES_ATLAS_HEIGHT);
                    lineTextureOffset = LINES_OFFSETS.get("hu");
                    blit(startX, startY + SlotmachineScreen.SYMBOL_HEIGHT_PIXEL * 2 + 12, 66, 65,
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

}
