/*
Copyright © 2022 https://github.com/d3d9-dll

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
