import java.util.Random;

public class Matchup {
    String opponent;
    int pointsFor;
    int pointsAllowed;
    boolean isComplete;
    boolean win;
    boolean isSimulated;
    
    public Matchup(String opponent, int pointsFor, int pointsAllowed) {
        this.opponent = opponent;
        this.pointsFor = pointsFor;
        this.pointsAllowed = pointsAllowed;
        win = pointsFor > pointsAllowed;
        isComplete = true;
    }
    
    public Matchup(String opponent) {
        this.opponent = opponent;
        isComplete = false;
    }
    
    public String getOpponent() {
        return opponent;
    }
    
    public boolean isWin() {
        return win;
    }
    
    public int getPointsFor() {
        return pointsFor;
    }
    
    public int getPointsAllowed() {
        return pointsAllowed;
    }
    
    public boolean completed() {
        return isComplete;
    }
    
    public void simulate(double odds) {
       Random rand = new Random();
       double randDouble = rand.nextDouble();
       win = randDouble < odds;
       isComplete = true;
       isSimulated = true;
    }
    
    public String toString() {
        return "vs " + opponent + (isComplete ? (isWin() ? " W " : " L ") + pointsFor + "-" + pointsAllowed : "");
    }
}