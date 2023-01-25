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
import io.github.m8anis.mc_fabric_erek.block.AtmBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.TranslatableText;
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

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!checkMachine()) return;
        this.renderBackground(matrices);

        String moneys = new TranslatableText("gui.m8anis_erek.atm.text.moneys", AtmScreen.moneys).getString();

        int x = this.width / 2 - this.textRenderer.getWidth(moneys) / 2;
        int y = this.height / 2 - this.textRenderer.fontHeight / 2;

        this.textRenderer.drawWithShadow(matrices, moneys, x, y, 0xFFFFFFFF);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onClose() {
        this.client.openScreen(null);
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
