package quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Database implements Serializable {


    private QuestionCategory animal = new QuestionCategory();
    private QuestionCategory furniture = new QuestionCategory();

    List<QuestionCategory> allQuestions = new ArrayList<>();

    public Database() {
        furniture.addQuestion(new Question("Hur många ben har en vanlig stol?", "1", "2", "124", "4"));
        furniture.addQuestion(new Question("Vem har designat den ikoniska fotöljen ägget?", "Billy the kid", "Jonathan Lejonhjärta", "Daniel Sedin", "Arne Jacobsen"));
        furniture.addQuestion(new Question("Vad heter stolens fula lillebror?", "Hammock", "Skänk", "Gökur", "Pall"));
        animal.addQuestion(new Question("Hur lång är en hammarhaj?", "600 m", "300 000 m", "1 cm", "2 m"));
        animal.addQuestion(new Question("Vad kostar en standard-ko?", "1,2 milj kr", "54 kr", "600 kr", "6 000 kr"));
        animal.addQuestion(new Question("Lever dronten?", "Jajjamensan", "Det beror på", "Ingen som vet", "Nepps keps"));
        allQuestions.add(animal);
        allQuestions.add(furniture);
    }

    public List<QuestionCategory> getAllQuestions() {
        return allQuestions;
    }

    public Question getRandomQuestionFromCategory(CategoryName category) {
        if (category.equals(CategoryName.ANIMAL)) {
           return getRandomQuestion(animal);
        } else if (category.equals(CategoryName.FURNITURE)) {
            return getRandomQuestion(furniture);
        }
        return null;
    }
    private Question getRandomQuestion (QuestionCategory category) {
        Random r = new Random();
        return category.getQuestions().get(r.nextInt(category.getQuestions().size()));
    }
}
