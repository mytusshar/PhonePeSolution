import java.util.ArrayList;

public class Team {
    public static final int STATUS_BATTING = 1;
    public static final int STATUS_BOWLING = 2;

    private ArrayList<Batsman> playersBattingOrder;
    private ArrayList<Bowler> playersBowlingOrder;
    private Batsman plOnStrike;
    private Batsman plNonStrike;
    private Bowler currentBowler;
    
    private String name;
    private int nextBattingPlayer = 0;
    private int nextBowlingPlayer = 0;
    private int teamScore = 0;
    private int ballsPlayed = 0;
    private int totalWickets = 0; // this is for batting team
    private int totalWicketsTaken = 0; // this is for bowling team
    private int overNumber = 0; // for batting team
    private int oversBowled = 0; // for bolwing team
    private int ballsInOver = 0;
    private int totalWideBalls = 0;
    private int totalNoBalls = 0;
    private int status;
    private int totalOvers;
    private int extraRuns = 0;

    Team(String name, int numPlayers, int totalOvers) {
        this.name = name;
        this.totalOvers = totalOvers;
        this.playersBattingOrder = new ArrayList<>(numPlayers);
        this.playersBowlingOrder = new ArrayList<>(numPlayers);
    }

    public void addPlayer(Player player) {
        if(player instanceof Batsman){
            this.playersBattingOrder.add((Batsman)player);
        } else {
            this.playersBowlingOrder.add((Bowler)player);
        }
    }

    public void startBatting() {
        this.plOnStrike = getNextPlayer();
        this.plNonStrike = getNextPlayer();
    }
    public void startBowling() {
        this.sendNewPlayerToBowl();
    }

    public void addPlayedBall(Ball ball) {
        if(this.status == Team.STATUS_BATTING) {
            this.addBatsmanBall(ball);
        } else {
            this.addBowlerBall(ball);
        }
    }

    private void addBatsmanBall(Ball ball) {
        this.ballsPlayed++; // updating balls played by team
        this.plOnStrike.updateBalls(ball); // updating on strike player data
        
        // updating score
        int runsInThisBall = ball.getRuns();
        this.teamScore += runsInThisBall;

        if(!ball.isWide() && !ball.isNoBall()) {
            this.ballsInOver++;
        }

        if(ball.isNoBall() || ball.isWide()) {
            this.extraRuns += runsInThisBall;
        }

        if(ball.isWide()) {
            this.totalWideBalls++;
        }
        if(ball.isNoBall()) {
            this.totalNoBalls++;
        }

        if(ball.isWicket()) {
            this.totalWickets++;
            if(!this.isAllOut()) {
                this.sendNewPlayerToBat();
            }
        } else if(!ball.isWide() && !ball.isNoBall() && (runsInThisBall % 2 == 1)) {
            this.strikeChange(); // if its a valid ball and odd runs scored, then change the strike.
        }
    }
    private void addBowlerBall(Ball ball) {
        this.currentBowler.updateBalls(ball);
        if(ball.isWicket()) {
            this.totalWicketsTaken++;
        }
    }

    public void overCompleted() {
        if(this.status == Team.STATUS_BATTING) {
            this.ballsInOver = 0;
            this.strikeChange();
            this.overNumber++;
        } else {
            this.oversBowled++;
            this.currentBowler.overEnd();
            if(this.oversBowled < this.totalOvers) {
                this.sendNewPlayerToBowl();
            }
        }
    }

    private void strikeChange() {
        Batsman temp = plOnStrike;
        plOnStrike = plNonStrike;
        plNonStrike = temp;
    }

    private void sendNewPlayerToBat() {
        this.plOnStrike = getNextPlayer();
    }
    private void sendNewPlayerToBowl() {
        this.currentBowler = this.playersBowlingOrder.get(this.nextBowlingPlayer++);
        this.currentBowler.startOver();
    }

    private Batsman getNextPlayer() {
        Batsman player = this.playersBattingOrder.get(this.nextBattingPlayer++);
        player.setStatus(Batsman.STATUS_BATTING);
        return player;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public String getName() {
        return this.name;
    }
    public boolean isAllOut() {
        if(this.status == Team.STATUS_BATTING)
            return this.totalWickets == (this.playersBattingOrder.size() - 1);
        else
            return this.totalWicketsTaken == (this.playersBattingOrder.size() - 1);
    }
    public int getScore() {
        return this.teamScore;
    }

    private int getExtras() {
        return this.extraRuns;
    }

    public int getRemainingWickets() {
        return (this.playersBattingOrder.size() - this.totalWickets);
    }

    public void printScore() {
        System.out.println();
        
        if(this.status == Team.STATUS_BATTING) {
            System.out.println("Scorecard for #" + getName() + "# (Batting)");
            System.out.println("[Name]  [Score]  [4s]  [6s]  [Balls]  [StrikeRate]");
    
            for(Batsman player : playersBattingOrder) {
                String plScore = player.getScoreCard();
                System.out.println(plScore);
            }
    
            System.out.println("=> Extras: " + this.getExtras());
            System.out.println("=> Total: " + this.getScore() + "/" + this.totalWickets);
            String overs = "" + this.overNumber;
            overs += ((this.ballsInOver < CricketScorecard.BALLS_IN_OVER) ? ("." + this.ballsInOver) : "");
            System.out.println("=> Overs: " + overs);
        } else {
            System.out.println("Scorecard for #" + getName() + "# (Bowling)");
            System.out.println("[Name]  [Overs]  [Runs]  [4s]  [6s]  [Wickets]  [Maiden]  [Balls]  [dotBalls]  [Wide]  [noBalls]  [EconomyRate]");
            for(Bowler player : playersBowlingOrder) {
                String plScore = player.getScoreCard();
                System.out.println(plScore);
            }
        }
    }
}
