package LadderAndSnakeGame;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Represents a 10x10 game board of the Ladder and Snake game. The board has predetermined ladders and snakes positions that are constant and can't be changed. The board itself is represented by a 2D-array and is displayed in 2D.
 * <br>The class contains methods to display the board, to update the board based on a player's position, and to move a player on the board according to the player's dice throw.
 * <br>The class also contains helper methods to convert between 2D-array indices and board positions.
 */
public class Board {
    /** 2D-array representing the 10x10 game board */
    private final String[][] gameBoard = new String[10][10];
    /** 2D-arrays representing the ladders on the game board. For each inner array, the first number is the starting position of the ladder and the second number is the landing position of the ladder. */
    private static final int[][] LADDERS = {{1,38}, {4,14}, {9,31}, {21,42}, {28,84}, {36,44}, {51,67}, {71,91}, {80,100}};
    /** 2D-arrays representing the snakes on the game board. For each inner array, the first number is the head position of the snake and the second number is the tail position of the snake. */
    private static final int[][] SNAKES = {{16,6}, {48,30}, {62,19}, {64,60}, {93,68}, {95,24}, {97,76}, {98,78}};
    /** Cases on the game board that will move players landing on it to another case*/
    private HashMap<Integer,Integer> moveCases;

    /**
     * Initialize a Board object and set up the ladders and snakes on the board.
     */
    public Board(){
        initializeBoard();
        setupLaddersAndSnakes();
    }

    /**
     * Initialize the game board with all positions being empty with no player
     */
    private void initializeBoard(){
        for (String[] row : gameBoard) {
            Arrays.fill(row, "<    >");
        }
    }

    /**
     * Set up the ladders and snakes on the game board
     */
    private void setupLaddersAndSnakes(){
        moveCases = new HashMap<>();
        for (int[] ladder : LADDERS){ // put all the ladders as move cases
            moveCases.put(ladder[0], ladder[1]);
        }
        for (int[] snake : SNAKES){ // put all the snakes as move cases
            moveCases.put(snake[0], snake[1]);
        }
    }

    /**
     * Given the i-th and j-th indices in a 2D-array, return the position from 1 to 100 on the 10x10 game board
     * @param   i   the first index of a 2D-array
     * @param   j   the second index of a 2D-array
     * @return  the position on the board as a number from 1-100
     */
    private int arrayIndicesToBoardPosition(int i, int j){
        if (i%2==0){
            return 100-((i*10)+j);
        }else{
            return (100-((i+1)*10))+j+1;
        }
    }

    /**
     * Given the board position number, returns the corresponding array indices on the game board.
     * @param pos the board position number of a player
     * @return an array of size two containing the i-th and j-th indices of the board position number in the game board array
     */
    private int[] boardPositionToArrayIndices(int pos){
        int[] indices = new int[2];
        int firstNum = (pos-1)/10; // the 10-th position in (pos-1)
        int secondNum = (pos-1)%10; // the unit position in (pos-1)
        indices[0] = 9-firstNum;
        if (firstNum%2 == 0){ // for the rows of increasing board position number
            indices[1] = secondNum;
        }else{// for the rows fo decreasing board position number
            indices[1] = 9-secondNum;
        }
        return indices;
    }

    /**
     * Display the current state of the game board.
     * For each position, the number of the board position is first displayed, followed by players at that position
     */
    public void displayBoard(){
        System.out.println("=====================================================================================================");
        for (int i=0; i<gameBoard.length; i++){
            if (i != 0){ // if not very first line, print divider
                System.out.println("+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+");
            }
            for (int j=0; j<gameBoard[i].length; j++){
                System.out.printf("|%-3d%s", arrayIndicesToBoardPosition(i,j), gameBoard[i][j]);
            }
            System.out.print("|\n");
        }
        System.out.println("=====================================================================================================");
    }

    /**
     * Given a Player, update the game board with the current position of the player.
     * @param p a Player object
     */
    private void updateBoard(Player p){
        int player = p.getPlayerID(); // player id
        int prev = p.getPreviousPosition(); // player's previous position
        int curr = p.getPosition(); // player's new position

        // remove player from previous position
        if (prev >= 1 && prev <= 100){
            int i = boardPositionToArrayIndices(prev)[0];
            int j = boardPositionToArrayIndices(prev)[1];
            gameBoard[i][j] = gameBoard[i][j].substring(0,player) + " " + gameBoard[i][j].substring(player+1); // replace the displayed player ID by an empty space
        }

        // add player to new position
        if (curr >= 1 && curr <= 100){
            int i = boardPositionToArrayIndices(curr)[0];
            int j = boardPositionToArrayIndices(curr)[1];
            gameBoard[i][j] = gameBoard[i][j].substring(0,player) + player + gameBoard[i][j].substring(player+1); // replace the empty space by the player ID
        }
    }

    /**
     * Given a Player and a dice roll, find the new position to which the player should be moved to, and move the Player to the new position
     * @param p a Player
     * @param diceRoll the number rolled by the dice row
     */
    public void movePlayer(Player p, int diceRoll){
        int newPosition = p.getPosition() + diceRoll; // new position is player's current position + the dice roll
        if (newPosition > 100)  newPosition = 100-(newPosition%100); // if rolled past 100, go back
        if (moveCases.get(newPosition) != null){ // if new position is the base of a ladder or the head of a snake, move to the end of the ladder or to the tail of the snake
            int movedPosition = moveCases.get(newPosition);
            if (movedPosition > newPosition){ // a ladder
                System.out.println("\t> Lucky! " + p + " found a ladder at position " + newPosition + "! Climbing to position " + movedPosition);
            }else{ // a snake
                System.out.println("\t> Oh no! " + p + " found encountered a snake at position " + newPosition + "! Running back to position " + movedPosition);
            }
            p.moveTo(movedPosition);
        }else{
            p.moveTo(newPosition);
        }
        updateBoard(p);
    }
}
