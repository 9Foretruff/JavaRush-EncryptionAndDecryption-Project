import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Encrypt {
    private int shift;
    private ConsoleWriter consoleWriter;

    public Encrypt(ConsoleWriter consoleWriter) {
        this.consoleWriter = consoleWriter;
    }

    public void encrypt(String word) {
        shift = howMuchMoveToRight();
        String encryptWord = "";
        for (int i = 0; i < word.length(); i++) {
            encryptWord = encryptWord + symbolRightShift(word.charAt(i), shift);
        }
        makeFileWithEncryptWord(encryptWord);
        Scanner scanner = new Scanner(System.in);
        consoleWriter.print(Constants.USER_WANT_TO_EXIT);
        String choice = scanner.nextLine();
        if ("exit".equals(choice.toLowerCase())) {
            consoleWriter.print(Constants.THANKS_FOR_USING);
            Menu.setRunning(false);
        }
    }

    private char symbolRightShift(char symbol, int shift) {
        if (Menu.getLocalization().indexOf(symbol) != -1) {
            return Menu.getLocalization().charAt((Menu.getLocalization().indexOf(symbol) + shift) % Menu.getLocalization().length());
        } else {
            return symbol;
        }
    }

    private int howMuchMoveToRight() {
        consoleWriter.print(Constants.TYPE_SHIFT);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            var temp = scanner.next();
            Scanner scanner1 = new Scanner(temp);
            if (scanner1.hasNextInt()) {
                return Integer.valueOf(temp);
            } else {
                consoleWriter.print(Constants.YOU_TYPE_NOT_INT);
            }
        }
    }

    private void makeFileWithEncryptWord(String word) {
        File file = new File("EncryptWord");
        try (FileWriter fileWriter = new FileWriter(file)){
            for (int i = 0; i < word.length(); i++) {
                fileWriter.write(word.charAt(i));
                fileWriter.flush();
            }
            consoleWriter.print(Constants.TEXT_IS_SUCCESSFULLY_ENCRYPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
