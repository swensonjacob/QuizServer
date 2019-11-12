package quiz;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private Database database;



    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.database = new Database();


    }

    @Override
    public void run() {

        try(ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            do {
                output.writeObject(database.getRandomQuestion());
            } while (input.readObject()!=null);


        }catch (Exception e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
