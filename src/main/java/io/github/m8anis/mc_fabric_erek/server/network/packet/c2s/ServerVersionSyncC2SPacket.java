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

package io.github.m8anis.mc_fabric_erek.server.network.packet.c2s;

import io.github.m8anis.mc_fabric_erek.Version;
import io.github.m8anis.mc_fabric_erek.server.ServerEntrypoint;
import io.github.m8anis.mc_fabric_erek.server.models.VersionSynchronizeQueue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.SERVER)
public class ServerVersionSyncC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        long clientVersionFingerprint = buf.readLong();
        if (Version.VERSION_SERIAL != clientVersionFingerprint) {
            TranslatableText reason = new TranslatableText(
                    clientVersionFingerprint > Version.VERSION_SERIAL ?
                            "text.m8anis_erek.version_sync.newer" : "text.m8anis_erek.version_sync.older",
                    new TranslatableText("text.m8anis_erek.erek_prefix")
            );
            handler.disconnect(reason);
        }
        VersionSynchronizeQueue.remove(handler);

        ServerEntrypoint.LOGGER.debug("Player \"" + player.getName().asString() +
                "\" removed from version synchronize queue");
    }

}
