package github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine;

import github.d3d9_dll.minecraft.fabric.erek.server.models.bank.Moneys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class Pieces extends Moneys {

    private static final float DEFAULT_PIECES_COUNTER = 0.0f;

    public Pieces() {
    }

    @Override
    public float get(String UUID) {
        if (!balances.containsKey(UUID)) balances.put(UUID, DEFAULT_PIECES_COUNTER);
        return super.get(UUID);
    }

}
