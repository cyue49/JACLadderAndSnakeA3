package LadderAndSnakeGame;

/**
 * Represents a player of the Ladder and Snake game. A player has:
 * <ul>
 *     <li>An ID set at initialization of the Player object</li>
 *     <li>A playing turn which indicates the player's turn of playing among many players</li>
 *     <li>A position indicating the player's current position</li>
 *     <li>A previous position indicating the player's previous position</li>
 *     <li>A dice throw indicating the most recent dice throw result of the player</li>
 * </ul>
 */
public class Player implements Comparable<Player>{
    /** The player's ID */
    private final int playerID;
    /** The player's playing turn. Ranges from 1 to total number of players(inclusive). For example, if playTurn = 3, this player would be the third to roll the dice. */
    private int playTurn;
    /** The player's current position on the board. From 1 to 100 inclusive. If 0, player is not on board yet */
    private int position;
    /** The player's previous position on the board. From 1 to 100 inclusive. If 0, player is not on board yet.*/
    private int previousPosition;
    /** The most recent dice throw result of the player. If 0, the player haven't made any throw yet. */
    private int diceThrow;

    /**
     * Initialize a Player object with its ID.
     * @param id the ID of the player
     */
    public Player(int id){
        playerID = id;
        playTurn = -1;
        position = 0;
        previousPosition = 0;
        diceThrow = 0;
    }

    /**
     * Gets the player's ID.
     * @return player's ID as an int
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Gets the player's current position on the game board.
     * @return player's current position on the game board as an int
     */
    public int getPosition() {
        return position;
    }

    /**
     * Gets the player's previous position on the game board.
     * @return player's previous position on the game board as an int
     */
    public int getPreviousPosition() {
        return previousPosition;
    }

    /**
     * Gets the result of the player's most recent dice throw.
     * @return the player's most recent dice throw result as an int
     */
    public int getDiceThrow() {
        return diceThrow;
    }

    /**
     * Set the player's playing turn among other players.
     * @param playTurn the playing turn that has been decided for the player
     */
    public void setPlayTurn(int playTurn) {
        this.playTurn = playTurn;
    }

    /**
     * Set the dice throw value as the result form the most recent dice throw
     * @param diceThrow most recent dice throw result
     */
    public void setDiceThrow(int diceThrow) {
        this.diceThrow = diceThrow;
    }

    /**
     * Returns the String representation of the Player object as Player and the player ID
     * @return String representation of the Player object as Player and the player ID
     */
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

    /**
     * Compare two Player object by their playing turn
     * @param p the Player to be compared
     * @return a value less than 0 if this player's turn is smaller than player p's turn, a value greater than 0 if this player's turn is greater than player p's turn, and 0 if this player's turn is equal to player p's turn.
     */
    @Override
    public int compareTo(Player p) {
        return Integer.compare(playTurn, p.playTurn);
    }
}
