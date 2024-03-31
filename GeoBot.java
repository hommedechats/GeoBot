public class GeoBot {
    
    private int streakCount;
    private static String[] difficultyStates = {"easy", "normal", "hard", "impossible"};
    private int currentState;
    private String userName;

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
    public String [] getDifficultyStates(){
        return difficultyStates;
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
}