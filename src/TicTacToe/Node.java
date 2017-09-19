package TicTacToe;

public class Node {
	String[][] board = new String[3][3];
	String nextPlayer;
	Node parent;
	int heuristicValue;
	int atDepth;
	
	public Node() {}
	public Node(String[][] board, Node parent, int heuristicValue, int atDepth, String nextPlayer) {
		this.board = board;
		this.nextPlayer = nextPlayer;
		this.parent = parent;
		this.heuristicValue = heuristicValue;
		this.atDepth = atDepth;
	}
	public Node(String[][] board) {
		this(board, null, 0, 0, null);
	}
	public Node initializeNode() {
		return new Node(); 
	}
	public Node initializeNodeWithInput(String[][] board) {
		Node root = new Node();
		root.board = board;
		root.nextPlayer = "X";	//Always initialize machine as X node.
		return root;
	}
	
}
