/**
 * Created by kharunsamuel on 6/11/16.
 */
import java.util.ArrayList;
public class Chessboard {
    public Chessboard() {
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
        rotated = false;
        whiteKingMoved = false;
        blackKingMoved = false;
        blackLongRookMoved = false;
        blackShortRookMoved = false;
        whiteLongRookMoved = false;
        whiteShortRookMoved = false;
    }
    public Chessboard(Chessboard chessboard) {
        board = new Piece[8][8];
        Piece[][] otherBoard = chessboard.getBoard();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = new Piece(0, 0);
                board[i][j].setPiece(otherBoard[i][j].getColor(), otherBoard[i][j].getPiece());
            }
        }
        whiteToMove = chessboard.whiteToMove();
        rotated = chessboard.rotated();
        whiteKingMoved = chessboard.isWhiteKingMoved();
        blackKingMoved = chessboard.isBlackKingMoved();
        whiteShortRookMoved = isWhiteShortRookMoved();
        blackShortRookMoved = isBlackShortRookMoved();
        whiteLongRookMoved = isWhiteLongRookMoved();
        blackLongRookMoved = isBlackLongRookMoved();
        whiteCastled = isWhiteCastled();
        blackCastled = isBlackCastled();
    }
    public void setBoard(Chessboard chessboard) {
        Piece[][] otherBoard = chessboard.getBoard();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j].setPiece(otherBoard[i][j].getColor(), otherBoard[i][j].getPiece());
            }
        }
        whiteToMove = chessboard.whiteToMove();
        rotated = chessboard.rotated();
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
    public void printBoard() {
        System.out.println("    a  b  c  d  e  f  g  h");
        System.out.println("   -- -- -- -- -- -- -- --");
        for(int i = 7; i >= 0; i--) {
            System.out.print(i + 1 + " | ");
            for(int j = 0; j < 8; j++) {
                Piece piece = board[j][i];
                switch(piece.getPiece()) {
                    case 0:
                        System.out.print(" | ");
                        break;
                    case 1:
                        if(piece.getColor() == 1) System.out.print("P| ");
                        else
                            System.out.print("p| ");
                        break;
                    case 2:
                        if(piece.getColor() == 1) System.out.print("N| ");
                        else
                            System.out.print("n| ");
                        break;
                    case 3:
                        if(piece.getColor() == 1) System.out.print("B| ");
                        else
                            System.out.print("b| ");
                        break;
                    case 4:
                        if(piece.getColor() == 1) System.out.print("R| ");
                        else
                            System.out.print("r| ");
                        break;
                    case 5:
                        if(piece.getColor() == 1) System.out.print("Q| ");
                        else
                            System.out.print("q| ");
                        break;
                    case 6:
                        if(piece.getColor() == 1) System.out.print("K| ");
                        else
                            System.out.print("k| ");
                        break;
                }
            }
            System.out.println(i + 1);
            System.out.println("   -- -- -- -- -- -- -- --");
        }
        System.out.println("    a  b  c  d  e  f  g  h");

    }
    private boolean checkOccupied(Coordinate unchecked) {
        if(!checkOnBoard(unchecked)) return false;
        Piece piece = board[unchecked.getX()][unchecked.getY()];
        if(piece.getPiece() != 0) return true;
        return false;
    }
    private boolean checkTakeable(Coordinate unchecked, int color) {
        if(!checkOnBoard(unchecked)) return false;
        Piece piece = board[unchecked.getX()][unchecked.getY()];
        if(piece.getColor() != color && piece.getColor() != 0) return true;
        return false;
    }
    private boolean checkOnBoard(Coordinate point) {
        int x = point.getX();
        int y = point.getY();
        if(x < 0 || x > 7 || y < 0 || y > 7) return false;
        return true;
    }
    private ArrayList<Coordinate> checkDiagonals(Coordinate origin) {
        ArrayList<Coordinate> available = new ArrayList<Coordinate>();
        int startX = origin.getX();
        int startY = origin.getY();
        Piece piece = board[startX][startY];
        int color = piece.getColor();
        Coordinate curr = new Coordinate(startX + 1, startY + 1);
        boolean nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) addCheck(available, origin, curr);
            else {
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                nextDiagonal = true;
            }
            startX += 1;
            startY += 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX() + 1;
        startY = origin.getY() - 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) addCheck(available, origin, curr);
            else {
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                nextDiagonal = true;
            }
            startX += 1;
            startY -= 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX() - 1;
        startY = origin.getY() - 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) addCheck(available, origin, curr);
            else {
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                nextDiagonal = true;
            }
            startX -= 1;
            startY -= 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX() - 1;
        startY = origin.getY() + 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) addCheck(available, origin, curr);
            else {
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                nextDiagonal = true;
            }
            startX -= 1;
            startY += 1;
            curr.setXY(startX, startY);
        }
        return available;
    }
    private ArrayList<Coordinate> checkDiagonalsWithoutCheck(Coordinate origin) {
        ArrayList<Coordinate> available = new ArrayList<Coordinate>();
        int startX = origin.getX();
        int startY = origin.getY();
        Piece piece = board[startX][startY];
        int color = piece.getColor();
        Coordinate curr = new Coordinate(startX + 1, startY + 1);
        boolean nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) available.add(new Coordinate(curr));
            else {
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                nextDiagonal = true;
            }
            startX += 1;
            startY += 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX() + 1;
        startY = origin.getY() - 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) available.add(new Coordinate(curr));
            else {
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                nextDiagonal = true;
            }
            startX += 1;
            startY -= 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX() - 1;
        startY = origin.getY() - 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) available.add(new Coordinate(curr));
            else {
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                nextDiagonal = true;
            }
            startX -= 1;
            startY -= 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX() - 1;
        startY = origin.getY() + 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) available.add(new Coordinate(curr));
            else {
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                nextDiagonal = true;
            }
            startX -= 1;
            startY += 1;
            curr.setXY(startX, startY);
        }
        return available;
    }
    private ArrayList<Coordinate> checkLaterals(Coordinate origin) {
        ArrayList<Coordinate> available = new ArrayList<Coordinate>();
        int startX = origin.getX();
        int startY = origin.getY();
        Piece piece = board[startX][startY];
        int color = piece.getColor();
        Coordinate curr = new Coordinate(startX + 1, startY);
        boolean nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) addCheck(available, origin, curr);
            else {
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                nextDiagonal = true;
            }
            startX += 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX() - 1;
        startY = origin.getY();
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) addCheck(available, origin, curr);
            else {
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                nextDiagonal = true;
            }
            startX -= 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX();
        startY = origin.getY() - 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) addCheck(available, origin, curr);
            else {
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                nextDiagonal = true;
            }
            startY -= 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX();
        startY = origin.getY() + 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) addCheck(available, origin, curr);
            else {
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                nextDiagonal = true;
            }
            startY += 1;
            curr.setXY(startX, startY);
        }
        return available;
    }
    private ArrayList<Coordinate> checkLateralsWithoutCheck(Coordinate origin) {
        ArrayList<Coordinate> available = new ArrayList<Coordinate>();
        int startX = origin.getX();
        int startY = origin.getY();
        Piece piece = board[startX][startY];
        int color = piece.getColor();
        Coordinate curr = new Coordinate(startX + 1, startY);
        boolean nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) available.add(new Coordinate(curr));
            else {
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                nextDiagonal = true;
            }
            startX += 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX() - 1;
        startY = origin.getY();
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) available.add(new Coordinate(curr));
            else {
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                nextDiagonal = true;
            }
            startX -= 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX();
        startY = origin.getY() - 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) available.add(new Coordinate(curr));
            else {
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                nextDiagonal = true;
            }
            startY -= 1;
            curr.setXY(startX, startY);
        }
        startX = origin.getX();
        startY = origin.getY() + 1;
        curr.setXY(startX, startY);
        nextDiagonal = false;
        while(checkOnBoard(curr) && !nextDiagonal) {
            if(!checkOccupied(curr)) available.add(new Coordinate(curr));
            else {
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                nextDiagonal = true;
            }
            startY += 1;
            curr.setXY(startX, startY);
        }
        return available;
    }
    private ArrayList<Coordinate> getAvailableMoves(Coordinate origin) {
        ArrayList<Coordinate> available = new ArrayList<Coordinate>();
        int startX = origin.getX();
        int startY = origin.getY();
        Piece piece = board[startX][startY];
        int color = piece.getColor();
        if(whiteToMove && color != WHITE) return available;
        if(!whiteToMove && color != BLACK) return available;
        int type = piece.getPiece();
        Coordinate curr = new Coordinate(startX, startY);
        switch(type) {
            case EMPTY:
                break;
            case PAWN:  //TODO Enpassant and Promotion
                curr.setXY(startX, startY + 1);
                if(!checkOccupied(curr)) {
                    addCheck(available, origin, curr);
                }
                if(startY == 1) {
                    if(!checkOccupied(curr)) {
                        curr.setXY(startX, startY + 2);
                        if (!checkOccupied(curr)) addCheck(available, origin, curr);
                    }
                }
                curr.setXY(startX + 1, startY + 1);
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                curr.setXY(startX - 1, startY + 1);
                if(checkTakeable(curr, color)) addCheck(available, origin, curr);
                break;
            case KNIGHT: //Knight
                curr.setXY(startX + 1, startY + 2);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX + 2, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX + 2, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX + 1, startY - 2);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX - 1, startY - 2);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX - 2, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX - 2, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX - 1, startY + 2);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                break;
            case BISHOP: //Bishop
                available = checkDiagonals(origin);
                break;
            case ROOK: //Rook
                available = checkLaterals(origin);
                break;
            case QUEEN: //Queen
                available = checkDiagonals(origin);
                ArrayList<Coordinate> lateral = checkLaterals(origin);
                for(Coordinate point : lateral)
                    available.add(new Coordinate(point));
                 break;
            case KING: //King
                curr.setXY(startX, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX + 1, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX + 1, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX - 1, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX - 1, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX + 1, startY);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                curr.setXY(startX - 1, startY);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) addCheck(available, origin, curr);
                if(whiteToMove) {
                    if(castleShort()) {
                        available.add(new Coordinate(6, 0));
                    }
                    if(castleLong()) {
                        available.add(new Coordinate(2, 0));
                    }
                }
                else {
                    if(castleShort()) {
                        available.add(new Coordinate(1, 0));
                    }
                    if(castleLong()) {
                        available.add(new Coordinate(5, 0));
                    }
                }
                break;
        }
        return available;
    }
    public ArrayList<Coordinate> getAvailableMovesTranslated(Coordinate origin) {
        int color;
        boolean functionColorSwitch = false;
        boolean functionRotated = false;
        if(whiteToMove()) color = WHITE;
        else color = BLACK;
        if(board[origin.getX()][origin.getY()].getColor() != color) {
            functionColorSwitch = true;
            whiteToMove = !whiteToMove;
        }
        if(color == BLACK) {
            functionRotated = true;
            rotateBoard();
            origin.setXY(7 - origin.getX(), 7 - origin.getY());
        }
        ArrayList<Coordinate> available = getAvailableMoves(origin);
        if(functionRotated) {
            rotateBoard();
        }
        if(functionColorSwitch) whiteToMove = !whiteToMove();
        if(color == BLACK) {
            for(Coordinate c : available) {
                c.setXY(7 - c.getX(), 7 - c.getY());
            }
        }
        return available;
    }
    public ArrayList<String> getAllAvailableMovesTranslated() {
        int color = 0;
        if(whiteToMove())
            color = WHITE;
        else color = BLACK;
        ArrayList<String> moves = new ArrayList<String>();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                String curr = "XXXX";
                StringBuilder builder = new StringBuilder(curr);
                char first = (char) (i + 97);
                char second = (char) (j + 49);
                builder.setCharAt(0, first);
                builder.setCharAt(1, second);
                if(color == board[i][j].getColor()) {
                    ArrayList<Coordinate> available = getAvailableMovesTranslated(new Coordinate(i, j));
                    for(Coordinate c : available) {
                        char third = (char) (c.getX() + 97);
                        char fourth = (char) (c.getY() + 49);
                        builder.setCharAt(2, third);
                        builder.setCharAt(3, fourth);
                        String translatedMove = builder.toString();
                        moves.add(translatedMove);
                    }
                }
            }
        }
        return moves;
    }
    private ArrayList<Coordinate> getAvailableMovesWithoutCheck(Coordinate origin) {
        ArrayList<Coordinate> available = new ArrayList<Coordinate>();
        int startX = origin.getX();
        int startY = origin.getY();
        Piece piece = board[startX][startY];
        //piece.printPiece();
        int color = piece.getColor();
        if(whiteToMove && color != WHITE) return available;
        if(!whiteToMove && color != BLACK) return available;
        int type = piece.getPiece();
        Coordinate curr = new Coordinate(startX, startY);
        switch(type) {
            case EMPTY: //Empty Spot
                break;
            case PAWN: //Pawn TODO Enpassant and Promotion
                curr.setXY(startX, startY + 1);
                if(!checkOccupied(curr)) {
                    available.add(new Coordinate(curr));
                }
                if(startY == 1) {
                    if(!checkOccupied(curr)) {
                        curr.setXY(startX, startY + 2);
                        if (!checkOccupied(curr)) available.add(new Coordinate(curr));
                    }
                }
                curr.setXY(startX + 1, startY + 1);
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                curr.setXY(startX - 1, startY + 1);
                if(checkTakeable(curr, color)) available.add(new Coordinate(curr));
                break;
            case KNIGHT: //Knight
                curr.setXY(startX + 1, startY + 2);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX + 2, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX + 2, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX + 1, startY - 2);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX - 1, startY - 2);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX - 2, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX - 2, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX - 1, startY + 2);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                break;
            case BISHOP: //Bishop
                available = checkDiagonalsWithoutCheck(origin);
                break;
            case ROOK: //Rook
                available = checkLateralsWithoutCheck(origin);
                break;
            case QUEEN: //Queen
                available = checkDiagonalsWithoutCheck(origin);
                ArrayList<Coordinate> lateral = checkLateralsWithoutCheck(origin);
                for(Coordinate point : lateral)
                    available.add(new Coordinate(point));
                break;
            case KING: //King
                curr.setXY(startX, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX + 1, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX + 1, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX - 1, startY + 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX - 1, startY - 1);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX + 1, startY);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                curr.setXY(startX - 1, startY);
                if((!checkOccupied(curr) || checkTakeable(curr, color)) && checkOnBoard(curr)) available.add(new Coordinate(curr));
                break;
        }
        return available;
    }
    private ArrayList<Coordinate> getAvailableAttackMovesWithoutCheck(Coordinate origin) {
        int startX = origin.getX();
        int startY = origin.getY();
        Piece piece = board[startX][startY];
        if(piece.getPiece() != 1) {
            return getAvailableMovesWithoutCheck(origin);
        }
        int color = piece.getColor();
        ArrayList<Coordinate> available = new ArrayList<Coordinate>();
        if(whiteToMove && color != 2) return available;
        if(!whiteToMove && color != 1) return available;
        Coordinate curr = new Coordinate(startX, startY);
        curr.setXY(startX + 1, startY + 1);
        if(checkTakeable(curr, color) && checkOnBoard(curr)) available.add(new Coordinate(curr));
        curr.setXY(startX - 1, startY + 1);
        if(checkTakeable(curr, color) && checkOnBoard(curr)) available.add(new Coordinate(curr));
        return available;
    }
    private void addCheck(ArrayList<Coordinate> available, Coordinate startVal, Coordinate endVal) {
        Piece start = board[startVal.getX()][startVal.getY()];
        Piece end = board[endVal.getX()][endVal.getY()];
        int backupEndColor = end.getColor();
        int backupEndPiece = end.getPiece();
        end.setPiece(start.getColor(), start.getPiece());
        start.setPiece(0, 0);
        if (!check()) available.add(new Coordinate(endVal));
        start.setPiece(end.getColor(), end.getPiece());
        end.setPiece(backupEndColor, backupEndPiece);
    }
    private boolean castleShort() {
        boolean canCastle = true;
        boolean functionRotated = false;
        int color;
        if(whiteToMove == false) {
            color = BLACK;
            if(rotated == false) {
                rotateBoard();
                functionRotated = true;
            }
        }
        else color = WHITE;
        while(color == BLACK) {
            if(blackKingMoved || blackShortRookMoved) {
                canCastle = false;
                break;
            }
            if(check()) {
                canCastle = false;
                break;
            }
            Coordinate target = new Coordinate(2, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
                break;
            }
            board[2][0].setPiece(1, 6);
            board[3][0].setPiece(0, 0);
            if(check()) {
                canCastle = false;
                board[3][0].setPiece(1, 6);
                board[2][0].setPiece(0, 0);
                break;
            }
            target.setXY(1, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
                board[3][0].setPiece(1, 6);
                board[2][0].setPiece(0, 0);
                break;
            }
            board[1][0].setPiece(1, 6);
            board[2][0].setPiece(0, 0);
            if(check()) {
                canCastle = false;
            }
            board[1][0].setPiece(0, 0);
            board[3][0].setPiece(1, 6);
            break;
        }
        while(color == WHITE) {
            if(whiteKingMoved || whiteShortRookMoved) {
                canCastle = false;
                break;
            }
            if(check()) {
                canCastle = false;
                break;
            }
            Coordinate target = new Coordinate(5, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
                break;
            }
            board[5][0].setPiece(2, 6);
            board[4][0].setPiece(0, 0);
            if(check()) {
                canCastle = false;
                board[5][0].setPiece(0, 0);
                board[4][0].setPiece(2, 6);
                break;
            }
            target.setXY(6, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
                board[5][0].setPiece(0, 0);
                board[4][0].setPiece(2, 6);
                break;
            }
            board[6][0].setPiece(2, 6);
            board[5][0].setPiece(0, 0);
            if(check()) {
                canCastle = false;
            }
            board[6][0].setPiece(0, 0);
            board[4][0].setPiece(2, 6);
            break;
        }
        if(rotated && functionRotated) {
            rotateBoard();
        }
        return canCastle;
    }
    private boolean castleLong() {
        boolean canCastle = true;
        boolean functionRotated = false;
        int color;
        if(whiteToMove == false) {
            color = BLACK;
            if(rotated == false) {
                rotateBoard();
                functionRotated = true;
            }
        }
        else color = WHITE;
        while(color == BLACK) {
            if(blackKingMoved || blackLongRookMoved) {
                canCastle = false;
                break;
            }
            if(check()) {
                canCastle = false;
                break;
            }
            Coordinate target = new Coordinate(4, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
                break;
            }
            board[4][0].setPiece(1, 6);
            board[3][0].setPiece(0, 0);
            if(check()) {
                canCastle = false;
                board[4][0].setPiece(0, 0);
                board[3][0].setPiece(1, 6);
                break;
            }
            target.setXY(5, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
                board[4][0].setPiece(0, 0);
                board[3][0].setPiece(1, 6);
                break;
            }
            board[5][0].setPiece(1, 6);
            board[4][0].setPiece(0, 0);
            if(check()) {
                canCastle = false;
            }
            target.setXY(6, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
            }
            board[5][0].setPiece(0, 0);
            board[3][0].setPiece(1, 6);
            break;
        }
        while(color == WHITE) {
            if(whiteKingMoved || whiteLongRookMoved) {
                canCastle = false;
                break;
            }
            if(check()) {
                canCastle = false;
                break;
            }
            Coordinate target = new Coordinate(3, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
                break;
            }
            board[3][0].setPiece(2, 6);
            board[4][0].setPiece(0, 0);
            if(check()) {
                canCastle = false;
                board[3][0].setPiece(0, 0);
                board[4][0].setPiece(2, 6);
                break;
            }
            target.setXY(2, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
                board[3][0].setPiece(0, 0);
                board[4][0].setPiece(2, 6);
                break;
            }
            board[2][0].setPiece(2, 6);
            board[3][0].setPiece(0, 0);
            if(check()) {
                canCastle = false;
            }
            target.setXY(1, 0);
            if(checkOccupied(target) == true) {
                canCastle = false;
            }
            board[2][0].setPiece(0, 0);
            board[4][0].setPiece(2, 6);
            break;
        }
        if(rotated && functionRotated) {
            rotateBoard();
        }
        return canCastle;
    }
    public void rotateBoard() {
        rotated = !rotated;
        Coordinate a = new Coordinate(0, 0);
        Coordinate b = new Coordinate(0, 0);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 8; j++) {
                a.setXY(i, j);
                b.setXY(7 - i, 7 - j);
                swap(a, b);
            }
        }
    }
    private void swap(Coordinate a, Coordinate b) {
        int ax = a.getX();
        int ay = a.getY();
        int bx = b.getX();
        int by = b.getY();
        int tempColor = board[ax][ay].getColor();
        int tempPiece = board[ax][ay].getPiece();
        board[ax][ay].setPiece(board[bx][by].getColor(), board[bx][by].getPiece());
        board[bx][by].setPiece(tempColor, tempPiece);
    }
    public int move(String move) {
        Coordinate[] moves = translateMove(move);
        if(moves[0].getX() == -1) {
            System.out.println("Wrong format - eg e2e4");
            return -2;
        }
        if(!whiteToMove) {
            rotateBoard();
        }
        if(searchList(getAvailableMoves(moves[0]), moves[1]) == false) {
            System.out.println("Illegal move - Not in available set");
            if(rotated) rotateBoard();
            return -1;
        }
        if(whiteToMove()) whiteCastled = false;
        else blackCastled = false;
        int color = board[moves[0].getX()][moves[0].getY()].getColor();
        int piece = board[moves[0].getX()][moves[0].getY()].getPiece();
        board[moves[0].getX()][moves[0].getY()].setPiece(0,0);
        board[moves[1].getX()][moves[1].getY()].setPiece(color, piece);
        if(piece == PAWN) {
            if(moves[1].getY() == 7)
                board[moves[1].getX()][moves[1].getY()].setPiece(color, QUEEN);
        }
        if(piece == 6) {
            if(color == 1) blackKingMoved = true;
            if(color == 2) whiteKingMoved = true;
            if(moves[1].getX() - moves[0].getX() == 2 || moves[0].getX() - moves[1].getX() == 2) {
                if(color == 1) {
                    blackCastled = true;
                    if(moves[1].getX() == 1) {
                        board[0][0].setPiece(0, 0);
                        board[2][0].setPiece(1, 4);
                    }
                    if(moves[1].getX() == 5) {
                        board[7][0].setPiece(0, 0);
                        board[4][0].setPiece(1, 4);
                    }
                }
                if(color == 2) {
                    whiteCastled = true;
                    if(moves[1].getX() == 6) {
                        board[7][0].setPiece(0, 0);
                        board[5][0].setPiece(2, 4);
                    }
                    if(moves[1].getX() == 2) {
                        board[0][0].setPiece(0, 0);
                        board[3][0].setPiece(2, 4);
                    }
                }
            }
        }
        if(piece == 4) {
            if(color == 2) {
                if(moves[0].getX() == 0)
                    whiteLongRookMoved = true;
                if(moves[0].getX() == 7)
                    whiteShortRookMoved = true;
            }
            if(color == 1) {
                if(moves[0].getX() == 7)
                    blackLongRookMoved = true;
                if(moves[0].getX() == 0)
                    blackShortRookMoved = true;
            }
        }
        if(!whiteToMove) {
            rotateBoard();
        }
        whiteToMove = !whiteToMove;
        return 0;
    }
    public boolean checkmate() {
        //TODO Stalemate
        int color;
        if(whiteToMove) color = WHITE;
        else color = BLACK;
        if(check()) {
            boolean moveFound = false;
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    if(board[i][j].getColor() == color) {
                        if(getAvailableMoves(new Coordinate(i, j)).size() != 0) moveFound = true;
                    }
                }
            }
            if(moveFound == false) return true;
        }
        return false;
    }
    private Coordinate[] translateMove(String move) {
        Coordinate[] ret = new Coordinate[2];
        if(move.length() != 4) {
            ret[0] = new Coordinate(-1, -1);
            ret[1] = new Coordinate(-1, -1);
            return ret;
        }
        int startX = (int) move.charAt(0) - 97;
        int startY = (int) move.charAt(1) - 49;
        int endX = (int) move.charAt(2) - 97;
        int endY = (int) move.charAt(3) - 49;
        if(!whiteToMove) {
            startX = 7 - startX;
            startY = 7 - startY;
            endX = 7 - endX;
            endY = 7 - endY;
        }
        if(startX < 0 || startY < 0 || endX < 0|| endY < 0 || startX > 7 || startY > 7 || endX > 7|| endY > 7) {
            ret[0] = new Coordinate(-1, -1);
            ret[1] = new Coordinate(-1, -1);
            return ret;
        }
        ret[0] = new Coordinate(startX, startY);
        ret[1] = new Coordinate(endX, endY);
        return ret;
    }
    private boolean searchList(ArrayList<Coordinate> list, Coordinate value) {
        for(Coordinate curr : list) {
            if(curr.getX() == value.getX() && curr.getY() == value.getY()) return true;
        }
        return false;
    }
    private boolean check() {
        int color;
        boolean functionRotated = false;
        if(!whiteToMove)
            color = BLACK;
        else {
            color = WHITE;
            if(!rotated) {
                rotateBoard();
                functionRotated = true;
            }
        }
        Coordinate kingLocation = findPiece(color, KING);
        int kingX = kingLocation.getX();
        int kingY = kingLocation.getY();

        if(whiteToMove) color = BLACK;
        else color = WHITE;
        whiteToMove = !whiteToMove;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(i == kingX && j == kingY) continue;
                if(board[i][j].getColor() == color) {
                    for (Coordinate c : getAvailableAttackMovesWithoutCheck(new Coordinate(i, j))) {
                        if(c.getX() == kingX && c.getY() == kingY) {
                            if(functionRotated) rotateBoard();
                            whiteToMove = !whiteToMove;
                            return true;
                        }
                    }
                }
            }
        }
        if(functionRotated) rotateBoard();
        whiteToMove = !whiteToMove;
        return false;
    }
    public Coordinate findPiece(int color, int value) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j].getColor() == color && board[i][j].getPiece() == value)
                    return new Coordinate(i, j);
            }
        }
        return new Coordinate(-1, -1);
    }
    public ArrayList<Coordinate> neighboringCoordinates(Coordinate origin) {
        int originX = origin.getX();
        int originY = origin.getY();
        Coordinate curr = new Coordinate(0, 0);
        ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();
        curr.setXY(originX, originY + 1);
        if(checkOnBoard(curr)) neighbors.add(new Coordinate(curr));
        curr.setXY(originX + 1, originY + 1);
        if(checkOnBoard(curr)) neighbors.add(new Coordinate(curr));
        curr.setXY(originX + 1, originY);
        if(checkOnBoard(curr)) neighbors.add(new Coordinate(curr));
        curr.setXY(originX + 1, originY - 1);
        if(checkOnBoard(curr)) neighbors.add(new Coordinate(curr));
        curr.setXY(originX, originY - 1);
        if(checkOnBoard(curr)) neighbors.add(new Coordinate(curr));
        curr.setXY(originX - 1, originY - 1);
        if(checkOnBoard(curr)) neighbors.add(new Coordinate(curr));
        curr.setXY(originX - 1, originY);
        if(checkOnBoard(curr)) neighbors.add(new Coordinate(curr));
        curr.setXY(originX - 1, originY + 1);
        if(checkOnBoard(curr)) neighbors.add(new Coordinate(curr));
        return neighbors;

    }
    public boolean whiteToMove() {
        return whiteToMove;
    }
    private boolean rotated() {
        return rotated;
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
    private Piece[][] board;
    private boolean whiteToMove;
    private boolean whiteKingMoved;
    private boolean blackKingMoved;
    private boolean blackLongRookMoved;
    private boolean blackShortRookMoved;
    private boolean whiteLongRookMoved;
    private boolean whiteShortRookMoved;
    private boolean rotated;
    private boolean blackCastled;
    private boolean whiteCastled;
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
