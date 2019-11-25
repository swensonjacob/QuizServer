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

    public Player getOpponent() {
        return opponent;
    }

    public void setRoundPoints(int roundPoints) {
        this.roundPoints = roundPoints;
    }

    /**
     * Skickar Question till Player och String till opponent,
     */
    public void sendQuestion(Question question) throws IOException {
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

    public CategoryName getCategoryFromUser() throws IOException, ClassNotFoundException {
        List<CategoryName> enumList = Arrays.asList(CategoryName.values());
        Collections.shuffle(enumList);
        List<CategoryName> categoryList = Arrays.asList(enumList.get(0),enumList.get(1),enumList.get(2),enumList.get(3));
        opponent.sendString("Inväntar val av kategori från din motståndare");
        output.writeObject(categoryList);

        return (CategoryName) input.readObject();
    }
}
