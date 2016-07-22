/**
 * Created by kharunsamuel on 6/22/16.
 */
import java.util.ArrayList;
public class Evaluator {
    public int evaluate(Chessboard chessboard) {
        /*double positionValue = evaluatePosition(chessboard) + evaluateAttackOnKing(chessboard)
                + evaluateMaterial(chessboard) + evaluateCheckmate(chessboard) + evaluateCastling(chessboard)
                + evaluateMobility(chessboard) + evaluateDoubledPawns(chessboard);*/
        double positionValue = evaluatePosition(chessboard) + evaluateMaterial(chessboard);
        return (int) positionValue;
    }
    private double evaluateMaterial(Chessboard chessboard) {
        Piece[][] board = chessboard.getBoard();
        double sumMaterial = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                double pieceVal = 0;
                switch(piece.getPiece()) {
                    case EMPTY:
                        break;
                    case PAWN:
                        pieceVal = 100;
                        break;
                    case KNIGHT:
                        pieceVal = 320;
                        break;
                    case BISHOP:
                        pieceVal = 330;
                        break;
                    case ROOK:
                        pieceVal = 500;
                        break;
                    case QUEEN:
                        pieceVal = 900;
                        break;
                    case KING:
                        pieceVal = 20000;
                        break;
                }
                if(piece.getColor() == 1) pieceVal *= -1;
                sumMaterial += pieceVal;
            }
        }
        return sumMaterial;
    }
    private double evaluateCheckmate(Chessboard chessboard) {
        if(chessboard.checkmate()) {
            if(chessboard.whiteToMove()) return -10000;
            else return 10000;
        }
        return 0;
    }
    private double evaluatePosition(Chessboard chessboard) {
        int[][] pawnPosition = initPawnPosition();
        int[][] knightPosition = initKnightPosition();
        int[][] bishopPosition = initBishopPosition();
        int[][] rookPosition = initRookPosition();
        int[][] queenPosition = initQueenPosition();
        int[][] kingPosition = initKingPosition(chessboard);
        int sumPositionVal = 0;
        for(int color = 2; color >= 1; color--) {
            Piece[][] board = chessboard.getBoard();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Piece piece = board[i][j];
                    if (piece.getColor() == color) {
                        int pieceVal = 0;
                        switch (piece.getPiece()) {
                            case EMPTY:
                                break;
                            case PAWN:
                                pieceVal = pawnPosition[i][j];
                                break;
                            case KNIGHT:
                                pieceVal = knightPosition[i][j];
                                break;
                            case BISHOP:
                                pieceVal = bishopPosition[i][j];
                                break;
                            case ROOK:
                                pieceVal = rookPosition[i][j];
                                break;
                            case QUEEN:
                                pieceVal = queenPosition[i][j];
                                break;
                            case KING:
                                pieceVal = kingPosition[i][j];
                                break;
                        }
                        if(color == BLACK) pieceVal *= (-1);
                        sumPositionVal += pieceVal;
                    }
                }
            }
            chessboard.rotateBoard();
        }
        return (double) sumPositionVal;
    }
    private int[][] initPawnPosition() {
        int[] pawnVal = new int[] {
                0,  0,  0,  0,  0,  0,  0,  0,
                50, 50, 50, 50, 50, 50, 50, 50,
                10, 10, 20, 30, 30, 20, 10, 10,
                5,  5, 10, 25, 25, 10,  5,  5,
                0,  0,  0, 20, 20,  0,  0,  0,
                5, -5,-10,  0,  0,-10, -5,  5,
                5, 10, 10,-20,-20, 10, 10,  5,
                0,  0,  0,  0,  0,  0,  0,  0};
        int[][] pawnPosition = new int[8][8];
        int count = 0;
        for(int j = 7; j >= 0; j--) {
            for(int i = 0; i < 8; i++) {
                pawnPosition[i][j] = pawnVal[count];
                count++;
            }
        }
        return pawnPosition;
    }
    private int[][] initKnightPosition() {
        int[] knightVal = new int[] {
                -50,-40,-30,-30,-30,-30,-40,-50,
                -40,-20,  0,  0,  0,  0,-20,-40,
                -30,  0, 10, 15, 15, 10,  0,-30,
                -30,  5, 15, 20, 20, 15,  5,-30,
                -30,  0, 15, 20, 20, 15,  0,-30,
                -30,  5, 10, 15, 15, 10,  5,-30,
                -40,-20,  0,  5,  5,  0,-20,-40,
                -50,-40,-30,-30,-30,-30,-40,-50};
        int[][] knightPosition = new int[8][8];
        int count = 0;
        for(int j = 7; j >= 0; j--) {
            for(int i = 0; i < 8; i++) {
                knightPosition[i][j] = knightVal[count];
                count++;
            }
        }
        return knightPosition;
    }
    private int[][] initBishopPosition() {
        int[] bishopVal = new int[] {
                -20,-10,-10,-10,-10,-10,-10,-20,
                -10,  0,  0,  0,  0,  0,  0,-10,
                -10,  0,  5, 10, 10,  5,  0,-10,
                -10,  5,  5, 10, 10,  5,  5,-10,
                -10,  0, 10, 10, 10, 10,  0,-10,
                -10, 10, 10, 10, 10, 10, 10,-10,
                -10,  5,  0,  0,  0,  0,  5,-10,
                -20,-10,-10,-10,-10,-10,-10,-20};
        int[][] bishopPosition = new int[8][8];
        int count = 0;
        for(int j = 7; j >= 0; j--) {
            for(int i = 0; i < 8; i++) {
                bishopPosition[i][j] = bishopVal[count];
                count++;
            }
        }
        return bishopPosition;
    }
    private int[][] initQueenPosition() {
        int[] queenVal = new int[] {
                -20,-10,-10, -5, -5,-10,-10,-20,
                -10,  0,  0,  0,  0,  0,  0,-10,
                -10,  0,  5,  5,  5,  5,  0,-10,
                 -5,  0,  5,  5,  5,  5,  0, -5,
                  0,  0,  5,  5,  5,  5,  0, -5,
                -10,  5,  5,  5,  5,  5,  0,-10,
                -10,  0,  5,  0,  0,  0,  0,-10,
                -20,-10,-10, -5, -5,-10,-10,-20};
        int[][] queenPosition = new int[8][8];
        int count = 0;
        for(int j = 7; j >= 0; j--) {
            for(int i = 0; i < 8; i++) {
                queenPosition[i][j] = queenVal[count];
                count++;
            }
        }
        return queenPosition;
    }
    private int[][] initKingPosition(Chessboard board) {
        int[] kingVal;
        boolean endGame = endGame(board);
        if(!endGame) {
            kingVal = new int[]{
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -30, -40, -40, -50, -50, -40, -40, -30,
                    -20, -30, -30, -40, -40, -30, -30, -20,
                    -10, -20, -20, -20, -20, -20, -20, -10,
                    20, 20, 0, 0, 0, 0, 20, 20,
                    20, 30, 10, 0, 0, 10, 30, 20};
        }
        else {
            kingVal = new int[]{
                    -50,-40,-30,-20,-20,-30,-40,-50,
                    -30,-20,-10,  0,  0,-10,-20,-30,
                    -30,-10, 20, 30, 30, 20,-10,-30,
                    -30,-10, 30, 40, 40, 30,-10,-30,
                    -30,-10, 30, 40, 40, 30,-10,-30,
                    -30,-10, 20, 30, 30, 20,-10,-30,
                    -30,-30,  0,  0,  0,  0,-30,-30,
                    -50,-30,-30,-30,-30,-30,-30,-50};

        }
        int[][] kingPosition = new int[8][8];
        int count = 0;
        for(int j = 7; j >= 0; j--) {
            for(int i = 0; i < 8; i++) {
                kingPosition[i][j] = kingVal[count];
                count++;
            }
        }
        return kingPosition;
    }
    private int[][] initRookPosition() {
        int[] rookVal = new int[] {
                0,  0,  0,  0,  0,  0,  0,  0,
                5, 10, 10, 10, 10, 10, 10,  5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                0,  0,  0,  5,  5,  0,  0,  0};
        int[][] rookPosition = new int[8][8];
        int count = 0;
        for(int j = 7; j >= 0; j--) {
            for(int i = 0; i < 8; i++) {
                rookPosition[i][j] = rookVal[count];
                count++;
            }
        }
        return rookPosition;
    }
    private boolean endGame(Chessboard board) {
        boolean endGame = false;
        if(board.findPiece(1, 5).getX() == -1 && board.findPiece(2, 5).getX() == -1) endGame = true;
        if(board.findPiece(1, 5).getX() != -1) {
            if(board.findPiece(1,2).getX() == -1 && board.findPiece(1, 3).getX() == -1 && board.findPiece(1, 4).getX() == -1)
                endGame = true;
        }
        if(board.findPiece(2, 5).getX() != -1) {
            if(board.findPiece(2,2).getX() == -1 && board.findPiece(2, 3).getX() == -1 && board.findPiece(2, 4).getX() == -1)
                endGame = true;
        }
        return endGame;
    }
    private double evaluateAttackOnKing(Chessboard chessboard) {
        double attackOnKing = 0;
        Coordinate blackKing = chessboard.findPiece(BLACK, KING);
        Coordinate whiteKing = chessboard.findPiece(WHITE, KING);
        ArrayList<Coordinate> blackKingSpaces = chessboard.neighboringCoordinates(blackKing);
        ArrayList<Coordinate> whiteKingSpaces = chessboard.neighboringCoordinates(whiteKing);
        Piece[][] board = chessboard.getBoard();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Coordinate curr = new Coordinate(i, j);
                ArrayList<Coordinate> moves = chessboard.getAvailableMovesTranslated(curr);
                for(Coordinate move : moves) {
                    if(board[i][j].getColor() == WHITE) {
                        for(Coordinate c : blackKingSpaces) {
                            if(move.getX() == c.getX() && move.getY() == c.getY())
                                attackOnKing += 6;
                        }
                        if(move.getX() == blackKing.getX() && move.getY() == blackKing.getY())
                            attackOnKing += 12;
                    }
                    if(board[i][j].getColor() == BLACK) {
                        for(Coordinate c : whiteKingSpaces) {
                            if(move.getX() == c.getX() && move.getY() == c.getY())
                                attackOnKing -= 6;
                        }
                        if(move.getX() == whiteKing.getX() && move.getY() == whiteKing.getY())
                            attackOnKing += 12;
                    }
                }
            }
        }
        return attackOnKing;
    }
    private double evaluateCastling(Chessboard chessboard) {
        if(endGame(chessboard) == true) return 0;
        double castleVal = 0;
        if(chessboard.isWhiteCastled()) castleVal += 50;
        if(chessboard.isBlackCastled()) castleVal -= 50;
        return castleVal;
    }
    private double evaluateMobility(Chessboard chessboard) {
        double mobility = 0;
        Piece[][] board = chessboard.getBoard();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Coordinate curr = new Coordinate(i, j);
                if(board[i][j].getColor() == WHITE)
                    mobility += chessboard.getAvailableMovesTranslated(curr).size();
                if(board[i][j].getColor() == BLACK)
                    mobility -= chessboard.getAvailableMovesTranslated(curr).size();

            }
        }
        return mobility * 1.75;
    }
    private double evaluateDoubledPawns(Chessboard chessboard) {
        Piece[][] board = chessboard.getBoard();
        double doubledPawnVal = 0;
        for(int i = 0; i < 8; i++) {
            int whitePawnsInFile = 0;
            int blackPawnsInFile = 0;
            for(int j = 0; j < 8; j++) {
                if(board[i][j].getPiece() == PAWN) {
                    if(board[i][j].getColor() == WHITE) whitePawnsInFile++;
                    if(board[i][j].getColor() == BLACK) blackPawnsInFile++;
                }
            }
            if(whitePawnsInFile == 1) whitePawnsInFile = 0;
            if(blackPawnsInFile == 1) blackPawnsInFile = 0;
            doubledPawnVal -= whitePawnsInFile*13;
            doubledPawnVal += blackPawnsInFile*13;
        }
        return doubledPawnVal;
    }
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
