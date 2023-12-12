package LadderAndSnakeGame;

import java.util.*;

public class LadderAndSnake {
    private int numPlayers; // the number of players
    private Board board; // the game board
    private Player[] players; // the players of the game

    // constructor
    public LadderAndSnake(int numPlayers){
        this.numPlayers = numPlayers;
        board = new Board();
        addPlayers();
    }

    /**
     * Initiate the core engine of the game where players start to play.
     */
    public void play(){
        // welcome message
        System.out.println("============ Welcome to Chen's Ladder & Snake Game! ============");
        System.out.println("This game will be played by " + numPlayers + " players.");

        // setting player turns
        System.out.println("Let's start by first figuring out the players' playing turns!");
        String turns = determinePlayersTurn(new ArrayList<>(Arrays.asList(players)));
        for (int i=0; i<turns.length(); i++){
            players[Integer.valueOf(String.valueOf(turns.charAt(i)))-1].setPlayTurn(i);
        }
        Arrays.sort(players);
        System.out.println("The order of playing has been decided as follow: " + Arrays.toString(players).replace("[", "").replace("]", ""));

        // playing the game
        System.out.println("Here is the current state of the board:");
        board.displayBoard();

        while (true){ // keep playing while no winner
            if (!playTurn()){ // if win, stop game
                break;
            }
        }
        System.out.println("============ Thank you for playing! The game has ended! ============");
    }

    /**
     * Roll a dice with six sides numbered from 1 to 6
     * @return an integer between 1 and 6 (inclusive) indicating the result of a dice roll
     */
    private int flipDice(){
        Random rd = new Random();
        return rd.nextInt(6)+1;
    }

    /**
     * Add players to the game according to the number of players and give each of then an ID
     */
    private void addPlayers(){
        players = new Player[numPlayers];
        for (int i=0; i<numPlayers; i++){
            players[i] = new Player(i+1);
        }
    }

    /**
     * Determine the order of playing turns for all players by having each player throw the dice to obtain the largest possible number.
     * In case of a tie, the process is repeated only between players with the tie.
     * @param p an arraylist of players who will roll the dice to determine the playing turns among them
     * @return a string containing the player IDs of the players in the arraylist in the order of the playing turns, from first to play to last to play
     */
    private String determinePlayersTurn(ArrayList<Player> p){
        if (p.isEmpty())  return ""; // no player in the arraylist
        if (p.size() == 1)  return String.valueOf(p.get(0).getPlayerID()); // only one player in the arraylist
        if (p.get(0).getDiceThrow() != 0){ // if not the very first roll, display message saying there was a tie
            System.out.print("A tie was achieved between the following players:  ");
            for (int i=0; i<p.size(); i++){
                System.out.print("Player " + p.get(i).getPlayerID());
                if (i != p.size()-1){
                    System.out.print(", ");
                }else {
                    System.out.print(". ");
                }
            }
            System.out.print("Attempting to break the tie.");
            System.out.println();
        }
        // each player flip the dice
        for (Player player: p){
            int diceVal = flipDice();
            player.setDiceThrow(diceVal);
            System.out.println(player + " rolled a dice value of " + diceVal);
        }
        // create a hashmap entry for each dice number
        HashMap<Integer, ArrayList<Player>> diceRolls = new HashMap<>();
        for (int i=1; i<7; i++){
            diceRolls.put(i, new ArrayList<>());
        }
        // put player in hashmap at the appropriate dice roll number key
        for (Player player : p) {
            diceRolls.get(player.getDiceThrow()).add(player);
        }
        return determinePlayersTurn(diceRolls.get(6)) + determinePlayersTurn(diceRolls.get(5)) + determinePlayersTurn(diceRolls.get(4)) + determinePlayersTurn(diceRolls.get(3)) + determinePlayersTurn(diceRolls.get(2)) + determinePlayersTurn(diceRolls.get(1));
    }

    /**
     * Given a player, check if this player reached position 100 and won the game
     * @param p a player
     * @return true if this player reached exactly the position 100, false otherwise
     */
    private boolean checkWinner(Player p){
        return p.getPosition() == 100;
    }

    /**
     * Play a turn of the game. Ends turn when any of the players reaches position 100 and wins.
     * @return return true if no player won, return false if any player won
     */
    private boolean playTurn(){
        for (int i=0; i<players.length; i++){ // each player flips the dice and move the player
            int diceVal = flipDice();
            players[i].setDiceThrow(diceVal);
            System.out.println(players[i] + " rolled a " + diceVal + ".");
            board.movePlayer(players[i], diceVal); // move player to new position based on dice roll
            System.out.println(players[i] + " moved from position " + players[i].getPreviousPosition() + " to position " + players[i].getPosition() + ".\nHere is the new state of the board: ");
            board.displayBoard();
            if (checkWinner(players[i])){ // if this player won
                System.out.println(players[i] + " reached position 100 and won the game!");
                return false;
            }
        }
        System.out.println("No player won this round. Continuing the game.");
        return true;
    }
}
