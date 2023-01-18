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
