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
            if(i%2 == 0) {
                chess.printBoard();
                String move = scanner.nextLine();
                if(move.equals("exit")) return;
                if(chess.move(move) != 0) continue;
            }
            else
                chess = new Chessboard(engine.generateMovePly(chess, 3));
            i++;
        }
        chess.printBoard();
        if(chess.whiteToMove()) System.out.println("Black Wins!");
        else System.out.println("White Wins!");
    }
}
