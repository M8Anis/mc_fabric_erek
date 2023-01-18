package github.d3d9_dll.minecraft.fabric.erek.util;

import java.io.*;

public class File {

    public static String read(java.io.File file) throws FileNotFoundException {
        if (!file.canRead()) return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder data = new StringBuilder();
        try {
            String line = reader.readLine();
            while (line != null) {
                data.append(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }

        return data.toString();
    }

    public static void write(java.io.File file, String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(data);
        writer.close();
    }

}
