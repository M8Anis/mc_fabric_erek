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

package github.m8anis.minecraft.fabric.erek.server.models.bank;

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
