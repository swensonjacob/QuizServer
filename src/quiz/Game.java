package quiz;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game implements Runnable {

    private List<Player> players;
    private Database database;
    private Settings settings;
    private int roundCounter = 1;

    public Game(Socket socketP1, Socket socketP2) {
        players = Arrays.asList(new Player("Player 1", socketP1), new Player("Player 2", socketP2));
        players.get(0).setOpponent(players.get(1));
        players.get(1).setOpponent(players.get(0));
        settings = new Settings();
        this.database = new Database(settings);
    }

    @Override
    public void run() {
        try {
            newGame();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Skapar nytt spel där newRound() anropas antal ggr som finns angivet i settings.
     */
    public void newGame() throws IOException, ClassNotFoundException {
        for (int i = 0; i < settings.getNumberOfRounds(); i++) {
            newRound();
            if (i != settings.getNumberOfRounds() - 1) {
                Database.scoreBoardList.add(new ScoreBoard(players.get(0).getName(), players.get(0).getRoundPoints(), roundCounter));
                Database.scoreBoardList.add(new ScoreBoard(players.get(1).getName(), players.get(1).getRoundPoints(), roundCounter));
                printRoundPoints();
                roundCounter++;
            }

            Collections.reverse(players);
        }
        Database.scoreBoardList.add(new ScoreBoard(players.get(0).getName(), players.get(0).getRoundPoints(), roundCounter));
        Database.scoreBoardList.add(new ScoreBoard(players.get(1).getName(), players.get(1).getRoundPoints(), roundCounter));
        printTotalPoints();
        Database.returnPlayerPoints();
    }

    /**
     * Ny runda skapas där val av kategori inhämtas från player1, genom en nästlad for-loop hämtas en slumpad fråga och
     * skickas till båda spelarna i players det antal ggr som är lika med frågor per runda enligt settings
     */
    public void newRound() throws IOException, ClassNotFoundException {
        List<Question> roundQuestions = database.getRoundQuestions(players.get(0).getCategoryFromUser());
        for (Player player : players) {
            player.getOpponent().sendString("Inväntar svar från motståndare");
            for (int i = 0; i < roundQuestions.size(); i++) {
                setOfQuestions(player, roundQuestions.get(i));
            }
        }
    }

    /**
     * Skickar fråga till spelare, läser in svar från användaren.
     * är inkommande objekt en en String innehållande "correct" adderas spelarens poäng.
     */
    public void setOfQuestions(Player player, Question question) throws IOException, ClassNotFoundException {
        player.sendQuestion(question);
        Object inputFromPlayer = player.getInput();
        if (inputFromPlayer.equals("correct")) {
            player.addPoint();
        }
    }

    /**
     * Skickar rundans resultat till användaren och nollställer rundans poäng till nästa runda.
     */
    public void printRoundPoints() throws IOException {

        players.get(0).sendRoundPoints();
        players.get(1).sendRoundPoints();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        players.get(0).setRoundPoints(0);
        players.get(1).setRoundPoints(0);
    }

    /**
     * Skickar totalt resultat till användaren
     */
    public void printTotalPoints() throws IOException {
        players.get(0).sendTotalPoints();
        players.get(1).sendTotalPoints();

    }
}