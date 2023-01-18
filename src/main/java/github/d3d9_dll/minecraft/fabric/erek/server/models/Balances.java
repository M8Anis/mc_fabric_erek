package github.d3d9_dll.minecraft.fabric.erek.server.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.SERVER)
public class Balances {

    private static final float DEFAULT_BALANCE = 1000.0f;

    private static final HashMap<String, Float> balances = new HashMap<>();

    public static float get(String UUID) {
        if (!balances.containsKey(UUID)) balances.put(UUID, DEFAULT_BALANCE);
        return balances.get(UUID);
    }

    public static void increment(String UUID, float count) {
        if (count < 0) return;

        balances.replace(UUID, get(UUID) + count);
    }

    public static void subtract(String UUID, float count) {
        if (count < 0) return;

        balances.replace(UUID, get(UUID) - count);
    }

}
