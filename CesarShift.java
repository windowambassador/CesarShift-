import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class CesarShift {

    private static int normalizeKey(int key) {
        key = key % 26;
        if (key < 0) {
            key += 26;
        }
        return key;
    }

    private static void checkFileNotEmpty(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.size(path) == 0) {
            throw new IOException("Файл пустой: " + filePath);
        }
    }

    public static void encryptFile(String inputPath, String outputPath, int key) throws IOException {
        key = normalizeKey(key);

        Path inputFilePath = Paths.get(inputPath);
        if (!Files.exists(inputFilePath)) {
            throw new NoSuchFileException("Файл не найден: " + inputPath);
        }

        checkFileNotEmpty(inputPath);

        try (BufferedReader reader = Files.newBufferedReader(inputFilePath);
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {

            String line;
            boolean hasContent = false;
            while ((line = reader.readLine()) != null) {
                String encryptedLine = encryptString(line, key);
                writer.write(encryptedLine);
                writer.newLine();
                hasContent = true;
            }

            if (!hasContent) {
                throw new IOException("Файл не содержит текста: " + inputPath);
            }
        }
    }

    public static void decryptFile(String inputPath, String outputPath, int key) throws IOException {
        encryptFile(inputPath, outputPath, -key);
    }

    private static String encryptString(String text, int key) {
        StringBuilder encrypted = new StringBuilder();

        key = key % 26;
        if (key < 0) key += 26;

        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                int shifted = (c - 'A' + key) % 26;
                encrypted.append((char) ('A' + shifted));
            } else if (c >= 'a' && c <= 'z') {
                int shifted = (c - 'a' + key) % 26;
                encrypted.append((char) ('a' + shifted));
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }

    public static void shiftArrayInPlace(char[] arr, int key) {
        key = key % 26;
        if (key < 0) key += 26;
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c >= 'A' && c <= 'Z') {
                int shifted = (c - 'A' + key) % 26;
                if (shifted < 0) shifted += 26;
                arr[i] = (char) ('A' + shifted);
            }
        }
    }
}
