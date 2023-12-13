package LadderAndSnakeGame;

import java.util.*;

/**
 * Represents a Ladder and Snake game. A game has a set number of players initialized at creation of the object, a game board, and an array of players. Calling play will set up the game to automatically play between all players until a winner is found.
 * <br>The game will proceed as follows:
 * <ul>
 *     <li>Display a welcome message.</li>
 *     <li>Decide on the playing turns of all players.</li>
 *     <li>Play the game turn by turn until a winner is found.</li>
 *     <li>Display a closing message.</li>
 * </ul>
 */
public class LadderAndSnake {
    /** The number of players for the game */
    private final int numPlayers;
    /** Board object representing the game board */
    private final Board board;
    /** Array containing all players of the game */
    private Player[] players;

    /**
     * Initialise a LadderAndSnake game with the number of players.
     * @param numPlayers the number of players of the game
     */
    public LadderAndSnake(int numPlayers){
        this.numPlayers = numPlayers;
        board = new Board();
        addPlayers();
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
     * Print a welcome message, decide on players' playing turn, play the game until a player wins, then print a closing message.
     */
    public void play(){
        // welcome message
        System.out.println("============== Welcome to Chen's Ladder & Snake Game! ==============\n");
        System.out.println("This game will be played by " + numPlayers + " players.\n");

        // setting player turns
        System.out.println("Deciding players' playing turns: ");
        String turns = determinePlayersTurn(new ArrayList<>(Arrays.asList(players)));
        for (int i=0; i<turns.length(); i++){
            players[Integer.valueOf(String.valueOf(turns.charAt(i)))-1].setPlayTurn(i);
        }
        Arrays.sort(players);
        System.out.println("The order of playing has been decided as follow: " + Arrays.toString(players).replace("[", "").replace("]", ""));

        // playing the game
        System.out.println("\nPreparing the board ...\n" +
                "Here is the current state of the board before starting the game:");
        board.displayBoard();
        System.out.println("Starting the game!\n");

        while (playTurn()){ // keep playing while no winner
            System.out.println("Here is the current state of the game after this round: ");
            board.displayBoard();
            System.out.println("No player won this round. Continuing the game.\n");
        }

        // a player has won, closing message
        System.out.println("Here is the final state of the game after the last round: ");
        board.displayBoard();
        System.out.println();
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
                System.out.print(p.get(i));
                if (i != p.size()-1){
                    System.out.print(", ");
                }else {
                    System.out.print(". ");
                }
            }
            System.out.print("Attempting to break the tie.\n");
        }
        // each player flip the dice
        for (Player player: p){
            int diceVal = flipDice();
            player.setDiceThrow(diceVal);
            System.out.println("\t> " + player + " rolled a dice value of " + diceVal);
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
     * Play a turn of the game. Ends turn when any of the players reaches position 100 and wins.
     * @return return true if no player won the game and there is a next turn, return false if any player won the game and there won't be a next turn
     */
    private boolean playTurn(){
        for (Player player : players) { // each player flips the dice and move the player
            int diceVal = flipDice();
            player.setDiceThrow(diceVal);
            System.out.println(player + " rolled a " + diceVal + ".");
            board.movePlayer(player, diceVal); // move player to new position based on dice roll
            System.out.println("\t> " + player + " moved from position " + player.getPreviousPosition() + " to position " + player.getPosition() + ".");
            if (player.getPosition() == 100) { // if this player won
                System.out.println("\n+----------------------------------------------------------+\n" +
                        "| !!!! " + player + " reached position 100 and won the game !!!! |\n" +
                        "+----------------------------------------------------------+\n");
                return false;
            }
        }
        return true; // no player won, has next turn
    }
}
