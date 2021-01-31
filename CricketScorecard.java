import java.util.ArrayList;
import java.util.Scanner;

/* 
input2:
5 2
P1 P2 P3 P4 P5
B1 B2 B3 B4 B5

P6 P7 P8 P9 P10
B6 B7 B8 B9 B10

1 1 1 1 1 2
W 4 4 Wd W 1 6

4 6 W W 1 1
6 1 W W

*/

public class CricketScorecard {
    private ArrayList<Team> teams;
    private Team team1;
    private Team team2;
    private Team battingTeam;
    private Team bowlingTeam;

    CricketScorecard(int numOfPlayers) {
        teams = new ArrayList<>();
        team1 = new Team("Team1", numOfPlayers);
        team2 = new Team("Team2", numOfPlayers);
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // reading input number of playes and overs
        int numOfPlayers = sc.nextInt();
        int numOfOvers = sc.nextInt();
                                    
        CricketScorecard cScorecard = new CricketScorecard(numOfPlayers);
        cScorecard.setBattingOrder();

        // reading batting and bwoling order for teams
        for(Team team : cScorecard.teams) {
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

        for(int i = 0; i < cScorecard.teams.size(); i++) {
            // start batting
            cScorecard.battingTeam.startBatting();
            // start bowling
            cScorecard.bowlingTeam.startBowling();

            // reading overs for team
            for(int overNum = 0; overNum < numOfOvers; overNum++) {
                int ballsInOver = 6;
                // getting each over input..
                for(int ballNum = 0; ballNum < ballsInOver; ballNum++) {
                    if(cScorecard.battingTeam.isAllOut()) {
                        break; // over breaking condition..
                    }
                    // reading each ball.
                    String ballType = sc.next();
                    Ball ball = new Ball(ballType);
                    if(ball.isWide() || ball.isNoBall()) {
                        ballsInOver++; // if wide ball, then increasing balls in over..
                    }

                    cScorecard.battingTeam.addPlayedBall(ball); // adding ball for batting team
                    cScorecard.bowlingTeam.addPlayedBall(ball); // adding ball for bowling team

                    // over completion.
                    if(ballNum == ballsInOver-1) {
                        cScorecard.battingTeam.overCompleted(); // over completed..
                        cScorecard.bowlingTeam.overCompleted(); // over completed..
                    }
                }

                System.out.println("---------------");
            }

            cScorecard.changeBattingOrder();
            System.out.println("#######################");
        }

        // match result..
        if(cScorecard.team1.getScore() == cScorecard.team2.getScore()) {
            System.out.println("Result: Match Draw");
        } else {
            String winningTeamName = cScorecard.team1.getScore() > cScorecard.team2.getScore() ? cScorecard.team1.getName() : cScorecard.team2.getName();
            int scoreDiff = Math.abs(cScorecard.team1.getScore() - cScorecard.team2.getScore());
            System.out.println("Result: " + winningTeamName + " won the match by " + scoreDiff + " run.");
        }
    }
}