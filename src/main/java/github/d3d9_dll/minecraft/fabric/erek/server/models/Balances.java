package github.d3d9_dll.minecraft.fabric.erek.server.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.SERVER)
public class Balances {

    private static final float DEFAULT_BALANCE = 1000.0f;

    private static final HashMap<String, Float> balances = new HashMap<>();

    public static float get(String UUID) {
        if (!balances.containsKey(UUID))
            balances.put(UUID, DEFAULT_BALANCE);
        return balances.get(UUID);
    }

    public static void increment(String UUID, float count) throws BalanceOverflow {
        if (count < 0) return;

        float currentBalance = get(UUID);
        float newBalance = currentBalance + count;

        if (newBalance < 0) throw new BalanceOverflow(UUID, currentBalance, newBalance);

        balances.replace(UUID, newBalance);
    }

    public static void subtract(String UUID, float count) throws BalanceOverflow {
        if (count < 0) return;

        float currentBalance = get(UUID);
        float newBalance = currentBalance - count;

        if (newBalance > 0) throw new BalanceOverflow(UUID, currentBalance, newBalance);

        balances.replace(UUID, newBalance);
    }

    public static class BalanceOverflow extends Exception {

        public BalanceOverflow(String UUID, float oldBalance, float newBalance) {
            super("balance overflowed: " + UUID + "; " + oldBalance + " -> " + newBalance);
        }

    }

}
