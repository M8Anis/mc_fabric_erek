package github.d3d9_dll.minecraft.fabric.erek.client.gui.screen;

import github.d3d9_dll.minecraft.fabric.erek.Entrypoint;
import github.d3d9_dll.minecraft.fabric.erek.block.SlotMachineBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawableHelper;
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
    private static final float MINIMAL_BET = 4.0f;
    private static final float MAXIMAL_BET = 12.0f;
    public static boolean bonusGame = false;

    private final BlockPos slotmachinePos;
    private final ClientWorld world;
    private static String[][] lastSpin;
    private static float balance = 0.0f;
    private static float bet = MINIMAL_BET;
    private static boolean initialized = false;
    private final HashMap<String, int[]> OFFSETS = new HashMap<>(9);

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget spinButton;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget addBetButton;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ButtonWidget subBetButton;

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
        this.subBetButton = this.addButton(
                new ButtonWidget(
                        (this.width / 2) - 50,
                        (this.height / 10) + (65 * 3) + 16 + this.font.fontHeight, 16, 20,
                        "-", this::subBetButton
                )
        );
        this.spinButton = this.addButton(
                new ButtonWidget(
                        (this.width / 2) - 32, (this.height / 10) + (65 * 3) + 16 + this.font.fontHeight,
                        64, 20,
                        new TranslatableText("gui.d3d9_dllerek.slotmachine.button.spin").asString(),
                        this::spinButton
                )
        );
        this.addBetButton = this.addButton(
                new ButtonWidget(
                        (this.width / 2) + 34, (this.height / 10) + (65 * 3) + 16 + this.font.fontHeight,
                        16, 20, "+", this::addBetButton
                )
        );

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(slotmachinePos);

        ClientPlayNetworking.send(Entrypoint.PACKET_SLOTMACHINE_BALANCE, buf);
    }

    public void render(int mouseX, int mouseY, float delta) {
        if (!checkMachine()) return;

        int x = (this.width / 2) - ((66 * 5) / 2);
        int y = (this.height / 10);

        if (lastSpin != null) {
            //noinspection ConstantConditions
            this.minecraft.getTextureManager().bindTexture(SYMBOLS_ATLAS);
            for (int real = 0; real < lastSpin.length; real++) {
                for (int symbol = 0; symbol < lastSpin[real].length; symbol++) {
                    int symbolX = x + (66 * real);
                    int symbolY = y + (65 * symbol);
                    String resultSymbol = lastSpin[real][symbol];
                    int[] textureOffset = OFFSETS.get(resultSymbol);
                    blit(symbolX, symbolY, 64, 64, textureOffset[0], textureOffset[1],
                            256, 256, 768, 768);
                }
            }
        } else {
            DrawableHelper.fill(x, y, x + (66 * 5), y + (65 * 3), 0x80000000);
        }

        y = y + (65 * 3) + 8;

        String balanceString =
                new TranslatableText("gui.d3d9_dllerek.slotmachine.text.balance", balance).asString();
        String betString =
                new TranslatableText("gui.d3d9_dllerek.slotmachine.text.bet", bet).asString();

        int betWidth = this.font.getStringWidth(betString);

        this.font.drawWithShadow(balanceString, x + 4, y, 0xFFFFFFFF);
        this.font.drawWithShadow(betString, x + 66 * 5 - 4 - betWidth, y, 0xFFFFFFFF);

        if (bonusGame) {
            this.font.drawWithShadow("BONUS", 8, 8, 0xFFFFFFFF);
        }

        super.render(mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        lastSpin = null;
        balance = 0.0f;
        bet = MINIMAL_BET;
        initialized = false;
        //noinspection ConstantConditions
        this.minecraft.openScreen(null);
    }

    public static void setResult(String[][] spinResult) {
        lastSpin = spinResult;
    }

    public static void setBalance(float value) {
        if (!initialized) initialized = true;
        balance = value;
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

        if (bet > balance || !initialized) return;

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

}
