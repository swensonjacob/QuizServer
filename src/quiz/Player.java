package quiz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player{

    private String name;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private int points;



    public Player(String name, Socket socket) {
        this.socket = socket;
        this.points=0;
        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getPoints() {
        return points;
    }

    public void sendQuestion(Question question) throws IOException{
                output.writeObject(question);
        }
        public Object getInput() throws IOException,ClassNotFoundException {
        return input.readObject();
        }
        public void addPoint () {
        points++;
        }
    }

