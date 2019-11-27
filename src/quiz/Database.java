package quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Database implements Serializable {

    private List<Question> questions;
    private Settings settings;
    public static List<ScoreBoard> scoreBoardList = new ArrayList<>();


    public Database(Settings settings) {
        this.settings = settings;
        questions = new ArrayList<>();
        questions.add(new Question(CategoryName.INREDNING, "Hur många ben har en vanlig stol?", "1", "2", "124", "4"));
        questions.add((new Question(CategoryName.INREDNING, "Vem har designat den ikoniska fotöljen ägget?", "Billy the kid", "Jonathan Lejonhjärta", "Daniel Sedin", "Arne Jacobsen")));
        questions.add((new Question(CategoryName.INREDNING, "Vad heter stolens fula lillebror?", "Hammock", "Skänk", "Gökur", "Pall")));
        questions.add((new Question(CategoryName.DJUR, "Hur lång är en hammarhaj?", "600 m", "300 000 m", "1 cm", "2 m")));
        questions.add((new Question(CategoryName.DJUR, "Vad kostar en standard-ko?", "1,2 milj kr", "54 kr", "600 kr", "6 000 kr")));
        questions.add((new Question(CategoryName.DJUR, "Lever dronten?", "Jajjamensan", "Det beror på", "Ingen som vet", "Nepps keps")));
        questions.add((new Question(CategoryName.MUSIK, "Vem var inte en medlem av ABBA", "Benny Andersson", "Björn Ulveus", "Anne-Frid Lyngstad", "Clabbe af Geijerstam")));
        questions.add((new Question(CategoryName.MUSIK, "Vilket instrument spelar Lars Ulrich?", "Trombon", "Mungiga", "Fan", "Trummor")));
        questions.add((new Question(CategoryName.MUSIK, "Vilken artist är känd för sin moonwalk?", "Harvey Weinstein", "R Kelly", "Hagamannen", "Michael Jackson")));
        questions.add((new Question(CategoryName.MUSIK, "Vem sjunger låten Tuff brud i lyxförpackning?", "Stor-Babs", "Lil' Babs", "Babsan", "Lill-Babs")));
        questions.add((new Question(CategoryName.SPORT, "Vad heter Daniel Sedins bror?", "Hjalle", "Heavy", "Lionel Richie", "Henrik Sedin")));
        questions.add((new Question(CategoryName.SPORT, "Vad har patrik Sjöberg hoppat som högst?", "1,25 m", "980 m", "0,7 m", "2,42 m")));
        questions.add((new Question(CategoryName.SPORT, "Vad kallas biljardpinnen?", "Gaffel", "Rododendron", "Heap", "Kö")));
        questions.add((new Question(CategoryName.TV, "Vad hette Mikael Persbrandts karaktär i rederiet?", "Svenne Rubin", "Pär Le Port", "Micke Samuelsson", "Ola Simonsson")));
        questions.add((new Question(CategoryName.TV, "Vilket program existerar?", "Hela Sverige hälsar", "Hela Sverige stavar", "Hela Sverige grinar", "Hela Sverige bakar")));
        questions.add((new Question(CategoryName.TV, "Vem var expedition robinsons första programledare?", "Hans Fahlén", "Mr T", "Dr Phil", "Harald Treutiger")));
        questions.add((new Question(CategoryName.TV, "Vem av dessa karaktärer finns inte i Twin Peaks?", "Leland Palmer", "Log Lady", "Dale Cooper", "Charlie Brown")));
        questions.add((new Question(CategoryName.TV, "Hur många säsonger av Friends finns det?", "15", "20", "5", "10")));
        questions.add((new Question(CategoryName.TV, "Vilken serie är karaktären Walter White ifrån?", "House", "Friends", "Game of Thrones", "Breaking Bad")));
        questions.add((new Question(CategoryName.FILM, "Vilket år kom den första Harry Potter-filmen ut?", "2011", "1999", "2005", "2001")));
        questions.add((new Question(CategoryName.FILM, "Vilket år är första Blade Runner ifrån?", "1979", "1992", "1988", "1982")));
        questions.add((new Question(CategoryName.FILM, "När dog regissören Alfred Hitchcock?", "1950", "1960", "1970", "1980")));
        questions.add((new Question(CategoryName.FILM, "Vad heter julfilmen från 1988 där Bruce Willis spelar huvudrollen?", "Scrooge", "The Holiday", "Home Alone", "Die Hard")));
        questions.add((new Question(CategoryName.FILM, "Vad heter regissören som gjort Unbreakable?", "Steven Spielberg", "Stanley Kubrick", "Martin Scorsese", "M. Night Shyamalan")));
        questions.add((new Question(CategoryName.FILM, "Vilken film är inte en Quentin Tarantino-film?", "Death Proof", "Pulp Fiction", "Jackie Brown", "Shaft")));


    }

    public List<Question> getRoundQuestions(CategoryName category) {
        List<Question> questionList = questions.stream().filter(c -> c.getCategory().equals(category)).collect(Collectors.toList());
        Collections.shuffle(questionList);
        return questionList.subList(0,settings.getQuestionsPerRound());
    }

    public static void returnPlayerPoints(){
        scoreBoardList.forEach(scoreBoard -> {
            System.out.println(scoreBoard);
        } );
    }

}
