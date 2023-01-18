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
