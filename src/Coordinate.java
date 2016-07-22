/**
 * Created by kharunsamuel on 6/17/16.
 */
public class Coordinate {
    public Coordinate(int xVal, int yVal) {
        x = xVal;
        y = yVal;
    }
    public Coordinate(Coordinate c) {
        x = c.getX();
        y = c.getY();
    }
    public void setXY(int xVal, int yVal) {
        x = xVal;
        y = yVal;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void printCoordinate() {
        System.out.println("X: " + x + " Y: " + y);
    }
    private int x;
    private int y;
}