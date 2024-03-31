import java.util.Scanner;

public class InputHandler {
    public Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String promptUser(String message) {

        System.out.println(message);

        return scanner.nextLine().trim();
    }
}
