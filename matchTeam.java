import java.util.ArrayList;

public class matchTeam {
    String name;
    //Player Array List
    ArrayList<Player> players;
    // Determine position in the players.txt file
    int readNumber;


    matchTeam(String name, ArrayList<Player> players, int readNumber){
        this.name = name;
        this.players = players;
        this.readNumber = readNumber;
    }

    public void print(){
        // Printing the elements in ArrayList
        System.out.println(this.name);
        for (Player player : this.players) System.out.print(player.name + " ");
        System.out.println();
    }
}
