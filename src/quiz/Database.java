package quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {

   private List<Question> questions;

    public Database() {
        questions = new ArrayList<>();
        questions.add(new Question("Hur många ben har en vanlig stol?","1","2","124","4"));
        questions.add(new Question("Hur lång är en hammarhaj?","600 m", "300 000 m", "1 cm", "2 m"));
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Question getRandomQuestion () {
        Question randomQuestion = questions.get(1);
        return randomQuestion;
    }

}
