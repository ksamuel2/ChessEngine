/**
 * Created by kharunsamuel on 7/27/16.
 */
public class ChessboardImproved {
    public ChessboardImproved() {
        board = new Piece[8][8];
        int rank = 0;
        int color = WHITE;
        for(int repeat = 0; repeat < 2; repeat++) {
            for (int i = 0; i < 8; i++) {
                if (i == 0 || i == 7)
                    board[i][rank] = new Piece(color, ROOK);
                if (i == 1 || i == 6)
                    board[i][rank] = new Piece(color, KNIGHT);
                if (i == 2 || i == 5)
                    board[i][rank] = new Piece(color, BISHOP);
                if (i == 3)
                    board[i][rank] = new Piece(color, QUEEN);
                if (i == 4)
                    board[i][rank] = new Piece(color, KING);
            }
            color = BLACK;
            rank = 7;
        }
        rank = 1;
        color = WHITE;
        for(int repeat = 0; repeat < 2; repeat++) {
            for(int i = 0; i < 8; i++) {
                board[i][rank] = new Piece(color, PAWN);
            }
            color = BLACK;
            rank = 6;
        }
        for(int i = 2; i <= 5; i++) {
            for(int j = 0; j < 8; j++) {
                board[j][i] = new Piece(EMPTY, EMPTY);
            }
        }
        whiteToMove = true;
        whiteKingMoved = false;
        blackKingMoved = false;
        blackLongRookMoved = false;
        blackShortRookMoved = false;
        whiteLongRookMoved = false;
        whiteShortRookMoved = false;
    }
    public ChessboardImproved(ChessboardImproved chessboard) {
        board = new Piece[8][8];
        Piece[][] otherBoard = chessboard.getBoard();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = new Piece(0, 0);
                board[i][j].setPiece(otherBoard[i][j].getColor(), otherBoard[i][j].getPiece());
            }
        }
        whiteToMove = chessboard.whiteToMove();
        whiteKingMoved = chessboard.isWhiteKingMoved();
        blackKingMoved = chessboard.isBlackKingMoved();
        whiteShortRookMoved = isWhiteShortRookMoved();
        blackShortRookMoved = isBlackShortRookMoved();
        whiteLongRookMoved = isWhiteLongRookMoved();
        blackLongRookMoved = isBlackLongRookMoved();
        whiteCastled = isWhiteCastled();
        blackCastled = isBlackCastled();
    }




    public Piece[][] getBoard() {
        return board;
    }
    public boolean whiteToMove() {
        return whiteToMove;
    }
    private boolean isWhiteKingMoved() {
        return whiteKingMoved;
    }
    private boolean isBlackKingMoved() {
        return blackKingMoved;
    }
    private boolean isWhiteLongRookMoved() {
        return whiteLongRookMoved;
    }
    private boolean isWhiteShortRookMoved() {
        return whiteShortRookMoved;
    }
    private boolean isBlackLongRookMoved() {
        return blackLongRookMoved;
    }
    private boolean isBlackShortRookMoved() {
        return blackShortRookMoved;
    }
    public boolean isWhiteCastled() {
        return whiteCastled;
    }
    public boolean isBlackCastled() {
        return blackCastled;
    }
    private boolean whiteToMove;
    private boolean whiteKingMoved;
    private boolean blackKingMoved;
    private boolean blackLongRookMoved;
    private boolean blackShortRookMoved;
    private boolean whiteLongRookMoved;
    private boolean whiteShortRookMoved;
    private boolean blackCastled;
    private boolean whiteCastled;
    private Piece[][] board;
    private final int EMPTY = 0;
    private final int WHITE = 2;
    private final int BLACK = 1;
    private final int PAWN = 1;
    private final int KNIGHT = 2;
    private final int BISHOP = 3;
    private final int ROOK = 4;
    private final int QUEEN = 5;
    private final int KING = 6;
}
