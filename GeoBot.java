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
    private static final int ROW_COUNT = 16;
    
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

    public void quizLoop(Scanner scanner){

        int key;
        String answer = "";
        int result;
    
        while (!answer.equalsIgnoreCase("bye")){

            key = createRandomKey(capitals[0].length);
            String[] parts = capitals[currentState][key].split(",");
            String country = parts[0];
            String capital = parts[1];

            System.out.println("What is the capital of " + country + "?");
            answer = scanner.nextLine().trim();
            result = compareAnswer(capital, answer);
           
            if(result == 0){
                System.out.println("Do you want a hint? Say \"please\" if you do");
                answer = scanner.nextLine();
                if(answer.equalsIgnoreCase("please")){
                    giveHint(capital);
                    answer = scanner.nextLine();
                    result = compareAnswer(capital, answer);
                    if(result == 0){
                        System.out.println("The correct answer was: " + capital);
                    }
                }
                else {
                    System.out.println("The correct answer was: " + capital);
                }
            }
            printStreakCount(); 
            if(changeDifficulty()){
                System.out.println("I've changed the difficulty level to " + difficultyStates[currentState] + " :)");
            }
        }        
    }

    public static int createRandomKey(int maxValue){

        Random random = new Random();
        int key = random.nextInt(maxValue);

        return key;
    }

    public int compareAnswer(String correctCapital, String guessCapital){

        if(guessCapital.equalsIgnoreCase(correctCapital)){
            System.out.println("Correct :)");
            if(streakCount < 0){
                setStreakCount(1);
            }
            else{
                setStreakCount(streakCount + 1);
            }
            return 1;
        }
        else if(guessCapital.equalsIgnoreCase("Bye")){
            System.out.println("Aww, I'll miss you, " + userName + " :(( ");

            return -1;
        }
        else{
            System.out.println("Incorrect :/");
            if(streakCount > 0){
                setStreakCount(0);
            }
            else{
                setStreakCount(streakCount - 1);
            }
            return 0;
        }        
    }

    public void printStreakCount(){
        
        if(streakCount == 3){
            System.out.println("You're doing great " + userName + ":)");
        }
        else if(streakCount == 5){
            System.out.println("You're doing fantastic :)");
        }
        else if(streakCount == 10){
            System.out.println("You've answered correct 10 times in a row!!!");
        }
        else if(streakCount == -3){
            System.out.println("You'll get it next time");
        }
        else if(streakCount == -5){
            System.out.println("Wrong again :/");
        }
        else if(streakCount == -10){
            System.out.println("You're hopeless, " + userName);
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

    public void giveHint(String correctCapital){

        char firstLetter = correctCapital.charAt(0);
        char secondLetter = correctCapital.charAt(1);
        System.out.println("The first two letters are: " + firstLetter + secondLetter);
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

        BotUI botUI = new BotUI();
        botUI.printFirstNChars(TEXT_FILE, 50);
        GeoBot geoBot = new GeoBot();
        Scanner scanner = new Scanner(System.in);
        readSeparatedLinesFromTxt(TEXT_FILE, "~");
        System.out.println("Hi, my name is GeoBot and I will help you learn the capital cities of the world. What is your name?");
        geoBot.setUserName(scanner.nextLine());
        System.out.println("Nice to meet you, " + geoBot.getUserName() + " :)\nYou can stop our conversation anytime by saying \"bye\"");
        geoBot.setCurrentState(getStartingDifficulty(scanner));
        geoBot.quizLoop(scanner);

        scanner.close();
    }
}
