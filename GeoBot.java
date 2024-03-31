import java.util.Scanner;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class GeoBot {
    
    private int streakCount;
    private static String[] difficultyStates = {"easy", "normal", "hard", "impossible"};
    private int currentState;
    private String userName;

    private static final String TEXT_FILE = "countries.txt";
    private static final String SEPARATOR = "~";
    private static final int ROW_COUNT = 15;
    private static final int HINT_CHAR_COUNT = 2;
    private static final String CAPITAL_SEPATOR = ",";

    public GeoBot(){

        this.streakCount = 0;
        this.currentState = 0;
        this.streakCount = 0;
        this.userName = "";
    }

    public void setStreakCount(int streakCount){
        this.streakCount = streakCount;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setCurrentState(int currentState){
        this.currentState = currentState;
    }

    public String getUserName(){
        return userName;
    }
    public int getStreakCount(){
        return streakCount;
    }

    public int getCurrentState(){
        return currentState;
    }

    public void quizLoop(Scanner scanner, String [][] countriesAndCapitals){
        
        String answer = "";
        Random random = new Random();
        InputHandler inputHandler = new InputHandler();

        while (!answer.equalsIgnoreCase("bye")){

            int key = random.nextInt(ROW_COUNT);
            String[] parts = countriesAndCapitals[currentState][key].split(CAPITAL_SEPATOR);

            answer = inputHandler.promptUser("What is the capital of " + parts[0] + "?");
            if(!compareAnswer(parts[1], answer)){
                answer = inputHandler.promptUser("Incorrect :/ \nDo you want a hint? Say \"please\" if you do");
                if(answer.equalsIgnoreCase("please")){
                    answer = inputHandler.promptUser(BotOutput.getFirstNChars(parts[1], HINT_CHAR_COUNT));
                    if(compareAnswer(parts[1], answer)){
                        System.out.println("Correct :)");
                    }
                }
                else{
                    System.out.println("The correct answer was: " + parts[1]);
                }
            }
            else{
                System.out.println("Correct :)");
            }
            BotOutput.printStreakCount(streakCount, userName); 
            if(isChangedDifficulty()){
                  System.out.println("I've changed the difficulty level to " + difficultyStates[currentState] + " :)");
            }
        }
    }

    public boolean compareAnswer(String correctCapital, String guessCapital){//@fix    -What is the capital of North Korea?
                                                                                // -bye
        if(guessCapital.equalsIgnoreCase(correctCapital)){                      // -Incorrect :/
            if(streakCount < 0){
                setStreakCount(1);
            }
            else{
                setStreakCount(streakCount + 1);
            }
            return true;
        }
        else{
            if(streakCount > 0){
                setStreakCount(0);
            }
            else{
                setStreakCount(streakCount - 1);
            }
            return false;
        }        
    }

    public boolean isChangedDifficulty(){

        if(streakCount % 3 == 0 && currentState != 3 && streakCount > 0){
            setCurrentState(currentState + 1);
            return true;
        }
        else if(streakCount % 3 == 0 && currentState != 0 && streakCount < 0){
            setCurrentState(currentState - 1);
            return true;
        }
        return false;
    }

    public static void main(String[] args){

        BotOutput botUI = new BotOutput();
        GeoBot geoBot = new GeoBot();
        Scanner scanner = new Scanner(System.in);
        BotInput botInput = new BotInput();

        String[][] capitalsf = Reader.readSeparatedLinesFromTxt(TEXT_FILE, SEPARATOR);

        System.out.println("Hi, my name is GeoBot and I will help you learn the capital cities of the world. What is your name?");
        geoBot.setUserName(scanner.nextLine());
        System.out.println("Nice to meet you, " + geoBot.getUserName() + " :)\nYou can stop our conversation anytime by saying \"bye\"");
        geoBot.setCurrentState(botInput.getIntInRange(scanner, 0, 3));
        geoBot.quizLoop(scanner, capitalsf);

        System.out.println("Aww, I'll miss you, " + geoBot.getUserName() + " :(( ");

        scanner.close();
    }
}