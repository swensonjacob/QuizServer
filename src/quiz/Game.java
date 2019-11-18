package quiz;

import java.io.IOException;
import java.net.Socket;

public class Game implements Runnable {

    private int questionsPerRound;
    private int rounds;
    private Player[] players;
    private Database database;

    public Game(Socket socketP1, Socket socketP2) {
        Settings gameSettings = new Settings();
        players = new Player[] {new Player("Player 1", socketP1),new Player("Player 2", socketP2)};
        players[0].setOpponent(players[1]);
        players[1].setOpponent(players[0]);
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
        }
        printTotalPoints();
    }
    /**
     * Ny runda skapas där val av kategori inhämtas från player1, genom en nästlad for-loop hämtas en slumpad fråga och
     * skickas till båda spelarna i players[] det antal ggr som är lika med frågor per runda enligt settings
     */
    public void newRound() throws IOException, ClassNotFoundException {
        CategoryName categoryName = players[0].getCategoryFromUser();
        for (int i = 0; i < questionsPerRound; i++) {
            Question question = database.getRandomQuestionFromCategory(categoryName);
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
        players[0].sendRoundPoints();
        players[1].sendRoundPoints();
        players[0].setRoundPoints(0);
        players[1].setRoundPoints(0);
    }
    /**
     * Skickar totalt resultat till användaren
     */
    public void printTotalPoints() throws IOException {
        players[0].sendTotalPoints();
        players[1].sendTotalPoints();
    }
}
