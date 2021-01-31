import java.util.ArrayList;
import java.util.Scanner;

/* 
input:
5 2
P1 P2 P3 P4 P5
1 1 1 1 1 2
W 4 4 Wd W 1 6
P6 P7 P8 P9 P10
4 6 W W 1 1
6 1 W W

*/

public class CricketScorecard {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfPlayers = sc.nextInt();
        int numOfOvers = sc.nextInt();
                                    
        ArrayList<Team> teams = new ArrayList<>();
        Team team1 = new Team("Team1", numOfPlayers);
        Team team2 = new Team("Team2", numOfPlayers);

        teams.add(team1);
        teams.add(team2);

        for(Team team : teams) {
            // getting batting order from input for team
            for(int plNum = 0; plNum < numOfPlayers; plNum++) {
                String playerName = sc.next();
                Player player = new Player(playerName);
                team.addPlayer(player);
            }

            // start ining
            team.startIning();

            // reading overs for team
            for(int overNum = 0; overNum < numOfOvers; overNum++) {
                int ballsInOver = 6;
                // getting each over input..
                for(int ballNum = 0; ballNum < ballsInOver; ballNum++) {
                    if(team.isAllOut()) {
                        break;
                    }
                    String ballType = sc.next();
                    Ball ball = new Ball(ballType);
                    if(ball.isWide() || ball.isNoBall()) {
                        ballsInOver++; // if wide ball, then increasing balls in over..
                    }

                    team.addPlayedBall(ball);

                    if(ballNum == ballsInOver-1) {
                        team.overCompleted(); // over completed..
                    }
                }

                System.out.println("---------------");
            }
            System.out.println("#######################");
        }

        // match result..
        if(team1.getScore() == team2.getScore()) {
            System.out.println("Result: Match Draw");
        } else {
            String winningTeamName = team1.getScore() > team2.getScore() ? team1.getName() : team2.getName();
            int scoreDiff = Math.abs(team1.getScore() - team2.getScore());
            System.out.println("Result: " + winningTeamName + " won the match by " + scoreDiff + " run.");
        }
    }
}