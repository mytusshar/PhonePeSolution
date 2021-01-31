import java.util.ArrayList;
import java.util.Scanner;

public class CricketScorecard {
    public static final int BALLS_IN_OVER = 6;

    private ArrayList<Team> teams;
    private Team team1;
    private Team team2;
    private Team battingTeam;
    private Team bowlingTeam;

    CricketScorecard(int numOfPlayers, int numOfOvers) {
        teams = new ArrayList<>();
        team1 = new Team("Team1", numOfPlayers, numOfOvers);
        team2 = new Team("Team2", numOfPlayers, numOfOvers);
        teams.add(team1);
        teams.add(team2);
    }

    private void setBattingOrder() {
        battingTeam = team1;
        bowlingTeam = team2;
        setTeamStatus();
    }

    private void changeBattingOrder() {
        Team temp = battingTeam;
        battingTeam = bowlingTeam;
        bowlingTeam = temp;
        setTeamStatus();
    }

    private void setTeamStatus() {
        battingTeam.setStatus(Team.STATUS_BATTING);
        bowlingTeam.setStatus(Team.STATUS_BOWLING);
    }

    private void matchCompleted() {
        // match result..
        System.out.println("#######################");
        if(team1.getScore() == team2.getScore()) {
            System.out.println("Result: Match Draw");
        } else {
            String winningTeamName = team1.getScore() > team2.getScore() ? team1.getName() : team2.getName();
            int scoreDiff = Math.abs(team1.getScore() - team2.getScore());
            System.out.println("Result: " + winningTeamName + " won the match by " + scoreDiff + " run.");
        }
        System.out.println("#######################");
    }

    private void showScores() {
        System.out.println("#######################");
        battingTeam.printScore();
        System.out.println("-----------------------");
        bowlingTeam.printScore();
        System.out.println("#######################");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // reading input number of playes and overs
        int numOfPlayers = sc.nextInt();
        int numOfOvers = sc.nextInt();
                                    
        CricketScorecard sCard = new CricketScorecard(numOfPlayers, numOfOvers);
        sCard.setBattingOrder();

        // reading batting and bwoling order for teams
        for(Team team : sCard.teams) {
            // getting batting order from input for team
            for(int plNum = 0; plNum < numOfPlayers; plNum++) {
                String playerName = sc.next();
                Player player = new Batsman(playerName);
                team.addPlayer(player);
            }

            // getting bowling order from input for team
            for(int plNum = 0; plNum < numOfPlayers; plNum++) {
                String playerName = sc.next();
                Player player = new Bowler(playerName);
                team.addPlayer(player);
            }
        }

        for(int i = 0; i < sCard.teams.size(); i++) {
            // start batting
            sCard.battingTeam.startBatting();
            // start bowling
            sCard.bowlingTeam.startBowling();

            // reading overs for team
            for(int overNum = 0; overNum < numOfOvers; overNum++) {
                int ballsInOver = CricketScorecard.BALLS_IN_OVER;

                // edge case 0: check if all out before next over..
                if(sCard.battingTeam.isAllOut()) {
                    break;
                }

                // getting each over input..
                for(int ballNum = 0; (ballNum < ballsInOver) && !sCard.battingTeam.isAllOut(); ballNum++) { // edge case 1: if all out.. over breaking condition..
                    // edge case 2: if during second inning if batting team wins before completion of overs
                    if((i == 1) && (sCard.battingTeam.getScore() > sCard.bowlingTeam.getScore())) {
                        // batting team wins..
                        sCard.showScores();
                        sCard.matchCompleted();
                        return;
                    }

                    // reading each ball.
                    String ballType = sc.next();
                    Ball ball = new Ball(ballType);
                    if(ball.isWide() || ball.isNoBall()) {
                        ballsInOver++; // if wide ball, then increasing balls in over..
                    }

                    sCard.battingTeam.addPlayedBall(ball); // adding ball for batting team
                    sCard.bowlingTeam.addPlayedBall(ball); // adding ball for bowling team

                    // over completion.
                    if(ballNum == ballsInOver-1) {
                        sCard.battingTeam.overCompleted(); // over completed..
                        sCard.bowlingTeam.overCompleted(); // over completed..
                    }
                }
                sCard.showScores();
            }

            // change batting order only when first team is completed playing..
            if(i == 0) {
                sCard.changeBattingOrder();
            }
        }

        // show results..
        sCard.matchCompleted();
    }
}