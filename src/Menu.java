import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private static final int TO_ENCRYPT = 1;
    private static final int TO_DECRYPT = 2;
    private static final int UKRAINIAN_ALPHABET = 1;
    private static final int ENGLISH_ALPHABET = 2;
    private static final int SYMBOL_ALPHABET = 3;
    private static boolean isRunning = true;
    private static String localization;
    private ConsoleWriter consoleWriter;
    private Scanner scanner;
    private Decrypt decrypt;
    private Encrypt encrypt;
    private FileReader fileReader;
    private String wordFromFile = "";

    public Menu(ConsoleWriter consoleWriter, Scanner scanner) {
        this.consoleWriter = consoleWriter;
        this.scanner = scanner;
        start();
    }

    public static String getLocalization() {
        return localization;
    }

    public static void setRunning(boolean running) {
        isRunning = running;
    }

    public void start() {
        while (isRunning) {
            getLocalizationFromConsole();
            getPathFromConsole();
            int mode = getEncryptOrDecryptFromConsole();
            if (mode == TO_ENCRYPT) {
                encrypt = new Encrypt(consoleWriter);
                encrypt.encrypt(wordFromFile);
            } else if (mode == TO_DECRYPT) {
                decrypt = new Decrypt(consoleWriter);
                decrypt.decrypt(wordFromFile);
            }
        }
    }

    private int getEncryptOrDecryptFromConsole() {
        consoleWriter.print(Constants.ASK_USER_WHICH_MODE_TO_USE);
        while (true) {
            var temp = scanner.next();
            Scanner userChoice = new Scanner(temp);
            if (userChoice.hasNextInt()) {
                if (Integer.valueOf(temp) == TO_ENCRYPT) {
                    consoleWriter.print(Constants.YOU_CHOICE_TO_ENCRYPT);
                    return 1;
                } else if (Integer.valueOf(temp) == TO_DECRYPT) {
                    consoleWriter.print(Constants.YOU_CHOICE_TO_DECRYPT);
                    return 2;
                } else {
                    consoleWriter.print(Constants.NOT_FOUND_MODE_WITH_THIS_NUMBER);
                }
            } else {
                consoleWriter.print(Constants.YOU_TYPE_NOT_INT);
            }
        }
    }

    private void getLocalizationFromConsole() {
        consoleWriter.print(Constants.LANGUAGE_LOCALIZATION);
        Scanner userWord = new Scanner(System.in);
        while (true) {
            var temp = userWord.next();
            Scanner userLocalization = new Scanner(temp);
            if (userLocalization.hasNextInt()) {
                switch (Integer.valueOf(temp)) {
                    case UKRAINIAN_ALPHABET -> {
                        localization = Constants.UKRAINIAN_ALPHABET;
                        return;
                    }
                    case ENGLISH_ALPHABET -> {
                        localization = Constants.ENGLISH_ALPHABET;
                        return;
                    }
                    case SYMBOL_ALPHABET -> {
                        localization = Constants.SYMBOL_ALPHABET;
                        return;
                    }
                    default -> {
                        consoleWriter.print(Constants.NOT_FOUND_MODE_WITH_THIS_NUMBER);
                    }
                }
            } else {
                consoleWriter.print(Constants.YOU_TYPE_NOT_INT);
            }
        }
    }

    private void getPathFromConsole() {
        consoleWriter.print(Constants.TO_ENCRYPT);
        String path;
        while (true) {
            path = scanner.nextLine();
            try {
                FileReader fileReaderFromConsole = new FileReader(path);
                fileReader = fileReaderFromConsole;
                readWordFromFile();
                consoleWriter.print(Constants.FILE_EXIST);
                return;
            } catch (FileNotFoundException e) {
                consoleWriter.print(Constants.FILE_NOT_EXIST);
            }
        }
    }

    private void readWordFromFile() {
        try (FileReader readerForReadFromFile = fileReader) {
            int tempForReadFromInputStream = 0;
            while ((tempForReadFromInputStream = readerForReadFromFile.read()) != -1) {
                wordFromFile += (char) tempForReadFromInputStream;
            }
            wordFromFile = wordFromFile.toLowerCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
