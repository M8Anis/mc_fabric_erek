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

package github.d3d9_dll.minecraft.fabric.erek.server.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.TranslatableText;

import java.util.HashMap;

@Environment(EnvType.SERVER)
public class VersionSynchronizeQueue {

    private static final long TIMEOUT_MILLISECONDS = 5000L;

    private static final HashMap<String, AutoKick> versionSynchronizes = new HashMap<>();

    public static void add(ServerPlayNetworkHandler handler) {
        String UUID = handler.player.getUuidAsString();
        if (!versionSynchronizes.containsKey(UUID)) {
            AutoKick thread = new AutoKick(handler);
            versionSynchronizes.put(UUID, thread);
            thread.start();
        }
    }

    public static void remove(ServerPlayNetworkHandler handler) {
        String UUID = handler.player.getUuidAsString();
        if (versionSynchronizes.containsKey(UUID)) {
            AutoKick thread = versionSynchronizes.get(UUID);
            thread.interrupt();
        }
    }

    private static class AutoKick extends Thread {

        private final ServerPlayNetworkHandler handler;

        public AutoKick(ServerPlayNetworkHandler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                sleep(TIMEOUT_MILLISECONDS);
                handler.disconnect(
                        new TranslatableText(
                                "text.d3d9_dllerek.version_sync.timeout",
                                new TranslatableText("text.d3d9_dllerek.erek_prefix")
                        )
                );
            } catch (InterruptedException ignore) {
                // IGNORE because it's not important
            }
            versionSynchronizes.remove(handler.player.getUuidAsString());
        }

    }

}
