package quiz;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game implements Runnable {

    private List<Player> players;
    private Database database;
    private Settings settings;

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

    public void newGame() throws IOException, ClassNotFoundException {
        for (int i = 0; i < settings.getNumberOfRounds(); i++) {
            newRound();
            if (i != settings.getNumberOfRounds() - 1) {
                players.get(0).closeRound(false);
                players.get(0).sendPoints();
            }
            Collections.reverse(players);
        }
        players.get(0).closeRound(true);
        players.get(0).sendPoints();
    }

    public void newRound() throws IOException, ClassNotFoundException {
        List<Question> roundQuestions = database.getRoundQuestions(players.get(0).getCategoryFromUser());
        for (Player player : players) {
            player.getOpponent().sendString("Inväntar svar från motståndare");
            for (Question roundQuestion : roundQuestions) {
                setOfQuestions(player, roundQuestion);
            }
        }

    }

    public void setOfQuestions(Player player, Question question) throws IOException, ClassNotFoundException {
        player.sendQuestion(question);
        Object inputFromPlayer = player.getInput();
        if (inputFromPlayer.equals(question.getAnswerCorrect())) {
            player.addPoint();
        }else if(inputFromPlayer.equals("GETTUPP")){
            player.getOpponent().sendString("GETTUPP");
        }
    }
}