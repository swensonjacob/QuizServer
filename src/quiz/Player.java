package quiz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Player {

    private String name;
    private Player opponent;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int roundPoints;
    private ScoreBoard scoreBoard;


    public Player(String name, Socket socket) {
        this.scoreBoard = new ScoreBoard();
        this.roundPoints = 0;
        this.name = name;
        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public int getRoundPoints() {
        return roundPoints;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setRoundPoints(int roundPoints) {
        this.roundPoints = roundPoints;
    }

    public void sendQuestion(Question question) throws IOException {
        output.writeObject(question);
    }

    public void sendString(String message) throws IOException {
        output.writeObject(message);
    }

    public Object getInput() throws IOException, ClassNotFoundException {
        return input.readObject();
    }

    public void addPoint() {
        roundPoints++;
    }

    public void closeRound(boolean lastRound) {

        this.scoreBoard.addPlayerRoundScore(this.roundPoints);
        this.scoreBoard.addOpponentRoundScore(opponent.getRoundPoints());
        this.scoreBoard.newRound();
        this.scoreBoard.setLastRound(lastRound);

        this.opponent.scoreBoard.addPlayerRoundScore(opponent.getRoundPoints());
        this.opponent.scoreBoard.addOpponentRoundScore(this.roundPoints);
        this.opponent.scoreBoard.newRound();
        this.opponent.scoreBoard.setLastRound(lastRound);

        setRoundPoints(0);
        this.opponent.setRoundPoints(0);
    }

    public void sendPoints() throws IOException {
        this.output.reset();
        opponent.output.reset();
        this.output.writeObject(this.scoreBoard);
        opponent.output.writeObject(opponent.getScoreBoard());
    }

    public CategoryName getCategoryFromUser() throws IOException, ClassNotFoundException {
        List<CategoryName> enumList = Arrays.asList(CategoryName.values());
        Collections.shuffle(enumList);
        List<CategoryName> categoryList = Arrays.asList(enumList.get(0),enumList.get(1),enumList.get(2),enumList.get(3));
        opponent.sendString("Inväntar val av kategori från din motståndare");
        output.writeObject(categoryList);

        return (CategoryName) input.readObject();
    }
}
