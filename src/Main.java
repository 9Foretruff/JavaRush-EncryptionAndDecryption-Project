import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleWriter consoleWriter = new ConsoleWriter();
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(consoleWriter,scanner);
    }
}
