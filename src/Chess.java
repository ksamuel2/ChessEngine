import java.util.Scanner;

/**
 * Created by kharunsamuel on 6/14/16.
 */
public class Chess {
    public static void main(String[] args) {
        Chessboard chess = new Chessboard();
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        SearchTree engine = new SearchTree();
        while(!chess.checkmate()) {
            System.out.println("Current Board Position");
            chess.printBoard();
            System.out.println("Computer Generated Move");
            engine.generateMovePly(chess, 4);
            String move = scanner.nextLine();
            if(move.equals("exit")) return;
            chess.move(move);
            i++;
        }
        chess.printBoard();
        if(chess.whiteToMove()) System.out.println("Black Wins!");
        else System.out.println("White Wins!");
    }
}
