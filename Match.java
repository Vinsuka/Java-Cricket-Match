import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Match {
    int SecondBatsmanNumber;
    int runsScoringTeam = 0;
    int runsChasingTeam = 0;
    int ScoringTeamOvers = 0;
    int ChasingTeamOvers = 0;
    int ChasingTeamWickets = 0;
    int ScoringTeamWickets = 0;
    Player CurrentBatsman;
    int CurrentBatsmanNum;
    Player SecondBatsman;
    matchTeam TeamScoring;
    matchTeam TeamChasing;
    matchTeam winner;

    Match(matchTeam firstTeam, matchTeam secondTeam){
        toss(firstTeam, secondTeam);
    }

    //Toss Navigation between two teams
    private void toss(matchTeam firstTeam, matchTeam secondTeam){
        Random rand = ThreadLocalRandom.current();
        int toss = rand.nextInt(2);
        int choice = rand.nextInt(2);
        if (toss == 0 && choice == 0 || toss == 1 && choice == 1) {
            if (toss == 0) {
                System.out.println(firstTeam.name + " won the toss and chose to bat.");
                ExtraMethod.AddToSummary(firstTeam.name + " won the toss and chose to bat.");
            }else {
                System.out.println(secondTeam.name + " won the toss and chose to field.");
                ExtraMethod.AddToSummary(secondTeam.name + " won the toss and chose to field.");
            }
            TeamScoring = firstTeam;
            TeamChasing = secondTeam;
        } else {
            if (toss == 0) {
                System.out.println(firstTeam.name + " won the toss and chose to field.");
                ExtraMethod.AddToSummary(firstTeam.name + " won the toss and chose to field.");

            }else {
                System.out.println(secondTeam.name + " won the toss and chose to bat.");
                ExtraMethod.AddToSummary(secondTeam.name + " won the toss and chose to bat.");

            }
            TeamScoring = secondTeam;
            TeamChasing = firstTeam;
        }
        System.out.println();
        ExtraMethod.AddToSummary("\n");
    }

    //Lost and Won Navigation
    public matchTeam playGame(){
        firstInningsPlay();
        secondInningsPlay();
        //Print Won or Lost with runs stats
        if (runsScoringTeam>runsChasingTeam) {
            System.out.println(TeamScoring.name + " won with " + runsScoringTeam + " runs.");
            ExtraMethod.AddToSummary(TeamScoring.name + " won with " + runsScoringTeam + " runs.");

            System.out.println(TeamChasing.name + " lost with " + runsChasingTeam + " runs.");
            ExtraMethod.AddToSummary(TeamChasing.name + " lost with " + runsChasingTeam + " runs.");
            return TeamScoring;
        } else {
            System.out.println(TeamScoring.name + " lost with " + runsScoringTeam + " runs.");
            ExtraMethod.AddToSummary(TeamScoring.name + " lost with " + runsScoringTeam + " runs.");
            System.out.println(TeamChasing.name + " won with " + runsChasingTeam + " runs.");
            ExtraMethod.AddToSummary(TeamChasing.name + " won with " + runsChasingTeam + " runs.");
            return TeamChasing;
        }
    }
    //Batsman Scores in the first inning with method of dismiss
    private void firstInningsPlay(){
        CurrentBatsman = TeamScoring.players.get(0);
        CurrentBatsmanNum = 0;
        SecondBatsman = TeamScoring.players.get(1);
        SecondBatsmanNumber = 1;
        Player bowler = TeamChasing.players.get(0);
        int totalOfBalls = 120;

        for (int i = 1; i <= totalOfBalls && ChasingTeamWickets < 10; i++) {

            //
            switch(PlayBall()) {
                case 0:
                    CurrentBatsman.AddingFacedBalls();
                    break;
                case 1:
                    CurrentBatsman.AddingRunsScored(1);
                    CurrentBatsman.AddingFacedBalls();
                    runsScoringTeam += 1;
                    BatsmanSwitch();
                    break;
                case 2:
                    CurrentBatsman.AddingRunsScored(2);
                    CurrentBatsman.AddingFacedBalls();
                    runsScoringTeam += 2;
                    break;
                case 3:
                    CurrentBatsman.AddingRunsScored(3);
                    CurrentBatsman.AddingFacedBalls();
                    runsScoringTeam += 3;
                    BatsmanSwitch();
                    break;
                case 4:
                    CurrentBatsman.AddingRunsScored(4);
                    CurrentBatsman.AddingFacedBalls();
                    runsScoringTeam += 4;
                    break;
                case 5: //wicket
                    CurrentBatsman.AddingFacedBalls();
                    CurrentBatsman.AddDissmissalMethod(this.WicketMethod());
                    ChasingTeamWickets++;
                    bowler.AddWicketsTaken();
                    if (ChasingTeamWickets<10){
                        CurrentBatsmanNum = Math.max(CurrentBatsmanNum, SecondBatsmanNumber) + 1;
                        CurrentBatsman = TeamScoring.players.get(CurrentBatsmanNum);
                        //System.out.println(CurrentBatsman.name);
                    }
                    break;
                case 6:
                    CurrentBatsman.AddingRunsScored(6);
                    CurrentBatsman.AddingFacedBalls();
                    runsScoringTeam += 6;
                    break;
            }
            if (i%6==0) { // over
                bowler.AddingOversBowled();
                ChasingTeamOvers++;
                bowler = TeamChasing.players.get(CalBowlerNum(ChasingTeamOvers + 1));
            }
        }
        System.out.println("Total runs by "+ TeamScoring.name + " was " + runsScoringTeam);
        ExtraMethod.AddToSummary("Total runs by "+ TeamScoring.name + " was " + runsScoringTeam);
        System.out.println("Total wickets taken was " + ChasingTeamWickets);
        ExtraMethod.AddToSummary("Total wickets taken was " + ChasingTeamWickets);
        System.out.println("Total number of overs was " + ChasingTeamOvers);
        ExtraMethod.AddToSummary("Total number of overs was " + ChasingTeamOvers);
        System.out.println();
        ExtraMethod.AddToSummary("\n");

    }

    private void secondInningsPlay(){
        CurrentBatsman = TeamChasing.players.get(0);
        CurrentBatsmanNum = 0;
        SecondBatsman = TeamChasing.players.get(1);
        SecondBatsmanNumber = 1;
        Player bowler = TeamScoring.players.get(0);
        int totalOfBalls = 120;

        for (int i = 1; i <= totalOfBalls && ScoringTeamWickets < 10 && runsChasingTeam <= runsScoringTeam; i++) {
            switch(PlayBall()) {
                case 0:
                    CurrentBatsman.AddingFacedBalls();
                    break;
                case 1:
                    CurrentBatsman.AddingRunsScored(1);
                    CurrentBatsman.AddingFacedBalls();
                    runsChasingTeam += 1;
                    BatsmanSwitch();
                    break;
                case 2:
                    CurrentBatsman.AddingRunsScored(2);
                    CurrentBatsman.AddingFacedBalls();
                    runsChasingTeam += 2;
                    break;
                case 3:
                    CurrentBatsman.AddingRunsScored(3);
                    CurrentBatsman.AddingFacedBalls();
                    runsChasingTeam += 3;
                    BatsmanSwitch();
                    break;
                case 4:
                    CurrentBatsman.AddingRunsScored(4);
                    CurrentBatsman.AddingFacedBalls();
                    runsChasingTeam += 4;
                    break;
                case 5: //wicket
                    CurrentBatsman.AddingFacedBalls();
                    CurrentBatsman.AddDissmissalMethod(this.WicketMethod());
                    ScoringTeamWickets++;
                    bowler.AddWicketsTaken();
                    if (ScoringTeamWickets<10){
                        CurrentBatsmanNum = Math.max(CurrentBatsmanNum, SecondBatsmanNumber) + 1;
                        CurrentBatsman = TeamChasing.players.get(CurrentBatsmanNum);
                    }
                    break;
                case 6:
                    CurrentBatsman.AddingRunsScored(6);
                    CurrentBatsman.AddingFacedBalls();
                    runsChasingTeam += 6;
                    break;
            }
            if (i%6==0) { // over
                bowler.AddingOversBowled();
                ScoringTeamOvers++;
                bowler = TeamScoring.players.get(CalBowlerNum(ScoringTeamOvers + 1));
            }
        }
        System.out.println("Total runs by "+ TeamChasing.name + " was " + runsChasingTeam);
        ExtraMethod.AddToSummary("Total runs by "+ TeamChasing.name + " was " + runsChasingTeam);
        System.out.println("Total wickets taken was " + ScoringTeamWickets);
        ExtraMethod.AddToSummary("Total wickets taken was " + ScoringTeamWickets);
        System.out.println("Total number of overs was " + ScoringTeamOvers);
        ExtraMethod.AddToSummary("Total number of overs was " + ScoringTeamOvers);
        System.out.println();
        ExtraMethod.AddToSummary("\n");

    }

    private int CalBowlerNum(int OverNumber){
        if (OverNumber < 11)
            return OverNumber - 1;
        return OverNumber - 11;
    }

    private int PlayBall(){
        Random rnd = ThreadLocalRandom.current();
        return rnd.nextInt(7);
    }

    private void BatsmanSwitch(){
        Player temp;
        int temp_int;
        temp = this.CurrentBatsman;
        CurrentBatsman = this.SecondBatsman;
        SecondBatsman = temp;
        temp_int = CurrentBatsmanNum;
        CurrentBatsmanNum = SecondBatsmanNumber;
        SecondBatsmanNumber = temp_int;
    }

    private String WicketMethod() {
        Random rnd = ThreadLocalRandom.current();
        int random = rnd.nextInt(5);
        switch(random) {
            case 0:
                return "Bowled";
            case 1:
                return "Caught";
            case 2:
                return "LBW";
            case 3:
                return "Stumped";
            case 4:
                return "Run Out";
            default:
                return "Retired";
        }
    }
}
