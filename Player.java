public abstract class Player {
    protected String name;
    protected int status;

    Player(String name, int status) {
        this.name = name;
        this.setStatus(status);
    }

    abstract void updateBalls(Ball ball);
    abstract boolean isPlaying();
    abstract String getScoreCard();

    protected void setStatus(int status) {
        this.status = status;
    }

    protected String getPaddedString(String str, int paddding) {
        StringBuilder paddedStr = new StringBuilder(str);
        while(paddedStr.length() < paddding) {
            paddedStr.append(" ");
        }
        return paddedStr.toString();
    }
    
}
