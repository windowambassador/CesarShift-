import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;

public class Main {
    public static void main(String[] args) {
        try {
            Path inputPath = Paths.get("input.txt");

            try (var writer = Files.newBufferedWriter(inputPath)) {
                for (int i = 0; i < 2; i++) {
                    writer.write("Hello World! Line ");
                    writer.newLine();
                }
            }

            char[] letters = {
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z'
            };

            CesarShift.encryptFile("input.txt", "encrypted.txt", 19);

            System.out.println("Зашифрованный текст из файла:");
            try (var reader = Files.newBufferedReader(Paths.get("encrypted.txt"))) {
                String line;
                int lineCount = 0;
                while ((line = reader.readLine()) != null && lineCount < 10) {
                    System.out.println(line);
                    lineCount++;
                }
            }

            CesarShift.decryptFile("encrypted.txt", "decrypted.txt", 19);
            System.out.println("\nРасшифрованный текст из файла:");
            try (var reader = Files.newBufferedReader(Paths.get("decrypted.txt"))) {
                String line;
                int lineCount = 0;
                while ((line = reader.readLine()) != null && lineCount < 10) {
                    System.out.println(line);
                    lineCount++;
                }
            }


            CesarShift.shiftArrayInPlace(letters, 19);
            System.out.print("Сдвинутый алфавит: ");
            for (char c : letters) {
                System.out.print(c);
            }
            System.out.println();

        } catch (NoSuchFileException e) {
            System.err.println("ОШИБКА: Файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("ОШИБКА ввода-вывода: " + e.getMessage());
        }
    }
}
