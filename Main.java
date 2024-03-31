import java.util.Scanner;
import java.util.Random;

public class Main {
     public static void main(String[] args) {

        final String TEXT_FILE = "countries.txt";
        final String SEPARATOR = "~";
        final int ROW_COUNT = 15;
        final int HINT_CHAR_COUNT = 2;
        final String CAPITAL_SEPATOR = ",";

        BotOutput botOutput = new BotOutput();
        GeoBot geoBot = new GeoBot();
        Scanner scanner = new Scanner(System.in);
        BotInput botInput = new BotInput();

        String[][] capitals = Reader.readSeparatedLinesFromTxt(TEXT_FILE, SEPARATOR, geoBot.getDifficultyStates().length, ROW_COUNT);

        System.out.println("Hi, my name is GeoBot and I will help you learn the capital cities of the world. What is your name?");
        geoBot.setUserName(scanner.nextLine());
        System.out.println("Nice to meet you, " + geoBot.getUserName() + " :)\nYou can stop our conversation anytime by saying \"bye\"");
        geoBot.setCurrentState(botInput.getIntInRange(scanner, 0, geoBot.getDifficultyStates().length - 1));

        String answer = "";
        Random random = new Random();
        InputHandler inputHandler = new InputHandler();

        while (!answer.equalsIgnoreCase("bye")){

            int key = random.nextInt(ROW_COUNT);
            String[] parts = capitals[geoBot.getCurrentState()][key].split(CAPITAL_SEPATOR);

            answer = inputHandler.promptUser("What is the capital of " + parts[0] + "?");
            if(!geoBot.compareAnswer(parts[1], answer)){
                answer = inputHandler.promptUser("Incorrect :/ \nDo you want a hint? Say \"please\" if you do");
                if(answer.equalsIgnoreCase("please")){
                    answer = inputHandler.promptUser(botOutput.getFirstNChars(parts[1], HINT_CHAR_COUNT));
                    if(geoBot.compareAnswer(parts[1], answer)){
                        System.out.println("Correct :)");
                    }
                }
                System.out.println("The correct answer is: " + parts[1]);
            }
            else{
                System.out.println("Correct :)");
            }
            
            botOutput.printStreakCount(geoBot.getStreakCount(), geoBot.getUserName()); 
            if(geoBot.isChangedDifficulty()){
                  System.out.println("I've changed the difficulty level to " + geoBot.getDifficultyStates()[geoBot.getCurrentState()] + " :)");
            }
        }

        System.out.println("Aww, I'll miss you, " + geoBot.getUserName() + " :(( ");

        scanner.close();
     }
}
