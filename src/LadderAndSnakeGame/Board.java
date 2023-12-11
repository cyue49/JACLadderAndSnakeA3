package LadderAndSnakeGame;

import java.util.Arrays;

public class Board {
    private String[][] gameBoard; // 2D-array representing the game board
    private static final int[][] LADDERS = {{1,38}, {4,14}, {9,31}, {21,42}, {28,84}, {36,44}, {51,67}, {71,91}, {80,100}};
    private static final int[][] SNAKES = {{16,6}, {48,30}, {62,19}, {64,60}, {93,68}, {95,24}, {97,76}, {98,78}};

    // Constructor
    public Board(){
        gameBoard = new String[10][10]; // initialize a 10x10 sized game board
        initializeBoard();
    }

    /**
     * Given the i-th and j-th indices in a 2D-array, return the position from 1-100 on the 10x10 game board
     * @param   i   the first index of a 2D-array
     * @param   j   the second index of a 2D-array
     * @return  the position on the board as a number from 1-100
     */
    private int arrayIndicesToBoardPosition(int i, int j){
        // TODO: check for cases with invalid indices i and j
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
        // TODO: check for cases with invalid position
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
        int player = p.getPlayTurn(); // player number
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
     * Given an array of Player objects, update the game board with the position of each player.
     * @param players an array of Player objects
     */
    public void updateBoard(Player[] players){
        for (Player p : players){
            updateBoard(p);
        }
    }

    /**
     * Given a Player and a new position, move the Player to the new position
     * @param p a Player
     * @param newPosition the new position to which the player will be moved
     */
    public void movePlayer(Player p, int newPosition){
        p.moveTo(newPosition);
        updateBoard(p);
    }
}
