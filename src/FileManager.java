import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public static String readPseudoCodeFromFile(String filePath) {
        StringBuilder pseudoCode = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pseudoCode.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }
        return pseudoCode.toString();
    }

    public static void writeToOutputFile(String javaCode, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(javaCode);
        } catch (IOException e) {
            System.err.println("Eroare la scrierea în fișier: " + e.getMessage());
        }
    }
}
