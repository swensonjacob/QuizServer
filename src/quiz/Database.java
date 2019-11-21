package quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Database implements Serializable {

   private List<Question> questions;

    public Database() {
        questions = new ArrayList<>();
        questions.add(new Question(CategoryName.INREDNING,"Hur många ben har en vanlig stol?", "1", "2", "124", "4"));
        questions.add((new Question(CategoryName.INREDNING,"Vem har designat den ikoniska fotöljen ägget?", "Billy the kid", "Jonathan Lejonhjärta", "Daniel Sedin", "Arne Jacobsen")));
        questions.add((new Question(CategoryName.INREDNING,"Vad heter stolens fula lillebror?", "Hammock", "Skänk", "Gökur", "Pall")));
        questions.add((new Question(CategoryName.DJUR,"Hur lång är en hammarhaj?", "600 m", "300 000 m", "1 cm", "2 m")));
        questions.add((new Question(CategoryName.DJUR,"Vad kostar en standard-ko?", "1,2 milj kr", "54 kr", "600 kr", "6 000 kr")));
        questions.add((new Question(CategoryName.DJUR,"Lever dronten?", "Jajjamensan", "Det beror på", "Ingen som vet", "Nepps keps")));
        questions.add((new Question(CategoryName.MUSIK,"Vem var inte en medlem av ABBA", "Benny Andersson", "Björn Ulveus", "Anne-Frid Lyngstad", "Clabbe af Geijerstam")));
        questions.add((new Question(CategoryName.MUSIK,"Vilket instrument spelar Lars Ulrich?", "Trombon", "Mungiga", "Fan", "Trummor")));
        questions.add((new Question(CategoryName.SPORT,"Vad heter Daniel Sedins bror?", "Hjalle", "Heavy", "Lionel Richie", "Henrik Sedin")));
        questions.add((new Question(CategoryName.SPORT,"Vad har patrik Sjöberg hoppat som högst??", "1,25 m", "980 m", "0,7 m", "2,42 m")));
        questions.add((new Question(CategoryName.TV,"Vad hette Mikael Persbrandts karaktär i rederiet?", "Svenne Rubin", "Pär Le Port", "Micke Samuelsson", "Ola Simonsson")));
        questions.add((new Question(CategoryName.TV,"Vilke program existerar?", "Hela Sverige hälsar", "Hela Sverige stavar", "Hela Sverige grinar", "Hela Sverige bakar")));
    }
    /**
     * Hämtar slumpad fråga utifrån ingående CategoryName.
     */
    public Question getRandomQuestionFromCategory(CategoryName category) {
      return getRandomQuestion(questions.stream().filter(c -> c.getCategory().equals(category)).collect(Collectors.toList()));
    }
    private Question getRandomQuestion (List<Question> questionList) {
        Collections.shuffle(questionList);
        return questionList.get(0);
    }
}
