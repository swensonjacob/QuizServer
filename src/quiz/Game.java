package quiz;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game implements Runnable {

    private int questionsPerRound;
    private int rounds;
    private List<Player> players;
    private Database database;

    public Game(Socket socketP1, Socket socketP2) {
        Settings gameSettings = new Settings();
        players = Arrays.asList(new Player("Player 1", socketP1), new Player("Player 2", socketP2));
        players.get(0).setOpponent(players.get(1));
        players.get(1).setOpponent(players.get(0));
        this.database = new Database();
        this.questionsPerRound = gameSettings.getNumberOfQuestions();
        this.rounds = gameSettings.getNumberOfRounds();
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
        for (int i = 0; i < rounds; i++) {
            newRound();
            if (i != rounds - 1) {
                printRoundPoints();
            }
            Collections.reverse(players);
        }
        printTotalPoints();
    }
    /**
     * Ny runda skapas där val av kategori inhämtas från player1, genom en nästlad for-loop hämtas en slumpad fråga och
     * skickas till båda spelarna i players det antal ggr som är lika med frågor per runda enligt settings
     */
    public void newRound() throws IOException, ClassNotFoundException {
        CategoryName roundCategory = players.get(0).getCategoryFromUser();
        for (int i = 0; i < questionsPerRound; i++) {
            Question question = database.getRandomQuestionFromCategory(roundCategory);
            for (Player player : players) {
                setOfQuestions(player, question);
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
