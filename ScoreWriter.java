import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScoreWriter {


    public static void writeNewHighscore(int score) throws IOException {
        FileWriter myWriter = new FileWriter("Highscore.txt");
        myWriter.write(String.valueOf(score));
        myWriter.close();
    }

    public static int readScores() throws FileNotFoundException {
        String data = "";
        File highscore = new File("Highscore.txt");
        Scanner myReader = new Scanner(highscore);
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();

        if (data.trim().isEmpty()) {
            return 0;
        }

        return Integer.parseInt(data);
    }

}
