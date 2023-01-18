package github.d3d9_dll.minecraft.fabric.erek.server.models;

import github.d3d9_dll.minecraft.fabric.erek.server.ServerEntrypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class AutoSave extends Thread {

    private final long interval;

    public AutoSave(long intervalMilliseconds) {
        super("Data Auto-Saver Thread");
        interval = intervalMilliseconds;
    }

    @Override
    public void run() {
        ServerEntrypoint.LOGGER.debug("Auto-saver enabled");
        while (!isInterrupted()) {
            try {
                //noinspection BusyWait
                sleep(interval);
                ServerEntrypoint.saveData();
            } catch (InterruptedException e) {
                ServerEntrypoint.LOGGER.debug("Auto-saver interrupted");
                this.interrupt();
            }
        }
    }

}
