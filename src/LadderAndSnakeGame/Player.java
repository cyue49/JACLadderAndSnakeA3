package LadderAndSnakeGame;

/**
 * This class represents a player of the Ladder and Snake game. A player has:
 * <ul>
 *     <li>An ID set at initialization of the Player object</li>
 *     <li>A playing turn which indicates the player's turn of playing among many players</li>
 *     <li>A position indicating the player's current position</li>
 *     <li>A previous position indicating the player's previous position</li>
 *     <li>A dice throw indicating the most recent dice throw result of the player</li>
 * </ul>
 */
public class Player implements Comparable<Player>{
    private final int playerID; // The player's ID
    private int playTurn; // The player's turn. Ranges from 1 to total number of players(inclusive). For example, if playTurn = 3, this player would be the third to roll the dice.
    private int position; // player's current position on the board. From 1 to 100 inclusive. If 0, not on board yet.
    private int previousPosition; // player's previous position on the board. From 1 to 100 inclusive. If 0, not on board yet.
    private int diceThrow; // the most recent dice throw result of the player. 0 if haven't made any throw yet

    // Constructor
    public Player(int id){
        playerID = id;
        playTurn = -1;
        position = 0;
        previousPosition = 0;
        diceThrow = 0;
    }

    // Getters
    public int getPlayerID() {
        return playerID;
    }

    public int getPosition() {
        return position;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    public int getDiceThrow() {
        return diceThrow;
    }

    // Setters
    public void setPlayTurn(int playTurn) {
        this.playTurn = playTurn;
    }

    public void setDiceThrow(int diceThrow) {
        this.diceThrow = diceThrow;
    }

    // printing the player
    @Override
    public String toString() {
        return "Player " + playerID;
    }

    /**
     * Given a new position on the board, move the player to the new position
     * @param nextPosition the next position on the game board to which the player will move to
     */
    public void moveTo(int nextPosition){
        previousPosition = position;
        position = nextPosition;
    }

    // compare players by their playing turn
    @Override
    public int compareTo(Player p) {
        return Integer.compare(playTurn, p.playTurn);
    }
}
