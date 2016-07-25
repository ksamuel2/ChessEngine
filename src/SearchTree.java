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
    /*public void generateMoveSeconds(Chessboard chessboard, int seconds) {
        root = new TreeNode(new Data(chessboard));
        int bestMove;
        long time = System.currentTimeMillis() + 1000*seconds;
        while(System.currentTimeMillis() ) {
            //generateFullTree(ply);
        }
    }*/
    public Chessboard generateMovePly(Chessboard chessboard, int ply) {
        root = new TreeNode(new Data(chessboard));
        generateFullTree(root, ply);
        bestMoveIndex = 0;
        alphabeta(root, 100000000, -100000000, ply, 0);
        TreeNode bestMove = root.getChildren().get(bestMoveIndex);
        //bestMove.getData().getBoard().printBoard();
        return bestMove.getData().getBoard();

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
    public void testTree(TreeNode root) {
        System.out.println("Root");
        //root = root.getChildren().get(8).getChildren().get(7);
        root.getData().getBoard().printBoard();
        int length = root.getChildren().size();
        System.out.println(length + " Children Found");
        for(TreeNode child : root.getChildren()) {
            child.getData().getBoard().printBoard();
            System.out.println(evaluator.evaluate(child.getData().getBoard()));
        }
    }
    public int alphabeta(TreeNode node, int depth, int alpha, int beta, int index) {
        //Reached Search Depth
        if(depth == 0) return evaluator.evaluate(node.getData().getBoard());
        //Leaf Node
        if(node.getChildren().size() == 0) return evaluator.evaluate(node.getData().getBoard());
        //Maximizing Player
        if(node.getData().getBoard().whiteToMove()) {
            int i = 0;
            for(TreeNode child : node.getChildren()) {
                int newalpha = alphabeta(child, depth - 1, alpha, beta, i);
                if(newalpha > alpha) {
                    alpha = newalpha;
                    if(node == root) bestMoveIndex = i;
                }
                if(beta <= alpha) break;
                i++;
            }
            return alpha;
        }
        //Minimizing Player
        else {
            int i = 0;
            for(TreeNode child : node.getChildren()) {
                int newbeta = alphabeta(child, depth - 1, alpha, beta, i);
                if(newbeta < beta) {
                    beta = newbeta;
                    if(node == root) bestMoveIndex = i;
                }
                if(beta <= alpha) break;
                i++;
            }
            return beta;
        }
    }
    private TreeNode root;
    Evaluator evaluator;
    int bestMoveIndex;

}
