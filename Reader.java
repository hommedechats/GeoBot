import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    public static String[][] readSeparatedLinesFromTxt(String fileName, String separator) {
        String[][] array = new String[4][15];
        int rowIndex = 0;
        int columnIndex = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.equals(separator)){
                    columnIndex++;
                    rowIndex = 0;
                }
                else{
                    array[columnIndex][rowIndex] = line;
                    rowIndex++;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }
}
