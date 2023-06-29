import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Encrypt {
    private static final String EXIT = "exit";
    private int shift;
    private ConsoleWriter consoleWriter;

    public Encrypt(ConsoleWriter consoleWriter) {
        this.consoleWriter = consoleWriter;
    }

    public void encrypt(String word) {
        shift = askUserFromConsoleHowMuchMoveToRight();
        String encryptWord = "";
        for (int i = 0; i < word.length(); i++) {
            encryptWord = encryptWord + symbolRightShift(word.charAt(i), shift);
        }
        makeFileWithEncryptWord(encryptWord);
        Scanner userChoice = new Scanner(System.in);
        consoleWriter.print(Constants.USER_WANT_TO_EXIT);
        String choice = userChoice.nextLine();
        if (EXIT.equals(choice.toLowerCase())) {
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

    private int askUserFromConsoleHowMuchMoveToRight() {
        consoleWriter.print(Constants.TYPE_SHIFT);
        Scanner userWordFromConsole = new Scanner(System.in);
        while (true) {
            var temp = userWordFromConsole.next();
            Scanner askUserHowMuchMoveToRight = new Scanner(temp);
            if (askUserHowMuchMoveToRight.hasNextInt()) {
                return Integer.valueOf(temp);
            } else {
                consoleWriter.print(Constants.YOU_TYPE_NOT_INT);
            }
        }
    }

    private void makeFileWithEncryptWord(String word) {
        File file = new File("EncryptWord");
        try (FileWriter fileWriter = new FileWriter(file)) {
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
