import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private static boolean isRunning = true;
    private static String localization;
    private ConsoleWriter consoleWriter;
    private Scanner scanner;
    private Decrypt decrypt;
    private Encrypt encrypt;
    private FileReader fileReader;
    private String wordFromFile = "";
    private static final int TO_ENCRYPT = 1;
    private static final int TO_DECRYPT = 2;

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
            File file = new File("hello");
            whichLocalization();
            userTypePathOfFile();
            int mode = whichModeEncryptOrDecrypt();
            if (mode == TO_ENCRYPT) {
                encrypt = new Encrypt(consoleWriter);
                encrypt.encrypt(wordFromFile);
            } else if (mode == TO_DECRYPT){
                decrypt = new Decrypt(consoleWriter);
                decrypt.decrypt(wordFromFile);
            }
        }
    }

    private int whichModeEncryptOrDecrypt() {
        consoleWriter.print(Constants.ASK_USER_WHICH_MODE_TO_USE);
        while (true) {
            var temp = scanner.next();
            Scanner scanner1 = new Scanner(temp);
            if (scanner1.hasNextInt()) {
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

    private void whichLocalization() {
        consoleWriter.print(Constants.LANGUAGE_LOCALIZATION);
        Scanner scanner1 = new Scanner(System.in);
        while (true) {
            var temp = scanner1.next();
            Scanner scanner2 = new Scanner(temp);
            if (scanner2.hasNextInt()) {
                switch (Integer.valueOf(temp)) {
                    case 1 -> {
                        localization = Constants.UKRAINIAN_ALPHABET;
                        return;
                    }
                    case 2 -> {
                        localization = Constants.ENGLISH_ALPHABET;
                        return;
                    }
                    case 3 -> {
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

    private void userTypePathOfFile() {
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
