package quiz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player{

    private String name;
    private Player opponent;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private int points;

    public Player(String name, Socket socket) {
        this.points=0;
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

    public int getPoints() {
        return points;
    }

    public void sendQuestion(Question question) throws IOException{
                output.writeObject(question);
        }

        public void sendString(String message) throws IOException {
        output.writeObject(message);
        }
        public Object getInput() throws IOException,ClassNotFoundException {
        return input.readObject();
        }
        public void addPoint () {
        points++;
        }

        public void sendRoundPoints() throws IOException{
        String result = this.name + " points: " + points +
                "\n" + opponent.getName() + " points: " + opponent.getPoints();
        output.writeObject(result);
    }

    public void sendTotalPoints() throws IOException{
        String winMessage;
        if (points>opponent.getPoints()) {
            winMessage = "Du vann!";
        }else if (points==opponent.getPoints()) {
            winMessage = "Lika";
        } else {
            winMessage = "Du förlorade";
        }
        String result = winMessage +
                "\n" + this.name + " points: " + points +
                "\n" + opponent.getName() + " points: " + opponent.getPoints();
        output.writeObject(result);
    }

        public CategoryName getCategoryFromUser() throws IOException, ClassNotFoundException{
        opponent.sendString("Waiting on your opponent to choose category");
        output.writeObject(new CategoryChooser());
        CategoryChooser categoryFromUser = (CategoryChooser) input.readObject();

            return categoryFromUser.getChoosedCategory();
        }
    }

