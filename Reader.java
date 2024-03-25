import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private final String TEXT_FILE = "countries.txt";
    private final String SEPARATOR = "~";

    public String[][] readSeparatedLinesFromTxt() {
        String[][] capitals = new String[4][16];
        int rowIndex = 0;
        int columnIndex = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(TEXT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(SEPARATOR)) {
                    columnIndex++;
                    rowIndex = 0;
                } else {
                    capitals[columnIndex][rowIndex] = line;
                    rowIndex++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return capitals;
    }
}
