public class Bowler extends Player{
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

    Bowler(String name) {
        super(name, Bowler.STATUS_NOT_BOWLING);
    }

    public void startOver() {
        this.setStatus(Bowler.STATUS_BOWLING);
    }
    public void overEnd() {
        this.setStatus(Bowler.STATUS_NOT_BOWLING);
        this.overs++;
        if((this.dotBallsInOver == CricketScorecard.BALLS_IN_OVER) && (this.ballsInOver == CricketScorecard.BALLS_IN_OVER)) {
            this.maidenOvers++;
        }
        this.ballsInOver = 0;
        this.dotBallsInOver = 0;
    }

    private String getEconomyRate() {
        if(this.overs == 0 && this.ballsInOver == 0) {
            return "-";
        }
        float totalOvers = this.overs + ((float)ballsInOver / (float)CricketScorecard.BALLS_IN_OVER);
        float economy = ((float)this.runs / totalOvers);
        return String.format("%.2f", economy);
    }

    @Override
    void updateBalls(Ball ball) {
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

    @Override
    boolean isPlaying() {
        return (this.status == Bowler.STATUS_BOWLING);
    }

    @Override
    String getScoreCard() {
        String playerName = this.isPlaying() ? (this.name + "*") : this.name + " ";
        String overStr = (this.ballsInOver == 0) ? (this.overs + "  ") : this.overs + "." + this.ballsInOver;
        StringBuilder scoreCard = new StringBuilder(" ");
        scoreCard.append(this.getPaddedString(playerName, "[Name]".length()+2));
        scoreCard.append(this.getPaddedString(overStr, "[Overs]".length()+2));
        scoreCard.append(this.getPaddedString(runs+"", "[Runs]".length()+2));
        scoreCard.append(this.getPaddedString(fours+"", "[4s]".length()+2));
        scoreCard.append(this.getPaddedString(sixes+"", "[6s]".length()+2));
        scoreCard.append(this.getPaddedString(wickets+"", "[Wickets]".length()+2));
        scoreCard.append(this.getPaddedString(maidenOvers+"", "[Maiden]".length()+2));
        scoreCard.append(this.getPaddedString(balls+"", "[Balls]".length()+2));
        scoreCard.append(this.getPaddedString(dotBalls+"", "[dotBalls]".length()+2));
        scoreCard.append(this.getPaddedString(wideBalls+"", "[Wide]".length()+2));
        scoreCard.append(this.getPaddedString(noBalls+"", "[noBalls]".length()+2));
        scoreCard.append(this.getPaddedString(this.getEconomyRate(), "[EconomyRate]".length()+2));
        return scoreCard.toString();
    }
    
}
