package github.d3d9_dll.minecraft.fabric.erek.client.gui.screen;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import github.d3d9_dll.minecraft.fabric.erek.games.slotmachine.Lines;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class SlotmachineScreen extends Screen {

    private final static Identifier SYMBOLS_ATLAS =
            new Identifier("d3d9_dllerek:textures/gui/slotmachine/symbols.png");
    private static String[][] lastSpin;
    private final BlockPos slotmachinePos;
    private final ClientWorld world;
    private ButtonWidget spin;
    private final HashMap<String, int[]> OFFSETS = new HashMap<>(9);

    public SlotmachineScreen(BlockPos slotmachinePos, ClientWorld world) {
        super(NarratorManager.EMPTY);
        // symbol = {x, y}
        OFFSETS.put("B", new int[]{0, 0});
        OFFSETS.put("W", new int[]{256, 0});
        OFFSETS.put("1", new int[]{512, 0});
        OFFSETS.put("2", new int[]{0, 256});
        OFFSETS.put("3", new int[]{256, 256});
        OFFSETS.put("A", new int[]{512, 256});
        OFFSETS.put("K", new int[]{0, 512});
        OFFSETS.put("Q", new int[]{256, 512});
        OFFSETS.put("J", new int[]{512, 512});

        this.world = world;
        this.slotmachinePos = slotmachinePos;
    }

    public void init() {
        this.spin = this.addButton(
                new ButtonWidget(
                        8, 8, 128, 20,
                        new TranslatableText("gui.d3d9_dllerek.slotmachine.spin").asString(), this::spinButton
                )
        );
    }

    public void render(int mouseX, int mouseY, float delta) {
        if (!checkMachine()) return;
        if (lastSpin != null) {
            this.minecraft.getTextureManager().bindTexture(SYMBOLS_ATLAS);
            for (int real = 0; real < lastSpin.length; real++) {
                for (int symbol = 0; symbol < lastSpin[real].length; symbol++) {
                    int y = 30 + (65 * symbol);
                    int x = 8 + (66 * real);
                    String resultSymbol = lastSpin[real][symbol];
                    int[] textureOffset = OFFSETS.get(resultSymbol);
                    blit(x, y, 64, 64, textureOffset[0], textureOffset[1],
                            256, 256, 768, 768);
                }
            }

            Lines lines = new Lines(lastSpin);
            Lines.Matched matchedLines = new Lines.Matched(lines);
            for (int lineNum = 0; lineNum < lines.lines.length; lineNum++) {
                int y = 32 + (lineNum * this.font.fontHeight);
                int x = 10 + (66 * 5);
                for (int symbolNum = 0; symbolNum < lines.lines[lineNum].length; symbolNum++) {
                    String symbol = lines.lines[lineNum][symbolNum];
                    int width = this.font.getStringWidth(symbol);

                    this.font.drawWithShadow(symbol, x, y, 0xFFFFFFFF);

                    x += width + 1;
                }
                if (matchedLines.getLines().containsKey(lineNum + 1)) {
                    Lines.Matched.Line line = matchedLines.getLines().get(lineNum + 1);

                    int widthSymbol = this.font.getStringWidth(line.symbol);

                    this.font.drawWithShadow(line.symbol, x + 4, y, 0xFFFFFFFF);
                    this.font.drawWithShadow(String.valueOf(line.length), x + 4 + widthSymbol + 1, y, 0xFFFFFFFF);
                }
            }

            String bonusGameStatus = lines.isBonusGame ? "BONUS" : "nothing";
            int y = 34 + (10 * this.font.fontHeight);
            int x = 10 + (66 * 5);
            this.font.drawWithShadow(bonusGameStatus, x, y, 0xFFFFFFFF);
        }

        super.render(mouseX, mouseY, delta);
    }

    public static void setResult(String[][] spinResult) {
        lastSpin = spinResult;
    }

    @Override
    public void onClose() {
        lastSpin = null;
        this.minecraft.openScreen(null);
    }

    private void spinButton(ButtonWidget button) {
        if (!checkMachine()) return;

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(slotmachinePos);

        ClientPlayNetworking.send(Entrypoint.PACKET_SLOTMACHINE_SPIN, buf);
    }

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
}
