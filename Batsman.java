
public class Batsman extends Player{
    public static final int STATUS_BATTING = 1;
    public static final int STATUS_OUT = 2;
    public static final int STATUS_YET_TO_BAT = 3;
    private int score = 0;
    private int sixes = 0;
    private int fours = 0;
    private int balls = 0;

    Batsman(String name) {
        super(name, STATUS_YET_TO_BAT);
    }
    
    @Override
    void updateBalls(Ball ball) {
        // if valid ball, then only update runs and balls played for player.
        if(!ball.isWide() && !ball.isNoBall()) {
            this.balls += 1;
            this.score += ball.getRuns();
        }

        if(ball.isFour()) {
            this.fours += 1;
        } else if(ball.isSix()) {
            this.sixes += 1;
        } else if(ball.isWicket()) {
            this.setStatus(STATUS_OUT); // if wicket then set isPlaying as false
        }
    }

    @Override
    boolean isPlaying() {
        return (this.status == STATUS_BATTING);
    }

    @Override
    String getScoreCard() {
        String playerName = this.isPlaying() ? (name + "*") : name + " ";
        StringBuilder scoreCard = new StringBuilder(" ");
        scoreCard.append(this.getPaddedString(playerName, "[Name]".length()+2));
        scoreCard.append(this.getPaddedString(score+"", "[Score]".length()+2));
        scoreCard.append(this.getPaddedString(fours+"", "[4s]".length()+2));
        scoreCard.append(this.getPaddedString(sixes+"", "[6s]".length()+2));
        scoreCard.append(this.getPaddedString(balls+"", "[Balls]".length()+2));
        return scoreCard.toString();
    }
}
