public class Bowler {
    public static final int STATUS_BOWLING = 1;
    public static final int STATUS_NOT_BOWLING = 2;
    private int runs = 0;
    private int sixes = 0;
    private int fours = 0;
    private int balls = 0;
    private int dotBalls = 0;
    private int wideBalls = 0;
    private int noBalls = 0;
    private int wickets = 0;
    private int dotBallsInOver = 0;
    private int maidenOvers = 0;
    private int overs = 0;
    private int ballsInOver = 0;
    private String name;
    private int status;

    Bowler(String name) {
        this.name = name;
        this.setStatus(Bowler.STATUS_NOT_BOWLING);
    }

    public void updateBalls(Ball ball) {
        this.balls++;
        this.ballsInOver++;

        if(ball.isWicket()) {
            this.wickets++;
            this.dotBalls++;
            this.dotBallsInOver++;
        } else if(ball.isWide()) {
            this.wideBalls++;
        } else if(ball.isNoBall()) {
            this.noBalls++;
        } else if(ball.isFour()) {
            this.fours++;
        } else if(ball.isSix()) {
            this.sixes++;
        } else if(ball.getRuns() == 0) {
            this.dotBalls++;
            this.dotBallsInOver++;
        }

        // updating runs
        this.runs += ball.getRuns();
    }

    public void startOver() {
        this.setStatus(Bowler.STATUS_BOWLING);
    }
    public void overEnd() {
        this.setStatus(Bowler.STATUS_NOT_BOWLING);
        this.overs++;
        if(this.dotBallsInOver == 6) {
            this.maidenOvers++;
        }
        this.ballsInOver = 0;
        this.dotBallsInOver = 0;
    }

    // bowling status
    private void setStatus(int status) {
        this.status = status;
    }
    private boolean isBowling() {
        return (this.status == Bowler.STATUS_BOWLING);
    }

    public String getScoreCard() {
        String playerName = this.isBowling() ? (this.name + "*") : this.name + " ";
        String overStr = (this.ballsInOver == 0) ? (this.overs + "  ") : this.overs + "." + this.ballsInOver;
        String scoreCard = " ";
        scoreCard += this.getPaddedString(playerName, "[Name]".length()+2);
        scoreCard += this.getPaddedString(overStr, "[Overs]".length()+2);
        scoreCard += this.getPaddedString(runs+"", "[Runs]".length()+2);
        scoreCard += this.getPaddedString(fours+"", "[4s]".length()+2);
        scoreCard += this.getPaddedString(sixes+"", "[6s]".length()+2);
        scoreCard += this.getPaddedString(wickets+"", "[Wickets]".length()+2);
        scoreCard += this.getPaddedString(maidenOvers+"", "[Maiden]".length()+2);
        scoreCard += this.getPaddedString(balls+"", "[Balls]".length()+2);
        scoreCard += this.getPaddedString(dotBalls+"", "[dotBalls]".length()+2);
        scoreCard += this.getPaddedString(wideBalls+"", "[Wide]".length()+2);
        scoreCard += this.getPaddedString(noBalls+"", "[noBalls]".length()+2);
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
