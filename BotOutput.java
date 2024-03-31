public class BotOutput {

    public String getFirstNChars(String line, int charCount){

        String nChars;
        char[] lineArray = new char[line.length()];

        for(int i = 0; i < charCount; ++i){
            if(i >= line.length()){
                charCount = line.length();
                break;
            }
            lineArray[i] = line.charAt(i);
        }
        nChars = new String(lineArray);

        return "The first " + charCount + " letters are: " + nChars;

    }
    public void printStreakCount(int streak, String name){
        
        if(streak == 3){
            System.out.println("You're doing great " + name + ":)");
        }
        else if(streak == 5){
            System.out.println("You're doing fantastic :)");
        }
        else if(streak == 10){
            System.out.println("You've answered correct 10 times in a row!!!");
        }
        else if(streak == -3){
            System.out.println("You'll get it next time");
        }
        else if(streak == -5){
            System.out.println("Wrong again :/");
        }
        else if(streak == -10){
            System.out.println("You're hopeless, " + name);
        }
    }
}
