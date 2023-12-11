package LadderAndSnakeGame;

public class Test {
    public static void main(String[] args) {
        Board b = new Board();
        b.displayBoard();

        Player p = new Player();
        p.setPlayTurn(3);
        p.moveTo(7);

        Player p2 = new Player();
        p2.setPlayTurn(2);
        p2.moveTo(7);

        b.updateBoard(p);
        b.updateBoard(p2);
        b.displayBoard();

        p.moveTo(10);
        p2.moveTo(14);

        b.updateBoard(p);
        b.updateBoard(p2);
        b.displayBoard();
    }
}
