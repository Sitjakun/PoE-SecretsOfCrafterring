package Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteFile {

    public static void delete(String file) {
        try {
            Files.deleteIfExists(Paths.get(file));
        } catch (IOException e) {
            System.out.println("Error deleting file");
        }
    }
}
