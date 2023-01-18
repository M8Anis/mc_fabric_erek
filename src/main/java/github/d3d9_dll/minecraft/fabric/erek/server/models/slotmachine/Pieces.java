package github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.SERVER)
public class Pieces {

    private static final Gson GSON = new Gson();
    private static final float DEFAULT_PIECES_COUNTER = 0.0f;

    private static HashMap<String, Float> pieces = new HashMap<>();

    public static float get(String UUID) {
        if (!pieces.containsKey(UUID)) pieces.put(UUID, DEFAULT_PIECES_COUNTER);
        return pieces.get(UUID);
    }

    public static void increment(String UUID, float count) {
        if (count < 0) return;

        pieces.replace(UUID, get(UUID) + count);
    }

    public static void subtract(String UUID, float count) {
        if (count < 0) return;

        pieces.replace(UUID, get(UUID) - count);
    }

    public static String exportData() {
        return GSON.toJson(pieces);
    }

    public static void importData(String data) {
        //noinspection UnstableApiUsage
        pieces = GSON.fromJson(data, new TypeToken<HashMap<String, Float>>() {
        }.getType());
        if (pieces == null) pieces = new HashMap<>();
    }

    @Override
    public String toString() {
        return exportData();
    }

}
