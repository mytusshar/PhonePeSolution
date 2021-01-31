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
            this.runs = 1;
        } else if(!this.isWicket()) {
            this.runs = Integer.parseInt(this.ballType);
        }
    }

    public boolean isWicket() {
        return this.ballType.equals(WICKET);
    }
    public boolean isWide() {
        return this.ballType.equals(WIDE);
    }
    public boolean isNoBall() {
        return this.ballType.equals(NO_BALL);
    }
    public boolean isFour() {
        return (this.runs == 4);
    }
    public boolean isSix() {
        return (this.runs == 6);
    }
}
