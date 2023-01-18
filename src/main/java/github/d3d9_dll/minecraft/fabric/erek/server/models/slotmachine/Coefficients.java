package github.d3d9_dll.minecraft.fabric.erek.server.models.slotmachine;

import github.d3d9_dll.minecraft.fabric.erek.models.slotmachine.Lines;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.SERVER)
public class Coefficients {

    @SuppressWarnings("FieldCanBeLocal")
    private final float LOSE_COEFFICIENT = -2.0f;
    private float coefficient = 0.0f;

    public Coefficients(Lines.Matched matchedLines) {
        HashMap<String, float[]> COEFFICIENTS = new HashMap<>(9);
        COEFFICIENTS.put("W", new float[]{0, 0, 30});
        COEFFICIENTS.put("1", new float[]{16, 17, 18});
        COEFFICIENTS.put("2", new float[]{11, 12, 15});
        COEFFICIENTS.put("3", new float[]{8, 9, 10});
        COEFFICIENTS.put("A", new float[]{5, 6, 7});
        COEFFICIENTS.put("K", new float[]{3, 3.5f, 3.75f});
        COEFFICIENTS.put("Q", new float[]{2, 2.25f, 2.5f});
        COEFFICIENTS.put("J", new float[]{0.25f, 0.5f, 0.75f});

        HashMap<Integer, Lines.Matched.Line> lines = matchedLines.getLines();
        if (lines.size() != 0) {
            for (int line = 0; line < 10; line++) {
                if (lines.containsKey(line + 1)) {
                    Lines.Matched.Line matchedLine = lines.get(line + 1);
                    coefficient += COEFFICIENTS.get(matchedLine.symbol)[matchedLine.length - 3];
                }
            }
        }
    }

    public float getCoefficient() {
        return coefficient == 0.0f ? LOSE_COEFFICIENT : coefficient;
    }

    @Override
    public String toString() {
        return String.valueOf(getCoefficient());
    }

}
