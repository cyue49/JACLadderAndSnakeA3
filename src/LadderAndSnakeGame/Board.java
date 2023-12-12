package LadderAndSnakeGame;

import java.util.Arrays;
import java.util.HashMap;

public class Board {
    private String[][] gameBoard; // 2D-array representing the game board
    private static final int[][] LADDERS = {{1,38}, {4,14}, {9,31}, {21,42}, {28,84}, {36,44}, {51,67}, {71,91}, {80,100}}; // each array in this 2D-array represents a ladder, with the first number being the starting case of the ladder and the second number being the landing case of the ladder.
    private static final int[][] SNAKES = {{16,6}, {48,30}, {62,19}, {64,60}, {93,68}, {95,24}, {97,76}, {98,78}}; // each array in this 2D-array represents a snake, with the first number being the head case of the ladder and the second number being the tail case of the ladder.
    private HashMap<Integer,Integer> moveCases; // cases on the game board where players will be moved to another case if they land there

    // Constructor
    public Board(){
        gameBoard = new String[10][10]; // initialize a 10x10 sized game board
        initializeBoard();
        setupLaddersAndSnakes();
    }

    /**
     * Given the i-th and j-th indices in a 2D-array, return the position from 1-100 on the 10x10 game board
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
     * Display the current state of the game board.
     * For each position, the number of the board position is first displayed, followed by players at that position inside the "<" and ">" symbols
     */
    public void displayBoard(){
        System.out.println("=====================================================================================================");
        for (int i=0; i<gameBoard.length; i++){
            System.out.println("-----------------------------------------------------------------------------------------------------");
            for (int j=0; j<gameBoard[i].length; j++){
                System.out.printf("|%-3d%s", arrayIndicesToBoardPosition(i,j), gameBoard[i][j]);
            }
            System.out.print("|\n");
        }
        System.out.println("=====================================================================================================");
    }

    /**
     * Given a Player, update the game board with the position of the player.
     * @param p a Player object
     */
    public void updateBoard(Player p){
        int player = p.getPlayerID(); // player id
        int prev = p.getPreviousPosition(); // player's previous position
        int curr = p.getPosition(); // player's new position

        // remove player from previous position
        if (prev >= 1 && prev <= 100){
            int i = boardPositionToArrayIndices(prev)[0];
            int j = boardPositionToArrayIndices(prev)[1];
            gameBoard[i][j] = gameBoard[i][j].substring(0,player) + " " + gameBoard[i][j].substring(player+1);
        }

        // add player to new position
        if (curr >= 1 && curr <= 100){
            int i = boardPositionToArrayIndices(curr)[0];
            int j = boardPositionToArrayIndices(curr)[1];
            gameBoard[i][j] = gameBoard[i][j].substring(0,player) + String.valueOf(player) + gameBoard[i][j].substring(player+1);
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
                System.out.println("Lucky! " + p + " found a ladder at position " + newPosition + "! Climbing to position " + movedPosition);
            }else{ // a snake
                System.out.println("Oh no! " + p + " found encountered a snake at position " + newPosition + "! Running back to position " + movedPosition);
            }
            p.moveTo(movedPosition);
        }else{
            p.moveTo(newPosition);
        }
        updateBoard(p);
    }
}
