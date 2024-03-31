import java.util.Scanner;

public class BotInput {

    public int getIntInRange(Scanner scanner, int lowerBound, int upperBound){

        System.out.println("How good is your knowledge on capital cities from " + lowerBound + " to "+ upperBound +"?");
        
        while(true){
            try{
                int intInRange = Integer.parseInt(scanner.nextLine());
                
                if(intInRange >= lowerBound && intInRange <= upperBound){
                    return intInRange;
                }
                else{
                    System.out.println("Please enter a number from " + lowerBound + " to " + upperBound + " :)");
                }
            }
            catch (NumberFormatException e){
                System.out.println("Please only use digits :)");
            }  
        }      
    }
}
