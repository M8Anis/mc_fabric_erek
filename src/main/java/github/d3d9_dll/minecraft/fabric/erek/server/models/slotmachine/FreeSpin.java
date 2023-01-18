package github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.SERVER)
public class FreeSpin {

    private static final HashMap<String, Integer> freeSpins = new HashMap<>();

    public static int get(String UUID) {
        if (!freeSpins.containsKey(UUID)) freeSpins.put(UUID, 0);

        return freeSpins.get(UUID);
    }

    public static void add(String UUID, int count) {
        if (count < 0) return;

        freeSpins.replace(UUID, get(UUID) + count);
    }

    public static void subtract(String UUID) {
        int freeSpinCount = get(UUID);
        if (freeSpinCount <= 0) return;

        freeSpins.replace(UUID, get(UUID) - 1);
    }

}
