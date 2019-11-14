package quiz;

import java.util.ArrayList;
import java.util.List;

public class QuestionCategory {

    List<Question> questions;

    public QuestionCategory() {
        this.questions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}
