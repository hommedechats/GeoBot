public class BotOutput {

    public static String getFirstNChars(String line, int charCount){

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
}
