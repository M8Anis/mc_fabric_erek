package github.d3d9_dll.minecraft.fabric.erek.server.models;

import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class AutoSave extends Thread {

    private final long interval;

    public AutoSave(long intervalMilliseconds) {
        interval = intervalMilliseconds;
    }

    @Override
    public void run() {
        ServerEntrypoint.LOGGER.debug("Auto-saver enabled");
        while (!interrupted()) {
            try {
                //noinspection BusyWait
                sleep(interval);
                ServerEntrypoint.saveData();
            } catch (InterruptedException e) {
                ServerEntrypoint.LOGGER.debug("Auto-saver interrupted");
            }
        }
    }

}
