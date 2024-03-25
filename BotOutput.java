public class BotOutput {

    public void printFirstNChars(String line, int charCount){

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
        System.out.println("The first " + charCount + " letters are: " + nChars);

    }
}
