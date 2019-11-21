package quiz;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Score implements Serializable {

    private List<Integer> roundScores= new ArrayList<>();
    private boolean lastRound;

    public List<Integer> getRoundScores() {
        return roundScores;
    }

    public void addRoundScore (int score) {
        roundScores.add(score);
    }

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }

    public int getTotalScore() {
        return roundScores.stream().mapToInt(Integer::intValue).sum();
    }

}
