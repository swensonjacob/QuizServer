package quiz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class QuizServer {

    Game clientHandler;


    public QuizServer() {
        try (ServerSocket serverSocket = new ServerSocket(5989)) {


            while (true) {
                Socket socket1 = serverSocket.accept();
                Socket socket2 = serverSocket.accept();
                Thread t = new Thread(new Game(socket1,socket2));
                t.start();
            }

            } catch(IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }
