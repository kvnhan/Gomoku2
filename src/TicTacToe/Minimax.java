package TicTacToe;
import java.util.Vector;

public class Minimax {
	
	AlphaBetaPruning t = new AlphaBetaPruning();
	WinCondition w = new WinCondition();
	Node n = new Node();
	Board b = new Board();

	/*
	 * Minimax
	 */
	Vector<Node> possibleNextMoveNodes = new Vector<Node>();
	
	public int getMinimax(Node currentNode) {
		if(n.isLeafNode(currentNode)==true) return this.miniMaxLeafNode(currentNode);
		else return this.miniMaxNonLeafNode(currentNode);
	}
	public int miniMaxNonLeafNode(Node currentNode) {
		Vector<Node> allSuccessors = b.getAllSuccessors(currentNode);
		
		for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
			Node aSuccessor = allSuccessors.get(atIndex);
			if(currentNode.nextPlayer == "O") currentNode.heuristicValue = n.getMinTwoIntegers(currentNode.heuristicValue, this.getMinimax(aSuccessor));
			else currentNode.heuristicValue = n.getMaxTwoIntegers(currentNode.heuristicValue, this.getMinimax(aSuccessor));	
		}
		if(n.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
		return currentNode.heuristicValue;
	}
	public int miniMaxLeafNode(Node currentNode){
		if(n.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
		return w.evaluateHeuristicValue(currentNode);
	}

}
