package LadderAndSnakeGame;

public class Player {
    private int playTurn; // The player's turn. Ranges from 1 to total number of players(inclusive). For example, if playTurn = 3, this player would be the third to roll the dice. The player turn also indicates the player number. For example, the player whose playTurn = 3 would be player #3.
    private int position; // player's current position on the board. From 1 to 100 inclusive. If 0, not on board yet.
    private int previousPosition; // player's previous position on the board. From 1 to 100 inclusive. If 0, not on board yet.

    // Constructor
    public Player(){
        playTurn = -1;
        position = 0;
        previousPosition = 0;
    }

    // Getters
    public int getPlayTurn() {
        return playTurn;
    }

    public int getPosition() {
        return position;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    // Setter
    public void setPlayTurn(int playTurn) {
        this.playTurn = playTurn;
    }

    /**
     * Given a new position on the board, move the player to the new position
     * @param nextPosition the next position on the game board to which the player will move to
     */
    public void moveTo(int nextPosition){
        previousPosition = position;
        position = nextPosition;
    }
}
