package quiz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class QuizServer {

    ClientHandler clientHandler;


    public QuizServer() {
        try (ServerSocket serverSocket = new ServerSocket(5989)) {


            while (true) {
                Socket socket = serverSocket.accept();
                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            }

            } catch(IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }
