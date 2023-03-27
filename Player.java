import java.util.ArrayList;

public class Player {
    String name;
    int TotalRuns = 0;
    int TotalBalls = 0;
    int TotalWickets = 0;
    protected int overs_bowled = 0;
    protected ArrayList<String> wayToDismiss = new ArrayList<>();


    Player(String name) {
        this.name = name;
    }

    public void AddingRunsScored(int runs) {
        this.TotalRuns += runs;
    }

    public void AddingFacedBalls(int balls) {
        this.TotalBalls += balls;
    }

    public void AddingFacedBalls() {
        AddingFacedBalls(1);
    }

    public void AddingOversBowled(int overs) {
        this.overs_bowled += overs;
    }

    public void AddingOversBowled() {
        AddingOversBowled(1);
    }

    public void AddWicketsTaken(int wickets) {
        this.TotalWickets += wickets;
    }

    public void AddWicketsTaken() {
        AddWicketsTaken(1);
    }

    public void AddDissmissalMethod(String dismal){
        this.wayToDismiss.add(dismal);
    }

}
