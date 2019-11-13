package quiz;

        import java.net.Socket;

public class Game implements Runnable {

    private Player player1;
    private Player player2;
    private int rounds;


    private Database database;

    public Game(Socket socketP1, Socket socketP2) {
        this.player1 = new Player("Player 1",socketP1);
        this.player2 = new Player("Player 2",socketP2);
        this.database = new Database();
        this.rounds = 2;
    }

    @Override
    public void run() {

        for (int i = 0; i < 3; i++) {
            roundOfQuestions(player1);
            roundOfQuestions(player2);
        }

    }

    public void roundOfQuestions (Player player) {
        Object inputFromPlayer;

        try {
            player.sendQuestion(database.getRandomQuestion());
            inputFromPlayer = player.getInput();
            if (inputFromPlayer instanceof String) {
                if (inputFromPlayer.equals("correct")) {
                    player.addPoint();
                }
                System.out.println(player.getPoints());
            }
        }catch (Exception e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
