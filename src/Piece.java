/**
 * Created by kharunsamuel on 6/17/16.
 */
public class Piece{
    /*
    Color:
        No Piece = 0
        Black = 1
        White = 2
    Piece Type:
        No Piece = 0
        Pawn = 1
        Knight = 2
        Bishop = 3
        Rook = 4
        Queen = 5
        King = 6
     */
    public Piece(int colorVal, int pieceVal) {
        color = colorVal;
        piece = pieceVal;
    }
    public void setPiece(int colorVal, int pieceVal) {
        color = colorVal;
        piece = pieceVal;
    }
    public void printPiece() {
        String colorVal;
        if(color == 1) colorVal = "Black";
        else if(color == 2) colorVal = "White";
        else colorVal = "None";
        System.out.println("Piece: " + piece + " Color: " + colorVal);
    }
    public int getColor() {
        return color;
    }
    public int getPiece() {
        return piece;
    }
    private int color;
    private int piece;
}
