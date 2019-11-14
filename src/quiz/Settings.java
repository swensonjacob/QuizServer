package quiz;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {

   private int numberOfRounds;
   private int numberOfQuestions;

    public Settings() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/quiz/config.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.numberOfRounds = Integer.parseInt(properties.getProperty("rounds"));
        this.numberOfQuestions = Integer.parseInt(properties.getProperty("questions"));

    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }
}
