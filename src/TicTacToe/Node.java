package TicTacToe;
import java.util.Vector;

public class Node {
	
	String[][] board = new String[3][3];
	String nextPlayer;
	Node parent;
	int heuristicValue;
	int atDepth;
	AlphaBetaPruning t = new AlphaBetaPruning();
	Board b = new Board();

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
	
	//Check leaf node
	public boolean isLeafNode(Node currentNode) {
		WinCondition w = new WinCondition();
		return w.checkWin(currentNode) || (b.scanEmptySquareOnBoard(currentNode)==null);
	}
	
	/*
	 * Get max/min.
	 */
	public int getMaxTwoIntegers(int anInteger, int anotherInteger) {
		if(anInteger < anotherInteger) return anotherInteger;
		else return anInteger;
	}
	public int getMinTwoIntegers(int anInteger, int anotherInteger) {
		if(anInteger > anotherInteger) return anotherInteger;
		else return anInteger;
	}
	public Node getMinNodeInList(Vector<Node> aVectorNode) {
		Node minNode = aVectorNode.get(0);
		int listSize = aVectorNode.size();
		for(int index = 0; index < listSize; index++) 
			if(minNode.heuristicValue > aVectorNode.get(index).heuristicValue) minNode = aVectorNode.get(index);
		return minNode;
	}
	public Node getMaxNodeInList(Vector<Node> aVectorNode) {
		Node maxNode = aVectorNode.get(0);
		int listSize = aVectorNode.size();
		for(int index = 0; index < listSize; index++) 
			if(maxNode.heuristicValue < aVectorNode.get(index).heuristicValue) maxNode = aVectorNode.get(index);
		return maxNode;
	}
	// Get next move
	public Node possibleNextMoveNodes (Node currentNode) {
		if(currentNode.atDepth == 1) return currentNode;
		else return null;
	}

}