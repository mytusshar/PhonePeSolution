public class Ball {
    private static final String WICKET = "W";
    private static final String WIDE = "Wd";
    private static final String NO_BALL = "Nb";
    private int runs = 0;
    private String ballType;

    Ball(String ballType) {
        this.ballType = ballType;
        this.setRuns();
    }

    public int getRuns() {
        return this.runs;
    }
    private void setRuns() {
        if(this.isWide() || this.isNoBall()) {
            if(this.ballType.length() == 2) {
                this.runs = 1;
            } else {
                int newRuns = Integer.parseInt(this.ballType.substring(3, this.ballType.length()));
                this.runs = newRuns + 1;
            }
        } else if(!this.isWicket()) {
            this.runs = Integer.parseInt(this.ballType);
        }
    }

    public boolean isWicket() {
        return this.ballType.equals(WICKET);
    }
    public boolean isWide() {
        return (this.ballType.indexOf(WIDE) > -1);
    }
    public boolean isNoBall() {
        return (this.ballType.indexOf(NO_BALL) > -1);
    }
    public boolean isFour() {
        return (this.runs == 4);
    }
    public boolean isSix() {
        return (this.runs == CricketScorecard.BALLS_IN_OVER);
    }
}
