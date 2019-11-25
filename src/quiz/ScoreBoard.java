package quiz;

public class ScoreBoard {
    private String name;
    private int score;
    private int round;

    public ScoreBoard(String name, int score, int round) {
        this.name = name;
        this.score = score;
        this.round = round;
    }

    @Override
    public String toString() {
        return this.name + " Poäng " + this.score + " Runda" + this.round;
    }
}
