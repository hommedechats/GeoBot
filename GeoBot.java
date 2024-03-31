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
    private static final int ROW_COUNT = 16;
    private static final int HINT_CHAR_COUNT = 2;
    
    public static String[][] capitals = new String[difficultyStates.length][ROW_COUNT];

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

    public void quizLoop(Scanner scanner){

        int key;
        String answer = "";
        Random random = new Random();
        InputHandler inputHandler = new InputHandler();

        while (!answer.equalsIgnoreCase("bye")){

            key = random.nextInt(capitals[0].length);
            String[] parts = capitals[currentState][key].split(","); //@fix overreaches probably
            String country = parts[0];
            String capital = parts[1];

            answer = inputHandler.promptUser("What is the capital of " + country + "?");
           
            if(!compareAnswer(capital, answer)){

                answer = inputHandler.promptUser("Do you want a hint? Say \"please\" if you do");
                if(answer.equalsIgnoreCase("please")){

                    answer = inputHandler.promptUser(BotOutput.getFirstNChars(capital, HINT_CHAR_COUNT));
                    if(!compareAnswer(capital, answer)){
                        System.out.println("The correct answer was: " + capital);
                    }
                }
                else {
                    System.out.println("The correct answer was: " + capital);
                }
            }
            BotOutput.printStreakCount(streakCount, userName); 
            if(changeDifficulty()){
                System.out.println("I've changed the difficulty level to " + difficultyStates[currentState] + " :)");
            }
        }        
    }
    public boolean compareAnswer(String correctCapital, String guessCapital){//@fix    -What is the capital of North Korea?
                                                                                // -bye
        if(guessCapital.equalsIgnoreCase(correctCapital)){                      // -Incorrect :/
            System.out.println("Correct :)");
            if(streakCount < 0){
                setStreakCount(1);
            }
            else{
                setStreakCount(streakCount + 1);
            }
            return true;
        }
        else{
            System.out.println("Incorrect :/");
            if(streakCount > 0){
                setStreakCount(0);
            }
            else{
                setStreakCount(streakCount - 1);
            }
            return false;
        }        
    }

    public boolean changeDifficulty(){

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
    

    public static int getStartingDifficulty(Scanner scanner){

        System.out.println("How good is your knowledge on capital cities from 1 to 4?");

        while(true){
            try{
                String inputLine = scanner.nextLine();
                int startingKnowledge = Integer.parseInt(inputLine);
                if(startingKnowledge >= 1 && startingKnowledge <= 4){
                    System.out.println("Got it. Let's start you off on the " + difficultyStates[startingKnowledge - 1] + " difficulty :)");

                    return startingKnowledge - 1;
                }
                else{
                    System.out.println("Please enter a number from 1 to 4 :)");
                }
            }
            catch (NumberFormatException e){
                System.out.println("Please only use digits :)");
            }  
        }      

    }

    public static void readSeparatedLinesFromTxt(String filename, String separator){

        int rowIndex = 0;
        int columnIndex = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){//reader closes at the end of the try block
            String line;
            while ((line = reader.readLine()) != null){
                if(line.equals(separator)){
                    columnIndex++;
                    rowIndex = 0;
                }
                else{
                    capitals[columnIndex][rowIndex] = line;
                    rowIndex++;
                }
            }
        }
         catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        BotOutput botUI = new BotOutput();
       // botUI.printFirstNChars(TEXT_FILE, HINT_CHAR_COUNT);
        GeoBot geoBot = new GeoBot();
        Scanner scanner = new Scanner(System.in);
        String[][] capitals = Reader.readSeparatedLinesFromTxt(TEXT_FILE, SEPARATOR);
        readSeparatedLinesFromTxt(TEXT_FILE, SEPARATOR);
        System.out.println("Hi, my name is GeoBot and I will help you learn the capital cities of the world. What is your name?");
        geoBot.setUserName(scanner.nextLine());
        System.out.println("Nice to meet you, " + geoBot.getUserName() + " :)\nYou can stop our conversation anytime by saying \"bye\"");
        geoBot.setCurrentState(getStartingDifficulty(scanner));
        geoBot.quizLoop(scanner);

        System.out.println("Aww, I'll miss you, " + geoBot.getUserName() + " :(( ");

        scanner.close();
    }
}
