package github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.SERVER)
public class FreeSpin {

    private static final Gson GSON = new Gson();
    private HashMap<String, Integer> freeSpins = new HashMap<>();

    public int get(String UUID) {
        if (!freeSpins.containsKey(UUID)) freeSpins.put(UUID, 0);

        return freeSpins.get(UUID);
    }

    public void add(String UUID, int count) {
        if (count < 0) return;

        freeSpins.replace(UUID, get(UUID) + count);
    }

    public void subtract(String UUID) {
        int freeSpinCount = get(UUID);
        if (freeSpinCount <= 0) return;

        freeSpins.replace(UUID, get(UUID) - 1);
    }

    public String exportData() {
        return GSON.toJson(freeSpins);
    }

    public void importData(String data) {
        //noinspection UnstableApiUsage
        freeSpins = GSON.fromJson(data, new TypeToken<HashMap<String, Integer>>() {
        }.getType());
        if (freeSpins == null) freeSpins = new HashMap<>();
    }

    @Override
    public String toString() {
        return exportData();
    }

}
