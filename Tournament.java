import java.io.*;
import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Tournament {
    public static final ArrayList<matchTeam> MATCH_TEAMS = new ArrayList<>();
    //Creating Array Lists
    ArrayList<String> summary = new ArrayList<String>();
    //Create teams Array List
    ArrayList<matchTeam> teams = new ArrayList<>();
    ArrayList<matchTeam> gamePlay = new ArrayList<>();
    ArrayList<matchTeam> finalGamePlay = MATCH_TEAMS;
    ArrayList<matchTeam> gameSecondRunnersUp = new ArrayList<>();
    //Add Player Class to an ArrayList "players"
    ArrayList<Player> players = new ArrayList<>();
    matchTeam gameFirstPlace;
    matchTeam gameSecondPlace;
    matchTeam gameThirdPlace;


//----------------------------------------MENU------------------------------------------------------------

    public static void main(String[] args) {

        //Calling replaceNewSum method inside Public Class ExtraMethod.
        ExtraMethod.replaceNewSum();
        //Create Object Gmenu
        Tournament Gmenu = new Tournament();
        //Calling Method of setupTeams and read team file path and player file path
        Gmenu.setupTeams("Files/players.txt", "Files/teams.txt");

        //
        int useChoice=1;
        int Select;
        Scanner input = new Scanner(System.in);

        while (useChoice==1){
            //Taking Input From The Menu
            System.out.print("Choose an option\n 1.Edit teams\n 2.Start game\n 3.View previous match summary\n 4.Exit\n Choice : ");
            Select = input.nextInt();
            // User Input to select option
            //Edit Teams
            if (Select == 1) {
                Gmenu.TeamEdit();
            // Start Game
            } else if (Select == 2) {
                ExtraMethod.replaceNewSum();
                Gmenu.RunFullToutnament();
                Gmenu.SummaryOfTournament();
            //Previous match summary
            } else if (Select == 3) {
                ExtraMethod.SummaryDisplay();
            //Exit Code
            } else if (Select == 4) {
                useChoice=0;
            }
        }
    }
//----------------------------------------------------------------------------------------------------

    //Edit Teams Option Method
    public void TeamEdit() {

        Scanner input = new Scanner(System.in);
        int teamNumber;

        for (int k = 0; k < teams.size(); k++) {
            //Returns the elements from teams
            matchTeam team = teams.get(k);
            // Print count number and name for each Country
            System.out.println("\n" + (k + 1) + ". " + team.name + "\n");
            // Print Players in the team
            for (int j = 0; j < team.players.size(); j++) {
                Player player = team.players.get(j);
                System.out.println(player.name);
            }
        }
        //Get user Input To Edit Team
        System.out.print("Which team you want to edit(Enter the number)\n(Press 0 to continue): ");
        teamNumber = input.nextInt() - 1;

        //Print Selected Team with Players
        if (teamNumber != -1) {
            //Print Team Name
            System.out.println(teams.get(teamNumber).name);
            for (int j = 0; j < teams.get(teamNumber).players.size(); j++) {
                Player player = teams.get(teamNumber).players.get(j);
                //Print Player Name
                System.out.println((j + 1) + " " + player.name);
            }
            //Get User input to Select Player
            System.out.print("Which player you want to edit (Enter the number): ");
            int playerNumber = input.nextInt();

            //Read the position of the player and team
            int LineNumber = teams.get(teamNumber).readNumber * 11 + playerNumber;
            //Print replace message with the replace player
            System.out.println("Replace " + teams.get(teamNumber).players.get(playerNumber - 1).name + " with: ");
            String NewAddedPlayer = input.next();

            //Replace the player/ Add newly added player(In ExtraMethod class, changePlayer Method)
            ExtraMethod.changePlayer("Files/players.txt", LineNumber, NewAddedPlayer);
            this.setupTeams("Files/players.txt", "Files/teams.txt");
         }
    }
        //Create Method To Setup and Read Teams and Players Names
        private void setupTeams (String playerFileDirection, String teamFileDirection){
        try {
            //Create Files for Players and Teams and give them File paths
            File playerFile = new File(playerFileDirection);
            File teamFile = new File(teamFileDirection);

            //read Files which were Created.
            FileReader playerRead = new FileReader(playerFile);
            FileReader teamRead = new FileReader(teamFile);

            //Read text from the input Streams
            BufferedReader playerBuffer = new BufferedReader(playerRead);
            BufferedReader teamBuffer = new BufferedReader(teamRead);


            String Rline;

            int count = 0;
            int teamCount = 0;
            //Add Player class to an arrayList "teamPlayers"
            ArrayList<Player> teamPlayers = new ArrayList<>();

            // Read and loop through players using Buffer
            while((Rline=playerBuffer.readLine())!=null) {
                //Object "player"
                Player player = new Player(Rline);
                //Add "teamPlayers" to Player ArrayList as a player Object
                teamPlayers.add(player);
                //Also add "players" to player ArrayList
                players.add(player);

                count += 1;
                // 11 players in a team
                if (count >= 11) {
                    count = 0;
                    //Add to "teams" ArrayList through reading "teamBuffer"
                    this.teams.add(new matchTeam(teamBuffer.readLine(), teamPlayers, teamCount));
                    //Add to empty ArrayList "teamPlayers" with capacity of 10
                    teamPlayers = new ArrayList<>();
                    //Increase team count
                    teamCount++;
                }
            }
            playerRead.close();    //closes the stream and release the resources
            teamRead.close();    //closes the stream and release the resources
        } catch(IOException handle) {
            System.out.println("Error When SetUP Teams.");
            handle.printStackTrace();
        }
    }

    private void RanShuffleTeams() {
        System.out.println();
        // Randomly Shuffle the "teams" Array List
        Random RanShuffle = ThreadLocalRandom.current();
        //Get the number of elements in the "teams" Array and loop to Shuffle
        for (int x = this.teams.size() - 1; x > 0; x--)
        {
            // Swap elements in "teams"
            int element = RanShuffle.nextInt(x + 1);
            matchTeam i = this.teams.get(element); // return element
            this.teams.set(element, this.teams.get(x)); //replace the element
            this.teams.set(x, i); //replace the element
        }
    }

    private void RunFullToutnament(){
        this.RanShuffleTeams();
        //Print On Console and Summary Text File
        System.out.println("START THE MATCH"); // Print Console
        ExtraMethod.AddToSummary("START THE MATCH"); // Print on Summary Text File
        System.out.println("\n"+"------------------"+"\n"+"First rounds"+"\n"+"------------------"+"\n");
        ExtraMethod.AddToSummary("First rounds\n\n");


        for (int a = 0; a < this.teams.size(); a+=2) {  // Get elements from the arraylist
            //Get random two team Names and Print their match Title "VS" in Console
            System.out.print("----------------------------------"
            + "\n" +this.teams.get(a).name + " vs " + this.teams.get(a + 1).name
                    +"\n" + "----------------------------------"+ "\n\n");
            // Same as the console Print in the Summary File
            ExtraMethod.AddToSummary(this.teams.get(a).name + " vs "+this.teams.get(a + 1).name);

            //Create New object "game" and add to ArrayList "gamePlay"
            Match game = new Match(this.teams.get(a), this.teams.get(a+1));
            gamePlay.add(game.playGame());

            System.out.println();
            ExtraMethod.AddToSummary("\n");
        }
        //Start Semi-Final tournament rounds
        System.out.println();
        System.out.println("\n"+"------------------"+"\n"+"Semi Finals"+"\n"+"------------------"+"\n");

        // Get Data From "gamePlay" Array list
        for (int x = 0; x < gamePlay.size(); x+=2) {

            // Print selected semi final Teams names from the list
            System.out.print("----------------------------------"
                    + "\n" +this.gamePlay.get(x).name + " vs " + gamePlay.get(x + 1).name
                    +"\n" + "----------------------------------"+ "\n\n");

            ExtraMethod.AddToSummary(gamePlay.get(x).name + " vs "+gamePlay.get(x + 1).name);

            //Add the winning teams to "finalGamePlay" Array.
            Match game = new Match(gamePlay.get(x), gamePlay.get(x+1));
            matchTeam gameWinners = game.playGame();
            finalGamePlay.add(gameWinners);

            //Select Second Runners Up
            if (gameWinners == gamePlay.get(x))
                gameSecondRunnersUp.add(gamePlay.get(x+1));
            else
                gameSecondRunnersUp.add(gamePlay.get(x));

            System.out.println();
            ExtraMethod.AddToSummary("\n");
        }
        // Print Tournament RunnersUp
        System.out.println("TOURNAMENT RUNNERS UP\n");
        ExtraMethod.AddToSummary("TOURNAMENT RUNNERS UP\n");

        //Get Third Place of the Tournament
        Match game = new Match(gameSecondRunnersUp.get(0), gameSecondRunnersUp.get(1));
        matchTeam gameWinners = game.playGame();

        gameThirdPlace = gameWinners;

        //Print Finals
        System.out.println("\n"+"------------------"+"\n"+"Finals"+"\n"+"------------------"+"\n");//Print On console
        ExtraMethod.AddToSummary("\nFinals\n"); //Print On summary File

        // Get First Place Of The Tournament
        game = new Match(finalGamePlay.get(0), finalGamePlay.get(1));
        gameWinners = game.playGame();
        gameFirstPlace = gameWinners;

        // Get Second Place Of The Tournament
        if (gameWinners == finalGamePlay.get(0))
            gameSecondPlace = finalGamePlay.get(1);
        else
            gameSecondPlace = finalGamePlay.get(0);
    }

    public void SummaryOfTournament() {
        System.out.println("-------------------------------");
        ExtraMethod.AddToSummary("\n");

        //Print Winner Of the tournament
        System.out.println("THE WINNER IS: " + gameFirstPlace.name);
        ExtraMethod.AddToSummary("THE WINNER IS: " + gameFirstPlace.name);

        //Print Runners up Of the tournament
        System.out.println("THE RUNNERS UP IS: " + gameSecondPlace.name);
        ExtraMethod.AddToSummary("THE RUNNERS UP IS: " + gameSecondPlace.name);

        //Print Second Runners up Of the tournament
        System.out.println("THE SECOND RUNNERS UP IS: " + gameThirdPlace.name);
        ExtraMethod.AddToSummary("THE SECOND RUNNERS UP IS: " + gameThirdPlace.name);

        //Create Match Summary of the team Players
        for (matchTeam team : teams) {
            //Get Team Name
            System.out.println("\n" + team.name);
            //Print Player Summary for all 11 players through a loop
            for (int k = 0; k < team.players.size(); k++) {
                Player cricketer = team.players.get(k);
                System.out.println(cricketer.name + ": Player scored " + cricketer.TotalRuns +
                        " from " + cricketer.TotalBalls + " balls" +
                        ". Getting " + cricketer.TotalWickets + " wickets" +
                        " And The way of dismissal is by :" + cricketer.wayToDismiss.toString());

                // Print Same summary in the Summary text File.
                ExtraMethod.AddToSummary(cricketer.name + ": Player scored " + cricketer.TotalRuns +
                        " from " + cricketer.TotalBalls + " balls" +
                        ". Getting " + cricketer.TotalWickets + " wickets" +
                        " And The way of dismissal is by :" + cricketer.wayToDismiss.toString());
            }
        }
        // Print and Display Best Batsmen Of The Tournament
        System.out.println("\n\n---------------------------------");
        System.out.println("BEST BATSMEN OF THE TOURNAMENT \n");
        ExtraMethod.AddToSummary("\n\nBEST BATSMEN OF THE TOURNAMENT");
        for (Player BestPlayer : TheBestBatsmen()) {
            System.out.println(BestPlayer.name + " scored " + BestPlayer.TotalRuns);
            ExtraMethod.AddToSummary(BestPlayer.name + " scored " + BestPlayer.TotalRuns);
        }
        System.out.println("---------------------------------");

        System.out.println();
        ExtraMethod.AddToSummary("\n");

        // Print and Display Best Bowlers Of The Tournament
        System.out.println("\n\n---------------------------------");
        System.out.println("BEST BOWLERS OF THE TOURNAMENT \n");
        ExtraMethod.AddToSummary("\n\nBEST BOWLERS OF THE TOURNAMENT");
        for (Player BestPlayer : TheBestBowler()) {
            System.out.println(BestPlayer.name + " got " + BestPlayer.TotalWickets + " wickets.");
            ExtraMethod.AddToSummary(BestPlayer.name + " got " + BestPlayer.TotalWickets + " wickets.");
        }
        System.out.println("---------------------------------");

        System.out.println();
        ExtraMethod.AddToSummary("\n");
    }

    // Get Players For The Best Batsmen
    public Player[] TheBestBatsmen() {
        players.sort((player, t1) -> Integer.compare(t1.TotalRuns, player.TotalRuns));
        return new Player[]{players.get(0), players.get(1), players.get(2), players.get(3), players.get(4)};
    }

    // Get Players For The Best Bowler
    public Player[] TheBestBowler() {
        players.sort((player, t1) -> Integer.compare(t1.TotalWickets, player.TotalWickets));
        return new Player[]{players.get(0), players.get(1), players.get(2), players.get(3), players.get(4)};
    }


}
