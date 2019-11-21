package quiz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class QuizServer {

    public QuizServer() {
        try (ServerSocket serverSocket = new ServerSocket(5989)) {

            /**
             * Inväntar två socket.accept, instans av Game skapas via ny tråd med
             * socket1 och socket2 till konstruktorn.
             */
            while (true) {
                Socket socket1 = serverSocket.accept();
                Socket socket2 = serverSocket.accept();
                new Thread(new Game(socket1,socket2)).start();
            }

            } catch(IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }
