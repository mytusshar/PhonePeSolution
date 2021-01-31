import java.util.ArrayList;

public class Team {
    private ArrayList<Player> playersBatOrder;
    private Player plOnStrike;
    private Player plNonStrike;
    
    private String name;
    private int nextBattingPlayer = 0;
    private int teamScore = 0;
    private int ballsPlayed = 0;
    private int totalWickets = 0;
    private int overNumber = 0;
    private int ballsInOver = 0;
    private int totalWideBalls = 0;
    private int totalNoBalls = 0;

    Team(String name, int numPlayers) {
        this.name = name;
        this.playersBatOrder = new ArrayList<>(numPlayers);
    }

    public void addPlayer(Player player) {
        this.playersBatOrder.add(player);
    }

    public void startIning() {
        this.plOnStrike = getNextPlayer();
        this.plNonStrike = getNextPlayer();
    }

    public void addPlayedBall(Ball ball) {
        this.updateBalls(); // updating balls played by team
        this.plOnStrike.updateBalls(ball); // updating on strike player data
        
        int runsInThisBall = ball.getRuns();
        this.updateScore(runsInThisBall);

        if(!ball.isWide() && !ball.isNoBall()) {
            this.updateBallsInOver();
        }

        if(ball.isWide()) {
            this.updateWideBalls();
        }
        if(ball.isNoBall()) {
            this.updateNoBalls();
        }

        if(ball.isWicket()) {
            this.updateWickets();
            if(!isAllOut()) {
                this.sendNewPlayerToBat();
            }
        } else if(!ball.isWide() && !ball.isNoBall() && (runsInThisBall % 2 == 1)) {
            this.strikeChange(); // if its a valid ball and odd runs scored, then change the strike.
        }

        if(this.isAllOut()) {
            this.printPlayerScores();
        }
    }

    public void overCompleted() {
        this.resetBallsInOver();
        this.strikeChange();
        this.updateOverNumber();
        this.printPlayerScores();
    }

    private void strikeChange() {
        Player temp = plOnStrike;
        plOnStrike = plNonStrike;
        plNonStrike = temp;
    }

    private void sendNewPlayerToBat() {
        this.plOnStrike = getNextPlayer();
    }

    private Player getNextPlayer() {
        Player player = this.playersBatOrder.get(this.nextBattingPlayer++);
        player.setStatus(Player.STATUS_BATTING);
        return player;
    }

    public String getName() {
        return this.name;
    }
    public boolean isAllOut() {
        return this.totalWickets == (this.playersBatOrder.size() - 1);
    }
    
    public int getScore() {
        return this.teamScore;
    }
    private void updateScore(int runs) {
        this.teamScore += runs;
    }

    private void updateBalls() {
        this.ballsPlayed++;
    }
    private int getBallsPlayed() {
        return this.ballsPlayed;
    }

    private void updateNoBalls() {
        this.totalNoBalls++;
    }
    private int getNoBalls() {
        return this.totalNoBalls;
    }

    private void updateWideBalls() {
        this.totalWideBalls++;
    }
    private int getWideBalls() {
        return this.totalWideBalls;
    }

    private void updateWickets() {
        this.totalWickets++;
    }
    private int getWickets() {
        return this.totalWickets;
    }

    private void updateBallsInOver() {
        this.ballsInOver++;
    }
    private int getBallsInOver() {
        return this.ballsInOver;
    }
    private void resetBallsInOver() {
        this.ballsInOver = 0;
    }

    private void updateOverNumber() {
        this.overNumber++;
    }
    private int getOverNumber() {
        return this.overNumber;
    }

    private void printPlayerScores() {
        System.out.println();
        System.out.println("Scorecard for #" + getName() + "# after overs: " + this.getOverNumber());
        System.out.println("PlayerName Score 4s 6s Balls");

        for(Player player : playersBatOrder) {
            String plScore = player.getScoreCard();
            System.out.println(plScore);
        }

        System.out.println("=> Total: " + this.getScore() + "/" + this.getWickets());
        String overs = "" + this.getOverNumber();
        overs += ((this.getBallsInOver() < 6) ? ("." + this.getBallsInOver()) : "");
        System.out.println("=> Overs: " + overs);
    }
}
