/*
Copyright (c) 2022-2023 https://github.com/d3d9-dll

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

package github.d3d9_dll.minecraft.fabric.erek;

import github.d3d9_dll.minecraft.fabric.erek.util.Logs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;

import java.io.File;

public class Entrypoint implements ModInitializer {

    public final static Logs LOGGER = new Logs(LogManager.getLogger(Logs.LOG_PREFIX));
    // Common
    public static final Identifier PACKET_VERSION_SYNC =
            new Identifier("d3d9_dllerek:network_packets/version_synchronize");
    // Casino
    public static final Identifier PACKET_SLOTMACHINE_SPIN =
            new Identifier("d3d9_dllerek:network_packets/slotmachine_spin");
    public static final Identifier PACKET_CASINO_PIECES =
            new Identifier("d3d9_dllerek:network_packets/casino_pieces");
    public static final Identifier PACKET_CASINO_TO_BANK_EXCHANGE =
            new Identifier("d3d9_dllerek:network_packets/casino_to_bank_exchange");
    // Bank
    public static final Identifier PACKET_BANK_TO_CASINO_EXCHANGE =
            new Identifier("d3d9_dllerek:network_packets/bank_to_casino_exchange");
    public static final Identifier PACKET_BANK_MONEYS =
            new Identifier("d3d9_dllerek:network_packets/bank_moneys");

    public static final File MOD_DATA_DIRECTORY =
            new File(FabricLoader.getInstance().getConfigDir().toFile(), "d3d9_dllerek");

    @Override
    public void onInitialize() {
        LOGGER.debug("Common-side initialization");

        if (!MOD_DATA_DIRECTORY.exists()) //noinspection ResultOfMethodCallIgnored
            MOD_DATA_DIRECTORY.mkdirs();
    }

}
