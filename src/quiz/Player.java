package quiz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player {

    private String name;
    private Player opponent;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int totalPoints;
    private int roundPoints;

    public Player(String name, Socket socket) {
        this.totalPoints = 0;
        this.roundPoints = 0;
        this.name = name;
        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public String getName() {
        return name;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getRoundPoints() {
        return roundPoints;
    }

    public void setRoundPoints(int roundPoints) {
        this.roundPoints = roundPoints;
    }

    /**
     * Skickar Question till Player och String till opponent,
     */
    public void sendQuestion(Question question) throws IOException {
        opponent.sendString("Waiting on opponent to answer question");
        output.writeObject(question);
    }

    /**
     * Skickar String till Player.
     */
    public void sendString(String message) throws IOException {
        output.writeObject(message);
    }
    /**
     * läser input från användare
     */
    public Object getInput() throws IOException, ClassNotFoundException {
        return input.readObject();
    }
    /**
     * adderar poäng till Player
     */
    public void addPoint() {
        roundPoints++;
        totalPoints++;
    }
    /**
     * Skickar rundans poäng för Player och opponent
     */
    public void sendRoundPoints() throws IOException {
        String result = this.name + " points: " + roundPoints +
                "\n" + opponent.getName() + " points: " + opponent.getRoundPoints();
        output.writeObject(result);
    }
    /**
     * Skickar total poäng för Player och opponent
     */
    public void sendTotalPoints() throws IOException {
        String winMessage;
        if (totalPoints > opponent.getTotalPoints()) {
            winMessage = "Du vann!";
        } else if (totalPoints == opponent.getTotalPoints()) {
            winMessage = "Lika";
        } else {
            winMessage = "Du förlorade";
        }
        String result = winMessage +
                "\n" + this.name + " points: " + totalPoints +
                "\n" + opponent.getName() + " points: " + opponent.getTotalPoints();
        output.writeObject(result);
    }
    /**
     * Skickar CategoryChooser till användare som returnerar CategoryChooser innehållande vald kategori.
     * skickar String till opponent.
     * läser in CategoryChooser och returnerar valt CategoryName
     */
    public CategoryName getCategoryFromUser() throws IOException, ClassNotFoundException {
        opponent.sendString("Waiting on your opponent to choose category");
        output.writeObject(new CategoryChooser());
        CategoryChooser categoryFromUser = (CategoryChooser) input.readObject();

        return categoryFromUser.getChoosedCategory();
    }
}

