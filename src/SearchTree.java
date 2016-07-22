/**
 * Created by kharunsamuel on 6/30/16.
 */
import java.util.ArrayList;
public class SearchTree {
    private class TreeNode {
        public TreeNode(Data d) {
            data = new Data(d);
            children  = new ArrayList<TreeNode>();
            alpha = -10000000;
            beta = 100000000;
            evaluated = 0;

        }
        public void addChild(TreeNode t) {
            children.add(t);
        }
        public Data getData() {
            return data;
        }
        public ArrayList<TreeNode> getChildren() {
            return children;
        }
        public int alpha;
        public int beta;
        public int evaluated;
        private Data data;
        private ArrayList<TreeNode> children;
    }
    private class Data {
        public Data(Chessboard chessboard) {
               board = new Chessboard(chessboard);
        }
        public Data(Data d) {
            board = new Chessboard(d.getBoard());
        }
        public Chessboard getBoard() {
            return board;
        }
        private Chessboard board;
    }
    public SearchTree() {
        evaluator = new Evaluator();
    }
    public void generateMoveSeconds(Chessboard chessboard, int seconds) {
        /*root = new TreeNode(new Data(chessboard));
        int bestMove;
        long time = System.currentTimeMillis() + 1000*seconds;
        while(System.currentTimeMillis() ) {
            //generateFullTree(ply);
        }*/
    }
    public void generateMovePly(Chessboard chessboard, int ply) {
        root = new TreeNode(new Data(chessboard));
        generateFullTree(root, ply);
        alphaBetaMinimax(root, 100000000, -100000000, ply, 0);
        TreeNode bestMove = root.getChildren().get(bestMoveIndex);
        bestMove.getData().getBoard().printBoard();

    }
    private void generateNextLevel(TreeNode node) {
        Chessboard board = node.getData().getBoard();
        Chessboard origin = new Chessboard(board);
        ArrayList<String> moves = board.getAllAvailableMovesTranslated();
        for(String move : moves) {
            origin.move(move);
            Data d = new Data(origin);
            TreeNode leaf = new TreeNode(d);
            node.addChild(leaf);
            origin.setBoard(board);
        }
    }
    private void generateFullTree(TreeNode curr, int ply) {
        if(ply == 0) return;
        generateNextLevel(curr);
        for(TreeNode t : curr.getChildren()) {
            generateFullTree(t, ply - 1);
        }
    }
    private Coordinate alphaBetaMinimax(TreeNode node, int alpha, int beta, int ply, int index) {
        //System.out.println(evaluator.evaluate(node.getData().getBoard()));
        if(ply == 0) { //We have reached the specified search depth for the function
            return new Coordinate(evaluator.evaluate(node.getData().getBoard()), index);
        }

        if(node.getChildren().size() == 0) { //The node we are searching is a leaf node
            return new Coordinate(evaluator.evaluate(node.getData().getBoard()), index);
        }

        if(node == root) {
            bestMoveIndex = 0;
            if(node.getChildren().size() == 1) {
                return null;
            }
        }
        if(node.getData().getBoard().whiteToMove()) { //Maximizing Player to Move
            int indexVal = 0;
            for(TreeNode child : node.getChildren()) {
                Coordinate result = alphaBetaMinimax(child, alpha, beta, ply - 1, indexVal);
                int positionVal = result.getX();
                if(positionVal > alpha) {
                    node.alpha = positionVal;
                    alpha = positionVal;
                    if(node == root) {
                        bestMoveIndex = result.getY();
                    }
                }
                if(alpha >= beta) return new Coordinate(alpha, result.getY());
            }
            return new Coordinate(alpha, index);
        }
        else { //Minimizing Player to Move
            int indexVal = 0;
            for(TreeNode child : node.getChildren()) {
                Coordinate result = alphaBetaMinimax(child, alpha, beta, ply - 1, indexVal);
                int positionVal = result.getX();
                if(positionVal < beta) {
                    node.beta = positionVal;
                    beta = positionVal;
                    if(node == root) {
                        bestMoveIndex = result.getY();
                    }
                }
                if(alpha >= beta) return new Coordinate(beta, result.getY());
            }
            return new Coordinate(beta, index);
        }
    }
    private TreeNode root;
    Evaluator evaluator;
    int bestMoveIndex;

}
