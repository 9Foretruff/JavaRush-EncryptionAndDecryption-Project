import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Decrypt {
    private ConsoleWriter consoleWriter;

    public Decrypt(ConsoleWriter consoleWriter) {
        this.consoleWriter = consoleWriter;
    }

    public void decrypt(String word) {
        File file = new File("Brute force");
        consoleWriter.print(Constants.FILE_BRUTE_FORCE_CREATED);
        try (FileWriter fileWriter = new FileWriter(file);) {
            for (int i = 0; i < Menu.getLocalization().length(); i++) {
                for (int j = 0; j < word.length(); j++) {
                    fileWriter.write(symbolRightShift(word.charAt(j), i));
                }
                fileWriter.write("\n");
            }
            Scanner scanner = new Scanner(System.in);
            consoleWriter.print(Constants.USER_WANT_TO_EXIT);
            String choice = scanner.nextLine();
            if ("exit".equals(choice.toLowerCase())) {
                consoleWriter.print(Constants.THANKS_FOR_USING);
                Menu.setRunning(false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private char symbolRightShift(char symbol, int shift) {
        if (Menu.getLocalization().indexOf(symbol) != -1) {
            return Menu.getLocalization().charAt((Menu.getLocalization().indexOf(symbol) + shift) % Menu.getLocalization().length());
        } else {
            return symbol;
        }
    }

}
