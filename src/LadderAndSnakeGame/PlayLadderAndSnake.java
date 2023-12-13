package LadderAndSnakeGame;

import java.util.Scanner;

public class PlayLadderAndSnake {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int numPlayers = 0; // user's input for the number of players
        int attempts = 0; // number of attempts made

        System.out.println("Enter the number of players that will play the game (Note that the number must be between 2 and 4 inclusively): "); // ask user for number of players

        while (attempts < 4){ // while user still has attempts left
            try{
                attempts ++; // add to attempt
                numPlayers = kb.nextInt(); // get user input
                if (!(numPlayers >=2 && numPlayers <=4)){ // if not a number between 2 and 4 inclusively prompt user for new input
                    if (attempts == 4)  break; // if the 4th attempt don't prompt for new input
                    System.out.println("Bad attempt #" + attempts + " - You have entered an invalid input. Please make sure that the number you have entered is between 2 and 4 inclusively: ");
                }else { // if correct user input, break
                    kb.nextLine();
                    break;
                }
            }catch (Exception e){ // if user entered anything other than an integer specify to user to enter an integer and prompt for new input
                kb.nextLine();
                if (attempts == 4)  break; // if the 4th attempt don't prompt for new input
                System.out.println("Bad attempt #" + attempts + " - You have entered an invalid input. Please make sure to enter an integer between 2 and 4 inclusively: ");
            }
        }

        if (attempts == 4){ // if used up all 4 attempts without correct input display message and exit program
            System.out.println("Bad attmept #" + attempts + "! You have exhausted all your chances. Program will terminate!");
        }else { // start the game with the correct number of players
            System.out.println("\nStarting the game ...\n");
            LadderAndSnake game = new LadderAndSnake(numPlayers);
            game.play();
        }
    }
}
