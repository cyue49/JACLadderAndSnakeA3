package LadderAndSnakeGame;

public class Test {
    public static void main(String[] args) {
        Board b = new Board();
        b.displayBoard();

        Player p = new Player();
        p.setPlayTurn(1);
        p.moveTo(3);

        Player p2 = new Player();
        p2.setPlayTurn(2);
        p2.moveTo(3);

        b.updateBoard(p);
        b.updateBoard(p2);
        b.displayBoard();

        p.moveTo(7);
        p2.moveTo(10);
        b.updateBoard(p);
        b.updateBoard(p2);

        b.displayBoard();

        b.movePlayer(p,9); // should move to 31
        b.movePlayer(p2,16); // should move to 6
        b.displayBoard();
    }
}
