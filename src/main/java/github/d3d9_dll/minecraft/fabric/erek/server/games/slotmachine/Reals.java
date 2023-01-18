package github.d3d9_dll.minecraft.fabric.erek.server.games.slotmachine;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.util.PacketByteBuf;

import java.security.SecureRandom;

@Environment(EnvType.SERVER)
public class Reals {

    private static final SecureRandom random = new SecureRandom();

    private static final String[][] reals = {
            {
                    "A", "J", "3", "J", "A", "J", "A", "2", "3", "J", "J", "A", "2", "1", "J", "J", "A", "K", "J", "J", "3",
                    "Q", "2", "3", "3", "A", "2", "2", "A", "A", "2", "J", "J", "1", "2", "J", "2", "Q", "J", "A", "2", "3",
                    "1", "W", "Q", "J", "J", "A", "Q", "K", "K", "A", "1", "A", "Q", "2", "3", "Q", "3", "A", "3", "2", "A",
                    "1", "K", "K", "Q", "A", "1", "3", "Q", "1", "K", "J", "K", "J", "A", "1", "1", "J", "K", "J", "J", "Q",
                    "2", "1", "J", "K", "3", "K", "Q", "Q", "Q", "Q", "Q", "A", "K", "1", "1", "1", "Q", "A", "3", "K", "1",
                    "1", "Q", "Q", "Q", "3", "J", "J", "A", "K", "K", "2", "Q", "Q", "Q", "K", "K", "K", "K", "Q", "J", "Q",
                    "A", "1", "K", "B", "J", "A", "Q", "B", "W", "K", "J", "1",
            },
            {
                    "A", "A", "Q", "J", "3", "2", "Q", "Q", "3", "J", "2", "K", "2", "1", "A", "J", "K", "A", "J", "A", "2",
                    "2", "B", "3", "A", "2", "2", "J", "1", "3", "J", "J", "2", "2", "K", "J", "K", "3", "K", "Q", "A", "A",
                    "A", "3", "Q", "A", "J", "J", "1", "Q", "1", "3", "B", "A", "2", "Q", "J", "Q", "K", "K", "K", "A", "2",
                    "1", "K", "3", "K", "A", "3", "1", "3", "A", "K", "Q", "W", "J", "A", "1", "1", "Q", "J", "K", "A", "J",
                    "J", "K", "K", "J", "3", "K", "J", "Q", "1", "K", "A", "K", "Q", "A", "Q", "1", "J", "J", "K", "K", "Q",
                    "J", "2", "Q", "3", "2", "J", "1", "1", "1", "Q", "A", "Q", "1", "Q", "W", "2", "Q", "J", "Q", "J", "Q",
                    "K", "1", "Q", "1", "J", "A", "K", "Q",
            },
            {
                    "3", "J", "2", "Q", "J", "A", "K", "2", "A", "A", "J", "A", "Q", "2", "J", "2", "J", "A", "A", "3", "3",
                    "K", "Q", "2", "3", "3", "Q", "3", "3", "3", "2", "2", "2", "3", "J", "2", "J", "Q", "Q", "A", "A", "3",
                    "2", "W", "1", "K", "J", "J", "2", "3", "2", "A", "J", "J", "Q", "2", "Q", "A", "3", "Q", "J", "K", "3",
                    "1", "J", "A", "J", "2", "1", "J", "3", "1", "1", "3", "J", "3", "A", "3", "3", "Q", "J", "K", "3", "J",
                    "W", "J", "3", "J", "Q", "A", "K", "3", "K", "K", "2", "W", "A", "K", "K", "1", "Q", "A", "Q", "1", "K",
                    "1", "A", "Q", "Q", "3", "K", "2", "3", "K", "1", "B", "Q", "3", "1", "K", "2", "K", "J", "Q", "2", "Q",
                    "K", "K", "K", "Q", "Q", "J", "Q", "B", "W",
            },
            {
                    "2", "2", "A", "3", "A", "3", "A", "Q", "A", "J", "3", "3", "1", "2", "3", "3", "A", "3", "3", "J", "3",
                    "K", "A", "J", "J", "2", "1", "3", "1", "3", "J", "3", "J", "J", "J", "2", "3", "K", "2", "J", "A", "Q",
                    "2", "Q", "3", "2", "J", "Q", "Q", "A", "K", "A", "Q", "A", "Q", "3", "3", "A", "2", "2", "W", "J", "2",
                    "A", "K", "1", "1", "K", "Q", "1", "Q", "A", "J", "K", "J", "J", "1", "1", "Q", "Q", "K", "K", "K", "W",
                    "J", "2", "A", "K", "J", "Q", "K", "J", "1", "3", "3", "A", "K", "Q", "Q", "3", "Q", "K", "W", "A", "1",
                    "K", "2", "Q", "J", "3", "1", "1", "A", "B", "2", "K", "1", "K", "2", "2", "W", "A", "J", "K", "K", "K",
                    "W", "Q", "Q", "K", "Q", "K", "Q", "B", "K", "J",
            },
            {
                    "2", "A", "1", "A", "A", "J", "3", "A", "1", "A", "J", "K", "1", "2", "J", "2", "A", "1", "J", "3", "3",
                    "A", "K", "2", "3", "J", "3", "A", "1", "K", "3", "A", "3", "J", "1", "1", "J", "3", "J", "2", "2", "Q",
                    "A", "3", "1", "J", "2", "K", "2", "K", "3", "1", "K", "K", "Q", "2", "Q", "Q", "J", "3", "B", "K", "3",
                    "3", "2", "B", "Q", "2", "K", "K", "2", "A", "3", "J", "W", "2", "A", "1", "J", "Q", "K", "3", "J", "A",
                    "A", "Q", "3", "W", "1", "K", "Q", "J", "Q", "3", "3", "A", "A", "Q", "2", "3", "Q", "K", "J", "1", "Q",
                    "K", "A", "K", "K", "3", "K", "Q", "Q", "Q", "2", "3", "2", "J", "J", "K", "2", "K", "J", "1", "K", "1",
                    "Q", "3", "K", "K", "Q", "A", "K", "K", "A",
            }
    };

    public static String[][] generateResult() {
        String[][] result = new String[5][3];
        for (int real = 0; real < result.length; real++) {
            for (int symbol = 0; symbol < result[real].length; symbol++) {
                int index = random.nextInt(reals[real].length);
                result[real][symbol] = reals[real][index];
            }
        }
        return result;
    }

    public static PacketByteBuf generateForPacket(){
        PacketByteBuf buff = PacketByteBufs.create();

        String[][] resultOfSpin = generateResult();
        /*
         *  Writing in reverse
         **/
        for (int reals = resultOfSpin.length - 1; 0 <= reals; reals--) {
            for (int symbol = resultOfSpin[reals].length - 1; 0 <= symbol; symbol--) {
                buff.writeString(resultOfSpin[reals][symbol], 1);
            }
        }

        return buff;
    }

}
