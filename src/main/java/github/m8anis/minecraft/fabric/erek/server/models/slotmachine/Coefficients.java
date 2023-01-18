/*
Copyright © 2022-2023 https://github.com/M8Anis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package github.m8anis.minecraft.fabric.erek.server.models.slotmachine;

import github.m8anis.minecraft.fabric.erek.models.slotmachine.Lines;
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
