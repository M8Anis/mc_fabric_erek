package github.d3d9_dll.minecraft.fabric.erek.server.models.bank;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.SERVER)
public class Moneys {

    protected static final Gson GSON = new Gson();
    private static final float DEFAULT_BALANCE = 1000.0f;

    protected HashMap<String, Float> balances = new HashMap<>();

    public Moneys() {
    }

    public float get(String UUID) {
        if (!balances.containsKey(UUID)) balances.put(UUID, DEFAULT_BALANCE);
        return balances.get(UUID);
    }

    public void increment(String UUID, float count) {
        if (count < 0) return;

        balances.replace(UUID, get(UUID) + count);
    }

    public void subtract(String UUID, float count) {
        if (count < 0) return;

        balances.replace(UUID, get(UUID) - count);
    }

    public String exportData() {
        return GSON.toJson(balances);
    }

    public void importData(String data) {
        //noinspection UnstableApiUsage
        balances = GSON.fromJson(data, new TypeToken<HashMap<String, Float>>() {
        }.getType());
        if (balances == null) balances = new HashMap<>();
    }

    @Override
    public String toString() {
        return exportData();
    }

}
