package LadderAndSnakeGame;

import java.util.Random;

public class LadderAndSnake {
    private int numPlayers; // the number of players
    private Board board; // the game board
    private Player[] players; // the players of the game

    // constructor
    public LadderAndSnake(int numPlayers){
        this.numPlayers = numPlayers;
        board = new Board();
        players = new Player[numPlayers];
        addPlayers();
    }

    /**
     * Initiate the core engine of the game where players start to play.
     */
    public void play(){
        ;
    }

    /**
     * Roll a dice with six sides numbered from 1 to 6
     * @return an integer between 1 and 6 (inclusive) indicating the result of a dice roll
     */
    public int flipDice(){
        Random rd = new Random();
        return rd.nextInt(6)+1;
    }

    /**
     * Add players to the game according to the number of players
     */
    public void addPlayers(){
        for (int i=0; i<numPlayers; i++){
            players[i] = new Player();
        }
    }
}
