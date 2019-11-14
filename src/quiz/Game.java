package quiz;

        import java.io.IOException;
        import java.net.Socket;

public class Game implements Runnable {

    private Player player1;
    private Player player2;
    private int rounds;


    private Database database;

    public Game(Socket socketP1, Socket socketP2) {
        this.player1 = new Player("Player 1",socketP1);
        this.player2 = new Player("Player 2",socketP2);
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        this.database = new Database();
        this.rounds = 2;
    }

    @Override
    public void run() {
        try {
            newGame();
        }catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public void newGame() throws IOException, ClassNotFoundException{
        newRound(player1,player2);
        printRoundPoints();
        newRound(player2,player1);
        printTotalPoints();
    }

    public void newRound(Player p1, Player p2) throws IOException,ClassNotFoundException {
        CategoryName categoryName = p1.getCategoryFromUser();
        for (int i = 0; i < rounds; i++) {
            Question question = database.getRandomQuestionFromCategory(categoryName);
            setOfQuestions(p1,p2,question);
            setOfQuestions(p2,p1,question);
    }

    }

    public void setOfQuestions(Player player, Player opponent, Question question) throws IOException,ClassNotFoundException {

        Object inputFromPlayer;
            player.sendQuestion(question);
            opponent.sendString("Waiting on opponent to answer question");
            if ((inputFromPlayer = player.getInput()) instanceof String) {
                if (inputFromPlayer.equals("correct")) {
                    player.addPoint();
                }
            }
    }
    public void printRoundPoints() throws IOException {
        player1.sendRoundPoints();
        player2.sendRoundPoints();
    }
    public void printTotalPoints() throws IOException {
        player1.sendTotalPoints();
        player2.sendTotalPoints();
    }
}
