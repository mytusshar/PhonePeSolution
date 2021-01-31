
public class Batsman {
    public static final int STATUS_BATTING = 1;
    public static final int STATUS_OUT = 2;
    public static final int STATUS_YET_TO_BAT = 3;
    private int score = 0;
    private int sixes = 0;
    private int fours = 0;
    private int balls = 0;
    private String name;
    private int status;

    Batsman(String name) {
        this.name = name;
        this.setStatus(STATUS_YET_TO_BAT);
    }
    
    public void updateBalls(Ball ball) {
        // if valid ball, then only update runs and balls played for player.
        if(!ball.isWide() && !ball.isNoBall()) {
            this.balls += 1;
            this.updateScore(ball);
        }

        if(ball.isFour()) {
            this.updateFours();
        } else if(ball.isSix()) {
            this.updateSixes();
        } else if(ball.isWicket()) {
            this.setStatus(STATUS_OUT); // if wicket then set isPlaying as false
        }
    }

    private boolean isPlaying() {
        return (this.status == STATUS_BATTING);
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public void updateFours() {
        this.fours += 1;
    }
    public void updateSixes() {
        this.sixes += 1;
    }
    public void updateScore(Ball ball) {
        this.score += ball.getRuns();
    }

    public String getScoreCard() {
        String playerName = this.isPlaying() ? (name + "*") : name + " ";
        String scoreCard = " ";
        scoreCard += this.getPaddedString(playerName, "[Name]".length()+2);
        scoreCard += this.getPaddedString(score+"", "[Score]".length()+2);
        scoreCard += this.getPaddedString(fours+"", "[4s]".length()+2);
        scoreCard += this.getPaddedString(sixes+"", "[6s]".length()+2);
        scoreCard += this.getPaddedString(balls+"", "[Balls]".length()+2);
        return scoreCard;
    }

    private String getPaddedString(String str, int paddding) {
        StringBuilder paddedStr = new StringBuilder(str);
        while(paddedStr.length() < paddding) {
            paddedStr.append(" ");
        }
        return paddedStr.toString();
    }
}
